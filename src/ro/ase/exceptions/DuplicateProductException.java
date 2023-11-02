package ro.ase.exceptions;

import ro.ase.display.MessageDisplayer;

/**
 * clasa derivata din Exception folosita sa trateze cazul incercarii adaugarii unui produs duplicat
 * */
public class DuplicateProductException extends Exception {
    public DuplicateProductException() {
        super(MessageDisplayer.getInstance().getMessage("duplicate_product_exception"));
    }
}
