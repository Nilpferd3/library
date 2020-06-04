# Library | Hausaufgabe

Da die E-Mail natürlich nicht so lange sein sollte, schreibe ich hier noch ein paar Informationen über den Aufbau des ganzen Programms.
Das Programm ist nach folgendem Prinzip aufgebaut:
  > Gebäude
    > Stockwerk
      > Regal
        > Bücher
          > Buch 
Alle aufgelisteten Klassen bzw. Elemente (außer das Gebäude) sind erstellbar und veränderbar.

## Erste Schritte

Zunächst muss die Jar-Datei zum Laufen gebracht werden:
`java -jar nameDerJar.jar`
In Folge dessen, öffnet sich ein Terminal.

Dannach sollte zunächst ein Stockwerk erstellt werden.
`neuesStockwerk`
In diesem Stockwerk, das keinen Namen benötigt, erstellen wir dann ein Regal.
`neuesRegal 0` (0, da Java anfängt von 0 zu zählen und wir das Regal im 0ten-Stockwerk haben wollen, da es sonst kein anderes gibt).
Daraufhin können wir ein Buch hinzufügen:
`neuesBuch NilpferdeSindToll 16`
Somit wurde ein Buch mit dem Namen NilpferdeSindToll und mit 16 Seiten erstellt.

Nun können wir `gebaeude` eingeben, um unsere eigene Bibliothek zu betrachten. Fertig! 
