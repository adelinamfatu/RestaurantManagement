package ro.ase.display;
import ro.ase.classes.*;
import ro.ase.database.SelectDatabase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;
import java.util.List;

public class MainPage extends JFrame {
    private JLabel titleLabel;
    private JPanel flipPanel;
    private CardLayout cardLayout;
    private JPanel categoryPanel;
    private Connection connection;
    private JComboBox<String> cbTable;
    private JCheckBox cbIsOccupied;
    private SelectDatabase selectDatabase;
    private JTextField tfNbSeats;
    private JList<OrderItem> orderItems;
    private DefaultListModel<OrderItem> orderItemsModel = new DefaultListModel<>();

    public MainPage(Connection connection) {
        this.connection = connection;
        this.selectDatabase = new SelectDatabase(connection);
        this.orderItems = new JList<>();

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

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(titleLabel, BorderLayout.NORTH);

        categoryPanel = new JPanel(new GridLayout(0, 5));
        Font buttonFont = new Font("Arial", Font.PLAIN, 50);

        for (Category category : Category.values()) {
            JButton categoryButton = new JButton(category.toString());
            categoryButton.setFont(buttonFont);
            categoryButton.addActionListener(new CategoryButtonActionListener(category.toString()));
            categoryPanel.add(categoryButton);
        }

        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        cbTable = new JComboBox<>();
        cbTable.addItem(MessageDisplayer.getInstance().getMessage("select_table_combobox"));
        cbTable.setFont(new Font("Arial", Font.PLAIN, 35));
        List<Table> tables = selectDatabase.getTables();
        for (Table table : tables) {
            cbTable.addItem(table.getName());
        }
        cbTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbTable.getSelectedIndex() == 0) {
                    cbTable.setSelectedItem(null);
                }
            }
        });

        cbIsOccupied = new JCheckBox(MessageDisplayer.getInstance().getMessage("is_occupied_checkbox"));
        cbIsOccupied.setFont(new Font("Arial", Font.PLAIN, 35));
        cbTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbTable.getSelectedIndex() != 0) {
                    String selectedTableName = (String) cbTable.getSelectedItem();
                    boolean isOccupied = tables.stream()
                            .filter(table -> table.getName().equals(selectedTableName))
                            .findFirst().orElse(null).isOccupied();
                    cbIsOccupied.setSelected(isOccupied);

                    int nbSeats = tables.stream()
                            .filter(table -> table.getName().equals(selectedTableName))
                            .findFirst().orElse(null).getNbSeats();

                    tfNbSeats.setText(String.valueOf(nbSeats));
                }
            }
        });

        tfNbSeats = new JTextField(5);
        tfNbSeats.setFont(new Font("Arial", Font.PLAIN, 35));
        tfNbSeats.setEditable(false);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        controlPanel.add(cbTable, constraints);

        constraints.gridy = 1;
        controlPanel.add(cbIsOccupied, constraints);

        constraints.gridy = 2;
        controlPanel.add(tfNbSeats, constraints);

        orderItems.setFont(new Font("Arial", Font.PLAIN, 35));
        orderItems.setModel(orderItemsModel);
        JScrollPane ordersPane = new JScrollPane(orderItems);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        constraints.fill = GridBagConstraints.BOTH;
        controlPanel.add(ordersPane, constraints);

        controlPanel.setPreferredSize(new Dimension(600, 500));

        mainContentPanel.add(categoryPanel, BorderLayout.CENTER);
        mainContentPanel.add(controlPanel, BorderLayout.SOUTH);

        flipPanel.add(mainContentPanel, "CategoryPanel");

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

            List<Product> productDetails = selectDatabase.getProductDetails(categoryName);

            JPanel productButtonsPanel = new JPanel(new GridLayout(0, 5));

            for (Product product : productDetails) {
                String buttonText = "<html><center><b style='font-size:80pt;'>" +
                        product.getName() + "</b><br>" +
                        product.getDescription() + "<br>" +
                        product.getPrice() + " lei<br>" +
                        product.getAmount() + " gr</center></html>";

                JButton productButton = new JButton(buttonText);
                productButton.putClientProperty("product", product);
                productButton.setFont(new Font("Arial", Font.PLAIN, 40));
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
                            Product clickedProduct = (Product) productButton.getClientProperty("product");
                            boolean found = false;
                            for (int i = 0; i < orderItemsModel.getSize(); i++) {
                                OrderItem item = orderItemsModel.getElementAt(i);
                                if (((item.getProduct()).compareTo(clickedProduct)) == 0) {
                                    item.increaseQuantity();
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                orderItemsModel.addElement(new OrderItem(clickedProduct, 1));
                            }
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