import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utilizador implements Serializable{
    private String nome;
    private String numerocc;
    private String nif;
    private String email;
    private String password;
    private String telefone;
    private String morada;

    public Utilizador(String nome, String numerocc, String nif, String email, String password, String telefone, String morada) {
        this.nome = nome;
        this.numerocc = numerocc;
        this.nif = nif;
        this.email = email;
        this.password = password;
        this.telefone = telefone;
        this.morada = morada;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }

    public String getNumerocc() {
        return numerocc;
    }

    public String getNif() {
        return nif;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getMorada() {
        return morada;
    }

    public String toString() {
        return "Nome: " + nome + "\nEmail: " + email + "\nTelefone: " + telefone;
    }

    public static List<Utilizador> lerFicheiro(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            List<Utilizador> users = (List<Utilizador>) ois.readObject();
            System.out.println("Utilizadores lidos com sucesso de " + ficheiro);
            return users;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler os Utilizadores de " + ficheiro + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public static void GuardarFicheiro(String ficheiro, List<Utilizador> utilizadores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(utilizadores);
            System.out.println("Utilizadores guardados com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar os Utilizadores em " + ficheiro + ": " + e.getMessage());
        }
    }
}
