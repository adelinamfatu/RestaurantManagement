package ro.ase.database;

import ro.ase.classes.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SelectDatabase {
    private Connection connection;

    public SelectDatabase(Connection connection) {
        this.connection = connection;
    }

    public Vector<String> getProductNames(String categoryName) {
        Vector<String> productNames = new Vector<>();

        try {
            String query = "SELECT name FROM Products WHERE type = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                productNames.add(name);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productNames;
    }

}
