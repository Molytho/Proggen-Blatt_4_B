package edu.kit.informatik.maumau.game;

import edu.kit.informatik.maumau.game.exceptions.InvalidGameStateException;
import edu.kit.informatik.maumau.game.exceptions.RuleException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Modelliert einen Spieler im MauMau Spiel
 *
 * @author urqyv
 * @version 1.0
 */
public class Player implements Iterable<Card> {
    private final Game gameController;
    private final SortedSet<Card> cardsOnHand;

    /**
     * Instanziiert einen neuen Spieler. Er wird mit einer Starthand instanziiert.
     *
     * @param gameController Das Spiel in dem der Spieler spielt.
     * @param initialCards Die Karten die der Spieler auf die Hand bekommt.
     */
    Player(Game gameController, Card[] initialCards) {
        this.gameController = gameController;
        cardsOnHand = new TreeSet<>(Arrays.asList(initialCards));
    }

    private void reportWonIfNeeded() {
        if (cardsOnHand.size() == 0) {
            gameController.reportWon();
        }
    }

    /**
     * Wirft wenn möglich eine Karte auf den Ablagestapel ab.
     *
     * @param card Die Karte die abgeworfen werden soll.
     * @throws InvalidGameStateException wird geworfen, denn das Spiel nicht läuft.
     * @throws RuleException wird geworfen, wenn eine Regelverletzung wie z.B. die Karte ist nicht mit der Karte
     *                       auf dem Ablagestapel kompatibel vorliegt.
     */
    void doDiscard(Card card) throws InvalidGameStateException, RuleException {
        Objects.requireNonNull(card);
        if (!cardsOnHand.contains(card)) {
            throw new RuleException("Error, card not on the hand of the player");
        }
        if (!gameController.getCurrentCard().isCompatible(card)) {
            throw new RuleException("Error, the given card is not compatible with the top of the stack");
        }

        gameController.discardCard(card);
        cardsOnHand.remove(card);

        reportWonIfNeeded();
    }

    /**
     * Zieht eine Karte vom Aufnahmestapel.
     * Dies ist nur möglich, wenn man keine Karte legen kann.
     *
     * @throws InvalidGameStateException wird geworfen, denn das Spiel nicht läuft.
     * @throws RuleException wird geworfen, wenn eine Karte abgeworfen werden kann.
     */
    void doDraw() throws InvalidGameStateException, RuleException {
        Card currentCard = gameController.getCurrentCard();
        for (Card card : this) {
            if (card.isCompatible(currentCard)) {
                throw new RuleException("Error, you can do a move. You are not allowed to draw.");
            }
        }

        cardsOnHand.add(gameController.draw());
    }

    /**
     * Gibt ein Nur-Lesen SortedSet für die Karten die der Spieler auf der Hand hat zurück.
     *
     * @return Ein SortedSet der Karten die der Spieler auf der Hand hat
     */
    public SortedSet<Card> getCardsOnHand() {
        return Collections.unmodifiableSortedSet(cardsOnHand);
    }

    @Override
    public Iterator<Card> iterator() {
        return getCardsOnHand().iterator();
    }
}
