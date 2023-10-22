package ro.ase.display;
import ro.ase.classes.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {
    public MainPage() {
        setTitle(MessageDisplayer.getInstance().getMessage("main_page_title"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 5));
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

            buttonPanel.add(categoryButton);
        }

        getContentPane().add(buttonPanel);
    }
}
