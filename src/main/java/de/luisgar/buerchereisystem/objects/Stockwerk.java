package de.luisgar.buerchereisystem.objects;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Entwickelt von Luis Garcia S.
 */
public class Stockwerk {

    //In dieser Liste werden alle Regale vom Stockwerk x gespeichert
    private final List <Regal> regale = Lists.newArrayList();

    /**
     * Diese Methode erstellt ein neues Regal, das in der Regal-Liste gespeichert wird
     */
    public void createRegal(){
        this.regale.add(new Regal());
    }

    /**
     * Diese Methode gibt alle Regale des Stockwerkes zurück
     * @return
     */
    public List<Regal> getRegale() {
        return regale;
    }
}
