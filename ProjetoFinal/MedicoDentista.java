import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDentista extends Funcionario implements Serializable{

    private String  especialidade;
    private String numCarteiraProfissional;
    private List<Consulta> consultas;

    public MedicoDentista(String nome, String numerocc, String nif, String email, String password, String telefone, String morada, String especialidade, Consultorio consultorio, String numCarteiraProfissional) {
        super(nome, numerocc, nif, email, password, telefone, morada,consultorio);
        this.especialidade = especialidade;
        this.numCarteiraProfissional = numCarteiraProfissional;
        this.consultas = new ArrayList<>();
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public String getNumCarteiraProfissional() {
        return numCarteiraProfissional;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void atualizarEstadoConsulta(Consulta consulta, Consulta.Estado novoEstado) {
        // Encontrar a consulta na lista do médico e atualizar o estado
        for (Consulta c : consultas) {
            if (c.equals(consulta)) {
                c.setEstado(novoEstado);
                break;
            }
        }
    }

    public static void guardarFicheiroMedicoDentista(String ficheiro, List<MedicoDentista> medicosDentistas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(medicosDentistas);
            System.out.println("Médicos Dentistas guardados com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar os Médicos Dentistas em " + ficheiro + ": " + e.getMessage());
        }
    }

    public static List<MedicoDentista> lerFicheiroMedicoDentista(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            List<MedicoDentista> medicosDentistas = (List<MedicoDentista>) ois.readObject();
            System.out.println("Médicos Dentistas lidos com sucesso de " + ficheiro);
            return medicosDentistas;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler os Médicos Dentistas de " + ficheiro + ": " + e.getMessage());
            return null;
        }
    }

}