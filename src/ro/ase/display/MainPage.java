package ro.ase.display;
import ro.ase.classes.Category;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {
    private JLabel titleLabel;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel categoryPanel;
    private JPanel categoryButtonsPanel;

    public MainPage() {
        setTitle(MessageDisplayer.getInstance().getMessage("main_page_title"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        titleLabel = new JLabel(MessageDisplayer.getInstance().getMessage("main_page_title"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        Border spaceBeforeTitle = BorderFactory.createEmptyBorder(20, 0, 10, 0);
        Border spaceAfterTitle = BorderFactory.createEmptyBorder(10, 0, 20, 0);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(spaceBeforeTitle, spaceAfterTitle));

        categoryPanel = new JPanel(new BorderLayout());

        categoryButtonsPanel = new JPanel(new GridLayout(0, 5));
        Font buttonFont = new Font("Arial", Font.PLAIN, 50);

        for (Category category : Category.values()) {
            JButton categoryButton = new JButton(category.toString());
            categoryButton.setFont(buttonFont);
            categoryButton.addActionListener(new CategoryButtonActionListener(category.toString()));
            categoryButtonsPanel.add(categoryButton);
        }

        categoryPanel.add(titleLabel, BorderLayout.NORTH);
        categoryPanel.add(categoryButtonsPanel, BorderLayout.CENTER);

        mainPanel.add(categoryPanel, "CategoryPanel");

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
    }

    private class CategoryButtonActionListener implements ActionListener {
        private String categoryName;

        public CategoryButtonActionListener(String categoryName) {
            this.categoryName = categoryName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel productsPanel = new JPanel();

            JButton backButton = new JButton(MessageDisplayer.getInstance().getMessage("back_to_categories_button"));
            backButton.setFont(new Font("Arial", Font.PLAIN, 45));
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(mainPanel, "CategoryPanel");
                }
            });
            productsPanel.add(backButton);
            mainPanel.add(productsPanel, categoryName);
            cardLayout.show(mainPanel, categoryName);
        }
    }
}