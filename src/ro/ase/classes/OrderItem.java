package ro.ase.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public float calculateSubtotal() {
        return product.getPrice() * quantity;
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    @Override
    public String toString() {
        return this.product.getName() + " x " + this.quantity + " buc ... " + this.product.getPrice() + " lei";
    }
}
