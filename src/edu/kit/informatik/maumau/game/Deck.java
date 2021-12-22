package edu.kit.informatik.maumau.game;

import java.util.Objects;

/**
 * Modelliert eine Singleton Klasse mit der man Singleton Instanzen von Karten erhalten kann.
 *
 * @author urqyv
 * @version 1.0
 */
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

    /**
     * Gibt eine Singleton Instanz auf eine Karte zurück, die die gegebene Familie und den gegebenen Wert besitzt.
     *
     * @param family Die Familie der Karte die man haben möchte.
     * @param value Der Wert der Karte die man haben möchte.
     * @return Die Karte die die gegebene Familie und den gegebenen Wert besitzt.
     */
    public Card getCard(CardFamily family, CardValue value) {
        // Die Karten werden linear nach der Reihenfolge der Enum-Instanzen (ordinal()) in das Array
        // gespeichert, also können wir den genauen Index der gesuchten Karte berechnen.
        int index = family.ordinal() * COUNT_OF_VALUES + value.ordinal();
        return cards[index];
    }

    /**
     * Erstellt einen neuen Kartenstapel der alle Karten enthält.
     * Die Karten werden bei der Hinzufügung gemischt.
     *
     * @param seed Der seed der an den {@link java.util.Random#Random(long)} übergeben wird.
     * @return Der erstellte Kartenstapel.
     */
    public CardStack createStack(long seed) {
        return new CardStack(cards.clone(), seed);
    }

    /**
     * Gibt eine Singleton Instanz von {@link Deck} zurück.
     *
     * @return Eine Instanz von {@link Deck}.
     */
    public static Deck getInstance() {
        return Objects.isNull(instance) ? instance = new Deck() : instance;
    }
}
