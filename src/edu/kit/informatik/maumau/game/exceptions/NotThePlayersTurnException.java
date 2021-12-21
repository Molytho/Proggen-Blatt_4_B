package edu.kit.informatik.maumau.game.exceptions;

public class NotThePlayersTurnException extends RuleException {
    public NotThePlayersTurnException(String message) {
        super(message);
    }
}
