package ro.ase.display;
import ro.ase.classes.*;
import ro.ase.database.SelectDatabase;
import ro.ase.exceptions.DuplicateProductException;
import ro.ase.exceptions.InvalidAmountException;
import ro.ase.exceptions.NegativeValueException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NewProductPage extends JFrame {
    private JLabel nameLabel;
    private JTextField nameTF;
    private JLabel descriptionLabel;
    private JTextField descriptionTF;
    private JLabel priceLabel;
    private JTextField priceTF;
    private JLabel amountLabel;
    private JTextField amountTF;
    private JLabel categoryLabel;
    private JComboBox categoryCB;
    private JButton addBtn;
    private Set<Product> products = new HashSet<>();
    private Connection connection;
    private SelectDatabase selectDatabase;

    public NewProductPage(Connection connection) {
        this.connection = connection;
        this.selectDatabase = new SelectDatabase(connection);
        products = selectDatabase.getAllProductNamesAndIds();

        setTitle(MessageDisplayer.getInstance().getMessage("new_product_title"));
        setResizable(false);
        setSize(600, 900);

        categoryCB = new JComboBox();
        Category[] categories = Category.values();
        for (Category category : categories) {
            categoryCB.addItem(category.toString());
        }

        Font font = new Font("Arial", Font.PLAIN, 40);

        nameLabel = new JLabel("Nume:");
        nameLabel.setFont(font);

        nameTF = new JTextField(10);
        nameTF.setFont(font);

        descriptionLabel = new JLabel("Descriere:");
        descriptionLabel.setFont(font);

        descriptionTF = new JTextField(10);
        descriptionTF.setFont(font);

        priceLabel = new JLabel("Pret:");
        priceLabel.setFont(font);

        priceTF = new JTextField(10);
        priceTF.setFont(font);

        amountLabel = new JLabel("Gramaj:");
        amountLabel.setFont(font);

        amountTF = new JTextField(10);
        amountTF.setFont(font);

        categoryLabel = new JLabel("Categorie:");
        categoryLabel.setFont(font);

        addBtn = new JButton("Adauga produs");
        addBtn.setFont(font);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameTF.getText();
                    String description = descriptionTF.getText();
                    float price = Float.parseFloat(priceTF.getText());
                    int amount = Integer.parseInt(amountTF.getText());
                    String selectedCategory = (String) categoryCB.getSelectedItem();

                    if (name.isEmpty() || description.isEmpty() || priceTF.getText().isEmpty() || amountTF.getText().isEmpty() || selectedCategory.isEmpty()) {
                        throw new IllegalArgumentException(MessageDisplayer.getInstance().getMessage("no_data_exception"));
                    }
                    if(price <= 0 || amount <= 0) {
                        throw new NegativeValueException();
                    }
                    if (name.matches(".*\\d+.*")) {
                        throw new IllegalArgumentException(MessageDisplayer.getInstance().getMessage("invalid_name_exception"));
                    }
                    if(amount < 100 || amount > 1000) {
                        throw new InvalidAmountException();
                    }

                    Product newProduct = new Product(name, description, price, amount, Category.valueOf(selectedCategory));
                    if (products.contains(newProduct)) {
                        throw new DuplicateProductException();
                    } else {
                        products.add(newProduct);
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(NewProductPage.this, MessageDisplayer.getInstance().getMessage("price_amount_format_exception"));
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(NewProductPage.this, ex.getMessage());
                } catch(InvalidAmountException ex) {
                    JOptionPane.showMessageDialog(NewProductPage.this, ex.getMessage());
                } catch(NegativeValueException ex) {
                    JOptionPane.showMessageDialog(NewProductPage.this, ex.getMessage());
                } catch (DuplicateProductException ex) {
                    JOptionPane.showMessageDialog(NewProductPage.this, ex.getMessage());
                }
            }
        });

        categoryCB.setFont(font);

        GridBagLayout gridBagLayout = new GridBagLayout();
        JPanel panel = new JPanel(gridBagLayout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(nameLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(nameTF, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(descriptionLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(descriptionTF, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(priceLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(priceTF, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(amountLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(amountTF, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(categoryLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(categoryCB, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        panel.add(addBtn, constraints);

        add(panel);
    }
}
