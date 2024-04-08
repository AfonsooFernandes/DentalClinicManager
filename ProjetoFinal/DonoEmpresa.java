import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonoEmpresa extends Utilizador implements Serializable{
    List<Empresa> empresas;
    private Map<DonoEmpresa, List<Empresa>> mapaDonoParaEmpresas;

    public DonoEmpresa(String nome, String numerocc, String nif, String email, String password, String telefone, String morada) {
        super(nome, numerocc, nif, email, password, telefone, morada);
        this.empresas = new ArrayList<>();
        this.mapaDonoParaEmpresas = new HashMap<>();

    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public Map<DonoEmpresa, List<Empresa>> getMapaDonoParaEmpresas() {
        return mapaDonoParaEmpresas;
    }

    public static void guardarFicheiroDonoEmpresa(String ficheiro, DonoEmpresa donoEmpresa) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(donoEmpresa);
            System.out.println("Dono de Empresa guardado com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar o Dono de Empresa em " + ficheiro + ": " + e.getMessage());
        }
    }

    public static DonoEmpresa lerFicheiroDonoEmpresa(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            DonoEmpresa donoEmpresa = (DonoEmpresa) ois.readObject();
            System.out.println("Dono de Empresa lido com sucesso de " + ficheiro);
            return donoEmpresa;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler o Dono de Empresa de " + ficheiro + ": " + e.getMessage());
            return null;
        }
    }
}