import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MenuGerirEmpregados extends JFrame{
    private List<Utilizador> users;
    private DonoEmpresa donoEmpresa;
    private Empresa empresa;
    private Consultorio consultorio;
    private JButton registarMedicoDentistaButton;
    private JPanel panel1;
    private JButton registarFuncionarioDeSecretariaButton;
    private JButton removerEmpregadoButton;
    private JButton voltarButton;
    public MenuGerirEmpregados(List<Utilizador> users, DonoEmpresa donoEmpresa, Empresa empresa, Consultorio consultorio){
        this.users = users;
        this.donoEmpresa = donoEmpresa;
        this.empresa = empresa;
        this.consultorio = consultorio;
        registarFuncionarioDeSecretariaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistarFuncionario(users, donoEmpresa, empresa,consultorio);
            }
        });
        registarMedicoDentistaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistarMedicoDentista(users, donoEmpresa, empresa, consultorio);
            }
        });
        removerEmpregadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RemoverEmpregado(users, donoEmpresa, empresa, consultorio);
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGerirConsultorio(users,donoEmpresa,empresa);

            }
        });


        setTitle("Menu Gerir Empregados");
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
