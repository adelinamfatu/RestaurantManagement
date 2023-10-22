package ro.ase.display;
import ro.ase.database.DatabaseConnection;
import javax.swing.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        DatabaseConnection dbConnection = new DatabaseConnection("src/ro/ase/database/restaurant.db");
        Connection connection = dbConnection.getConnection();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainPage mainPage = new MainPage();
                mainPage.setVisible(true);
            }
        });

        dbConnection.closeConnection();
    }
}