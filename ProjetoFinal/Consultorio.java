import java.io.*;
import java.util.*;

public class Consultorio implements Serializable {
    public enum Tipo{
        Clinica_Geral,
        Cirurgia,
        Endodontia,
        Estomatologia,
        Implantodontia,
        Ortodontia,
        Protese_Dentaria,
        Periodontia,
        Odontopediatria,
    }
    private String nome;
    private String morada;
    private String localidade;
    private String telefone;
    private Tipo tipo;
    private List<Funcionario> funcionarios;
    private List<Consulta> consultas;
    private List<Servico> produtosServicos;


    public Consultorio(String nome, String morada, String localidade, String telefone, Tipo tipo) {
        this.nome = nome;
        this.morada = morada;
        this.localidade = localidade;
        this.telefone = telefone;
        this.tipo = tipo;
        this.consultas = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.produtosServicos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public String getMorada() {
        return morada;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }
    public List<Servico> getProdutosServicos() {
        return produtosServicos;
    }

    public void atualizarEstadoConsulta(Consulta consulta, Consulta.Estado novoEstado) {
        consulta.setEstado(novoEstado);
        Cliente cliente = consulta.getCliente();
        MedicoDentista medico = consulta.getFuncionario();

        // Atualizar estado na lista do cliente
        cliente.atualizarEstadoConsulta(consulta, novoEstado);

        // Atualizar estado na lista do médico
        medico.atualizarEstadoConsulta(consulta, novoEstado);
    }

    public void GuardarFicheiro(String ficheiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(this);
            System.out.println("Consultorio guardado com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar o Consultorio em " + ficheiro + ": " + e.getMessage());
        }
    }

    public static Consultorio lerFicheiro(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            Consultorio consultorio = (Consultorio) ois.readObject();
            System.out.println("Consultorio carregado com sucesso de " + caminho);
            return consultorio;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar o Consultorio de " + caminho + ": " + e.getMessage());
            return null;
        }
    }


}