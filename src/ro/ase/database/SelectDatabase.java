package ro.ase.database;

import ro.ase.classes.Order;
import ro.ase.classes.OrderItem;
import ro.ase.classes.Product;
import ro.ase.classes.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * clasa care se ocupa cu comenzile de tip select din baza de date
 * */
public class SelectDatabase {
    private Connection connection;

    public SelectDatabase(Connection connection) {
        this.connection = connection;
    }

    /**
     * metoda care returneaza o lista de produse dintr-o categorie anume
     * @param categoryName - numele categoriei folosita in interogare
     * */
    public List<Product> getProductDetailsByCategory(String categoryName) {
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

    /**
     * metoda care returneaza informatii despre toate mesele din baza de date
     * */
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

    /**
     * metoda care returneaza toate id-urile si numele produselor din baza de date
     * */
    public Set<Product> getAllProductNamesAndIds() {
        Set<Product> productNamesAndIds = new HashSet<>();
        String selectQuery = "SELECT id, name FROM products";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int productId = resultSet.getInt("id");
                String productName = resultSet.getString("name");
                productNamesAndIds.add(new Product(productId, productName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productNamesAndIds;
    }

    /**
     * metoda care returneaza numarul de comenzi din saptamana curenta
     * */
    public int getWeekOrdersCount() {
        int ordersCount = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            Date weekStartDate = calendar.getTime();

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            Date weekEndDate = calendar.getTime();

            String sql = "SELECT COUNT(*) FROM orders WHERE orderDate >= ? AND orderDate <= ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(weekStartDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(weekEndDate.getTime()));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ordersCount = resultSet.getInt(1);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersCount;
    }

    /**
     * metoda care returneaza numele celui mai vandut produs
     * */
    public String getMostSoldProduct() {
        String mostSoldProduct = null;
        try {
            String sql = "SELECT p.name, SUM(oi.quantity) AS total_quantity " +
                    "FROM Products p " +
                    "INNER JOIN OrderItems oi ON p.id = oi.productId " +
                    "GROUP BY p.name " +
                    "ORDER BY total_quantity DESC " +
                    "LIMIT 1";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mostSoldProduct = resultSet.getString("name");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostSoldProduct;
    }

    /**
     * metoda care returneaza valoarea incasarilor din saptamana curenta
     * */
    public double getWeekRevenue() {
        double revenue = 0.0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            Date weekStartDate = calendar.getTime();

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            Date weekEndDate = calendar.getTime();

            String sql = "SELECT SUM(oi.quantity * p.price) AS total_revenue " +
                    "FROM OrderItems oi " +
                    "INNER JOIN Products p ON oi.productId = p.id " +
                    "INNER JOIN Orders o ON oi.orderId = o.id " +
                    "WHERE o.orderDate >= ? AND o.orderDate <= ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(weekStartDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(weekEndDate.getTime()));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                revenue = resultSet.getDouble("total_revenue");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenue;
    }

    /**
     * metoda care returneaza perechi cheie-valoare de produse si cantitatea vanduta in saptamana curenta
     * */
    public Map<Product, Integer> getWeekProductsSold() {
        Map<Product, Integer> productsSold = new HashMap<>();

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            Date weekStartDate = calendar.getTime();
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            Date weekEndDate = calendar.getTime();

            String sql = "SELECT p.id, p.name, oi.quantity " +
                    "FROM OrderItems oi " +
                    "INNER JOIN Products p ON oi.productId = p.id " +
                    "INNER JOIN Orders o ON oi.orderId = o.id " +
                    "WHERE o.orderDate >= ? AND o.orderDate <= ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(weekStartDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(weekEndDate.getTime()));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int productId = resultSet.getInt("id");
                String productName = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");

                Product product = new Product(productId, productName);
                if (productsSold.containsKey(product)) {
                    int totalQuantity = productsSold.get(product);
                    productsSold.put(product, totalQuantity + quantity);
                } else {
                    productsSold.put(product, quantity);
                }
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productsSold;
    }
}
