package ro.ase.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderItem {
    private Product product;
    private int quantity;
    private Date date;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.date = new Date();
    }

    public Date getDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = dateFormat.format(this.date);
        return dateFormat.parse(formattedDate);
    }

    public float calculateSubtotal() {
        return product.getPrice() * quantity;
    }
}
