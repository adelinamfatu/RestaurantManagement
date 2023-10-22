package ro.ase.display;
import javax.swing.*;

public class MainPage extends JFrame {
    public MainPage() {
        setTitle(MessageDisplayer.getInstance().getMessage("main_page_title"));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
