package edu.kit.informatik.maumau.game;

import java.util.Objects;

/**
 * Modelliert die Kartenwerte eines Skatspiels.
 *
 * @author urqyv
 * @version 1.0
 */
public enum CardValue implements Comparable<CardValue> {
    /**
     * Der Kartenwert Sieben
     */
    SEVEN("7"),
    /**
     * Der Kartenwert Acht
     */
    EIGHT("8"),
    /**
     * Der Kartenwert Neun
     */
    NINE("9"),
    /**
     * Der Kartenwert Zehn
     */
    TEN("10"),
    /**
     * Der Kartenwert Bube
     */
    JACK("B"),
    /**
     * Der Kartenwert Dame
     */
    QUEEN("D"),
    /**
     * Der Kartenwert König
     */
    KING("K"),
    /**
     * Der Kartenwert Ass
     */
    ASS("A");

    private final String name;

    private CardValue(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Konvertiert eine String Repräsentation zu einem Wert Objekt.
     *
     * @param name Der String der konvertiert werden soll.
     * @throws IllegalArgumentException, wenn der übergebene Name keinem Wert entspricht.
     * @return Das Objekt das den Kartenwert darstellt.
     */
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
