package edu.kit.informatik.ui;

/**
 * Modelliert einen ausführbaren Befehl.
 *
 * @param <T> Der Typ auf dem der Befehl arbeiten wird.
 * @author urqyv
 * @version 1.0
 */
public interface Command<T> {
    /**
     * Für den Befehl aus. Gibt Fehler selbständig an die Benutzerschnittstelle aus.
     *
     * @param loopHandle Ein Objekt, mit dem die Schleife gesteuert werden kann, der dieser Befehl zugeordnet ist.
     * @param userInterface Ein Objekt, über das mit dem Benutzer kommuniziert werden kann.
     * @param game Das Objekt, auf das der Befehl ausgeführt werden soll.
     * @param args Die Argumente, die für den Befehl übergeben wurden.
     * @return Ein boolescher Wert der angibt ober der Befehl erfolgreich war.
     */
    boolean execute(MainLoop<T>.MainLoopHandle loopHandle, UserInterface userInterface, T game, String args);
}
