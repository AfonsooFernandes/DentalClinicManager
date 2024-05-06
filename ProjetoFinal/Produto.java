import java.io.*;
public class Produto extends Servico implements Serializable {
    private String referencia;
    private int quantidade;

    public Produto(String referencia, String nome, double preco, int quantidade) {
        super(nome,preco);
        this.referencia = referencia;
        this.quantidade = quantidade;
    }

    public String getReferencia() {
        return referencia;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void guardarFicheiro(String ficheiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(this);
            System.out.println("Produto guardado com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar o Produto em " + ficheiro + ": " + e.getMessage());
        }
    }

    public static Produto lerFicheiro(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            Produto produto = (Produto) ois.readObject();
            System.out.println("Produto lido com sucesso de " + ficheiro);
            return produto;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler o Produto de " + ficheiro + ": " + e.getMessage());
            return null;
        }
    }
}