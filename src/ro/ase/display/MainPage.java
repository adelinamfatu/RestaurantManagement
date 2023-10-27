package ro.ase.display;
import ro.ase.classes.Category;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {
    private JLabel titleLabel;
    private JPanel categoriesPanel;

    public MainPage() {
        setTitle(MessageDisplayer.getInstance().getMessage("main_page_title"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel.setText(MessageDisplayer.getInstance().getMessage("main_page_title"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        Border spaceBeforeTitle = BorderFactory.createEmptyBorder(20, 0, 10, 0);
        Border spaceAfterTitle = BorderFactory.createEmptyBorder(10, 0, 20, 0);

        titleLabel.setBorder(BorderFactory.createCompoundBorder(spaceBeforeTitle, spaceAfterTitle));

        categoriesPanel = new JPanel(new GridLayout(0, 5));
        Font buttonFont = new Font("Arial", Font.PLAIN, 50);

        for (Category category : Category.values()) {
            JButton categoryButton = new JButton(category.toString());
            categoryButton.setFont(buttonFont);
            categoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String categoryText = categoryButton.getText();
                }
            });

            categoriesPanel.add(categoryButton);
        }

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(titleLabel, BorderLayout.NORTH);
        contentPane.add(categoriesPanel, BorderLayout.CENTER);
    }
}