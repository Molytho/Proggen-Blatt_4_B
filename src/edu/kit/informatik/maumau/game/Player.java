package edu.kit.informatik.maumau.game;

import edu.kit.informatik.maumau.game.exceptions.CardNotAllowedException;
import edu.kit.informatik.maumau.game.exceptions.IllegalMoveException;
import edu.kit.informatik.maumau.game.exceptions.InvalidGameStateException;
import edu.kit.informatik.maumau.game.exceptions.RuleException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class Player implements Iterable<Card> {
    private final Game gameController;
    private final SortedSet<Card> cardsOnHand;

    Player(Game gameController, Card[] initialCards) {
        this.gameController = gameController;
        cardsOnHand = new TreeSet<>(Arrays.asList(initialCards));
    }

    private void reportWonIfNeeded() {
        if (cardsOnHand.size() == 0) {
            gameController.reportWon();
        }
    }

    void doDiscard(Card card) throws InvalidGameStateException, RuleException {
        Objects.requireNonNull(card);
        if (!cardsOnHand.contains(card)) {
            throw new IllegalMoveException("Error, card not on the hand of the player");
        }
        if (!gameController.getCurrentCard().isCompatible(card)) {
            throw new CardNotAllowedException("Error, the given card is not compatible with the top of the stack");
        }

        gameController.discardCard(card);
        cardsOnHand.remove(card);

        reportWonIfNeeded();
    }

    void doDraw() throws InvalidGameStateException, IllegalMoveException {
        Card currentCard = gameController.getCurrentCard();
        for (Card card : this) {
            if (card.isCompatible(currentCard)) {
                throw new IllegalMoveException("Error, you can do a move. You are not allowed to draw.");
            }
        }

        cardsOnHand.add(gameController.draw());
    }

    public SortedSet<Card> getCardsOnHand() {
        return Collections.unmodifiableSortedSet(cardsOnHand);
    }

    @Override
    public Iterator<Card> iterator() {
        return getCardsOnHand().iterator();
    }
}
