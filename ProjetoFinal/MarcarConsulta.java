import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MarcarConsulta extends JFrame {
    private List<Utilizador> users;
    private Cliente cliente;
    private List<Consultorio> consultorios;
    private List<MedicoDentista> medicosDentistas;
    private JComboBox<String> comboBoxTipo;
    private JComboBox<String> comboBoxConsultorio;
    private JButton marcarButton;
    private JButton cancelarButton;
    private JPanel panel1;
    private JComboBox<String> comboBoxFuncionario;
    private JTextField textFieldData;
    private JComboBox comboBoxHora;

    public MarcarConsulta(Cliente cliente, List<Utilizador> users) {
        this.cliente = cliente;
        this.consultorios = new ArrayList<>();
        this.medicosDentistas = new ArrayList<>();
        this.users = users;

        for(Utilizador u : users){
            if(u instanceof DonoEmpresa){
                List<Empresa> empresas = ((DonoEmpresa) u).getEmpresas();
                for(Empresa e : empresas){
                    for(Consultorio c : e.getConsultorios()){
                        consultorios.add(c);
                    }
                }
            }
        }
        
        marcarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                marcarConsulta(cliente, medicosDentistas);
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuCliente(users,cliente);
            }
        });

        comboBoxTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarComboBoxConsultorio();
            }
        });

        comboBoxConsultorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarComboBoxFuncionarios();
            }
        });

        setTitle("Marcar Consulta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setContentPane(panel1);
        setLocationRelativeTo(null);
        setVisible(true);

        String[] opcoes = {" ", "Clinica_Geral", "Cirurgia", "Endodontia", "Estomatologia", "Implantodontia", "Ortodontia", "Protese_Dentaria", "Periodontia", "Odontopediatria"};
        String[] hora = {" ","9:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
        comboBoxTipo.setModel(new DefaultComboBoxModel<>(opcoes));
        comboBoxConsultorio.setModel(new DefaultComboBoxModel<>(obterNomesConsultorios(consultorios).toArray(new String[0])));
        comboBoxFuncionario.setModel(new DefaultComboBoxModel<>(obterNomesMedicosDentistas(obterConsultorioPorNome(comboBoxConsultorio.getSelectedItem().toString())).toArray(new String[0])));
        comboBoxHora.setModel(new DefaultComboBoxModel<>(hora));


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


    private void atualizarComboBoxConsultorio() {
        String tipoSelecionado = (String) comboBoxTipo.getSelectedItem();
        List<Consultorio> consultoriosFiltrados = filtrarConsultoriosPorTipo(tipoSelecionado);
        comboBoxConsultorio.setModel(new DefaultComboBoxModel<>(obterNomesConsultorios(consultoriosFiltrados).toArray(new String[0])));
    }

    private List<String> obterNomesConsultorios(List<Consultorio> consultorios) {
        List<String> nomesConsultorios = new ArrayList<>();
        for (Consultorio consultorio : consultorios) {
            nomesConsultorios.add(consultorio.getNome());
        }
        return nomesConsultorios;
    }

    private List<Consultorio> filtrarConsultoriosPorTipo(String tipo) {
        List<Consultorio> consultoriosFiltrados = new ArrayList<>();
        for (Consultorio consultorio : consultorios) {
            if (consultorio.getTipo().name().equals(tipo)) {
                consultoriosFiltrados.add(consultorio);
            }
        }
        return consultoriosFiltrados;
    }

    private void atualizarComboBoxFuncionarios() {
        String nomeConsultorioSelecionado = (String) comboBoxConsultorio.getSelectedItem();
        Consultorio consultorioSelecionado = obterConsultorioPorNome(nomeConsultorioSelecionado);
        List<MedicoDentista> medicosDentistasConsultorio = new ArrayList<>();

        if (consultorioSelecionado != null) {
            for(Funcionario funcionario : consultorioSelecionado.getFuncionarios()){
                if(funcionario instanceof MedicoDentista){
                    MedicoDentista medicoDentista = (MedicoDentista) funcionario;
                    medicosDentistasConsultorio.add(medicoDentista);
                }
            }
            comboBoxFuncionario.setModel(new DefaultComboBoxModel<>(obterNomesMedicosDentistas(obterConsultorioPorNome(comboBoxConsultorio.getSelectedItem().toString())).toArray(new String[0])));
        }
    }

    private List<String> obterNomesMedicosDentistas(Consultorio consultorio) {
        List<String> nomesMedicosDentistas = new ArrayList<>();
        for (Funcionario funcionario : consultorio.getFuncionarios()) {
            if(funcionario instanceof MedicoDentista){
                nomesMedicosDentistas.add(funcionario.getNome());
            }
        }
        return nomesMedicosDentistas;
    }

    private Consultorio obterConsultorioPorNome(String nome) {
        for (Consultorio consultorio : consultorios) {
            if (consultorio.getNome().equals(nome)) {
                return consultorio;
            }
        }
        return null;
    }

    private MedicoDentista obterMedicoDentistaPorNome(String nome, Consultorio consultorio) {
        List<MedicoDentista> medicosDentistasConsultorio = new ArrayList<>();
        for(Funcionario funcionario : consultorio.getFuncionarios()){
            if(funcionario instanceof MedicoDentista){
                MedicoDentista medicoDentista = (MedicoDentista) funcionario;
                medicosDentistasConsultorio.add(medicoDentista);
            }
        }
        for (MedicoDentista medicoDentista : medicosDentistasConsultorio) {
            if (medicoDentista.getNome().equals(nome)) {
                return medicoDentista;
            }
        }
        return null;
    }

    public void marcarConsulta(Cliente cliente, List<MedicoDentista> medicosDentistas) {
        String tipoConsultaTexto = (String) comboBoxTipo.getSelectedItem();
        String nomeConsultorio = (String) comboBoxConsultorio.getSelectedItem();
        String nomeMedicoDentista = (String) comboBoxFuncionario.getSelectedItem();
        String dataTexto = textFieldData.getText();
        String horaTexto = (String) comboBoxHora.getSelectedItem();
        double preco = 0;

        // Validação dos campos
        if (tipoConsultaTexto == null || nomeConsultorio == null || nomeMedicoDentista == null || dataTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Converte a data
        Date dataConsulta = new Date();
        try {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            dataConsulta = formatoEntrada.parse(dataTexto);

            // Substitui a hora na data
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataConsulta);

            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
            Date hora = formatoHora.parse(horaTexto);

            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hora.getHours());
            calendar.set(Calendar.MINUTE, hora.getMinutes());

            // Obtenha a nova data com a hora substituída
            dataConsulta = calendar.getTime();

            // Formata e exibe a data
            SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dataFormatada = formatoSaida.format(dataConsulta);
            System.out.println("Data e hora após substituir a hora: " + dataFormatada);

        } catch (ParseException exception) {
            System.err.println("Formato de data ou hora inválido. Use dd/MM/yyyy HH:mm.");
        }
        // Verificar se o dia selecionado é sábado ou domingo
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataConsulta);
        int diaDaSemana = calendar.get(Calendar.DAY_OF_WEEK);

        if (diaDaSemana == Calendar.SATURDAY || diaDaSemana == Calendar.SUNDAY) {
            JOptionPane.showMessageDialog(this, "O consultório está fechado aos sábados e domingos. Selecione outro dia.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Encontrar o consultório correspondente
        Consultorio consultorioSelecionado = obterConsultorioPorNome(nomeConsultorio);
        if (consultorioSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Consultório não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Encontrar o médico dentista correspondente
        MedicoDentista medicoDentistaSelecionado = obterMedicoDentistaPorNome(nomeMedicoDentista, consultorioSelecionado);
        if (medicoDentistaSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Médico Dentista não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (Consulta consultaExistente : medicoDentistaSelecionado.getConsultas()) {
            // Comparar apenas a data e hora (ignorando os segundos)
            if (consultaExistente.getData().compareTo(dataConsulta) == 0) {
                JOptionPane.showMessageDialog(this, "Médico já possui uma consulta marcada para essa data e hora. Selecione outro dia ou hora.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // Converte o tipo de consulta
        Consulta.Tipo tipoConsulta;
        try {
            tipoConsulta = Consulta.Tipo.valueOf(tipoConsultaTexto);
            switch (tipoConsulta) {
                case Clinica_Geral:
                    preco = 50;
                    break;
                case Cirurgia:
                    preco = 300;
                    break;
                case Endodontia:
                    preco = 75;
                    break;
                case Estomatologia:
                    preco = 75;
                    break;
                case Implantodontia:
                    preco = 85;
                    break;
                case Ortodontia:
                    preco = 90;
                    break;
                case Protese_Dentaria:
                    preco = 200;
                    break;
                case Periodontia:
                    preco = 100;
                    break;
                case Odontopediatria:
                    preco = 90;
                    break;
            }
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, "Tipo de consulta inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Criar a instância de Consulta
        Consulta novaConsulta = new Consulta(medicoDentistaSelecionado, cliente, consultorioSelecionado, dataConsulta, tipoConsulta,preco);


        // Adicionar a consulta às listas apropriadas (do cliente, do consultório e do médico)
        cliente.getConsultas().add(novaConsulta);
        consultorioSelecionado.getConsultas().add(novaConsulta);
        medicoDentistaSelecionado.getConsultas().add(novaConsulta);

        // Exibir mensagem de sucesso
        JOptionPane.showMessageDialog(this, "Consulta marcada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
