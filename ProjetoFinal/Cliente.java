import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Cliente extends Utilizador implements Serializable{
    List<Consulta> consultas;

    public Cliente(String nome, String numerocc, String nif, String email, String password, String telefone, String morada) {
        super(nome, numerocc, nif, email, password, telefone, morada);
        this.consultas = new ArrayList<>();
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void atualizarEstadoConsulta(Consulta consulta, Consulta.Estado novoEstado) {
        // Encontrar a consulta na lista do cliente e atualizar o estado
        for (Consulta c : consultas) {
            if (c.equals(consulta)) {
                c.setEstado(novoEstado);
                break;
            }
        }
    }

    public static void guardarFicheiroCliente(String ficheiro, List<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(clientes);
            System.out.println("Clientes guardados com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar os Clientes em " + ficheiro + ": " + e.getMessage());
        }
    }

    public static List<Cliente> lerFicheiroCliente(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            List<Cliente> clientes = (List<Cliente>) ois.readObject();
            System.out.println("Clientes lidos com sucesso de " + ficheiro);
            return clientes;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler os Clientes de " + ficheiro + ": " + e.getMessage());
            return null;
        }
    }
}