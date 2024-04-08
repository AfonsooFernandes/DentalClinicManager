import java.io.*;
import java.util.List;

public class Funcionario extends Utilizador implements Serializable{
    private Consultorio consultorio;

    public Funcionario(String nome, String numerocc, String nif, String email, String password, String telefone, String morada, Consultorio consultorio) {
        super(nome, numerocc, nif, email, password, telefone, morada);
        this.consultorio = consultorio;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public static void guardarFicheiroFuncionario(String ficheiro, List<Funcionario> funcionarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(funcionarios);
            System.out.println("Funcionários guardados com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar os Funcionários em " + ficheiro + ": " + e.getMessage());
        }
    }

    public static List<Funcionario> lerFicheiroFuncionario(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            List<Funcionario> funcionarios = (List<Funcionario>) ois.readObject();
            System.out.println("Funcionários lidos com sucesso de " + ficheiro);
            return funcionarios;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler os Funcionários de " + ficheiro + ": " + e.getMessage());
            return null;
        }
    }
}