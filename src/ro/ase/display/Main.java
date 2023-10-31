package ro.ase.display;
import ro.ase.classes.Product;
import ro.ase.database.DatabaseConnection;
import ro.ase.database.UpdateDatabase;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        DatabaseConnection dbConnection = new DatabaseConnection("src/ro/ase/database/restaurant.sqlite");
        Connection connection = dbConnection.getConnection();
        UpdateDatabase updateDatabase = new UpdateDatabase(connection);

        try {
            Statement statement = connection.createStatement();

            String createProductTableSQL = "CREATE TABLE IF NOT EXISTS Products (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "description TEXT, " +
                    "price REAL NOT NULL, " +
                    "amount INTEGER NOT NULL," +
                    "type TEXT NOT NULL" +
                    ");";

            String createTableTableSQL = "CREATE TABLE IF NOT EXISTS Tables (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nbSeats INTEGER NOT NULL, " +
                    "isOccupied INTEGER NOT NULL" +
                    ");";

            String createOrderTableSQL = "CREATE TABLE IF NOT EXISTS Orders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tableId INTEGER NOT NULL, " +
                    "totalPrice REAL NOT NULL, " +
                    "orderDate DATETIME NOT NULL" +
                    ");";

            String createOrderItemTableSQL = "CREATE TABLE IF NOT EXISTS OrderItems (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "productId INTEGER NOT NULL, " +
                    "quantity INTEGER NOT NULL" +
                    ");";

            statement.executeUpdate(createProductTableSQL);
            statement.executeUpdate(createTableTableSQL);
            statement.executeUpdate(createOrderTableSQL);
            statement.executeUpdate(createOrderItemTableSQL);
            System.out.println("Database tables created or updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainPage mainPage = new MainPage(connection);
                mainPage.setVisible(true);
                mainPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        if(mainPage.products != null) {
                            updateDatabase.addProducts(mainPage.products);
                        }
                        updateDatabase.updateTables(mainPage.tables);
                        System.out.println("Closing database connection");
                        dbConnection.closeConnection();
                    }
                });
            }
        });
    }
}