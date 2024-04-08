import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Consulta implements Serializable{
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
    public enum Estado{
        PENDENTE,
        CONFIRMADA,
        CANCELADA,
        EFETUADA,
    }
    private MedicoDentista medicoDentista;
    private Cliente cliente;
    private Consultorio consultorio;
    private Date data;
    private Tipo tipo;
    private double preco;
    private Estado estado;
    private String problemas;
    private List<Servico> produtosServicos;
    private boolean paga;


    public Consulta(MedicoDentista medicoDentista, Cliente cliente, Consultorio consultorio, Date data, Tipo tipo, double preco) {
        this.medicoDentista = medicoDentista;
        this.cliente = cliente;
        this.consultorio = consultorio;
        this.data = data;
        this.tipo = tipo;
        this.estado = Estado.PENDENTE;
        this.problemas = null;
        this.preco = preco;
        this.produtosServicos = new ArrayList<>();
        this.paga = false;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public Date getData() {
        return data;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public MedicoDentista getFuncionario() {
        return medicoDentista;
    }

    public void setFuncionario(MedicoDentista medicoDentista) {
        this.medicoDentista = medicoDentista;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public MedicoDentista getMedicoDentista() {
        return medicoDentista;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getPreco() {
        return preco;
    }

    public String getProblemas() {
        return problemas;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setProblemas(String problemas) {
        this.problemas = problemas;
    }

    public List<Servico> getProdutosServicos() {
        return produtosServicos;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public void GuardarFicheiro(String ficheiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(this);
            System.out.println("Consulta guardada com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar a Consulta em " + ficheiro + ": " + e.getMessage());
        }
    }

    public static Consulta lerFicheiro(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            Consulta consulta = (Consulta) ois.readObject();
            System.out.println("Consulta lida com sucesso de " + ficheiro);
            return consulta;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler a Consulta de " + ficheiro + ": " + e.getMessage());
            return null;
        }
    }

}