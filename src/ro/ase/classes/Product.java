package ro.ase.classes;

import java.lang.reflect.Type;

public class Product implements Comparable<Product> {
    private int id;
    private String name;
    private String description;
    private float price;
    private int amount;
    private Category category;

    public Product(int id, String name, String description, float price, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public Product(String name, String description, float price, int amount, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.category = category;
    }

    public Product(Product otherProduct) {
        this.id = otherProduct.id;
        this.name = otherProduct.name;
        this.description = otherProduct.description;
        this.price = otherProduct.price;
        this.amount = otherProduct.amount;
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
