import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ListarConsultorios extends JFrame {
    private List<Utilizador> users;
    private DonoEmpresa donoEmpresa;
    private Empresa empresa;
    private JTable table1;
    private JPanel panel1;
    private JButton voltarButton;
    private DefaultTableModel tableModel;
    public ListarConsultorios(List<Utilizador> users, DonoEmpresa donoEmpresa, Empresa empresa) {
        this.users = users;
        this.donoEmpresa = donoEmpresa;
        this.empresa = empresa;

        setTitle("Consultórios da empresa: " + empresa.getNome());
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

        String[] colunas = {"Tipo", "Nome", "Morada", "Localidade", "Nº Contacto", "Médicos Dentistas", "Funcionários", "Nº Consultas"};
        tableModel = new DefaultTableModel(colunas, 0);
        table1 = new JTable(tableModel);

        for (Consultorio consultorio : empresa.getConsultorios()) {
            adicionarConsultorioNaTabela(consultorio);
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

    private void adicionarConsultorioNaTabela(Consultorio consultorio) {
        String tipo = consultorio.getTipo().name();
        String nome= consultorio.getNome();
        String morada = consultorio.getMorada();
        String localidade = consultorio.getLocalidade();
        String telefone = consultorio.getTelefone();
        String medicosDentistas = "";
        String funcionarios = "";
        int nConsultas = consultorio.getConsultas().size();

        for(Funcionario f : consultorio.getFuncionarios()){
            if(f instanceof MedicoDentista){
                medicosDentistas = medicosDentistas.concat("\n" + f.getNome());
            }
            else{
                funcionarios = funcionarios.concat("\n" + f.getNome());
            }
        }
        Object[] rowData = {tipo, nome, morada, localidade, telefone, medicosDentistas, funcionarios, nConsultas};
        tableModel.addRow(rowData);
    }
}
