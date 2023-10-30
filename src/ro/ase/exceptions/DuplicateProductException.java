package ro.ase.exceptions;

import ro.ase.display.MessageDisplayer;

public class DuplicateProductException extends Exception {
    public DuplicateProductException() {
        super(MessageDisplayer.getInstance().getMessage(""));
    }
}
