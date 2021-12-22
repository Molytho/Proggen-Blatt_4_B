package edu.kit.informatik.maumau.game;

import edu.kit.informatik.maumau.game.exceptions.InvalidGameStateException;
import edu.kit.informatik.maumau.game.exceptions.RuleException;

import java.util.Objects;

public class Game implements GameControllerInterface {
    private static final int COUNT_OF_PLAYERS = 4;
    private static final int COUNT_OF_INITIAL_CARDS = 5;

    private GameState state;
    private Player[] players;
    private CardStack cardStack;
    private CardStack discardStack;
    private int currentPlayer;

    public Game() {
        state = GameState.NOT_RUNNING;
    }

    private boolean isRunning() {
        return state == GameState.RUNNING;
    }

    private void incrementPlayerIfNeeded() {
        if (state == GameState.RUNNING) {
            currentPlayer++;
            currentPlayer %= COUNT_OF_PLAYERS;
        }
    }

    private void ensureRunning() throws InvalidGameStateException {
        if (!isRunning()) {
            throw new InvalidGameStateException("Error, the game is not running!");
        }
    }

    private void ensurePlayersTurn(int playerId) throws RuleException {
        if (playerId != currentPlayer + 1) {
            throw new RuleException(String.format("Error, player %d's turn", currentPlayer + 1));
        }
    }

    Card draw() {
        Card drawnCard = cardStack.pop();

        if (cardStack.size() == 0) {
            state = GameState.DRAW;
        }

        return drawnCard;
    }

    void discardCard(Card card) {
        discardStack.push(card);
    }

    void reportWon() {
        this.state = GameState.WON;
    }

    @Override
    public void startGame(long seed) {
        cardStack = Deck.getInstance().createStack(seed);
        discardStack = new CardStack();
        players = new Player[COUNT_OF_PLAYERS];
        for (int i = 0; i < COUNT_OF_PLAYERS; i++) {
            Card[] initialCards = new Card[COUNT_OF_INITIAL_CARDS];
            for (int cardIndex = 0; cardIndex < COUNT_OF_INITIAL_CARDS; cardIndex++) {
                initialCards[cardIndex] = cardStack.pop();
            }
            players[i] = new Player(this, initialCards);
        }
        discardStack.push(cardStack.pop());
        currentPlayer = 0;
        state = GameState.RUNNING;
    }

    @Override
    public void doPlayerDiscard(int playerId, Card card)
        throws InvalidGameStateException, RuleException {
        Objects.requireNonNull(card);
        ensureRunning();
        ensurePlayersTurn(playerId);

        players[currentPlayer].doDiscard(card);

        incrementPlayerIfNeeded();
    }

    @Override
    public void doPlayerDraw(int playerId)
        throws InvalidGameStateException, RuleException {
        ensureRunning();
        ensurePlayersTurn(playerId);

        players[currentPlayer].doDraw();

        incrementPlayerIfNeeded();
    }

    @Override
    public Player getPlayerWithId(int playerId) throws InvalidGameStateException {
        ensureRunning();

        return players[playerId - 1];
    }

    @Override
    public Card getCurrentCard() throws InvalidGameStateException {
        ensureRunning();

        return discardStack.peek();
    }

    @Override
    public int sizeOfCardStack() throws InvalidGameStateException {
        ensureRunning();

        return cardStack.size();
    }

    @Override
    public GameState getState() {
        return state;
    }
}
