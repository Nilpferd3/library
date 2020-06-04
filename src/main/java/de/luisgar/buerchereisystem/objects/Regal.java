package de.luisgar.buerchereisystem.objects;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Entwickelt von Luis Garcia S.
 */
public class Regal {

    //Diese Liste speichert alle im Regal vorhandenen Bücher
    private final List<Buch> buecher = Lists.newArrayList();

    /**
     * Diese Methode fügt ein im Parameter angegebenes Buch der Bücher-Liste hinzu.
     * @param buch
     */
    public void addBuch(final Buch buch){
        this.buecher.add(buch);
    }

    /**
     * Diese Methode gibt alle im Regal gespeicherten Bücher wieder
     * @return
     */
    public List<Buch> getBuecher() {
        return buecher;
    }
}
