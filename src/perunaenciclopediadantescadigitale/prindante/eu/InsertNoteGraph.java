package perunaenciclopediadantescadigitale.prindante.eu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import perunaenciclopediadantescadigitale.prindante.eu.database.VirtuosoDBNew;
import perunaenciclopediadantescadigitale.prindante.eu.model.ModelJenaXMLNote;
import perunaenciclopediadantescadigitale.prindante.eu.parser.XMLNoteToJena;

/**
 * @author Daniele
 * Insert Notes into Virtuoso database
 */
public class InsertNoteGraph {
	/**
	 * @param args
	 * @throws Exception 
	 */	
	public static final String baseURI = "http://perunaenciclopediadantescadigitale.eu/resource";

	public static void main(String[] args) throws Exception {
		Logger.getRootLogger().setLevel(Level.OFF);
		String sep = File.separator;

		// OPERE DA TRIPLIFICARE (COMMENTARE QUELLE NON NECESSARIE)
		String[] toTriplify = new String[] {	
												"Convivio",
												"Monarchia",
												"VitaNova",
												"VitaNuovaDeRobertis",
												"DeVulgariEloquentia",
		};
		
		// GRAFI INTERMEDI DA INSERIRE (COMMENTARE QUELLI NON NECESSARI)
		String[] toInsert = new String[] {		
												//"Convivio",
												//"Monarchia",
												//"VitaNova",
												//"VitaNuovaDeRobertis",
												//"DeVulgariEloquentia",
		};
		
		// INIZIALIZZA MAPPA OPERE
		Map<String, String> uriMap = new HashMap<String, String>();
		
		uriMap.put("http://dbpedia.org/resource/Convivio", "Convivio");
		uriMap.put("http://it.dbpedia.org/resource/Monarchia", "Monarchia");
		uriMap.put("http://dbpedia.org/resource/De_vulgari_eloquentia", "DeVulgariEloquentia");		
		uriMap.put("http://perunaenciclopediadantescadigitale.eu/resource/work/Vita_Nova_(Gorni)", "VitaNova");
		uriMap.put("http://perunaenciclopediadantescadigitale.eu/resource/work/Vita_Nuova_(De_Robertis)", "VitaNuovaDeRobertis");

		// INIZIALIZZA MAPPA FILE
		Map<String, String> fileMap = new HashMap<String, String>();

		// INIZIALIZZA DATABASE VIRTUOSO
		VirtuosoDBNew virtDB = new VirtuosoDBNew();
		virtDB.initDBVirtuoso();

		// CARICA FILE DI CONFIGURAZIONE
		Properties configFile = new Properties();
		try {
			configFile.load(InsertNoteGraph.class.getClassLoader().getResourceAsStream("config.properties"));
		}
		catch (IOException e) {
			System.out.println("\nINSERT - Errore nel caricamento della configurazione");
		}
		String noteDir = configFile.getProperty("directoryNote");
		
		// INIZIALIZZA DIRECTORY XML E RDF
		File xmlDir = new File(noteDir + sep + "XML");
		File rdfDir = new File(noteDir + sep + "RDF");
		System.out.println("\nINSERT - Carico file XML da path: " + xmlDir + "\n");
		
		try {
			// CONTROLLA CHE DIRECTORY XML ESISTA
			if (!xmlDir.isDirectory()) {
				throw new Exception("ECCEZIONE - La directory " + xmlDir + " non esiste");
			}
			
			// CONTROLLA CHE DIRECTORY XML NON SIA VUOTA
			if (xmlDir.listFiles().length == 0) {
				throw new Exception("ECCEZIONE - La directory " + xmlDir + " è vuota");
			}
			
			// CONTROLLA CHE DIRECTORY RDF ESISTA
			if (!rdfDir.isDirectory()) {
				rdfDir.mkdirs();
				System.out.println("\nINSERT - Creo directory RDF: " + rdfDir + "\n");
			}
		}
		catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}

		// CONTROLLA QUALI FILE XML SONO PRESENTI NELLA DIRECTORY
		for (File file: xmlDir.listFiles()) {
			if (file.isFile()) {
				String fileName = file.getName();					
				if ((fileName.endsWith(".xml")) || (fileName.endsWith(".XML"))) {
					try
					{
						DataInputStream in = new DataInputStream(new FileInputStream(file));
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						String line;
						int i = 0;
						
						// LEGGI URI OPERA DA FILE XML
						while ((line = br.readLine()) != null && i < 10) {
							for (String uri: uriMap.keySet()) {
								if (line.contains("OperaURI")) {
									if (line.contains(uri)) {
										System.out.println("INSERT - Trovato file XML per opera: " + uriMap.get(uri));
										if (fileMap.keySet().contains(uri)) {
											throw new Exception("ECCEZIONE - Trovati due file xml per opera: " + uri);
										}
										
										// AGGIUNGI URI E NOME FILE A MAPPA FILE
										fileMap.put(uriMap.get(uri), fileName);
										break;
									}
								}
							}
							i++;
						}
						in.close();
					}
					catch (Exception e) {
						System.err.println("ECCEZIONE - " + e.getMessage());
						System.exit(1);
					}
				}
			}
		}
		System.out.println();
		
		// TRIPLIFICA XML IN RDF
		for (String title: toTriplify) {
			
			// CARICA FILE XML
			String xmlPath = xmlDir + sep + fileMap.get(title);
			ModelJenaXMLNote modelXmlNote = new ModelJenaXMLNote(new XMLNoteToJena().readXMLNotaFile(xmlPath));
			
			// GENERA FILE RDF
			String rdfPath = rdfDir + sep + title + ".rdf";
			System.out.println("INSERT - Genero file RDF: " + rdfPath);
			modelXmlNote.createRDFGraphModelNote(rdfPath, baseURI + "/work/" + title.toLowerCase());
		}
		
		if (toInsert.length == 0) {
			System.out.println("INSERT - Non inserisco grafi intermedi");
		}
		System.out.println();
		
		// CANCELLA E INSERISCI GRAFI INTERMEDI
		for (String title: toInsert) {
			
			// CANCELLA GRAFO ESISTENTE
			String graphURI = baseURI + "/" + title.toLowerCase() + "/note";
			virtDB.deleteGraph(graphURI);
			
			//INSERISCI NUOVO GRAFO
			String rdfPath = configFile.getProperty("directoryNote") + sep + "RDF" + sep + title + ".rdf";
			virtDB.insertGraph(graphURI, rdfPath);
		}
		
		// CANCELLA GRAFO OPERE ESISTENTE
		virtDB.deleteGraph(baseURI + "/opere/note");
		
		// INSERISCI NUOVO GRAFO OPERE
		virtDB.insertFullGraph(baseURI + "/opere/note", configFile.getProperty("directoryNote") + sep + "RDF");
		
		System.out.println("\nINSERT - Concluso inserimento grafi");
	}
}
