package edu.kit.informatik.maumau.game.exceptions;

/**
 * Modelliert einen Fehler, der bei der Verletzung von Spielregeln geworfen wird.
 *
 * @author rqyv
 * @version 1.0
 */
public class RuleException extends Exception {
    /**
     * Instanziiert eine neue RuleException Instanz.
     *
     * @param message Die Nachricht die als Fehlermeldung ausgegeben werden soll.
     */
    public RuleException(String message) {
        super(message);
    }
}
