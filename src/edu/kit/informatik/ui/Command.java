package edu.kit.informatik.ui;

public interface Command<T> {
    boolean execute(MainLoop<T>.MainLoopHandle loopHandle, UserInterface userInterface, T game, String args);
}
