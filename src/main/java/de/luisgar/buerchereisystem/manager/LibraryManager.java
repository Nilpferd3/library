package de.luisgar.buerchereisystem.manager;

import com.google.common.collect.Lists;
import de.luisgar.buerchereisystem.file.FileSystem;
import de.luisgar.buerchereisystem.objects.Buch;
import de.luisgar.buerchereisystem.objects.Regal;
import de.luisgar.buerchereisystem.objects.Stockwerk;
import de.luisgar.buerchereisystem.objects.fields.BookFields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entwickelt von Luis Garcia S.
 */
public class LibraryManager {

    public static LibraryManager instance;

    public final FileSystem fileSystem = new FileSystem("data");
    public final List<Stockwerk> stockwerke = Lists.newArrayList();

    /**
     * Dieser Konstruktor initialisiert die Instanze und ruft die setupFile()-Methode auf.
     */
    public LibraryManager(){
        instance = this;

        this.setupFile();
        this.importFromFile();
    }

    /**
     * Diese Methode erstellt die Standard-Werte, die das Programm benötigt, um Daten von der Speicher-Datei auszulesen, die
     * in diesem Fall 'data.yml' genannt wird.
     */
    private void setupFile() {
        this.fileSystem.getYamlConfiguration().addDefault("stockwerke",new ArrayList<ArrayList<ArrayList<Map>>>());
        this.fileSystem.getYamlConfiguration().options().copyDefaults(true);

        this.fileSystem.save();
    }

    /**
     * Diese Methode lädt die Daten aus der Datei data.yml, die den Aufbau der Bibliothek speichert, um
     * beim Starten die bereits existierenden Stockwerke, Regale und Bücher zu importieren bzw. zu laden.
     */
    private void importFromFile() {

        final List<List<List<Map>>> stockwerke = this.fileSystem.get("stockwerke");

        for (int i = 0; i < stockwerke.size(); i++) {
            final Stockwerk stockwerk = new Stockwerk();
            final List<List<Map>> regale = stockwerke.get(i);

            for (List<Map> rawregal : regale) {
                final Regal regal = new Regal();

                for (Map buecher : rawregal) {
                    final Buch buch = new Buch((String)buecher.get(BookFields.TITEL.getKey()),
                            (Integer) buecher.get(BookFields.SEITEN.getKey()));

                    buch.setAusgeliehen((Boolean) buecher.get(BookFields.ISTAUSGELIEHEN.getKey()));
                    buch.setAusleiher((String) buecher.get(BookFields.AUSLEIHER.getKey()));

                    regal.getBuecher().add(buch);
                }
                stockwerk.getRegale().add(regal);
            }
            this.stockwerke.add(stockwerk);
        }
    }

    /**
     * Diese Methode erstellt ein neues Objekt der Klasse Stockwerk, das in der Liste, in der alle Stockwerke gespeichert werden
     * gespeichert wird.
     */
    public void baueStockwerk(){
        final List<List<List<Map>>> rowstockwerke = this.fileSystem.get("stockwerke");
        rowstockwerke.add(new ArrayList<>());

        this.stockwerke.add(new Stockwerk());
        this.fileSystem.set("stockwerke",rowstockwerke);
    }

    /**
     * Diese Methode erstellt ein neues Objekt der Klasse Regal, das in das als Parameter 'stockwerk' angegebene Stockwerk
     * gespeicert wird.
     * @param stockwerk
     */
    public void neuesRegal(final int stockwerk){
        final List<List<List<Map>>> rowstockwerke = this.fileSystem.get("stockwerke");
        rowstockwerke.get(stockwerk).add(new ArrayList<>());

        this.stockwerke.get(stockwerk).getRegale().add(new Regal());
        this.fileSystem.set("stockwerke",rowstockwerke);
    }

    /**
     * Diese Methode erstellt ein neues Objekt der Klasse Buch und speichert dieses in einen
     * freien Regal. (Ein Regal kann in diesem Fall maximal 5 Bücher enthalten)
     * @param titel
     * @param seitenanzahl
     * @param etage
     * @return
     */
    public boolean neuesBuch(final String titel,final int seitenanzahl,final int etage) {

        final Buch buch = new Buch(titel,seitenanzahl);

        buch.setAusleiher(null);
        buch.setAusgeliehen(false);

        for (int i = 0; i < this.stockwerke.get(etage).getRegale().size(); i++) {
            final Regal regal = this.stockwerke.get(etage).getRegale().get(i);

            if(regal.getBuecher().size() < 5){ //Abfrage, ob weniger als 5 Bücher im Regal sind.
                regal.getBuecher().add(buch);

                final List<List<List<Map>>> rowstockwerke = this.fileSystem.get("stockwerke");
                final Map rowbuch = new HashMap();

                rowbuch.put(BookFields.TITEL.getKey(),titel);
                rowbuch.put(BookFields.SEITEN.getKey(),seitenanzahl);
                rowbuch.put(BookFields.AUSLEIHER.getKey(),null);
                rowbuch.put(BookFields.ISTAUSGELIEHEN.getKey(),false);

                rowstockwerke.get(etage).get(i).add(rowbuch);
                this.fileSystem.set("stockwerke",rowstockwerke);
                return true;
            }
        }
        return false;
    }

