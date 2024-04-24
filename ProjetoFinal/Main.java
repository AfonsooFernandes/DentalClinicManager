// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Utilizador> users = Utilizador.lerFicheiro("dados.dat");
        Admin a1 = new Admin("Pedro", "1", "1","pedro@gmai.com", "admin", "910844868", "Cardielos");
        users.add(a1);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DentalCareGUI gui = new DentalCareGUI(users);
            }
        });
    }
}