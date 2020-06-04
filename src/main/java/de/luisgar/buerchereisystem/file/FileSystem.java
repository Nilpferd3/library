package de.luisgar.buerchereisystem.file;

import org.simpleyaml.configuration.file.YamlConfiguration;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Entwickelt von Luis Garcia S.
 */
public class FileSystem {

    private final File file;
    private YamlConfiguration yamlConfiguration;

    /**
     * Dieser Konstruktor initalisiert und erstellt - sofern nötig - die im Parameter benannte Datei.
     * @param name
     */
    public FileSystem(final String name){
        this.file = new File(name + ".yml");

        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.yamlConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Diese Methode speichert die Datei ab
     */
    public void save() {
        try {
            this.yamlConfiguration.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Diese Methode lädt die Datei erneut in das Programm, falls beispielsweise manuelle Änderungen d.h.
     * Änderungen ohne Ausführung des Programms durchgeführt wurden.
     */
    private void reload() {
        try {
            this.yamlConfiguration.load(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Diese Methode setzt ein Objekt 'value' in die Datei, welches dann unter den angegebenem Schlüssel 'key' erreichbar ist.
     * @param key
     * @param value
     */
    public void set(final String key, final Object value){
        this.yamlConfiguration.set(key, value);
        this.save();
    }

    /**
     * Diese Methode ruft ein Objekt, das unter den angegebenem Schlüssel 'key' erreichbar ist, auf.
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(final String key){
        return (T) this.yamlConfiguration.get(key);
    }


    /**
     * Diese Methode ermöglicht das schnelle Hinzufügen von einer Map in eine beliebige Liste, die nur Maps enthält
     * @param key
     * @param map
     */
    public void addElementToList(final String key, final Map map){
        final List<Map<?,?>> mapList = this.get(key);
        mapList.add(map);

        this.yamlConfiguration.set(key,mapList);
        this.save();
    }

    /**
     * Diese Methode ermöglicht das schnelle Entfernen von einer Map in eine beliebige Liste, die nur Maps enthält
     * @param key
     * @param map
     */
    public void removeElementFromList(final String key, final Map map) {
        final List<Map<?, ?>> mapList = this.get(key);
        mapList.remove(map);

        this.yamlConfiguration.set(key, mapList);
        this.save();
    }

    /**
     * Diese Methode gibt die YAMLConfiguration wieder, die das bearbeiten von Datein ermöglicht.
     * @return
     */
    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