    /**
     * Diese Methode setzt das Buch mit angegebenem Parameter 'titel' als ausgeliehen
     * @param titel
     * @param ausleiher
     */
    public void leiheBuch(final String titel, final String ausleiher){
        final Buch buch = this.getBuchByName(titel);
        final List<List<List<Map>>> rowstockwerke = this.fileSystem.get("stockwerke");
        rowstockwerke.get(this.getBuchStockwerk(buch)).get(this.getBuchRegal(buch)).get(this.getBuchRegalIndex(buch))
                .put(BookFields.AUSLEIHER,ausleiher);
        rowstockwerke.get(this.getBuchStockwerk(buch)).get(this.getBuchRegal(buch)).get(this.getBuchRegalIndex(buch))
                .put(BookFields.ISTAUSGELIEHEN,true); //Richtig, man könnte auch einfach abfragen, ob der Ausleiher nicht null ist,
                                                      // dennoch ist es für Schüler so einfacher zu verstehen.
        this.stockwerke.get(this.getBuchStockwerk(buch)).getRegale().get(this.getBuchRegal(buch)).getBuecher().get(
                this.getBuchRegalIndex(buch)).setAusleiher(ausleiher);
        this.stockwerke.get(this.getBuchStockwerk(buch)).getRegale().get(this.getBuchRegal(buch)).getBuecher().get(
                this.getBuchRegalIndex(buch)).setAusgeliehen(true);
        this.fileSystem.set("stockwerke",rowstockwerke);
    }

    /**
     * Diese Methode setzt das Buch mit angegebenem Parameter 'titel' als zurückgegeben
     * @param titel
     */
    public void gebeBuch(final String titel){
        final Buch buch = this.getBuchByName(titel);
        final List<List<List<Map>>> rowstockwerke = this.fileSystem.get("stockwerke");
        rowstockwerke.get(this.getBuchStockwerk(buch)).get(this.getBuchRegal(buch)).get(this.getBuchRegalIndex(buch))
                .put(BookFields.AUSLEIHER,null);
        rowstockwerke.get(this.getBuchStockwerk(buch)).get(this.getBuchRegal(buch)).get(this.getBuchRegalIndex(buch))
                .put(BookFields.ISTAUSGELIEHEN,false); //Richtig, man könnte auch einfach abfragen, ob der Ausleiher nicht null ist,
        // dennoch ist es für Schüler so einfacher zu verstehen.
        this.stockwerke.get(this.getBuchStockwerk(buch)).getRegale().get(this.getBuchRegal(buch)).getBuecher().get(
                this.getBuchRegalIndex(buch)).setAusleiher(null);
        this.stockwerke.get(this.getBuchStockwerk(buch)).getRegale().get(this.getBuchRegal(buch)).getBuecher().get(
                this.getBuchRegalIndex(buch)).setAusgeliehen(false);
        this.fileSystem.set("stockwerke",rowstockwerke);
    }

    /**
     * Diese Methode gibt das Stockwerk des im Parameter angegebenen Buch wieder
     * @param buch
     * @return
     */
    public Integer getBuchStockwerk(final Buch buch){

        for (int i = 0; i < this.stockwerke.size(); i++) {
            if(this.stockwerke.get(i).getRegale().contains(this.getBuchByName(buch.getBuchTitel())))return i;
        }

        return 0;
    }

    /**
     * Diese Methode gibt das Regal des im Parameter angegebenen Buch wieder
     * @param buch
     * @return
     */
    public Integer getBuchRegal(final Buch buch){

        for (int i = 0; i < this.stockwerke.get(this.getBuchStockwerk(buch)).getRegale().size(); i++) {
            if(this.stockwerke.get(this.getBuchStockwerk(buch)).getRegale().get(i).getBuecher().contains(
                    this.getBuchByName(buch.getBuchTitel())))return i;
        }
        return 0;
    }

    /**
     * Diese Methode gibt den Regal-Index des im Parameter angegebenen Buch wieder
     * @param buch
     * @return
     */
    public Integer getBuchRegalIndex(final Buch buch){

        for (int i = 0; i < this.stockwerke.get(this.getBuchStockwerk(buch)).getRegale().get(this.getBuchRegal(buch)).getBuecher().size(); i++) {
            if(this.stockwerke.get(this.getBuchStockwerk(buch)).getRegale().get(this.getBuchRegal(buch)).getBuecher().get(i)
            .equals(this.getBuchByName(buch.getBuchTitel())))return i;
        }

        return 0;
    }

    /**
     * Diese Methode sucht nach ein Buch mit den angebenen Titel als Parameter und greift auf die Methode
     * getAllBooks() zurück.
     * @param titel
     * @return
     */
    public Buch getBuchByName(final String titel){
        for (Buch buch : this.getAllBooks()) {
            if(buch.getBuchTitel().equalsIgnoreCase(titel)){
                return buch;
            }
        }
        return null;
    }

    /**
     * Diese Methode gibt eine Liste aller Bücher zurück, die in den Stockwerken bzw. Regalen gespeichert sind
     * @return
     */
    public List<Buch> getAllBooks(){

        final List<Buch> out = Lists.newArrayList();

        //Suche alle Bücher aus jedem Regal in jedem Stockwerk und füge dieses zur Liste hinzu
        this.stockwerke.forEach(stockwerk -> stockwerk.getRegale().forEach(regal -> out.addAll(regal.getBuecher())));

        return out;
    }

    /**
     * Diese Methode gibt die Liste zurück, in der alle Stockwerke gespeichert werden
     * @return
     */
    public List<Stockwerk> getStockwerke() {
        return stockwerke;
    }
}
