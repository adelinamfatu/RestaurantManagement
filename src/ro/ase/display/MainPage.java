package ro.ase.display;
import ro.ase.classes.*;
import ro.ase.database.SelectDatabase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainPage extends JFrame {
    private JLabel titleLabel;
    private JPanel flipPanel;
    private CardLayout cardLayout;
    private JPanel categoryPanel;
    private Connection connection;
    private JComboBox<String> tableCombobox;
    private JCheckBox isOccupiedCheckbox;
    private SelectDatabase selectDatabase;
    private JTextField nbSeatsTf;
    private JList<OrderItem> orderDisplay;
    private DefaultListModel<OrderItem> orderListModel = new DefaultListModel<>();
    private List<Product> productDetails;
    public Set<Product> products;
    public List<Table> tables;
    public List<Order> orders = new ArrayList<>();

    public MainPage(Connection connection) {
        this.connection = connection;
        this.selectDatabase = new SelectDatabase(connection);
        this.orderDisplay = new JList<>();

        setTitle(MessageDisplayer.getInstance().getMessage("main_page_title"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        cardLayout = new CardLayout();
        flipPanel = new JPanel(cardLayout);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel(MessageDisplayer.getInstance().getMessage("main_page_title"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        JButton addButton = new JButton(MessageDisplayer.getInstance().getMessage("open_new_product_page"));
        addButton.setFont(new Font("Arial", Font.BOLD, 30));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewProductPage newProductPage = new NewProductPage(connection);
                newProductPage.setVisible(true);
                newProductPage.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        products = newProductPage.getProducts();
                    }
                });
            }
        });
        titlePanel.add(addButton, BorderLayout.EAST);

        JButton statisticsButton = new JButton(MessageDisplayer.getInstance().getMessage("open_statistics_page"));
        statisticsButton.setFont(new Font("Arial", Font.BOLD, 30));
        statisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatisticsPage statisticsPage = new StatisticsPage(connection);
                statisticsPage.setVisible(true);
            }
        });
        titlePanel.add(statisticsButton, BorderLayout.WEST);

        Border spaceBeforeTitle = BorderFactory.createEmptyBorder(20, 0, 10, 0);
        Border spaceAfterTitle = BorderFactory.createEmptyBorder(10, 0, 20, 0);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(spaceBeforeTitle, spaceAfterTitle));

        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(titlePanel, BorderLayout.NORTH);

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

        JLabel tableLabel = new JLabel(MessageDisplayer.getInstance().getMessage("select_table_label"));
        tableLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        JLabel isOccupiedLabel = new JLabel(MessageDisplayer.getInstance().getMessage("occupied_table_label"));
        isOccupiedLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        JLabel nbSeatsLabel = new JLabel(MessageDisplayer.getInstance().getMessage("nb_seats_label"));
        nbSeatsLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        tableCombobox = new JComboBox<>();
        tableCombobox.setFont(new Font("Arial", Font.PLAIN, 35));
        tables = selectDatabase.getTables();
        for (Table table : tables) {
            tableCombobox.addItem(table.getName());
        }

        JButton submitOrder = new JButton(MessageDisplayer.getInstance().getMessage("submit_order"));
        submitOrder.setFont(new Font("Arial", Font.PLAIN, 35));
        submitOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTableName = (String) tableCombobox.getSelectedItem();
                Table selectedTable = tables.stream()
                        .filter(table -> table.getName().equals(selectedTableName))
                        .findFirst().orElse(null);

                if (isOccupiedCheckbox.isSelected()) {
                    Font messageFont = new Font("Arial", Font.PLAIN, 35);
                    UIManager.put("OptionPane.messageFont", messageFont);
                    UIManager.put("Button.font", messageFont);
                    UIManager.put("OptionPane.minimumSize", new Dimension(300,200));

                    int choice = JOptionPane.showConfirmDialog(MainPage.this,
                            MessageDisplayer.getInstance().getMessage("submit_message"),
                            MessageDisplayer.getInstance().getMessage("submit_title"),
                            JOptionPane.YES_NO_OPTION);
                    if(choice == JOptionPane.YES_OPTION) {
                        selectedTable.freeTable();
                        isOccupiedCheckbox.setSelected(false);
                    }
                } else {
                    Order newOrder = new Order(selectedTable);
                    orders.add(newOrder);
                    selectedTable.occupyTable();
                    isOccupiedCheckbox.setSelected(true);
                }

                Optional<Order> order = orders.stream().
                        filter(o -> o.getTableId() == selectedTable.getId()).
                        max(Comparator.comparing(Order::getDate));

                if (order.isPresent()) {
                    Order tableOrder = order.get();
                    for (int i = 0; i < orderListModel.getSize(); i++) {
                        OrderItem item = orderListModel.getElementAt(i);

                        Optional<OrderItem> existingItemOptional = tableOrder.getOrderItems()
                                .stream()
                                .filter(orderItem -> orderItem.getProduct().equals(item.getProduct()))
                                .findFirst();

                        if (existingItemOptional.isPresent()) {
                            OrderItem existingItem = existingItemOptional.get();
                            existingItem.increaseQuantity();
                        }
                        else {
                            tableOrder.addOrderItem(item);
                        }
                    }
                }
            }
        });

        isOccupiedCheckbox = new JCheckBox();
        isOccupiedCheckbox.setFocusable(false);
        ImageIcon yesIcon = new ImageIcon(getClass().getResource("/ro/ase/images/yes.png"));
        ImageIcon noIcon = new ImageIcon(getClass().getResource("/ro/ase/images/no.png"));
        isOccupiedCheckbox.setSelectedIcon(yesIcon);
        isOccupiedCheckbox.setIcon(noIcon);
        isOccupiedCheckbox.setEnabled(false);

        tableCombobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tableCombobox.getSelectedIndex() != 0) {
                    String selectedTableName = (String) tableCombobox.getSelectedItem();
                    boolean isOccupied = tables.stream()
                            .filter(table -> table.getName().equals(selectedTableName))
                            .findFirst().orElse(null).isOccupied();
                    isOccupiedCheckbox.setSelected(isOccupied);

                    int nbSeats = tables.stream()
                            .filter(table -> table.getName().equals(selectedTableName))
                            .findFirst().orElse(null).getNbSeats();
                    nbSeatsTf.setText(String.valueOf(nbSeats));
                }
            }
        });

        nbSeatsTf = new JTextField(5);
        nbSeatsTf.setFont(new Font("Arial", Font.PLAIN, 35));
        nbSeatsTf.setEditable(false);

        int gridxForLabels = 0;
        int gridxForControls = 1;

        constraints.gridx = gridxForLabels;
        constraints.gridy = 0;
        controlPanel.add(tableLabel, constraints);

        constraints.gridx = gridxForControls;
        controlPanel.add(tableCombobox, constraints);

        constraints.gridy = 1;

        constraints.gridx = gridxForLabels;
        controlPanel.add(isOccupiedLabel, constraints);

        constraints.gridx = gridxForControls;
        controlPanel.add(isOccupiedCheckbox, constraints);

        constraints.gridy = 2;

        constraints.gridx = gridxForLabels;
        controlPanel.add(nbSeatsLabel, constraints);

        constraints.gridx = gridxForControls;
        controlPanel.add(nbSeatsTf, constraints);

        constraints.gridy = 3;
        constraints.gridx = gridxForControls;
        constraints.gridheight = 3;
        controlPanel.add(submitOrder, constraints);

        orderDisplay.setFont(new Font("Arial", Font.PLAIN, 35));
        orderDisplay.setModel(orderListModel);
        JScrollPane ordersPane = new JScrollPane(orderDisplay);
        constraints.gridx = 2;
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

            productDetails = selectDatabase.getProductDetailsByCategory(categoryName);

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
                            for (int i = 0; i < orderListModel.getSize(); i++) {
                                OrderItem item = orderListModel.getElementAt(i);
                                if (((item.getProduct()).compareTo(clickedProduct)) == 0) {
                                    item.increaseQuantity();
                                    found = true;
                                    break;
                                }
                            }

                            if (!found) {
                                orderListModel.addElement(new OrderItem(clickedProduct, 1));
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