package edu.kit.informatik.maumau.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Modelliert einen Kartenstapel von dem Skatkarten gezogen bzw. abgelegt werden können.
 *
 * @author urqyv
 * @version 1.0
 */
public class CardStack {
    private final Stack<Card> stack;

    /**
     * Instanziiert eine neue Instanz eines Kartenstapels.
     * Die in {@code cards} übergebenen Karten werden gemischt und dann in den Kartenstapel aufgenommen.
     *
     * @param cards Ein Array aus Karten die gemischt werden sollen und dann dem Stapel hinzugefügt werden sollen.
     *              Es ist nicht erlaubt ein leeres Array zu übergeben
     * @param seed  Der Seed der dem Random Objekt übergeben werden soll, um die Mischung vorhersagbar zu machen
     */
    CardStack(Card[] cards, long seed) {
        assert cards.length > 0;

        List<Card> cardList = Arrays.asList(cards);
        Collections.shuffle(cardList, new Random(seed));
        // Stack gibt die Elemente in umgekehrter Reihenfolge zurück.
        // Das wäre zwar logisch komplett irrelevant aber könnte Probleme mit
        // Artemis auslösen, also einmal invertieren.
        Collections.reverse(cardList);
        stack = new Stack<>();
        stack.addAll(cardList);
    }

    /**
     * Instanziiert eine neue Instanz eines Kartenstapels.
     * Es wird ein leerer Stapel erstellt.
     */
    CardStack() {
        stack = new Stack<>();
    }

    /**
     * Legt eine Karte auf den Stapel.
     *
     * @param card Die Karte die auf den Stapel gelegt werden soll.
     */
    public void push(Card card) {
        stack.push(card);
    }

    /**
     * Gibt die oberste Karte des Stapels zurück, lässt aber auf dem Stapel liegen.
     *
     * @return Die oberste Karte des Stapels.
     */
    public Card peek() {
        return stack.peek();
    }

    /**
     * Zieht die oberste Karte des Stapels.
     * Sie wird dabei vom Stapel entfernt.
     *
     * @return Die oberste Karte des Stapels.
     */
    public Card pop() {
        return stack.pop();
    }

    /**
     * Gibt die aktuelle Größe des Stapels zurück.
     *
     * @return Die Anzahl der Karten die auf dem Stapel liegen.
     */
    public int size() {
        return stack.size();
    }
}
