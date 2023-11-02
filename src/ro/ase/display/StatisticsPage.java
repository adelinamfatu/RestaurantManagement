package ro.ase.display;

import ro.ase.database.SelectDatabase;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

/**
 * clasa derivata din JFrame care reprezinta interfata pentru statistici
 * */
public class StatisticsPage extends JFrame {
    private JLabel totalWeekOrdersLabel;
    private JLabel mostOrderedProductLabel;
    private JLabel totalWeekRevenueLabel;
    private SelectDatabase selectDatabase;

    public StatisticsPage(Connection connection) {
        this.selectDatabase = new SelectDatabase(connection);

        setTitle(MessageDisplayer.getInstance().getMessage("statistics_page"));
        setResizable(false);
        setSize(1200, 1200);
        setLayout(new BorderLayout());

        JPanel labelsPanel = new JPanel(new GridLayout(1, 3));

        int ordersCount = selectDatabase.getWeekOrdersCount();
        totalWeekOrdersLabel = new JLabel("<html><div style='text-align: center;'><b>" + ordersCount + "</b><br>comenzi saptamana asta</div></html>");
        totalWeekOrdersLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        labelsPanel.add(totalWeekOrdersLabel);

        double weekRevenue = selectDatabase.getWeekRevenue();
        totalWeekRevenueLabel = new JLabel("<html><div style='text-align: center;'><b>" + weekRevenue + "</b><br>incasari saptamana asta</div></html>");
        totalWeekRevenueLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        labelsPanel.add(totalWeekRevenueLabel);

        String mostOrderedProduct = selectDatabase.getMostSoldProduct();
        mostOrderedProductLabel = new JLabel("<html><div style='text-align: center;'><b>" + mostOrderedProduct + "</b><br>cel mai vandut produs</div></html>");
        mostOrderedProductLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        labelsPanel.add(mostOrderedProductLabel);

        add(labelsPanel, BorderLayout.CENTER);
    }
}
