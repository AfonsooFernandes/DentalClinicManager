import java.io.*;
public class Servico implements Serializable{
    private String nome;
    private double preco;

    public Servico(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void guardarFicheiro(String ficheiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(this);
            System.out.println("Serviço guardado com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar o Serviço em " + ficheiro + ": " + e.getMessage());
        }
    }

    public static Servico lerFicheiro(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            Servico servico = (Servico) ois.readObject();
            System.out.println("Serviço lido com sucesso de " + ficheiro);
            return servico;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler o Serviço de " + ficheiro + ": " + e.getMessage());
            return null;
        }
    }
}