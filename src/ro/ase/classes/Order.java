package ro.ase.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private List<OrderItem> orderItems;
    private Table table;
    private Date date;
    private float totalPrice = 0;

    public Order() {
        this.orderItems = new ArrayList<>();
        this.date = new Date();
    }

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
    }

    public Date getDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(this.date);
        return dateFormat.parse(formattedDate);
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
