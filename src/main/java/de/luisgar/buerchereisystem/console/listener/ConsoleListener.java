package de.luisgar.buerchereisystem.console.listener;

/**
 * Entwickelt von Luis Garcia S.
 */
public interface ConsoleListener {

    /**
     * Diese Methode wird beim Empfangen einer Nachricht ausgeführt
     * @param input
     * @return Falls return != null wird eine Nachricht in der Console ausgegeben
     */
    String onReceive(String input);

}
