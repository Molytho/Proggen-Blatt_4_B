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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementiert die Kommandozeilenbefehle die beim MauMau Spiel ausgeführt werden können
 *
 * @author urqyv
 * @version 1.0
 */
public enum Commands implements Command<GameControllerInterface> {
    /**
     * Implementiert den "start <seed>" Befehl.
     * Als seed sind long-Werte erlaubt.
     */
    START {
        //Prüft, ob die Eingabe eine Zahl ist
        private static final String REGEX_STRING = "^[0-9]+$";
        private final Pattern regexPattern = Pattern.compile(REGEX_STRING);

        @Override
        public boolean execute(MainLoop<GameControllerInterface>.MainLoopHandle loopHandle,
                               UserInterface userInterface, GameControllerInterface game, String args) {
            Matcher matcher = checkAndPromptInvalidArguments(userInterface, regexPattern, args);
            if (matcher == null) {
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
    /**
     * Implementiert den "show <game|Spielernummer>" Befehl.
     * Als Argument sind das Wort "game" oder eine Zahl von 1 bis einschließlich 4 erlaubt.
     */
    SHOW {
        // Prüft, ob die Eingabe "game" oder eine Zahl von 1 bis 4 ist.
        private static final String REGEX_STRING = "^game|[1-4]$";
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
            Player player;
            try {
                // Hier sollte nie eine NumberFormatException geworfen werden, da das Regex
                // sowohl Format, als auch Größe der Zahl überprüft.
                int playerId = Integer.parseInt(argumentMatcher.group(0));
                player = game.getPlayerWithId(playerId);
            } catch (InvalidGameStateException ex) {
                userInterface.printOutput(ex.getMessage());
                return false;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (Card cardOnHand : player) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append(cardOnHand);
            }
            userInterface.printOutput(stringBuilder.toString());

            return true;
        }

        @Override
        public boolean execute(MainLoop<GameControllerInterface>.MainLoopHandle loopHandle, UserInterface userInterface,
                               GameControllerInterface game, String args) {
            Matcher matcher = checkAndPromptInvalidArguments(userInterface, regexPattern, args);
            if (matcher == null) {
                return false;
            }

            String firstArgument = matcher.group(0);
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
    /**
     * Implementiert den "discard <Spielernummer> <Karte>" Befehl.
     */
    DISCARD {
        // Erlaubte Eingaben sind:
        // Zahl von 1 bis 4
        // + Leerzeichen
        // + Zahl von 7 bis 10 oder B, D, K oder A
        // + E, L, H oder S
        private static final String REGEX_STRING = "^([1-4]) ([7-9BDKA]|10)([ELHS])$";
        private final Pattern regexPattern = Pattern.compile(REGEX_STRING);

        @Override
        public boolean execute(MainLoop<GameControllerInterface>.MainLoopHandle loopHandle, UserInterface userInterface,
                               GameControllerInterface game, String args) {
            Matcher matcher = checkAndPromptInvalidArguments(userInterface, regexPattern, args);
            if (matcher == null) {
                return false;
            }

            // Hier kann keine NumberFormatException geworfen werden da sowohl Format als auch Größe der Eingabe
            // schon geprüft sind.
            int playerId = Integer.parseInt(matcher.group(1));
            CardValue cardValue = CardValue.fromString(matcher.group(2));
            CardFamily cardFamily = CardFamily.fromString(matcher.group(3));

            Card card = Deck.getInstance().getCard(cardFamily, cardValue);
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
    /**
     * Implementiert den "pick <Spielernummer>" Befehl.
     */
    PICK {
        // Prüft auf Zahlen von 1 bis 4
        private static final String REGEX_STRING = "^[1-4]$";
        private final Pattern regexPattern = Pattern.compile(REGEX_STRING);

        @Override
        public boolean execute(MainLoop<GameControllerInterface>.MainLoopHandle loopHandle, UserInterface userInterface,
                               GameControllerInterface game, String args) {
            Matcher matcher = checkAndPromptInvalidArguments(userInterface, regexPattern, args);
            if (matcher == null) {
                return false;
            }

            try {
                int playerId = Integer.parseInt(matcher.group(0));
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
    /**
     * Implementiert den "quit" Befehl.
     * Beendet die Hauptschleife.
     */
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

    private static Matcher checkAndPromptInvalidArguments(UserInterface userInterface,
                                                          Pattern regexPattern, String args) {
        Matcher result = null;
        if (Objects.nonNull(args)) {
            Matcher matcher = regexPattern.matcher(args);
            if (matcher.matches()) {
                result = matcher;
            } else {
                userInterface.printOutput(INVALID_ARGUMENT_FORMAT);
            }
        } else {
            userInterface.printOutput(INVALID_ARGUMENT_FORMAT);
        }
        return result;
    }
}
