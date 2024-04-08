import java.io.*;

public class Admin extends Utilizador implements Serializable{
    public Admin(String nome, String numerocc, String nif, String email, String password, String telefone, String morada) {
        super(null, numerocc, null, null, password, null, null);
    }

    public static void guardarFicheiroAdmin(String ficheiro, Admin admin) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(admin);
            System.out.println("Admin guardado com sucesso em " + ficheiro);
        } catch (IOException e) {
            System.out.println("Erro ao guardar o Admin em " + ficheiro + ": " + e.getMessage());
        }
    }
    public static Admin lerFicheiroAdmin(String ficheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            Admin admin = (Admin) ois.readObject();
            System.out.println("Admin lido com sucesso de " + ficheiro);
            return admin;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler o Admin de " + ficheiro + ": " + e.getMessage());
            return null;
        }
    }
}