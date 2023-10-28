package ro.ase.display;
import ro.ase.classes.Category;
import ro.ase.database.SelectDatabase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;

public class MainPage extends JFrame {
    private JLabel titleLabel;
    private JPanel flipPanel;
    private CardLayout cardLayout;
    private JPanel categoryPanel;
    private JPanel categoryGrid;
    private Connection connection;

    public MainPage(Connection connection) {
        this.connection = connection;

        setTitle(MessageDisplayer.getInstance().getMessage("main_page_title"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        flipPanel = new JPanel(cardLayout);

        titleLabel = new JLabel(MessageDisplayer.getInstance().getMessage("main_page_title"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        Border spaceBeforeTitle = BorderFactory.createEmptyBorder(20, 0, 10, 0);
        Border spaceAfterTitle = BorderFactory.createEmptyBorder(10, 0, 20, 0);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(spaceBeforeTitle, spaceAfterTitle));

        categoryPanel = new JPanel(new BorderLayout());

        categoryGrid = new JPanel(new GridLayout(0, 5));
        Font buttonFont = new Font("Arial", Font.PLAIN, 50);

        for (Category category : Category.values()) {
            JButton categoryButton = new JButton(category.toString());
            categoryButton.setFont(buttonFont);
            categoryButton.addActionListener(new CategoryButtonActionListener(category.toString()));
            categoryGrid.add(categoryButton);
        }

        categoryPanel.add(titleLabel, BorderLayout.NORTH);
        categoryPanel.add(categoryGrid, BorderLayout.CENTER);

        flipPanel.add(categoryPanel, "CategoryPanel");

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(flipPanel, BorderLayout.CENTER);
    }

    private class CategoryButtonActionListener implements ActionListener {
        private String categoryName;

        public CategoryButtonActionListener(String categoryName) {
            this.categoryName = categoryName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel productsPanel = new JPanel(new BorderLayout());

            JButton backButton = new JButton(MessageDisplayer.getInstance().getMessage("back_to_categories_button"));
            backButton.setFont(new Font("Arial", Font.PLAIN, 45));
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(flipPanel, "CategoryPanel");
                }
            });

            JPanel backButtonPanel = new JPanel();
            backButtonPanel.add(backButton);

            productsPanel.add(backButtonPanel, BorderLayout.NORTH);

            SelectDatabase selectDatabase = new SelectDatabase(connection);
            Vector<String> productNames = selectDatabase.getProductNames(categoryName);

            JPanel productButtonsPanel = new JPanel(new GridLayout(0, 5));

            for (String productName : productNames) {
                JButton productButton = new JButton(productName);
                productButton.setFont(new Font("Arial", Font.PLAIN, 50));
                productButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Font messageFont = new Font("Arial", Font.PLAIN, 35);
                        UIManager.put("OptionPane.messageFont", messageFont);
                        UIManager.put("Button.font", messageFont);
                        UIManager.put("OptionPane.minimumSize", new Dimension(300,200));

                        String popupMessage = MessageDisplayer.getInstance().getMessage("add_product_message");
                        String popupTitle = MessageDisplayer.getInstance().getMessage("add_product_title");
                        int result = JOptionPane.showConfirmDialog(MainPage.this, popupMessage, popupTitle, JOptionPane.YES_NO_OPTION);

                        if (result == JOptionPane.YES_OPTION) {
                            //
                        } else {
                            //
                        }
                    }
                });
                productButtonsPanel.add(productButton);
            }

            productsPanel.add(productButtonsPanel, BorderLayout.CENTER);

            flipPanel.add(productsPanel, categoryName);
            cardLayout.show(flipPanel, categoryName);
        }
    }
}