package edu.kit.informatik.maumau.game;

import java.util.Objects;

public enum CardFamily {
    ACORNS('E'),
    LEAVES('L'),
    HEARTS('H'),
    BELLS('S');

    private final char name;

    private CardFamily(char name) {
        this.name = name;
    }

    public char getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    public static CardFamily fromString(String name) {
        Objects.requireNonNull(name);
        for (CardFamily cardFamily : CardFamily.values()) {
            if (name.equals(cardFamily.toString())) {
                return cardFamily;
            }
        }
        throw new IllegalArgumentException("Error, the given name was not found");
    }
}
