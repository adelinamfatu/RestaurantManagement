package ro.ase.classes;

import java.lang.reflect.Type;
import java.util.Objects;

public class Product implements Comparable<Product> {
    private int id;
    private String name;
    private String description;
    private float price;
    private int amount;
    private Category category;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
