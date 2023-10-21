package ro.ase.classes;

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public float calculateSubtotal() {
        return product.getPrice() * quantity;
    }
}
