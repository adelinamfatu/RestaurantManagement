package ro.ase.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * clasa ce se ocupa cu conexiunea cu baza de date
 * */
public class DatabaseConnection {
    private Connection connection;

    /**
     * metoda care face conexiunea cu baza de date sqlite
     * @param databasePath - calea catre baza de date
     * */
    public DatabaseConnection(String databasePath) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}