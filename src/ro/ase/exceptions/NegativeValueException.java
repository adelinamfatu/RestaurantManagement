package ro.ase.exceptions;

import ro.ase.display.MessageDisplayer;

public class NegativeValueException extends Exception {
    public NegativeValueException() {
        super(MessageDisplayer.getInstance().getMessage("negative_value_exception"));
    }
}
