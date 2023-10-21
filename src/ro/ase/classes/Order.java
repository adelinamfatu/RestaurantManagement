package ro.ase.classes;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private List<OrderItem> orderItems;
    private float totalPrice = 0;

    public Order(int orderId) {
        this.id = orderId;
        this.orderItems = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) throws ParseException {
        OrderItem item = new OrderItem(product, quantity);
        orderItems.add(item);
    }

    public void removeItem(OrderItem item) {
        orderItems.remove(item);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double calculateTotalPrice() {
        for (OrderItem item : orderItems) {
            totalPrice += item.calculateSubtotal();
        }
        return totalPrice;
    }
}
