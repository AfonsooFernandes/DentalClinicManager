import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ListarEmpregados extends JFrame{
    private List<Utilizador> users;
    private DonoEmpresa donoEmpresa;
    private Empresa empresa;
    private Consultorio consultorio;
    private JTable table1;
    private JPanel panel1;
    private JButton voltarButton;
    private DefaultTableModel tableModel;

    public ListarEmpregados(List<Utilizador> users, DonoEmpresa donoEmpresa, Empresa empresa, Consultorio consultorio) {
        this.users = users;
        this.donoEmpresa = donoEmpresa;
        this.empresa = empresa;
        this.consultorio = consultorio;
        setTitle("Empregados do Consultório: " + consultorio.getNome());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuGerirEmpresa(users, donoEmpresa);
            }
        });

        add(voltarButton, BorderLayout.SOUTH);

        String[] colunas = {"Nome", "Nº Cartão de Cidadão", "NIF", "Email", "Telefone","Morada", "Função", "Nº Carteira Profissional"};
        tableModel = new DefaultTableModel(colunas, 0);
        table1 = new JTable(tableModel);

        for (Funcionario funcionario : consultorio.getFuncionarios()) {
            adicionarEmpregadoNaTabela(funcionario);
        }

        JScrollPane scrollPane = new JScrollPane(table1);

        add(scrollPane);

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


    private void adicionarEmpregadoNaTabela(Funcionario funcionario){
        if(funcionario instanceof MedicoDentista){
            MedicoDentista medicoDentista = (MedicoDentista) funcionario;
            Object[] rowData = {medicoDentista.getNome(), medicoDentista.getNumerocc(), medicoDentista.getNif(), medicoDentista.getEmail(), medicoDentista.getTelefone(), medicoDentista.getMorada(), "Médico Dentista",medicoDentista.getNumCarteiraProfissional() };
            tableModel.addRow(rowData);
        }
        else{
            Object[] rowData = {funcionario.getNome(), funcionario.getNumerocc(), funcionario.getNif(), funcionario.getEmail(), funcionario.getTelefone(), funcionario.getMorada(), "Funcionário de Secretaria"};
            tableModel.addRow(rowData);
        }

    }
}
