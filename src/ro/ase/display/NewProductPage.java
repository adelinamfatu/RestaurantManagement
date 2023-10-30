package ro.ase.display;
import ro.ase.classes.*;

import javax.swing.*;
import java.awt.*;

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

    public NewProductPage() {
        setTitle(MessageDisplayer.getInstance().getMessage("new_page_title"));
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
