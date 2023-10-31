package ro.ase.database;

import ro.ase.classes.Product;
import ro.ase.classes.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class UpdateDatabase {
    private Connection connection;

    public UpdateDatabase(Connection connection) {
        this.connection = connection;
    }

    public void addProducts(Set<Product> products) {
        String insertQuery = "INSERT INTO products (name, description, price, amount, type) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Product product : products) {
                if(product.getId() == 0) {
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setString(2, product.getDescription());
                    preparedStatement.setFloat(3, product.getPrice());
                    preparedStatement.setInt(4, product.getAmount());
                    preparedStatement.setString(5, product.getCategory().toString());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTables(List<Table> tables) {
        String updateQuery = "UPDATE tables SET isOccupied = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            for (Table table : tables) {
                preparedStatement.setBoolean(1, table.isOccupied());
                preparedStatement.setInt(2, table.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
