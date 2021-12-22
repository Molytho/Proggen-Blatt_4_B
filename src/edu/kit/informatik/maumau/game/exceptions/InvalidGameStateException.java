package edu.kit.informatik.maumau.game.exceptions;

/**
 * Modelliert einen Fehler, der geworfen wird wenn eine Methode ausgeführt werden soll
 * während sich das {@link edu.kit.informatik.maumau.game.Game}
 * in einem nicht für den Aufruf zulässigen Zustand befindet.
 *
 * @author urqyv
 * @version 1.0
 */
public class InvalidGameStateException extends Exception {
    /**
     * Instanziiert eine neue InvalidGameStateException Instanz.
     *
     * @param message Die Nachricht die als Fehlermeldung ausgegeben werden soll.
     */
    public InvalidGameStateException(String message) {
        super(message);
    }
}
