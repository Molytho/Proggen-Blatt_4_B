package edu.kit.informatik.maumau.game;

/**
 * Modelliert die Zustände in denen sich das MauMau Spiel befinden kann
 *
 * @author urqyv
 * @version 1.0
 */
public enum GameState {
    /**
     * Das Spiel wurde noch nicht begonnen.
     */
    NOT_RUNNING,
    /**
     * Das Spiel läuft aktuell.
     */
    RUNNING,
    /**
     * Das Spiel ist zu Ende. Es ist unentschieden.
     */
    DRAW,
    /**
     * Das Spiel ist zu Ende. Ein Spieler hat gewonnen.
     */
    WON;
}
