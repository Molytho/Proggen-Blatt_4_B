package edu.kit.informatik.ui;

import java.util.Dictionary;
import java.util.Objects;

public class MainLoop<T> {
    private static final String COMMAND_NOT_FOUND = "Error, command not found";
    private final Dictionary<String, Command<T>> commands;
    private final CommandManager.ArgumentParser parser;
    private final MainLoopHandle handleSingleton;
    private boolean run;

    public MainLoop(CommandManager<T> commandManager) {
        this.commands = commandManager.getCommands();
        this.parser = commandManager.getArgumentParser();
        this.handleSingleton = new MainLoopHandle();
        run = false;
    }

    public void stop() {
        run = false;
    }

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
        Command<T> command = commands.get(parsedInput[0]);
        if (command != null) {
            return command.execute(handleSingleton, userInterface, objectOfInterest, parsedInput[1]);
        } else {
            userInterface.printOutput(COMMAND_NOT_FOUND);
            return false;
        }
    }

    public void run(final UserInterface userInterface, T objectOfInterest) {
        Objects.requireNonNull(userInterface);
        Objects.requireNonNull(objectOfInterest);

        run = true;
        do {
            dispatch(userInterface, objectOfInterest);
        } while (run);
    }


    public class MainLoopHandle {
        public void stop() {
            MainLoop.this.stop();
        }
    }
}
