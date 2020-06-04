package de.luisgar.buerchereisystem;

import de.luisgar.buerchereisystem.console.ConsoleHandler;
import de.luisgar.buerchereisystem.file.FileSystem;
import de.luisgar.buerchereisystem.listener.InputListener;
import de.luisgar.buerchereisystem.manager.LibraryManager;

import java.io.File;

/**
 * Entwickelt von Luis Garcia S.
 */
public class Buecherei {

    /**
     * Diese Methode wird immer beim Start des Programmes aufgerufen.
     * @param args
     */
    public static void main(String[] args) {

        final LibraryManager libraryManager = new LibraryManager();
        final ConsoleHandler consoleHandler = new ConsoleHandler();

        consoleHandler.fuegeListenerHinzu(new InputListener()); //Registrieren des InputListeners
        consoleHandler.listen(); //Dem ConsoleHandler sagen, dass er jetzt die Console auslesen soll.

    }

}
