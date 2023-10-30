package ro.ase.exceptions;

import ro.ase.display.MessageDisplayer;

public class InvalidAmountException extends Exception {
    public InvalidAmountException() {
        super(MessageDisplayer.getInstance().getMessage("amount_exception"));
    }
}
