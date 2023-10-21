package ro.ase.display;
import ro.ase.classes.Category;
import ro.ase.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(MessageDisplayer.getInstance().getMessage("welcome_message"));

        System.out.println(MessageDisplayer.getInstance().getMessage("product_categories"));
        for (Category category : Category.values()) {
            System.out.print(category.toString() + " ");
        }

        DatabaseConnection dbConnection = new DatabaseConnection("src/ro/ase/database/restaurant.db");
        Connection connection = dbConnection.getConnection();

        dbConnection.closeConnection();
    }
}