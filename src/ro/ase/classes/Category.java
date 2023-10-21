package ro.ase.classes;

public enum Category {
    APERITIV(1),
    SALATA(2),
    SUPA(3),
    CIORBA(4),
    PASTE(5),
    PIZZA(6),
    BURGER(7),
    GARNITURA(8),
    CAFEA(9),
    RACORITOARE(10),
    DESERT(11);

    private final int value;

    Category(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
