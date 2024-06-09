import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class RegistarMedicoDentista extends JFrame{
    private List<Utilizador> users;
    private DonoEmpresa donoEmpresa;
    private Empresa empresa;
    private Consultorio consultorio;
    private JTextField nomeTextField;
    private JTextField nCCTextField;
    private JTextField nifTextField;
    private JTextField nCarteiraTextField;
    private JTextField emailTextField;
    private JPasswordField passwordField1;
    private JTextField telefoneTextField;
    private JTextField moradaTextField;
    private JButton registarButton;
    private JButton voltarButton;
    private JPanel panel1;

    public RegistarMedicoDentista(List<Utilizador> users, DonoEmpresa donoEmpresa, Empresa empresa, Consultorio consultorio){
        this.users = users;
        this.donoEmpresa = donoEmpresa;
        this.empresa = empresa;
        this.consultorio = consultorio;
        registarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarMedicoDentista(consultorio);
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGerirEmpregados(users, donoEmpresa, empresa,consultorio);
            }
        });
        setTitle("Registar Médico Dentista");
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

    public void adicionarMedicoDentista(Consultorio consultorio) {
        String password = new String(passwordField1.getPassword());

        if (nomeTextField.getText().isEmpty() || nCCTextField.getText().isEmpty() ||nifTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || telefoneTextField.getText().isEmpty() || moradaTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verifica se o número do Cartão de Cidadão já está a ser
        for (Utilizador u : users) {
            if (u instanceof MedicoDentista && u.getNumerocc().equals(nCCTextField.getText())) {
                JOptionPane.showMessageDialog(this, "Número Cartão de Cidadão já está a ser usado", "Erro de Registo", JOptionPane.ERROR_MESSAGE);
                return; // Sai do método sem adicionar um novo Cliente
            }
        }

        // Se não encontrou um número de Cartão de Cidadão duplicado, adiciona o novo Médico Dentista
        MedicoDentista medicoDentista = new MedicoDentista(nomeTextField.getText(), nCCTextField.getText(), nifTextField.getText(), emailTextField.getText(), password , telefoneTextField.getText(), moradaTextField.getText(),consultorio.getTipo().toString(),consultorio,nCarteiraTextField.getText());
        users.add(medicoDentista);
        consultorio.getFuncionarios().add(medicoDentista);
        JOptionPane.showMessageDialog(this, "Médico Dentista registado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }


}


