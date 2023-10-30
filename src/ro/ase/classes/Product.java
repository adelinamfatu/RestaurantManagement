package ro.ase.classes;

public class Product implements Comparable<Product> {
    private int id;
    private String name;
    private String description;
    private float price;
    private int amount;
    //private Category type;

    public Product(int id, String name, String description, float price, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public int compareTo(Product otherProduct) {
        if (this.id < otherProduct.id) {
            return -1;
        } else if (this.id > otherProduct.id) {
            return 1;
        } else {
            return 0;
        }
    }
}
