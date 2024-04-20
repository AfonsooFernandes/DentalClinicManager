import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class GerirDonoEmpresa extends JFrame {
    private List<Utilizador> users;
    private Admin admin;

    private JPanel panel1;
    private JComboBox comboBox1;
    private JButton desativarDonoEmpresaButton;
    private JButton desativarEmpresaButton;
    private JButton cancelarButton;

    public GerirDonoEmpresa(List<Utilizador> users, Admin admin) {
        this.users = users;
        this.admin = admin;

        desativarDonoEmpresaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerDonoEmpresa(obterDonoEmpresaComboBox(users), users);
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuAdmin(users, admin);
            }
        });

        desativarEmpresaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DesativarEmpresa(users,admin,obterDonoEmpresaComboBox(users));
            }
        });
        comboBox1.setModel(new DefaultComboBoxModel<>(obterNomesDonoEmpresa(users).toArray(new String[0])));
        setTitle("Menu Gerir Dono Empresa");
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

    public List<String> obterNomesDonoEmpresa(List<Utilizador> users) {
        List<String> nomesDonoEmpresa = new ArrayList<>();
        for (Utilizador user : users) {
            if(user instanceof DonoEmpresa){
                nomesDonoEmpresa.add(user.getNome());
            }
        }
        return nomesDonoEmpresa;
    }

    public DonoEmpresa obterDonoEmpresaComboBox(List<Utilizador> users){
        for (Utilizador user : users) {
            if(user instanceof DonoEmpresa && user.getNome().equals(comboBox1.getSelectedItem())){
                DonoEmpresa donoEmpresa = (DonoEmpresa) user;
                return donoEmpresa;
            }
        }
        return null;
    }

    public void removerDonoEmpresa(DonoEmpresa donoEmpresa, List<Utilizador> users){
        users.remove(donoEmpresa);
        String mensagem = "O dono " + donoEmpresa.getNome() + " foi removido com sucesso.";
        JOptionPane.showMessageDialog(null, mensagem, "Remoção bem-sucedida", JOptionPane.INFORMATION_MESSAGE);

    }

}
