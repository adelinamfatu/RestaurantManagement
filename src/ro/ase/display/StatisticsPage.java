package ro.ase.display;

import javax.swing.*;

public class StatisticsPage extends JFrame {
    public StatisticsPage() {
        setTitle(MessageDisplayer.getInstance().getMessage("statistics_page"));
        setResizable(false);
        setSize(1200, 1200);
    }
}
