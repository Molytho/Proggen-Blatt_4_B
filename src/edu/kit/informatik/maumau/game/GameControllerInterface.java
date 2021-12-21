package edu.kit.informatik.maumau.game;

import edu.kit.informatik.maumau.game.exceptions.IllegalMoveException;
import edu.kit.informatik.maumau.game.exceptions.InvalidGameStateException;
import edu.kit.informatik.maumau.game.exceptions.NotThePlayersTurnException;
import edu.kit.informatik.maumau.game.exceptions.RuleException;

public interface GameControllerInterface {
    void startGame(long seed) throws InvalidGameStateException;

    void doPlayerDiscard(int playerId, Card card) throws InvalidGameStateException, RuleException;

    void doPlayerDraw(int playerId) throws NotThePlayersTurnException, InvalidGameStateException, IllegalMoveException,
        RuleException;

    Player getPlayerWithId(int playerId) throws InvalidGameStateException;

    Card getCurrentCard() throws InvalidGameStateException;

    int sizeOfCardStack() throws InvalidGameStateException;

    GameState getState();
}
