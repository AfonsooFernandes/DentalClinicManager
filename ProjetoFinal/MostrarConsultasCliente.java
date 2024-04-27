import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MostrarConsultasCliente extends JFrame {
    private List<Utilizador> users;
    private Cliente cliente;
    private JPanel panel1;
    private JTable table;
    private JButton voltarButton;
    private DefaultTableModel tableModel;

    public MostrarConsultasCliente(List<Utilizador> users, Cliente cliente) {
        this.users = users;
        this.cliente = cliente;

        setTitle("Consultas do Cliente: " + cliente.getNome());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuCliente(users, cliente);
            }
        });

        add(voltarButton, BorderLayout.SOUTH);

        // Criar o modelo da tabela com colunas
        String[] colunas = {"Tipo", "Consultório", "Médico Dentista", "Estado", "Data", "Problemas", "Produtos", "Preco", "Pagamento"};
        tableModel = new DefaultTableModel(colunas, 0);
        table = new JTable(tableModel);

        // Preencher a tabela com dados das consultas
        for (Consulta consulta : cliente.getConsultas()) {
            adicionarConsultaNaTabela(consulta);
        }

        JScrollPane scrollPane = new JScrollPane(table);

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



    private void adicionarConsultaNaTabela(Consulta consulta) {
        String tipo = consulta.getTipo().name();
        String consultorio = consulta.getConsultorio().getNome();
        String funcionario = consulta.getFuncionario().getNome();
        String estado = consulta.getEstado().name();
        String data = consulta.getData().toString();
        String problemas = consulta.getProblemas() ;
        Double preco = consulta.getPreco();
        String pagamento;
        String produtos = "";

        for(Servico servico : consulta.getProdutosServicos()){
            produtos.concat("\n" + servico.getNome());
        }

        if (consulta.isPaga()) {
            pagamento = "Efetuado";
        }else {
            pagamento = "Não efetuado";
        }
        tableModel.addRow(new Object[]{tipo, consultorio, funcionario, estado, data, problemas, produtos, preco, pagamento});

    }


}


