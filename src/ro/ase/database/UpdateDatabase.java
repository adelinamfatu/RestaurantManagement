package ro.ase.database;

import ro.ase.classes.Order;
import ro.ase.classes.OrderItem;
import ro.ase.classes.Product;
import ro.ase.classes.Table;

import java.sql.*;
import java.util.List;
import java.util.Set;

/**
 * clasa care se ocupa cu comenzile de tip insert si update din baza de date
 * */
public class UpdateDatabase {
    private Connection connection;

    public UpdateDatabase(Connection connection) {
        this.connection = connection;
    }

    /**
     * metoda care adauga produse in baza de date
     * @param products - setul de produse
     * */
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

    /**
     * metoda care actualizeaza starea ocuparii meselor in baza de date
     * @param tables - lista de mese
     * */
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

    /**
     * metoda care adauga comenzi in baza de date si elementele fiecarei comenzi
     * @param orders - lista de comenzi
     * */
    public void addOrders(List<Order> orders) {
        String insertOrderQuery = "INSERT INTO Orders (tableId, totalPrice, orderDate) VALUES (?, ?, ?)";
        String insertOrderItemQuery = "INSERT INTO OrderItems (productId, quantity, orderId) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement insertOrderStatement = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertOrderItemStatement = connection.prepareStatement(insertOrderItemQuery)) {

                for (Order order : orders) {
                    insertOrderStatement.setInt(1, order.getTableId());
                    insertOrderStatement.setDouble(2, order.getTotalPrice());
                    insertOrderStatement.setTimestamp(3, new java.sql.Timestamp(order.getDate().getTime()));
                    insertOrderStatement.executeUpdate();

                    try (ResultSet generatedKeys = insertOrderStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int orderId = generatedKeys.getInt(1);

                            for (OrderItem orderItem : order.getOrderItems()) {
                                insertOrderItemStatement.setInt(1, orderItem.getProductId());
                                insertOrderItemStatement.setInt(2, orderItem.getQuantity());
                                insertOrderItemStatement.setInt(3, orderId);
                                insertOrderItemStatement.executeUpdate();
                            }
                        }
                    }
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
