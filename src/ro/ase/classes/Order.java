package ro.ase.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private List<OrderItem> orderItems;
    private Table table;
    private Date date;
    private float totalPrice = 0;

    public Order(Table table) {
        this.table = table;
        this.orderItems = new ArrayList<>();
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
    }

    public Date getFormattedDate() throws ParseException {
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

    public double getTotalPrice() {
        for (OrderItem item : orderItems) {
            totalPrice += item.calculateSubtotal();
        }
        return totalPrice;
    }

    public int getTableId() {
        return this.table.getId();
    }
}
