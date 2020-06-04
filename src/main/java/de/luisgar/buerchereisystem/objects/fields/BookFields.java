package de.luisgar.buerchereisystem.objects.fields;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

/**
 * Entwickelt von Luis Garcia S.
 */
public enum BookFields {

    TITEL("name"),
    SEITEN("pages"),

    REGALCODE("regalcode"),
    BUCHCODE("bookcode"),

    AUSLEIHER("borrower"),
    ISTAUSGELIEHEN("isBorrowed");

    private final String key;

}
