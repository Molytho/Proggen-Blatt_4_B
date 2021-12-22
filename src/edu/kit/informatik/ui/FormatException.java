package edu.kit.informatik.ui;

/**
 * Modelliert einen allgemeinen Fehler im Eingabeformat.
 *
 * @author urqyv
 * @version 1.0
 */
public class FormatException extends Exception {
    /**
     * Erstellt ein neues Objekt mti der gegebenen Nachricht.
     *
     * @param message Die Nachricht die als Fehlermeldung ausgegeben werden soll.
     */
    public FormatException(String message) {
        super(message);
    }
}
