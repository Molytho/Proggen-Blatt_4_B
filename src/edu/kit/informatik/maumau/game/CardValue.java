package edu.kit.informatik.maumau.game;

import java.util.Objects;

public enum CardValue implements Comparable<CardValue> {
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("B"),
    QUEEN("D"),
    KING("K"),
    ASS("A");

    private final String name;

    private CardValue(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static CardValue fromString(String name) {
        Objects.requireNonNull(name);
        for (CardValue cardValue : CardValue.values()) {
            if (name.equals(cardValue.toString())) {
                return cardValue;
            }
        }
        throw new IllegalArgumentException("Error, the given name was not found");
    }
}
