package edu.kit.informatik.maumau.ui;

import edu.kit.informatik.ui.UserInterface;
import java.io.Closeable;
import java.util.Scanner;

public class StdIOInterface implements UserInterface, Closeable {
    private final Scanner scanner;

    public StdIOInterface() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String readInput() {
        return scanner.nextLine();
    }

    @Override
    public void printOutput(String message) {
        System.out.println(message);
    }

    @Override
    public void close() {
        scanner.close();
    }
}
