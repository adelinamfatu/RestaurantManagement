package ro.ase.database;

import ro.ase.classes.Product;
import ro.ase.classes.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SelectDatabase {
    private Connection connection;

    public SelectDatabase(Connection connection) {
        this.connection = connection;
    }

    public List<Product> getProductDetails(String categoryName) {
        List<Product> productDetails = new ArrayList<>();

        try {
            String query = "SELECT id, name, description, price, amount FROM Products WHERE type = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                float price = resultSet.getFloat("price");
                int amount = resultSet.getInt("amount");
                productDetails.add(new Product(id, name, description, price, amount));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productDetails;
    }

    public List<Table> getTables() {
        List<Table> tables = new ArrayList<>();

        try {
            String query = "SELECT id, nbSeats, isOccupied FROM Tables";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int tableId = resultSet.getInt("id");
                int numberOfSeats = resultSet.getInt("nbSeats");
                boolean isOccupied = resultSet.getBoolean("isOccupied");
                tables.add(new Table(tableId, numberOfSeats, isOccupied));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }
}
