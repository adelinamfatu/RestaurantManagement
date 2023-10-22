package ro.ase.display;
import ro.ase.classes.Category;
import ro.ase.database.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(MessageDisplayer.getInstance().getMessage("welcome_message"));

        System.out.println(MessageDisplayer.getInstance().getMessage("product_categories"));
        for (Category category : Category.values()) {
            System.out.print(category.toString() + " ");
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainPage mainPage = new MainPage();
                mainPage.setVisible(true);
            }
        });

        DatabaseConnection dbConnection = new DatabaseConnection("src/ro/ase/database/restaurant.db");
        Connection connection = dbConnection.getConnection();

        dbConnection.closeConnection();
    }
}