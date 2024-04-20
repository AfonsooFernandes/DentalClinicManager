import jdk.jshell.execution.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Login extends JFrame {
    public List<Utilizador> users;
    private JPanel panel1;
    private JButton loginButton;
    private JButton cancelarButton;
    private JTextField textField1;
    private JPasswordField passwordField1;

    public Login(List<Utilizador> users){
        this.users = users;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin(users);

            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DentalCareGUI(users);
            }
        });
        setTitle("Login");
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



    public void realizarLogin(List<Utilizador> users){
        String nCC = textField1.getText();
        String password = new String(passwordField1.getPassword());

        for(Utilizador u : users){
            if(u.getNumerocc().equals(nCC) && u.getPassword().equals(password)){
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                if(u instanceof Cliente){
                    Cliente cliente = (Cliente) u;
                    dispose();
                    new MenuCliente(users,cliente);
                } else if(u instanceof Funcionario){
                    Funcionario funcionario = (Funcionario) u;
                    dispose();
                    if(funcionario instanceof MedicoDentista){
                        MedicoDentista medicoDentista = (MedicoDentista) u;
                        new MenuMedicoDentista(users, medicoDentista);
                    }
                    else{
                        new MenuFuncionario(users, funcionario);
                    }
                } else if(u instanceof DonoEmpresa){
                    DonoEmpresa donoEmpresa = (DonoEmpresa) u;
                    dispose();
                    new MenuDonoEmpresa(users , donoEmpresa);
                } else if(u instanceof Admin){
                    Admin admin = (Admin) u;
                    dispose();
                    new MenuAdmin(users,admin);
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Número Cartão de Cidadão ou password incorretos", "Erro de Login", JOptionPane.ERROR_MESSAGE);
    }

}

