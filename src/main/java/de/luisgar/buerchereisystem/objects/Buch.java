package de.luisgar.buerchereisystem.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

/**
 * Entwickelt von Luis Garcia S.
 */
public class Buch {

    private final String buchTitel;
    private final int anzahlDerSeiten;

    private String ausleiher;
    private boolean ausgeliehen;

    /**
     * Initalisierung der relevanten Objekten
     * @param buchTitel
     * @param anzahlDerSeiten
     */
    public Buch(final String buchTitel, final int anzahlDerSeiten){
        this.buchTitel = buchTitel;
        this.anzahlDerSeiten = anzahlDerSeiten;
    }

}
