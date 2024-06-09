import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistarCliente extends JFrame{
    public List<Utilizador> users;
    private JPanel panel1;
    private JTextField nome;
    private JTextField nCC;
    private JTextField nif;
    private JTextField email;
    private JPasswordField passwordField1;
    private JTextField contacto;
    private JButton registarButton;
    private JButton cancelarButton;
    private JTextField morada;

    public RegistarCliente(List<Utilizador> users){
        this.users = users;
        registarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarCliente();
                dispose();
                new DentalCareGUI(users);
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new DentalCareGUI(users);
            }
        });
        setTitle("RegistarCliente");
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

    public void adicionarCliente() {
        String numeroCC = nCC.getText();
        String password = new String(passwordField1.getPassword());

        if (nome.getText().isEmpty() || numeroCC.isEmpty() ||nif.getText().isEmpty() || email.getText().isEmpty() || contacto.getText().isEmpty() || morada.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verifica se o número do Cartão de Cidadão já está a ser usado
        for (Utilizador u : users) {
            if (u instanceof Cliente && u.getNumerocc().equals(numeroCC)) {
                JOptionPane.showMessageDialog(this, "Número Cartão de Cidadão já está a ser usado", "Erro de Registo", JOptionPane.ERROR_MESSAGE);
                return; // Sai do método sem adicionar um novo Cliente
            }
        }

        // Se não encontrou um número de Cartão de Cidadão duplicado, adiciona o novo Cliente
        Cliente cliente = new Cliente(nome.getText(), numeroCC, nif.getText(), email.getText(), password , contacto.getText(), morada.getText());
        users.add(cliente);
        JOptionPane.showMessageDialog(this, "Cliente registado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }


}
