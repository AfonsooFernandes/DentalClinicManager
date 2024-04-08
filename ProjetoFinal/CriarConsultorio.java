import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class CriarConsultorio extends JFrame{
    private Empresa empresa;
    private List<Utilizador> users;
    private DonoEmpresa donoEmpresa;
    private JPanel panel1;
    private JTextField nomeTextField;
    private JTextField moradaTextField;
    private JTextField localidadeTextField;
    private JTextField nContactoTextField;
    private JComboBox comboBoxTipo;
    private JButton registarButton;
    private JButton cancelarButton;

    public CriarConsultorio(List<Utilizador> users, DonoEmpresa donoEmpresa, Empresa empresa){
        this.users = users;
        this.donoEmpresa = donoEmpresa;
        this.empresa =empresa;
        registarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                registarConsultorio(empresa);
                new MenuGerirEmpresa(users,donoEmpresa);
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuDonoEmpresa(users, donoEmpresa);
            }
        });

        String[] opcoes = {" ", "Clinica_Geral", "Cirurgia", "Endodontia", "Estomatologia", "Implantodontia", "Ortodontia", "Protese_Dentaria", "Periodontia", "Odontopediatria"};
        comboBoxTipo.setModel(new DefaultComboBoxModel<>(opcoes));

        setTitle("RegistarConsultório");
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



    public void registarConsultorio(Empresa empresa){
        for(Consultorio c : empresa.getConsultorios()){
            if(c.getNome().equals(nomeTextField.getText())){
                JOptionPane.showMessageDialog(this, "Já existe um consultório com esse nome", "Erro de Registo", JOptionPane.ERROR_MESSAGE);
            }
        }
        Consultorio consultorio = new Consultorio(nomeTextField.getText(), moradaTextField.getText(), localidadeTextField.getText(), nContactoTextField.getText(), Consultorio.Tipo.valueOf(comboBoxTipo.getSelectedItem().toString()));
        empresa.getConsultorios().add(consultorio);
        if (empresa.getMapaEmpresaParaConsultorios().containsKey(empresa)) {
            // Se sim, obtém a lista existente de consultórios
            List<Consultorio> consultorios = empresa.getMapaEmpresaParaConsultorios().get(empresa);

            // Adiciona o novo consultório à lista
            consultorios.add(consultorio);
        } else {
            // Se não, cria uma nova lista e adiciona a empresa e a lista ao mapa
            List<Consultorio> novaLista = new ArrayList<>();
            novaLista.add(consultorio);
            empresa.getMapaEmpresaParaConsultorios().put(empresa, novaLista);
        }
        JOptionPane.showMessageDialog(this, "Consultório criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
