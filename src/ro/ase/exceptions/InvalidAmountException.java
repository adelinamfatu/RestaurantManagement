package ro.ase.exceptions;

import ro.ase.display.MessageDisplayer;

/**
 * clasa derivata din Exception folosita sa trateze cazul incercarii introducerii unui gramaj invalid
 * */
public class InvalidAmountException extends Exception {
    public InvalidAmountException() {
        super(MessageDisplayer.getInstance().getMessage("amount_exception"));
    }
}
