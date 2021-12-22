package edu.kit.informatik;

import edu.kit.informatik.maumau.game.Game;
import edu.kit.informatik.maumau.game.GameControllerInterface;
import edu.kit.informatik.maumau.ui.CommandManager;
import edu.kit.informatik.maumau.ui.StdIOInterface;
import edu.kit.informatik.ui.MainLoop;

/**
 * Die Main Klasse die die {@link Main#main(String[])} Methode beinhaltet.
 *
 * @author urqyv
 * @version 1.0
 */
public abstract class Main {
    /**
     * Die main Methode für das MauMau Spiel
     *
     * @param args Die an uns übergebenen Argumente.
     */
    public static void main(String[] args) {
        assert args.length == 0;

        Game game = new Game();
        MainLoop<GameControllerInterface> loop = new MainLoop<GameControllerInterface>(
            new CommandManager()
        );
        loop.run(
            new StdIOInterface(),
            game
        );
    }
}
