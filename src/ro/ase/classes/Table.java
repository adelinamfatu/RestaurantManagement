package ro.ase.classes;

/**
 * clasa Masa
 * */
public class Table {
    private int id;
    private int nbSeats;
    private boolean isOccupied;
    private String name;

    public Table(int id, int nbSeats, boolean isOccupied) {
        this.id = id;
        this.nbSeats = nbSeats;
        this.isOccupied = isOccupied;
        this.name = "Masa " + this.id;
    }

    public void occupyTable() {
        this.isOccupied = true;
    }

    public void freeTable() {
        this.isOccupied = false;
    }

    public String getName() {
        return name;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public int getId() { return id; }

    public int getNbSeats() {
        return nbSeats;
    }
}
