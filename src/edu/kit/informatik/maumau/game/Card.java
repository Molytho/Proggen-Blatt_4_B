package edu.kit.informatik.maumau.game;

import java.util.Objects;

public class Card implements Comparable<Card> {
    private final CardFamily family;
    private final CardValue value;

    public Card(CardFamily family, CardValue value) {
        Objects.requireNonNull(family);
        Objects.requireNonNull(value);

        this.family = family;
        this.value = value;
    }

    public boolean isCompatible(Card other) {
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
