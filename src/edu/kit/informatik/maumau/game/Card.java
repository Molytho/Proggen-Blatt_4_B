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

        int comparedValueResult = value.compareTo(other.value);
        return comparedValueResult == 0 ? family.getName() - other.family.getName() : comparedValueResult;
    }

    @Override
    public String toString() {
        return value.toString() + family.toString();
    }
}
