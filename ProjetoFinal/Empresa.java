import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Empresa implements Serializable{
    public enum Estado{
        ATIVA,
        INATIVA,
    }
    private DonoEmpresa donoEmpresa;
    private String nome;
    private String morada;
    private String localidade;
    private String telefone;
    private Estado estado;

    private  Map<String, Empresa> mapaLocalidadeParaEmpresas;
    private Map<Empresa, List<Consultorio>> mapaEmpresaParaConsultorios;
    private List<Consultorio> consultorios;

    public Empresa(String nome, String morada, String localidade, String telefone) {
        this.nome = nome;
        this.morada = morada;
        this.localidade = localidade;
        this.telefone = telefone;
        this.consultorios = new ArrayList<>();
        this.mapaEmpresaParaConsultorios = new HashMap<>();
        this.mapaLocalidadeParaEmpresas = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Consultorio> getConsultorios() {
        return consultorios;
    }

    public Map<String, Empresa> getMapaLocalidadeParaEmpresas() {
        return mapaLocalidadeParaEmpresas;
    }

    public Map<Empresa, List<Consultorio>> getMapaEmpresaParaConsultorios() {
        return mapaEmpresaParaConsultorios;
    }

    public DonoEmpresa getDonoEmpresa() {
        return donoEmpresa;
    }

    public void setDonoEmpresa(DonoEmpresa donoEmpresa) {
        this.donoEmpresa = donoEmpresa;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void GuardarFicheiro(String ficheiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(this);
            System.out.println("Empresa guardada com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar a Empresa em " + ficheiro + ": " + e.getMessage());
        }
    }

    public static Empresa lerFicheiro(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            Empresa empresa = (Empresa) ois.readObject();
            System.out.println("Empresa lida com sucesso de " + ficheiro);
            return empresa;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler a Empresa de " + ficheiro + ": " + e.getMessage());
            return null;
        }
    }
}