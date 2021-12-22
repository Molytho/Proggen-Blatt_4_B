package edu.kit.informatik.maumau.game;

import java.util.Objects;

/**
 * Modelliert die vier möglichen Kartenfamilien eines Skatspieles
 *
 * @author urqyv
 * @version 1.0
 */
public enum CardFamily {
    /**
     * Modelliert die Familie der Eichel Karten
     */
    ACORNS('E'),
    /**
     * Modelliert die Familie der Blatt Karten
     */
    LEAVES('L'),
    /**
     * Modelliert die Familie der Herz Karten
     */
    HEARTS('H'),
    /**
     * Modelliert die Familie der Schelle Karten
     */
    BELLS('S');

    private final char name;

    private CardFamily(char name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(name);
    }

    /**
     * Konvertiert eine String Repräsentation zu einem Familien Objekt.
     *
     * @param name Der String der konvertiert werden soll.
     * @throws IllegalArgumentException, wenn der übergebene Name keiner Familie entspricht.
     * @return Das Objekt das die Kartenfamilie darstellt.
     */
    public static CardFamily fromString(String name) throws IllegalArgumentException {
        Objects.requireNonNull(name);
        for (CardFamily cardFamily : CardFamily.values()) {
            if (name.equals(cardFamily.toString())) {
                return cardFamily;
            }
        }
        throw new IllegalArgumentException("Error, the given name was not found");
    }
}
