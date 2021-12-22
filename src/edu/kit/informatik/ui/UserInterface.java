package edu.kit.informatik.ui;

/**
 * Modelliert eine Schnittstelle über die ein Programm mit dem Benutzer interagieren kann
 *
 * @author urqyv
 * @version 1.0
 */
public interface UserInterface {
    /**
     * Gibt die nächste Eingabe des Benutzers zurück.
     * Blockt falls noch keine Eingabe erfolgt ist.
     *
     * @return Die Eingabe des Benutzers
     */
    String readInput();

    /**
     * Gibt die gegebene Nachricht an den Benutzer aus.
     *
     * @param message Die Nachricht die Ausgegeben werden soll
     */
    void printOutput(String message);
}