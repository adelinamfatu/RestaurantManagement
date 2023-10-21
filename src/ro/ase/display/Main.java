package ro.ase.display;

public class Main {
    public static void main(String[] args) {
        System.out.println(MessageDisplayer.getInstance().getMessage("welcome_message"));
        System.out.println(MessageDisplayer.getInstance().getMessage("order_placed"));
        System.out.println(MessageDisplayer.getInstance().getMessage("error_message"));
    }
}
