package ro.ase.display;

import ro.ase.classes.Product;
import ro.ase.database.SelectDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

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

        /**
         * meniu pop-up pe eveniment de click dreapta al mouse-ului
         * */
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem downloadReportMenuItem = new JMenuItem(MessageDisplayer.getInstance().getMessage("download_context_menu_option"));
        Font menuItemFont = downloadReportMenuItem.getFont();
        downloadReportMenuItem.setFont(new Font(menuItemFont.getName(), Font.BOLD, 25));
        downloadReportMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Map<Product, Integer> productsSold = selectDatabase.getWeekProductsSold();

                    String csvFileName = MessageDisplayer.getInstance().getMessage("raport_title");
                    File csvFile = new File(csvFileName);
                    FileWriter writer = new FileWriter(csvFile);
                    writer.write("Produs,Cantitate\n");

                    for (Map.Entry<Product, Integer> entry : productsSold.entrySet()) {
                        String productName = entry.getKey().getName();
                        int quantitySold = entry.getValue();
                        String line = productName + "," + quantitySold + "\n";
                        writer.write(line);
                    }

                    writer.close();

                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setSelectedFile(csvFile);
                    int userSelection = fileChooser.showSaveDialog(StatisticsPage.this);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        csvFile.renameTo(selectedFile);
                    } else {
                        csvFile.delete();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        contextMenu.add(downloadReportMenuItem);
        contextMenu.setPreferredSize(new Dimension(400, 50));
        labelsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    contextMenu.show(labelsPanel, e.getX(), e.getY());
                }
            }
        });
    }
}
