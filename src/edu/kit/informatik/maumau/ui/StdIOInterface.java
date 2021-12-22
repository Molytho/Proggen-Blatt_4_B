package edu.kit.informatik.maumau.ui;

import edu.kit.informatik.ui.UserInterface;
import java.io.Closeable;
import java.util.Scanner;

/**
 * Implementiert Benutzerinteraktion über den Stdin und Stdout Stream
 *
 * @author urqyv
 * @version 1.0
 */
public class StdIOInterface implements UserInterface, Closeable {
    private final Scanner scanner;

    /**
     * Instanziiert ein neues StdIOInterface.
     * Es übernimmt dabei den Besitz des Standardeingabestreams, d.h. beim schließen wird der
     * Eingabestream auch geschlossen.
     */
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
