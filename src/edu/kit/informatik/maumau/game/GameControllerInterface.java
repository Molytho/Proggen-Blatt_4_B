package edu.kit.informatik.maumau.game;

import edu.kit.informatik.maumau.game.exceptions.InvalidGameStateException;
import edu.kit.informatik.maumau.game.exceptions.RuleException;

/**
 * Modelliert ein Interface das Methoden bereitstellt mit denen man MauMau spielen kann.
 *
 * @author urqyv
 * @version 1.0
 */
public interface GameControllerInterface {
    /**
     * Startet ein neues Spiel, dessen Zufall durch den gegebenen Seed bestimmt wird.
     *
     * @param seed Der Seed der an das Random Objekt übergeben wird.
     */
    void startGame(long seed);

    /**
     * Wirft eine Karte eines Spielers ab.
     *
     * @param playerId Der Identifikationsnummer des Spielers für den eine Karte abgeworfen werden soll.
     * @param card Die Karte die abgeworfen werden soll.
     * @throws InvalidGameStateException wird geworfen, wenn das Spiel sich nicht im {@link GameState#RUNNING}
     *                                   Zustand befindet.
     * @throws RuleException wird geworfen, wenn beim geforderten Zug eine Spielregel verletzt werden würde.
     */
    void doPlayerDiscard(int playerId, Card card) throws InvalidGameStateException, RuleException;

    /**
     * Zieht eine Karte für einen Spieler.
     *
     * @param playerId Der Identifikationsnummer des Spielers für den eine Karte gezogen werden soll.
     * @throws InvalidGameStateException wird geworfen, wenn das Spiel sich nicht im {@link GameState#RUNNING}
     *                                   Zustand befindet.
     * @throws RuleException wird geworfen, wenn beim geforderten Zug eine Spielregel verletzt werden würde.
     */
    void doPlayerDraw(int playerId) throws InvalidGameStateException, RuleException;

    /**
     * Gibt den Spieler zurück, dem die gegebene Identifikationsnummer zugeordnet ist.
     * Die Identifikationsnummer werden von 1 ab hochgezählt.
     *
     * @param playerId Die Identifikationsnummer des Spielers
     * @return Der Spieler dem die Identifikationsnummer zugeordnet ist.
     * @throws InvalidGameStateException wird geworfen, wenn das Spiel sich nicht im {@link GameState#RUNNING}
     *                                   Zustand befindet.
     */
    Player getPlayerWithId(int playerId) throws InvalidGameStateException;

    /**
     * Gibt die Karte zurück, die gerade oben auf dem Ablagestapel liegt.
     *
     * @return Die Karte die oben auf dem Ablagestapel liegt.
     * @throws InvalidGameStateException wird geworfen, wenn das Spiel sich nicht im {@link GameState#RUNNING}
     *                                   Zustand befindet.
     */
    Card getCurrentCard() throws InvalidGameStateException;

    /**
     * Gibt die aktuelle Größe des Aufnahmestapels zurück.
     *
     * @return Die Anzahl der Karten auf dem Aufnahmestapel.
     * @throws InvalidGameStateException wird geworfen, wenn das Spiel sich nicht im {@link GameState#RUNNING}
     *                                   Zustand befindet.
     */
    int sizeOfCardStack() throws InvalidGameStateException;

    /**
     * Gibt den aktuellen Zustand des Spiels zurück.
     *
     * @return Der aktuelle Zustand des Spieles.
     */
    GameState getState();
}
