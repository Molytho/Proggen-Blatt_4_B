package edu.kit.informatik.maumau.ui;

import edu.kit.informatik.maumau.game.Card;
import edu.kit.informatik.maumau.game.CardFamily;
import edu.kit.informatik.maumau.game.CardValue;
import edu.kit.informatik.maumau.game.Deck;
import edu.kit.informatik.maumau.game.GameControllerInterface;
import edu.kit.informatik.maumau.game.GameState;
import edu.kit.informatik.maumau.game.Player;
import edu.kit.informatik.maumau.game.exceptions.InvalidGameStateException;
import edu.kit.informatik.maumau.game.exceptions.RuleException;
import edu.kit.informatik.ui.Command;
import edu.kit.informatik.ui.MainLoop;
import edu.kit.informatik.ui.UserInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands implements Command<GameControllerInterface> {
    START {
        private static final String REGEX_STRING = "^[0-9]+$";
        private final Pattern regexPattern = Pattern.compile(REGEX_STRING);

        @Override
        public boolean execute(MainLoop<GameControllerInterface>.MainLoopHandle loopHandle,
                               UserInterface userInterface, GameControllerInterface game, String args) {
            Matcher matcher;
            if (args == null
                || !(matcher = regexPattern.matcher(args)).matches()) {
                userInterface.printOutput(INVALID_ARGUMENT_FORMAT);
                return false;
            }

            long seed;
            try {
                seed = Long.parseLong(matcher.group(0));
            } catch (NumberFormatException ex) {
                userInterface.printOutput(NUMBER_TOO_LONG);
                return false;
            }

            game.startGame(seed);

            userInterface.printOutput("Player 1 takes the turn.");
            return true;
        }
    },
    SHOW {
        private static final String REGEX_STRING = "^(game|[1-4])$";
        private final Pattern regexPattern = Pattern.compile(REGEX_STRING);

        private boolean executeGameSubcommand(UserInterface userInterface,
                                              GameControllerInterface game) {
            StringBuilder stringBuilder = new StringBuilder(3);
            try {
                stringBuilder.append(game.getCurrentCard());
                stringBuilder.append(" / ");
                stringBuilder.append(game.sizeOfCardStack());
            } catch (InvalidGameStateException e) {
                userInterface.printOutput(e.getMessage());
                return false;
            }
            userInterface.printOutput(stringBuilder.toString());
            return true;
        }

        private boolean executeDefault(UserInterface userInterface,
                                       GameControllerInterface game, Matcher argumentMatcher) {
            try {
                int playerId = Integer.parseInt(argumentMatcher.group(1));
                Player player = game.getPlayerWithId(playerId);

                StringBuilder stringBuilder = new StringBuilder();
                for (Card cardOnHand : player) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append(cardOnHand);
                }
                userInterface.printOutput(stringBuilder.toString());
            } catch (NumberFormatException ex) {
                userInterface.printOutput(NUMBER_TOO_LONG);
                return false;
            } catch (InvalidGameStateException ex) {
                userInterface.printOutput(ex.getMessage());
                return false;
            }

            return true;
        }

        @Override
        public boolean execute(MainLoop<GameControllerInterface>.MainLoopHandle loopHandle, UserInterface userInterface,
                               GameControllerInterface game, String args) {
            Matcher matcher;
            if (args == null
                || !(matcher = regexPattern.matcher(args)).matches()) {
                userInterface.printOutput(INVALID_ARGUMENT_FORMAT);
                return false;
            }
            String firstArgument = matcher.group(1);

            boolean returnValue;
            switch (firstArgument) {
                case "game":
                    returnValue = executeGameSubcommand(userInterface, game);
                    break;
                default:
                    returnValue = executeDefault(userInterface, game, matcher);
                    break;
            }
            return returnValue;
        }
    },
    DISCARD {
        private static final String REGEX_STRING = "^([1-4]) ([7-9BDKA]|10)([ELHS])$";
        private final Pattern regexPattern = Pattern.compile(REGEX_STRING);

        @Override
        public boolean execute(MainLoop<GameControllerInterface>.MainLoopHandle loopHandle, UserInterface userInterface,
                               GameControllerInterface game, String args) {
            Matcher matcher;
            if (args == null
                || !(matcher = regexPattern.matcher(args)).matches()) {
                userInterface.printOutput(INVALID_ARGUMENT_FORMAT);
                return false;
            }

            int playerId;
            CardValue cardValue;
            CardFamily cardFamily;
            try {
                playerId = Integer.parseInt(matcher.group(1));
                cardValue = CardValue.fromString(matcher.group(2));
                cardFamily = CardFamily.fromString(matcher.group(3));
            } catch (NumberFormatException ex) {
                // Ich hab keine Ahnung wie das passieren sollte. Dann muss das Regex kaputt sein ._.
                throw new IllegalStateException(ex);
            }

            Deck deck = Deck.getInstance();
            Card card = deck.getCard(cardFamily, cardValue);
            try {
                game.doPlayerDiscard(playerId, card);
            } catch (InvalidGameStateException | RuleException e) {
                userInterface.printOutput(e.getMessage());
                return false;
            }

            if (game.getState() == GameState.WON) {
                userInterface.printOutput(String.format(PLAYER_WON, playerId));
            }

            return true;
        }
    },
    PICK {
        private static final String REGEX_STRING = "^([1-4])$";
        private final Pattern regexPattern = Pattern.compile(REGEX_STRING);

        @Override
        public boolean execute(MainLoop<GameControllerInterface>.MainLoopHandle loopHandle, UserInterface userInterface,
                               GameControllerInterface game, String args) {
            Matcher matcher;
            if (args == null
                || !(matcher = regexPattern.matcher(args)).matches()) {
                userInterface.printOutput(INVALID_ARGUMENT_FORMAT);
                return false;
            }

            int playerId;
            try {
                playerId = Integer.parseInt(matcher.group(0));
            } catch (NumberFormatException e) {
                // Ich versteh irgendwas an Regex falsch.
                throw new IllegalStateException(e);
            }

            try {
                game.doPlayerDraw(playerId);
            } catch (InvalidGameStateException | RuleException e) {
                userInterface.printOutput(e.getMessage());
                return false;
            }

            if (game.getState() == GameState.DRAW) {
                userInterface.printOutput(DRAW_MESSAGE);
            }

            return true;
        }
    },
    QUIT {
        @Override
        public boolean execute(MainLoop<GameControllerInterface>.MainLoopHandle loopHandle, UserInterface userInterface,
                               GameControllerInterface game, String args) {
            if (args != null) {
                userInterface.printOutput(TOO_MANY_ARGUMENTS);
                return false;
            }

            loopHandle.stop();

            return true;
        }
    };

    private static final String TOO_MANY_ARGUMENTS = "Error, too many arguments where given";
    private static final String INVALID_ARGUMENT_FORMAT = "Error, argument(s) has/have bad format.";
    private static final String NUMBER_TOO_LONG = "Error, the given number was too long.";
    private static final String PLAYER_WON = "Game over: Player %d has won.";
    private static final String DRAW_MESSAGE = "Game over: Draw.";
}
