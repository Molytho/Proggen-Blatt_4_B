package edu.kit.informatik.ui;

import java.util.Dictionary;

public interface CommandManager<T> {
    Dictionary<String, Command<T>> getCommands();
    ArgumentParser getArgumentParser();

    interface ArgumentParser {
        String[] parse(String input) throws FormatException;
    }
}
