package de.luisgar.buerchereisystem.listener;

import de.luisgar.buerchereisystem.console.listener.ConsoleListener;
import de.luisgar.buerchereisystem.manager.LibraryManager;
import de.luisgar.buerchereisystem.objects.Buch;
import de.luisgar.buerchereisystem.objects.Regal;
import de.luisgar.buerchereisystem.objects.Stockwerk;

/**
 * Entwickelt von Luis Garcia S.
 */
public class InputListener implements ConsoleListener {

    public static final String NOTFOUND = "Befehl nicht gefunden. Gebe 'hilfe' ein, um alle Befehle zu sehen";

    /**
     * Diese Methode wird automatisch vom ConsoleHandler aufgerufen, sobald ein neuer Input einkommt bzw. eine neue
     * Nachricht in die Konsole geschrieben wird.
     * @param input
     * @return
     */
    public String onReceive(String input) {

        final String args[] = input.split(" ");
        final String command = args[0].toLowerCase();

        switch (args.length){
            case 1:
                switch (command){
                    case "hilfe": return this.help();
                    case "neuesstockwerk":
                        this.animateText("Gebäude wird erweitert...");
                        LibraryManager.instance.baueStockwerk();
                        System.out.print("Bauvorgang abgeschlossen!");
                        break;
                    case "info":
                        return "Es gibt insgesamt " + LibraryManager.instance.getStockwerke().size() + " Stockwerke/Etagen mit " +
                                "insgesamt " +
                                (LibraryManager.instance.getAllBooks().size() == 1 ? "einen Buch" : LibraryManager.instance.getAllBooks().size() +
                                        " Bücher") + ".";
                    case "gebaeude":

                        final StringBuilder building = new StringBuilder();

                        for (int i = 0; i < LibraryManager.instance.getStockwerke().size(); i++) {
                            final int size = LibraryManager.instance.getStockwerke().size() - i-1;
                            final Stockwerk stockwerk = LibraryManager.instance.getStockwerke()
                                    .get(size);

                            System.out.println(size + ". Stockwerk:");

                            for (int r = 0; r < stockwerk.getRegale().size(); r++) {
                                final int regalSize = stockwerk.getRegale().size() - r-1;
                                final Regal regal = stockwerk.getRegale().get(regalSize);

                                System.out.println("   " + regalSize + ". Regal");

                                regal.getBuecher().forEach(buch -> {
                                    System.out.println("      Buch: " + buch.getBuchTitel());
                                    System.out.println("         Seitenanzahl: " + buch.getAnzahlDerSeiten());
                                    System.out.println("         Ausgeliehen?: " + (buch.isAusgeliehen() ? "Ja" : "Nein"));
                                    System.out.println("         Ausgeliehen von: " + (buch.getAusleiher() == null ?
                                            "Niemand" : buch.getAusleiher()));
                                });
                            }

                        }

                        return building.toString();
                }

                break;
            case 2:
                switch (command){
                    case "neuesregal":
                        this.animateText("Regal wird aufgestellt...");
                        LibraryManager.instance.neuesRegal(Integer.parseInt(args[1]));
                        System.out.print("Bauvorgang abgeschlossen!");
                        break;
                    case "zurueck":
                        final Buch buch = LibraryManager.instance.getBuchByName(args[1]);
                        if(buch != null){
                            if(buch.isAusgeliehen()){
                                LibraryManager.instance.gebeBuch(buch.getBuchTitel());
                                return "Das Buch wurde zurückgegeben.";
                            }else return "Das Buch ist nicht ausgeliehen";
                        }else return "Das Buch ist nicht in der Bibliothek";
                }

                break;
            case 3:
                switch (command){
                    case "ausleihen":
                        final Buch buch = LibraryManager.instance.getBuchByName(args[1]);
                        if(buch != null){
                            if(!buch.isAusgeliehen()){
                                LibraryManager.instance.leiheBuch(buch.getBuchTitel(),args[2]);
                                return "Das Buch wurde zurückgegeben.";
                            }else return "Das Buch ist bereits verliehen";
                        }else return "Das Buch ist nicht in der Bibliothek";
                }
                break;
            case 4:
                switch (command){
                    case "neuesbuch":
                        this.animateText("Buch wird geschrieben und eingeräumt...");
                        if(LibraryManager.instance.neuesBuch(args[1],Integer.parseInt(args[2]),Integer.parseInt(args[3]))){
                            System.out.println("Buch wurde erfolgreich geschrieben und untergebracht.");
                        }else{
                            System.out.println("Alle Regale sind belegt.");
                        }
                        break;
                }

                break;
        }
        return NOTFOUND;
    }

    /**
     * Diese Methode gibt die Übersicht aller Befehle in Form eines Strings/Text-Objekts wieder.
     * @return
     */
    private String help() {

        final StringBuilder string = new StringBuilder();

        string.append("Übersicht [Hilfe]").append("\n");
        string.append("'hilfe' - Anzeigen dieses Hilfe-Menüs").append("\n");
        string.append("'info' - Gibt Informationen über das Archive").append("\n");
        string.append("'gebaeude' - Gibt eine Übersicht vom Gebäude").append("\n");
        string.append("'leihe [Buchtitel] [Ausleiher]' - Leihe ein Buch aus").append("\n");
        string.append("'zurueck [Buchtitel]' - Gebe ein Buch zurück").append("\n");
        string.append("'neuesStockwerk' - Erstelle eine neues Stockwerk").append("\n");
        string.append("'neuesRegal [Etage]' - Erstelle ein neues Regal").append("\n");
        string.append("'neuesBuch [Titel] [Seitenanzahl] [Etage]' - Erstelle ein neues Buch").append("\n");
        string.append("Anmerkung: Es wird automatisch nach einen freien Regal gesucht, da ein Regal nur 5 Bücher beinhalten darf.")
                .append("\n");

        return string.toString();
    }

    /**
     * Diese Methode gibt eine animierte Nachricht in der Konsole aus, die wie geschrieben auszieht.
     * @param texts
     */
    public void animateText(final String... texts){
        for (String text : texts) {
            for (int i = 0; i < text.length(); i++) {
                System.out.print(text.charAt(i));
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print("\n");
        }
    }

}
