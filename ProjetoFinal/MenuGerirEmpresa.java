import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuGerirEmpresa extends JFrame{
    private List<Utilizador> users;
    private DonoEmpresa donoEmpresa;
    private JPanel panel1;
    private JComboBox comboBoxEmpresa;
    private JButton desativarEmpresaButton;
    private JButton criarConsultorioButton;
    private JButton gerirConsultorioButton;
    private JButton voltarButton;
    private JButton listarConsultoriosButton;

    public MenuGerirEmpresa(List<Utilizador> users, DonoEmpresa donoEmpresa){
        this.users = users;
        this.donoEmpresa = donoEmpresa;
        desativarEmpresaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                desativarEmpresa(donoEmpresa);
                new MenuGerirEmpresa(users,donoEmpresa);
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuDonoEmpresa(users,donoEmpresa);
            }
        });
        criarConsultorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CriarConsultorio(users,donoEmpresa,obterEmpresaComboBox(donoEmpresa));
            }
        });

        gerirConsultorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGerirConsultorio(users,donoEmpresa, obterEmpresaComboBox(donoEmpresa));
            }
        });
        listarConsultoriosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ListarConsultorios(users, donoEmpresa, obterEmpresaComboBox(donoEmpresa));
            }
        });


        comboBoxEmpresa.setModel(new DefaultComboBoxModel<>(obterNomesEmpresas(donoEmpresa).toArray(new String[0])));

        setTitle("Menu Gerir Empresa");
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

    public List<String> obterNomesEmpresas(DonoEmpresa donoEmpresa) {
        List<String> nomesEmpresas = new ArrayList<>();
        for (Empresa empresa : donoEmpresa.getEmpresas()) {
            nomesEmpresas.add(empresa.getNome());
        }
        return nomesEmpresas;
    }

    public Empresa obterEmpresaComboBox(DonoEmpresa donoEmpresa){
        for(Empresa e : donoEmpresa.getEmpresas()) {
            if (e.getNome().equals(comboBoxEmpresa.getSelectedItem().toString())) {
                return e;
            }
        }
        return null;
    }

    public void desativarEmpresa(DonoEmpresa donoEmpresa) {
        Empresa empresa = obterEmpresaComboBox(donoEmpresa);

        // Remover a empresa da lista de empresas do DonoEmpresa
        donoEmpresa.getEmpresas().remove(empresa);

        // Remover a empresa do mapaDonoParaEmpresas
        List<Empresa> empresasDoDono = donoEmpresa.getMapaDonoParaEmpresas().get(donoEmpresa);
        empresasDoDono.remove(empresa);

        // Remover a empresa do mapaLocalidadeParaEmpresas da empresa
        if (empresa.getMapaLocalidadeParaEmpresas().containsKey(empresa.getLocalidade())) {
            empresa.getMapaLocalidadeParaEmpresas().remove(empresa.getLocalidade());
        }

        // Remover a empresa do mapaEmpresaParaConsultorios do DonoEmpresa
        for (Empresa e : empresasDoDono) {
            if (e.getMapaEmpresaParaConsultorios().containsKey(e)) {
                e.getMapaEmpresaParaConsultorios().get(e).clear(); // Limpar a lista de consultórios
            }
        }

        // Atualizar o mapaDonoParaEmpresas após a remoção da empresa
        donoEmpresa.getMapaDonoParaEmpresas().put(donoEmpresa, empresasDoDono);

        String mensagem = "A empresa " + empresa.getNome() + " foi desativada com sucesso.";
        JOptionPane.showMessageDialog(null, mensagem, "Desativação bem-sucedida", JOptionPane.INFORMATION_MESSAGE);
    }

}
