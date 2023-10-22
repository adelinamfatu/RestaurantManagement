package ro.ase.classes;

public enum Category {
    ROLLS(1),
    NIGIRI(2),
    SASHIMI(3),
    SALATE(4),
    SUPE(5),
    YAKITORI(6),
    NOODLES(7),
    OREZ(8),
    BAUTURI(9),
    DESERT(10);

    private final int value;

    Category(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
