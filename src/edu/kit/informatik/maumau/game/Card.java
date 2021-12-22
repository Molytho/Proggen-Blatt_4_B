package edu.kit.informatik.maumau.game;

import java.util.Objects;

/**
 * Modelliert eine Skatkarte
 *
 * @author urqyv
 * @version 1.0
 */
public class Card implements Comparable<Card> {
    private final CardFamily family;
    private final CardValue value;

    /**
     * Instanziiert eine neue Karte
     *
     * @param family Die Familie der die Karte angehören soll.
     * @param value Der Wert der Karte.
     */
    Card(CardFamily family, CardValue value) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(value);

        this.family = family;
        this.value = value;
    }

    /**
     * Gibt zurück, ob eine Karte auf eine andere gelegt werden kann.
     *
     * @param other Die Karte mit der Verglichen werden soll.
     * @return Ein boolescher Wert der angibt, ob die beiden Karte kompatible sind.
     */
    boolean isCompatible(Card other) {
        return family == other.family || value == other.value;
    }

    @Override
    public int compareTo(Card other) {
        Objects.requireNonNull(other);

        return this.toString().compareTo(other.toString());
    }

    @Override
    public String toString() {
        return value.toString() + family.toString();
    }
}
