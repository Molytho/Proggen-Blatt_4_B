package edu.kit.informatik.maumau.game;

import java.util.Objects;

public final class Deck {
    private static final int COUNT_OF_VALUES = 8;
    private static final int COUNT_OF_FAMILIES = 4;
    private static final int COUNT_OF_CARDS = COUNT_OF_FAMILIES * COUNT_OF_VALUES;
    private static Deck instance = null;
    private final Card[] cards;

    private Deck() {
        CardFamily[] families = CardFamily.values();
        CardValue[] values = CardValue.values();

        assert families.length == COUNT_OF_FAMILIES;
        assert values.length == COUNT_OF_VALUES;

        int index = 0;
        cards = new Card[COUNT_OF_CARDS];
        for (CardFamily family : families) {
            for (CardValue value : values) {
                cards[index++] = new Card(family, value);
            }
        }
    }

    public Card getCard(CardFamily family, CardValue value) {
        int index = family.ordinal() * COUNT_OF_VALUES + value.ordinal();
        return cards[index];
    }

    public CardStack createStack(long seed) {
        return new CardStack(cards.clone(), seed);
    }

    public static Deck getInstance() {
        return Objects.isNull(instance) ? instance = new Deck() : instance;
    }
}
