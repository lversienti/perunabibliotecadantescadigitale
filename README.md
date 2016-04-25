# perunabibliotecadantescadigitale
it's the first prototype developed for encoding the annotations of Dante's text from XML to RDF format. 

JDK:jdk1.7.0_21, URL http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html 
Eclipse Java EE IDE for Web Developers, Version: Juno Release, http://www.eclipse.org/downloads/

Plug-in per eclipse per il subversion http://subclipse.tigris.org/, per l'installazione seguire le istruzioni 
1) Apri eclipse
2) seleziona Help>Install New Software
3) seleziona 'Available Software' link 
4) clicca sul bottone Add
	1. inserisci nel campo di testo 'NAME' il nome del software che stai installando es 'subclipse' 
	2. inserisci nel campo di testo 'LOCATION' url del software http://subclipse.tigris.org/update_1.10.x
	3. clicca Ok
5) Scrivi dentro il campo di testo 'Work with' subclipse (o il nome che hai dato al plugin)
   nel campo di testo di sotto vi compariranno le librerie da installare.
   Scegliere SubEclipse e installare
   Da SVNKit scegliere
     1. SVNKit library 
     2. SVNKit Cliet Adapter
     3. Subversion JavaHL Native Library Adapter
   Da Subclipse	scegliere
     1. Subclipse 
     2. Subversion Client Adapter
     3. Subversion Revision Graph
6) Cliccare il bottone Install
7) Cliccare il bottone Next
8) Selezionare il radio button "I accept the terms of the license agreements"
9) Cliccare il bottone Finish
10) Riavviare Eclipse

Virtuoso Version: 06.01.3127, http://virtuoso.openlinksw.com/dataspace/doc/dav/wiki/Main/VOSDownload

TOMCAT http://tomcat.apache.org/download-70.cgi
  
INFORMAZIONI PER ESEGUIRE/COMPILARE IL CODICE

CHECK-OUT
1. Aprire esclipse
2. Posizionarsi sul Tab Package Explorer
3. Tasto desto Import>SVN>Checkout Project from SVN
4. Next
5. Segno di spunta sulla voce Use existing repository location
6. digitare https://dante2.isti.cnr.it/svn/DanteRepo1
7. Next
8. scegliere la cartella 'dante'
9. Next
10. Checkout as a project configured using New Project Wizard
11. Digitare il nome del progetto
12. Finish


Per poter eseguire il codice è necessario inserire nel classpath di Eclipse le librerie necessarie alla compilazione del software
1) Aprire Eclipse
2) Selezionare la voce Project>Properties>Java Build Path 
3) cliccare sul bottone Add JARs... e scegliere le lib
   1. virt_jena.jar
   2. mysql-connector-java-5.0.8-bin.jar
   3. virtjdbc3.jar
   4. jenajars
   5. apache-jena-2.10.1

Aprire il file config.properties e inserire le informazioni necessarie  
ES dirInputContenutoConvivio indica la directory dove è memorizzato il file XML del contenuto del convivio da parsare
ES dirNoteDanteOpere indica la directory che conyterrà i file RDF generati dal tool; 
ES nomeFileNoteConvivio indica il nome del file RDF
Nell'attributo conn_str è settata l'URL del data base Virtuoso, se avete installato 
il db sulla vostra macchina potete lasciare il valore invariato; altr. va modificato
per puntare al db esterno, valore che vi deve fornire il responsabile dell'installazione.

CASO D'USO: INSERIRE NEL DB VIRTUOSO LE NOTE DEL CONVIVIO
Aprire Eclipse in cui abbiamo già settato tutte le informazioni necessarie.
Nel package perunaenciclopediadantescadigitale.prindante.eu sono contenute le classi entry-point 
che vi permettono di eseguire in un unica chiamata le operazioni di parser-generazione RDF/XML-store
Selezioniamo la classe 
InsertNoteConvivio in altro in Eclipse scegliamo il bottone Run>Run AS>Java Application
Alla fine dell'operazione nella console apparirà il messaggio 
==deleted Note Convivio Graph==
==Inserted Note Convivio graph==
Altrimenti un messagio di errore del tipo                  
==Exception into insert Note Convivio graph==
Se in basso nell'IDE non vedete la console Window>Show View>Console


REPERIRE I DOCUMENTI XML
Per ogni opera dantesca (Convivio, De vulgari eloquentia, ecc.) sono stati creati due file XML: uno per il contenuto e uno per le note.
Abbiamo sviluppato uno schema XSD, allo scopo di definire la lista degli elementi permessi, i tipi di dati ad essi associati e la relazione gerarchica fra gli elementi contenuti nei file XML.
Lo schema XSD è conforme al vocabolario creato per rappresentare la conoscenza sulle opere dantesche (in particolare sulle loro citazioni) sulle opere stesse e sulle loro note.
I file XML prodotti per ogni opera sono validi.
