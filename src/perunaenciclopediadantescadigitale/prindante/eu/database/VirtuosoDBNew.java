package perunaenciclopediadantescadigitale.prindante.eu.database;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.*;

import virtuoso.jena.driver.*;

/**
 * @author Daniele
 */
public class VirtuosoDBNew {
	Model md;
	String conn_str;
	VirtGraph set;
	Graph graph = null;
	Properties configFile;
	String login;
	String pw;
	
	// INIZIALIZZA DATABASE
	public void initDBVirtuoso() {
		try {
				configFile = new Properties();
				configFile.load(VirtuosoDBNew.class.getClassLoader().getResourceAsStream("config.properties"));
				conn_str = configFile.getProperty("conn_str");
				login = configFile.getProperty("login");
				pw = configFile.getProperty("pw");
			} 
		catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.initDBVirtuoso: " + e);
		}
	}

	// CANCELLA GRAFO ESISTENTE
	public void deleteGraph(String graphURI) {
		try {
			md = VirtModel.openDatabaseModel(graphURI, conn_str, login, pw);
			if (!md.isEmpty()) {
			    System.out.println("INSERT - Cancello grafo:  " + graphURI);
				graph = md.getGraph();
			    graph.clear();
			}
			md.close();
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteGraph: " + e);
		}
	}
	
	// INSERISCI GRAFO DA FILE RDF SINGOLO
	public void insertGraph(String graphURI, String rdfFile) {
		try {
			System.out.println("INSERT - Inserisco grafo: " + graphURI + "\n");
			md = VirtModel.openDatabaseModel(graphURI, conn_str, login, pw);
			md.read(new FileInputStream(rdfFile), null);
			md.close();
		}
		catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.insertGraph: " + e);
		}
	}
	
	// INSERISCI GRAFO DA FILE RDF MULTIPLI
	public void insertFullGraph(String graphURI, String rdfDir) {
		try {
			ArrayList<File> fileList = new ArrayList<File>();
			md = VirtModel.openDatabaseModel(graphURI, conn_str, login, pw);
			
			// CREA LISTA FILE RDF
			for (File file: new File(rdfDir).listFiles()) {
				if (file.isFile())
				{
					String fileName = file.getName();					
					if ((fileName.endsWith(".rdf")) || (fileName.endsWith(".RDF"))) {
						fileList.add(file);					
					}
				}
			}
			
			// INSERISCI GRAFO DA FILE
			int remaining = fileList.size() - 1; 

			for (File file: fileList) {
				System.out.println("INSERT - Inserisco grafo: " + graphURI + " (" + (fileList.size() - remaining) + " di " + fileList.size() + ")");
				md.read(new FileInputStream(file.getPath()), null);
				remaining--;
			}
			md.close();
		}
		catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.insertFullGraph: " + e);
		}
	}
}
