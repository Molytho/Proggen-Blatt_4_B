package edu.kit.informatik.ui;

import java.util.Objects;

/**
 * Modelliert eine Hauptschleife mit der sequenziell Eingaben verarbeitet werden können..
 *
 * @param <T> Der Typ auf den die Befehle arbeiten.
 * @author urqyv
 * @version 1.0
 */
public class MainLoop<T> {
    private static final String COMMAND_NOT_FOUND = "Error, command not found";
    private final CommandManager.ArgumentParser parser;
    private final CommandManager<T> manager;
    private final MainLoopHandle handleSingleton;
    private boolean run;

    /**
     * Erstellt ein neues Objekt der MainLoop Klasse.
     *
     * @param commandManager Ein Objekt das benutzt wird um Eingaben zu verarbeiten.
     */
    public MainLoop(CommandManager<T> commandManager) {
        this.parser = commandManager.getArgumentParser();
        this.manager = commandManager;
        this.handleSingleton = new MainLoopHandle();
        run = false;
    }

    /**
     * Beendet eine durch run gestartete Schleife nach der Verarbeitung des aktuellen Befehls.
     */
    public void stop() {
        run = false;
    }

    /**
     * Verarbeitet eine Eingabe und gibt dann zurück, ob er erfolgreich war.
     *
     * @param userInterface    Ein Objekt, über das mit dem Nutzer kommuniziert werden kann.
     * @param objectOfInterest Das Objekt, auf das die Befehle ausgeführt werden soll.
     * @return Ein boolescher Wert der angibt ob der auszuführende Befehl erfolgreich war.
     */
    public boolean dispatch(final UserInterface userInterface, T objectOfInterest) {
        Objects.requireNonNull(userInterface);
        Objects.requireNonNull(objectOfInterest);

        String input = userInterface.readInput();
        String[] parsedInput;
        try {
            parsedInput = parser.parse(input);
        } catch (FormatException e) {
            userInterface.printOutput(e.getMessage());
            return false;
        }
        Command<T> command = manager.getCommand(parsedInput[0]);
        if (command != null) {
            return command.execute(handleSingleton, userInterface, objectOfInterest, parsedInput[1]);
        } else {
            userInterface.printOutput(COMMAND_NOT_FOUND);
            return false;
        }
    }

    /**
     * Führt {@link MainLoop#dispatch(UserInterface, Object)} in einer Schleife aus.
     *
     * @param userInterface    Ein Objekt, über das mit dem Nutzer kommuniziert werden kann.
     * @param objectOfInterest Das Objekt, auf das die Befehle ausgeführt werden soll.
     */
    public void run(final UserInterface userInterface, T objectOfInterest) {
        Objects.requireNonNull(userInterface);
        Objects.requireNonNull(objectOfInterest);

        run = true;
        do {
            dispatch(userInterface, objectOfInterest);
        } while (run);
    }

    /**
     * Modelliert ein Objekt, über das Befehle mit der Schleife interagieren können.
     */
    public class MainLoopHandle {
        /**
         * Stoppt die Schleife, die mit dem Handle assoziiert ist.
         */
        public void stop() {
            MainLoop.this.stop();
        }
    }
}
