package edu.kit.informatik.maumau.ui;

import edu.kit.informatik.maumau.game.GameControllerInterface;
import edu.kit.informatik.ui.Command;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Modelliert den für die MainLoop gebrauchten CommandManager für das MauMau Spiel
 *
 * @author urqyv
 * @version 1.0
 */
public class CommandManager implements edu.kit.informatik.ui.CommandManager<GameControllerInterface> {
    private final Dictionary<String, Command<GameControllerInterface>> commandMap;

    /**
     * Erstellt ein neues CommandManager Objekt.
     * Bei der Erstellung werden die Befehle mit Hilfe des {@link Commands} Enums initialisiert.
     */
    public CommandManager() {
        Commands[] commands = Commands.values();
        commandMap = new Hashtable<>(commands.length);

        for (Commands command : commands) {
            commandMap.put(command.name().toLowerCase(), command);
        }
    }

    @Override
    public Command<GameControllerInterface> getCommand(String name) {
        return commandMap.get(name);
    }

    @Override
    public ArgumentParser getArgumentParser() {
        return new Parser();
    }

    /**
     * Implementiert den {@link edu.kit.informatik.ui.CommandManager.ArgumentParser} für das MauMau Spiel.
     */
    private class Parser implements ArgumentParser {
        @Override
        public String[] parse(String input) {
            String[] splitArray = input.split(" ", 2);

            String[] returnValue;
            if (splitArray.length == 2) {
                returnValue = splitArray;
            } else {
                assert splitArray.length == 1;
                returnValue = new String[] {splitArray[0], null};
            }
            return returnValue;
        }
    }
}
