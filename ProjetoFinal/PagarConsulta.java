import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class PagarConsulta extends JFrame{
    private List<Utilizador> users;
    private Cliente cliente;
    private JTextField nCartaoTextField;
    private JPanel panel1;
    private JTextField validadeTextField;
    private JPasswordField passwordField1;
    private JTextField nomeTextField;
    private JButton pagarButton;
    private JButton voltarButton;
    private JComboBox comboBox1;

    public PagarConsulta(List<Utilizador> users, Cliente cliente){
        this.users = users;
        this.cliente = cliente;
        pagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pagarConsulta(obterConsultaComboBox(cliente));

            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuCliente(users, cliente);
            }
        });

        comboBox1.setModel(new DefaultComboBoxModel<>(obterDatasConsultas(cliente).toArray(new String[0])));

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


    public List<String> obterDatasConsultas(Cliente cliente) {
        List<String> datasConsultas = new ArrayList<>();
        for (Consulta consulta : cliente.getConsultas()) {
            if(!consulta.isPaga()){
                datasConsultas.add(consulta.getData().toString());
            }
        }
        return datasConsultas;
    }
    public Consulta obterConsultaComboBox(Cliente cliente){
        for(Consulta c : cliente.getConsultas()) {
            if (c.getData().toString().equals(comboBox1.getSelectedItem().toString())) {
                return c;
            }
        }
        return null;
    }
    public void pagarConsulta(Consulta consulta){
        consulta.setPaga(true);
        JOptionPane.showMessageDialog(null, "Pagamento efetuado com sucesso!",
                "Aviso de Pagamento", JOptionPane.INFORMATION_MESSAGE);
    }
}
