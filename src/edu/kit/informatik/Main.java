package edu.kit.informatik;

import edu.kit.informatik.maumau.game.Game;
import edu.kit.informatik.maumau.game.GameControllerInterface;
import edu.kit.informatik.maumau.ui.CommandManager;
import edu.kit.informatik.maumau.ui.StdIOInterface;
import edu.kit.informatik.ui.MainLoop;

public abstract class Main {
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
