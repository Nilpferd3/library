package de.luisgar.buerchereisystem.console;

import com.google.common.collect.Lists;
import de.luisgar.buerchereisystem.console.listener.ConsoleListener;
import de.luisgar.buerchereisystem.manager.LibraryManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Entwickelt von Luis Garcia S.
 */
public class ConsoleHandler {

    //Liste zum Speichern aller Listener
    private final List<ConsoleListener> consoleListeners;

    // Reader (Ausleser), der die Consolen Nachrichten, die einkommen, verarbeitet.
    private final BufferedReader bufferedReader;

    /**
     * Dieser Konstruktor initalisiert und lädt das Intro d.h. der "Vorspann" für das Programm
     */
    public ConsoleHandler(){
        this.consoleListeners = Lists.newArrayList();

        //Abruf der Systemeingabe (Systen.in (input))
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        this.zeigeIntro();
    }

    /**
     * Diese Methode zeigt in der Konsole die Standard-Nachricht
     */
    private void zeigeIntro() {
        System.out.println("Entwickelt von Luis Garcia im Jahre 2020");
        System.out.println("Die Bücherrei wurde geladen!\n" +
                "Gebe 'hilfe' ein, um eine kurze Übersicht aller Befehle zu erhalten.");
    }

    /**
     * Diese Methode fügt einen Listener d.h. "Überprüfer oder Ausleser" in die Listener-Liste hinzu
     * @param consoleListener
     */
    public void fuegeListenerHinzu(final ConsoleListener consoleListener){
        this.consoleListeners.add(consoleListener);
    }

    /**
     * Diese Methode liest alle eingehenden Konsolen-Nachrichten und gibt diese an die in der Listener-Liste gespeicherteten
     * Listener weiter.
     */
    public void listen(){

        String consolenEingabe;

        //Auslesen der Consolen-Eingabe
        try {
             consolenEingabe = this.bufferedReader.readLine();
        } catch (IOException e) {
            consolenEingabe = null;
            e.printStackTrace(); //Falls ein Fehler auftaucht, wird er ausgegeben
        }

        if(consolenEingabe != null){ //Abfrage, ob kein Fehler aufgetaucht ist, da beim try{} initalisiert wird.
            //Weitergabe an die Console-Listeners
            for (ConsoleListener listener : this.consoleListeners) {
                final String listenerAntwort = listener.onReceive(consolenEingabe); //Antwort des Listener beim Aufruf von onReceive()

                if(listenerAntwort != null){ //Überprüfen, ob eine Rückgabe erfolgen soll, da falls Antwort null ist, nichts ausgegeben werden soll
                    System.out.println(listenerAntwort); //Ausgeben der Antwort, da diese nicht null ist
                }
            }
        }

        this.listen(); //Erneutes Abrufen der Methode, um einen Loop zu generieren, der permanent auf Eingabe wartet.
    }

}
