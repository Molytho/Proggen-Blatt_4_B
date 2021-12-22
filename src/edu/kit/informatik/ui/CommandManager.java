package edu.kit.informatik.ui;

/**
 * Modelliert eine Klasse mit deren Hilfe eine Eingabe an Befehle zugeordnet werden kann.
 *
 * @param <T> Der Typ auf dem die Befehle operieren.
 * @author urqyv
 * @version 1.0
 */
public interface CommandManager<T> {
    /**
     * Gibt den Befehl zurück, der dem Namen zugeordnet ist.
     *
     * @param name Der Name des Befehls der zurückgegeben werden soll.
     * @return Der Befehl der dem Namen zugeordnet ist.
     *         Falls kein Befehl zum gegebenen Namen gefunden wurde wird {@code null} zurück gegeben.
     */
    Command<T> getCommand(String name);

    /**
     * Gibt ein Objekt zurück, mit dessen Hilfe ein Eingabestring in zwei Hälften geteilt werden kann.
     * Die erste Hälfte ist der Befehlsname und die zweite sind die zu übergebenden Argumente
     *
     * @return Ein Argument Parser Objekt, mit dem Eingaben zerlegt werden können.
     */
    ArgumentParser getArgumentParser();

    /**
     * Modelliert eine Schnittstelle die Methoden spezifiziert mit deren Hilfe Eingaben zerlegt werden können.
     */
    interface ArgumentParser {
        /**
         * Zerlegt eine Eingabe in zwei Teile:
         *  - [0] Der Name des gegebenen Befehls
         *  - [1] Die Argumente, die dem Befehl übergeben werden sollen. Kann {@code null} sein.
         *
         * @param input Der Eingabestring der zerlegt werden soll.
         * @return Ein Array mit einer Länge von 2. An erster Stelle steht der Name des aufzurufenden Befehls.
         *         Der Befehl wird nicht auf Validität geprüft. Ob die Eingabe valide ist kann nur durch einen
         *         Aufruf von {@link CommandManager#getCommand(String)} festgestellt werden. An zweiter Stelle
         *         befindet sich der Substring der dem Befehl als letzter Parameter
         *         an {@link Command#execute(MainLoop.MainLoopHandle, UserInterface, Object, String)} übergeben
         *         werden soll. Die zweite Stelle kann {@code null} sein genau dann wenn keine Argumente übergeben
         *         wurden.
         * @throws FormatException Die Eingabe entspricht nicht dem vom Parser erwarteten Format.
         */
        String[] parse(String input) throws FormatException;
    }
}
