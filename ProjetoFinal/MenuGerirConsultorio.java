import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuGerirConsultorio extends JFrame {
    private List<Utilizador> users;
    private DonoEmpresa donoEmpresa;
    private Empresa empresa;
    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton removerButton;
    private JButton gerirEmpregadosButton;
    private JButton listarEmpregadosButton;
    private JButton voltarButton;
    private JButton listarConsultasButton;

    public MenuGerirConsultorio(List<Utilizador> users, DonoEmpresa donoEmpresa, Empresa empresa){
        this.users = users;
        this.donoEmpresa = donoEmpresa;
        this.empresa = empresa;
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                removerConsultorio(empresa, obterConsultorioComboBox(empresa));
                new MenuGerirConsultorio(users,donoEmpresa,empresa);
            }
        });
        gerirEmpregadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGerirEmpregados(users,donoEmpresa,empresa,obterConsultorioComboBox(empresa));
            }
        });
        listarEmpregadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ListarEmpregados(users,donoEmpresa,empresa,obterConsultorioComboBox(empresa));
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGerirEmpresa(users,donoEmpresa);
            }
        });
        listarConsultasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MostrarConsultasConsultorio(users,donoEmpresa,empresa,obterConsultorioComboBox(empresa));
            }
        });
        comboBox1.setModel(new DefaultComboBoxModel<>(obterNomesConsultorios(empresa).toArray(new String[0])));

        setTitle("Menu Gerir Consultório");
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

    public List<String> obterNomesConsultorios(Empresa empresa) {
        List<String> nomesConsultorios = new ArrayList<>();
        for (Consultorio consultorio : empresa.getConsultorios()) {
            nomesConsultorios.add(consultorio.getNome());
        }
        return nomesConsultorios;
    }
    public Consultorio obterConsultorioComboBox(Empresa empresa){
        for(Consultorio c : empresa.getConsultorios()) {
            if (c.getNome().equals(comboBox1.getSelectedItem().toString())) {
                return c;
            }
        }
        return null;
    }

    public void removerConsultorio(Empresa empresa, Consultorio consultorio){
        if (empresa.getMapaEmpresaParaConsultorios().containsKey(empresa)) {
            // Obtém a lista existente de consultórios
            List<Consultorio> consultorios = empresa.getMapaEmpresaParaConsultorios().get(empresa);

            // Remove o consultório da lista
            consultorios.remove(consultorio);

            if (consultorios.isEmpty()) {
                empresa.getMapaEmpresaParaConsultorios().remove(empresa);
            }
        }
    }
}
