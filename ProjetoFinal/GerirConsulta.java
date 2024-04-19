import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GerirConsulta extends JFrame {
    private List<Utilizador> users;
    private MedicoDentista medicoDentista;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton adicionarProdutoButton;
    private JButton terminarConsultaButton;
    private JButton voltarButton;
    private JPanel panel1;

    public GerirConsulta(List<Utilizador> users, MedicoDentista medicoDentista){
        this.users = users;
        this.medicoDentista = medicoDentista;
        adicionarProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarProdutoServico(obterProdutoComboBox(medicoDentista.getConsultorio()),obterConsultaComboBox(medicoDentista),medicoDentista.getConsultorio());
            }
        });
        terminarConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                terminarConsulta(medicoDentista, obterConsultaComboBox(medicoDentista));
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuMedicoDentista(users, medicoDentista);
            }
        });

        comboBox1.setModel(new DefaultComboBoxModel<>(obterHorariosConsultas(medicoDentista).toArray(new String[0])));
        comboBox2.setModel(new DefaultComboBoxModel<>(obterProdutosServicos(medicoDentista.getConsultorio()).toArray(new String[0])));
        setTitle("Gerir Consulta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setContentPane(panel1);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        // Adicione um WindowListener para o evento de fechar a janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Quando a janela é fechada, chamamos o método para fechar a GUI
                closeGUI();
            }
        });
    }

    // Método que é chamado quando a GUI deve ser fechada
    private void closeGUI() {
        // Salve a lista de usuários antes de fechar a GUI
        Utilizador.GuardarFicheiro("dados.dat", users);

        // Feche a GUI
        dispose();
    }


    public List<String> obterHorariosConsultas(MedicoDentista medicoDentista) {
        List<String> horariosConsultas = new ArrayList<>();
        Date dataAtual = new Date();
        for (Consulta consulta : medicoDentista.getConsultas()) {
            if(isMesmoDia(consulta.getData(),dataAtual)){
                horariosConsultas.add(consulta.getData().toString());
            }
        }
        return horariosConsultas;
    }

    public List<String> obterProdutosServicos(Consultorio consultorio) {
        List<String> produtosServicos = new ArrayList<>();
        for (Servico servico : consultorio.getProdutosServicos()) {
           produtosServicos.add(servico.getNome());
        }
        return produtosServicos;
    }

    public Servico obterProdutoComboBox(Consultorio consultorio){
        for(Servico s : consultorio.getProdutosServicos()) {
            if (s.getNome().equals(comboBox2.getSelectedItem().toString())) {
                return s;
            }
        }
        return null;
    }
    public Consulta obterConsultaComboBox(MedicoDentista medicoDentista){
        for(Consulta c : medicoDentista.getConsultas()) {
            if (c.getData().toString().equals(comboBox1.getSelectedItem().toString())) {
                return c;
            }
        }
        return null;
    }
    private static boolean isMesmoDia(Date data1, Date data2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(data1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(data2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }


    public void adicionarProdutoServico(Servico servico, Consulta consulta, Consultorio consultorio){
        consulta.getProdutosServicos().add(servico);
        for(Servico s: consultorio.getProdutosServicos()){
            if(servico.equals(s)){
                if(s instanceof Produto){
                    Produto p = (Produto) s;
                    p.setQuantidade(p.getQuantidade() - 1);
                }
                consulta.setPreco(consulta.getPreco()+s.getPreco());
            }
        }
    }


    public void terminarConsulta(MedicoDentista medicoDentista, Consulta consulta){
        consulta.setEstado(Consulta.Estado.EFETUADA);
        medicoDentista.atualizarEstadoConsulta(consulta,consulta.getEstado());
        consulta.getCliente().atualizarEstadoConsulta(consulta,consulta.getEstado());
        consulta.getConsultorio().atualizarEstadoConsulta(consulta,consulta.getEstado());
    }
}
