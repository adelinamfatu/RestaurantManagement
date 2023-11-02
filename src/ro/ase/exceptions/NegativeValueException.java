package ro.ase.exceptions;

import ro.ase.display.MessageDisplayer;

/**
 * clasa derivata din Exception folosita sa trateze cazul incercarii introducerii unei valori negative pentru pret sau gramaj
 * */
public class NegativeValueException extends Exception {
    public NegativeValueException() {
        super(MessageDisplayer.getInstance().getMessage("negative_value_exception"));
    }
}
