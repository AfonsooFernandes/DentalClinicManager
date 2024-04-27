import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MostrarConsultasMedicoDentista extends JFrame{
    private List<Utilizador> users;
    private MedicoDentista medicoDentista;
    private JTable table;
    private JPanel panel1;
    private JButton voltarButton;
    private DefaultTableModel tableModel;

    public MostrarConsultasMedicoDentista(List<Utilizador> users, MedicoDentista medicoDentista) {
        this.users = users;
        this.medicoDentista = medicoDentista;

        setTitle("Consultas do Médico Dentista: " + medicoDentista.getNome());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuMedicoDentista(users,medicoDentista);
            }
        });

        add(voltarButton, BorderLayout.SOUTH);

        // Criar o modelo da tabela com colunas
        String[] colunas = {"Tipo", "Consultório", "Cliente","Médico Dentista", "Estado", "Data", "Problemas", "Produtos","Preço", "Pagamento"};
        tableModel = new DefaultTableModel(colunas, 0);
        table = new JTable(tableModel);

        TableColumn estadoColumn = table.getColumn("Estado");
        estadoColumn.setCellRenderer(new MostrarConsultasMedicoDentista.ComboBoxCellRenderer());
        estadoColumn.setCellEditor(new DefaultCellEditor(createComboBox()));

        // Preencher a tabela com dados das consultas
        for (Consulta consulta : medicoDentista.getConsultas()) {
            adicionarConsultaNaTabela(consulta);
        }

        // Adicionar a tabela a um painel de rolagem
        JScrollPane scrollPane = new JScrollPane(table);

        // Adicionar o painel de rolagem ao JFrame
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

    private JComboBox<String> createComboBox() {
        // Criar uma combobox com os possíveis estados
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem(Consulta.Estado.CANCELADA.name());
        comboBox.addItem(Consulta.Estado.CONFIRMADA.name());
        comboBox.addItem(Consulta.Estado.PENDENTE.name());
        comboBox.addItem(Consulta.Estado.EFETUADA.name());
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém a linha selecionada na tabela
                int selectedRow = table.getSelectedRow();

                // Verifica se uma linha foi selecionada
                if (selectedRow != -1) {
                    // Obtém o valor selecionado na combobox
                    String selectedEstado = (String) comboBox.getSelectedItem();

                    // Atualiza o modelo de tabela com o novo estado
                    tableModel.setValueAt(selectedEstado, selectedRow, table.getColumn("Estado").getModelIndex());

                    // Atualiza o estado da consulta correspondente no objeto Consultorio
                    Consulta consulta = medicoDentista.getConsultas().get(selectedRow);
                    consulta.setEstado(Consulta.Estado.valueOf(selectedEstado));

                    if (Consulta.Estado.CANCELADA.name().equals(selectedEstado)) {
                        consulta.setProblemas("O horário tornou-se indisponível");
                    }

                    // Atualiza o estado nas listas do cliente, do médico e do consultório
                    medicoDentista.getConsultorio().atualizarEstadoConsulta(consulta, consulta.getEstado());
                    consulta.getCliente().atualizarEstadoConsulta(consulta,consulta.getEstado());
                    consulta.getConsultorio().atualizarEstadoConsulta(consulta,consulta.getEstado());
                }
            }
        });
        return comboBox;
    }

    // Classe para personalizar a renderização da célula da combobox na tabela
    private class ComboBoxCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Component) {
                return (Component) value;
            } else {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        }
    }

    private void adicionarConsultaNaTabela(Consulta consulta) {
        // Aqui você adiciona uma nova linha à tabela com os dados da consulta
        String tipo = consulta.getTipo().name();
        String consultorio = consulta.getConsultorio().getNome();
        String funcionario = consulta.getFuncionario().getNome();
        String estado = consulta.getEstado().name();
        String data = consulta.getData().toString();
        String nomeCliente = consulta.getCliente().getNome();
        String problemas = consulta.getProblemas() ;
        Double preco = consulta.getPreco();
        String produtos = "";

        for(Servico servico : consulta.getProdutosServicos()){
            produtos.concat("\n" + servico.getNome());
        }
        String pagamento;
        if(consulta.isPaga()){
            pagamento = "Efetuado";
        }
        else{
            pagamento = "Não efetuado";
        }

        Object[] rowData = {tipo, consultorio, nomeCliente, funcionario, estado, data, problemas,produtos,preco,pagamento};
        tableModel.addRow(rowData);
    }

}
