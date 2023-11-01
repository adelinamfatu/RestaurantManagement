package ro.ase.display;

import ro.ase.database.SelectDatabase;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

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

        JPanel labelsPanel = new JPanel(new BorderLayout());

        int ordersCount = selectDatabase.getWeekOrdersCount();
        totalWeekOrdersLabel = new JLabel("<html><div style='text-align: center;'><b>" + ordersCount + "</b><br>comenzi saptamana asta</div></html>");
        totalWeekOrdersLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        //totalWeekOrdersLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        labelsPanel.add(totalWeekOrdersLabel, BorderLayout.WEST);

        double weekRevenue = selectDatabase.getWeekRevenue();
        totalWeekRevenueLabel = new JLabel("<html><div style='text-align: center;'><b>" + weekRevenue + "</b><br>incasari saptamana asta</div></html>");
        totalWeekRevenueLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        //totalWeekRevenueLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        labelsPanel.add(totalWeekRevenueLabel, BorderLayout.CENTER);

        String mostOrderedProduct = selectDatabase.getMostSoldProduct();
        mostOrderedProductLabel = new JLabel("<html><div style='text-align: center;'><b>" + mostOrderedProduct + "</b><br>cel mai vandut produs</div></html>");
        mostOrderedProductLabel.setFont(new Font("Arial", Font.PLAIN, 35));
        //mostOrderedProductLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        labelsPanel.add(mostOrderedProductLabel, BorderLayout.EAST);

        add(labelsPanel, BorderLayout.CENTER);
    }
}
