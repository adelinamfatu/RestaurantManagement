package ro.ase.classes;

public class Table {
    private int id;
    private int nbSeats;
    private boolean isOccupied;

    public void occupyTable() {
        this.isOccupied = true;
    }

    public void freeTable() {
        this.isOccupied = false;
    }
}
