import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MenuMedicoDentista extends JFrame{
    private MedicoDentista medicoDentista;
    private List<Utilizador> users;
    private JButton listarConsultasButton;
    private JPanel panel1;
    private JButton gerirConsultaButton;
    private JButton terminarSessaoButton;

    public MenuMedicoDentista(List<Utilizador> users, MedicoDentista medicoDentista){
        this.medicoDentista = medicoDentista;
        this.users = users;
        listarConsultasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MostrarConsultasMedicoDentista(users,medicoDentista);

            }
        });
        terminarSessaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login(users);
            }
        });
        gerirConsultaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GerirConsulta(users, medicoDentista);

            }
        });
        setTitle("Menu Médico Dentista");
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

}
