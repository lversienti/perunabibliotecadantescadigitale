package perunaenciclopediadantescadigitale.prindante.eu.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.*;

import virtuoso.jena.driver.*;

/********************************************/
/**DIFFERENZA TRA VirtGraph & VirtModel*****/
/***************************************** */
//VirtGraph permette di caricare in virtuoso un grafo da url con il metodo read(java.lang.String url, java.lang.String type) 
//VirtModel permette di caricare in virtuoso RDF Model da FileSystem con il metodo read 

/**
 * 
 * @author Loredana
 * interact with virtuoso DB
 */
public class VirtuosoDB {
	
	String conn_str;
	VirtGraph set;
	VirtDataSource vd;
	Model md;
	Graph graph = null;
	Properties configFile;
	String login;
	String pw;
	
	/**
	 * Central manager of database connections, the necessary info e.f login pw are taken from properties file
	 * */
	public void initBDVirtuoso(){
				//conn_str = "jdbc:virtuoso://dante1.isti.cnr.it:1111";
				//nota ho aggiunto la stringa /charset=UTF-8/log_enable=2
				//localhost "jdbc:virtuoso://localhost:1111";
				try {
					 configFile = new Properties();
					 configFile.load(VirtuosoDB.class.getClassLoader().getResourceAsStream("config.properties"));
					 conn_str = configFile.getProperty("conn_str");
					 login = configFile.getProperty("login");
					 pw = configFile.getProperty("pw");
				   } 
				catch (Exception e) {
				     System.out.println("Exception=="+e);
				   }
		  }		 
	
	/**
	 * creates a irigraph for 'Soggettario' and stores it into virtuoso.
	 * if exists one first delete and then store it 
	 * */
	public void insertSoggettario() {
		try {
			deleteSoggettarioGraph();
			String path = configFile.getProperty("dirInputSoggettario");
			String fileToLoad;
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles(); 
			String iriSoggettario = configFile.getProperty("iriSoggettario");
			md = VirtModel.openDatabaseModel(iriSoggettario, conn_str, login, pw);
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if (listOfFiles[i].isFile()) 
				{
					fileToLoad = listOfFiles[i].getName();
					md.read(new FileInputStream(path+File.separator+fileToLoad), null);
					System.out.println("VIRTUOSO - Inserito nuovo grafo soggettario: " + fileToLoad);
				}
			}
			md.close();
		}
		catch (JenaException e) 
			{
				System.out.println("ECCEZIONE - VirtuosoDB.insertSoggettario: " + e);
			}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException:: "+ e);
		}
	}
	
	/**
	 * creates a irigraph for 'VitaNovaNote' and stores it into virtuoso.
	 * if exists one first delete it and then store the new graph 
	 * */
	public void insertVitaNovaNoteGraph(){
		try {
			deleteVitaNovaNoteGraph();
			String iriVitaNovaNote = configFile.getProperty("iriVitaNovaNote");
			md = VirtModel.openDatabaseModel(iriVitaNovaNote, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirNoteDanteOpere");
			String nomeFileNoteVN = configFile.getProperty("nomeFileNoteVitaNova");
			md.read(new FileInputStream(path+File.separator+nomeFileNoteVN), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo note Vita Nova");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into insert Note graph: "+e);
		}
	}
	
	public void insertVitaNovaContenutoGraph(){
		try {
			deleteVitaNovaContenutoGraph();
			String iriVitaNovaContenuto = configFile.getProperty("iriVitaNovaContenuto");
			md = VirtModel.openDatabaseModel(iriVitaNovaContenuto, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirOutputContenutoVitaNova");
			String nomeFileContenutoVN = configFile.getProperty("nomeFileContenutoVitaNova"); // aggiunta riga
			md.read(new FileInputStream(path+File.separator+nomeFileContenutoVN), null); // aggiunta riga
			System.out.println("VIRTUOSO - Inserito nuovo grafo contenuto Vita Nova");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into Contenuto graph VitaNova: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'VitaNuovaNote' and stores it into virtuoso.
	 * if exists one first delete it and then store the new graph 
	 * */
	public void insertVitaNuovaDeRobertisNoteGraph(){
		try {
			deleteVitaNuovaDeRobertisNoteGraph();
			String iriVitaNuovaDeRobertisNote = configFile.getProperty("iriVitaNuovaDeRobertisNote");
			md = VirtModel.openDatabaseModel(iriVitaNuovaDeRobertisNote, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirNoteDanteOpere");
			String nomeFileNoteVNDr = configFile.getProperty("nomeFileNoteVitaNuovaDr");
			md.read(new FileInputStream(path+File.separator+nomeFileNoteVNDr), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo note Vita Nuova (De Robertis)");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into insert Vita Nuova Note graph: "+e);
		}
	}
	
	public void insertVitaNuovaDeRobertisContenutoGraph(){
		try {
			deleteVitaNuovaDeRobertisContenutoGraph();
			String iriVitaNuovaDeRobertisContenuto = configFile.getProperty("iriVitaNuovaDeRobertisContenuto");
			md = VirtModel.openDatabaseModel(iriVitaNuovaDeRobertisContenuto, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirOutputContenutoVitaNuovaDeRobertis");
			String nomeFileContenutoVNDr = configFile.getProperty("nomeFileContenutoVitaNuovaDr"); // aggiunta riga
			md.read(new FileInputStream(path+File.separator+nomeFileContenutoVNDr), null); // aggiunta riga
			System.out.println("VIRTUOSO - Inserito nuovo grafo contenuto Vita Nuova (De Robertis)");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into Contenuto graph Vita Nuova: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'DeVulgarieEloquentiaNote' and stores it into virtuoso.
	 * if exists one first delete it and then store the new graph 
	 * */
	public void insertDeVulgarieEloquentiaNoteGraph(){
		try {
			deleteDeVulgarieEloquentiaNoteGraph();
			String iriDeVulgarieEloquentiaNote = configFile.getProperty("iriDeVulgariEloquentiaNote");
			md = VirtModel.openDatabaseModel(iriDeVulgarieEloquentiaNote, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirNoteDanteOpere");
			String nomeFileNoteDVE = configFile.getProperty("nomeFileNoteDVE");
			md.read(new FileInputStream(path+File.separator+nomeFileNoteDVE), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo note De Vulgari Eloquentia");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into insert Note graph: "+e);
		}
	}
	
	public void insertDeVulgarieEloquentiaContenutoGraph(){
		try {
			deleteDeVulgarieEloquentiaContenutoGraph();
			String iriDeVulgarieEloquentiaContenuto = configFile.getProperty("iriDeVulgariEloquentiaContenuto");
			md = VirtModel.openDatabaseModel(iriDeVulgarieEloquentiaContenuto, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirOutputContenutoDeVulgariEloquentia");
			String nomeFileContenutoDVE = configFile.getProperty("nomeFileContenutoDeVulgariEloquentia"); // aggiunta riga
			md.read(new FileInputStream(path+File.separator+nomeFileContenutoDVE), null); // aggiunta riga
			System.out.println("VIRTUOSO - Inserito nuovo grafo contenuto De Vulgari Eloquentia");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into Contenuto graph DeVulgarieEloquentia: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'DivinaCommediaContenuto' and stores it into virtuoso.
	 * if exists one first delete it and then store the new graph 
	 * */
	public void insertDivinaCommediaContenutoGraph(){
		try {
			deleteDivinaCommediaContenutoGraph();
			String iriDivinaCommediaContenuto = configFile.getProperty("iriDivinaCommediaContenuto");
			md = VirtModel.openDatabaseModel(iriDivinaCommediaContenuto, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirOutputContenutoDivinaCommedia");
			md.read(new FileInputStream(path), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo contenuto Divina Commedia");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into insert Divina Commedia contenuto graph: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'DivinaCommediaNote' and stores it into virtuoso.
	 * if exists one first delete it and then store the new graph 
	 * */
	public void insertDivinaCommediaNoteGraph(){
		try {
			deleteDivinaCommediaNoteGraph();
			String iriDivinaCommediaNote = configFile.getProperty("iriDivinaCommediaNote");
			md = VirtModel.openDatabaseModel(iriDivinaCommediaNote, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirNoteDanteOpere");
			String nomeFileNoteDivinaCommedia = configFile.getProperty("nomeFileNoteDivinaCommedia");
			md.read(new FileInputStream(path+File.separator+nomeFileNoteDivinaCommedia), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo note Divina Commedia");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into insert Divina Commedia contenuto graph: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'Monarchia' note and stores it into virtuoso.
	 * if exists one first delete it and then store the new graph 
	 * */
	public void insertMonarchiaNoteGraph(){
		try {
			deleteNoteMonarchiaGraph();
			String iriMonarchiaNote = configFile.getProperty("iriMonarchiaNote");
			md = VirtModel.openDatabaseModel(iriMonarchiaNote, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirNoteDanteOpere");
			String nomeFileNoteMonarchia = configFile.getProperty("nomeFileNoteMonarchia");
			md.read(new FileInputStream(path+File.separator+nomeFileNoteMonarchia), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo note Monarchia");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into insert Note graph: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'Monarchia' Contenuto and stores it into virtuoso.
	 * if exists one first delete it and then store the new graph 
	 * */
	public void insertMonarchiaContenutoGraph(){
		try {
			deleteContenutoMonarchiaGraph();
			String iriMonarchiaContenuto = configFile.getProperty("iriMonarchiaContenuto");
			md = VirtModel.openDatabaseModel(iriMonarchiaContenuto, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirOutputContenutoMonarchia");
			String nomeFileMonarchia = configFile.getProperty("nomeFileContenutoMonarchia");
			md.read(new FileInputStream(path+File.separator+nomeFileMonarchia), null);
			//md.read(new FileInputStream(path), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo contenuto Monarchia");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into insert contenuto Monarchia graph: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'Rime' Contenuto and stores it into virtuoso.
	 * if exists one first delete it and then store the new graph 
	 * */
	public void insertContenutoRimeGraph(){
		try {
			deleteContenutoRimeGraph();
			String iriRimeContenuto = configFile.getProperty("iriRimeContenuto");
			md = VirtModel.openDatabaseModel(iriRimeContenuto, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirOutputContenutoRime");
			md.read(new FileInputStream(path), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo contenuto Rime");
			//md.close();
		} catch (Exception e) {
			System.out.println("Exception into insert Contenuto Rime graph: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'Rime' Note and stores it into virtuoso.
	 * if exists one first delete it and then store the new graph 
	 * */
	public void insertNoteRimeGraph(){
		try {
			deleteRimeRimeGraph();
			String iriRimeNote = configFile.getProperty("iriRimeNote");
			md = VirtModel.openDatabaseModel(iriRimeNote, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirNoteDanteOpere");
			md.read(new FileInputStream(path), null);
			String nomeFileNoteRime = configFile.getProperty("nomeFileNoteRime");
			md.read(new FileInputStream(path+File.separator+nomeFileNoteRime), null);
			md.close();
			System.out.println("VIRTUOSO - Inserito nuovo grafo note Rime");
		} catch (Exception e) {
			System.out.println("Exception into insert Note Rime graph: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'Convivio Note' and stores it into virtuoso.
	 * if exists one first delete and then store it 
	 * */
	public void insertNoteConvivoGraph(){
		try {
			deleteNoteConvivioGraph();
			String iriConvivioNote = configFile.getProperty("iriConvivioNote");
			md = VirtModel.openDatabaseModel(iriConvivioNote, conn_str, login, pw);
			//load data in to graph data
			String path = configFile.getProperty("dirNoteDanteOpere");
			String nomeFileNoteConvivio = configFile.getProperty("nomeFileNoteConvivio");
			md.read(new FileInputStream(path+File.separator+nomeFileNoteConvivio), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo note Convivio");
			md.close();
		} catch (Exception e) {
			System.out.println("Exception into insert Note Convivio graph: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'Convivio contenuto' and stores it into virtuoso.
	 * if exists one first delete and then store it 
	 * */
	public void insertContenutoConvivioGraph(){
		try {
			deleteContenutoConvivioGraph();
			String iriConvivioContenuto = configFile.getProperty("iriConvivioContenuto");
			// load data into graph data
			md = VirtModel.openDatabaseModel(iriConvivioContenuto, conn_str, login, pw);
			String path = configFile.getProperty("dirOutputContenutoConvivio");
			String nomeFileContenutoConvivio = configFile.getProperty("nomeFileContenutoConvivio");
			md.read(new FileInputStream(path + File.separator + nomeFileContenutoConvivio), null);
			System.out.println("VIRTUOSO - Inserito nuovo grafo contenuto Convivio");
			md.close();
		}
		catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.insertContenutoConvivioGraph: " + e);
		}
	}
	
	/**
	 * delete Note Rime graph from database 
	 * */
	public void deleteRimeRimeGraph(){
		try {
			String dirOutputNoteRime = configFile.getProperty("dirOutputNoteRime");
			md = VirtModel.openDatabaseModel(dirOutputNoteRime, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo note Rime");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteRimeRimeGraph: " + e);
		}
	}
	
	/**
	 * delete Note Divina commedia graph from database 
	 * */
	public void deleteDivinaCommediaNoteGraph(){
		try {
			String iriDivinaCommediaNote = configFile.getProperty("iriDivinaCommediaNote");
			md = VirtModel.openDatabaseModel(iriDivinaCommediaNote, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo note Divina Commedia");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteDivinaCommediaNoteGraph: " + e);
		}
	}
	
	/**
	 * delete Note Rime graph from database 
	 * */
	public void deleteContenutoRimeGraph(){
		try {
			String iriRimeContenuto = configFile.getProperty("iriRimeContenuto");
			md = VirtModel.openDatabaseModel(iriRimeContenuto, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo note Rime");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteContenutoRimeGraph: " + e);
		}
	}
	
	/**
	 * delete Contenuto Divina commedia graph from database 
	 * */
	public void deleteDivinaCommediaContenutoGraph(){
		try {
			String iriDivinaCommediaContenuto = configFile.getProperty("iriDivinaCommediaContenuto");
			md = VirtModel.openDatabaseModel(iriDivinaCommediaContenuto, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo contenuto Divina Commedia");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteDivinaCommediaContenutoGraph: " + e);
		}
	}

	/**
	 * delete Note Convivio graph from database 
	 * */
	public void deleteNoteConvivioGraph(){
		try {
			String iriConvivioNote = configFile.getProperty("iriConvivioNote");
			md = VirtModel.openDatabaseModel(iriConvivioNote, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo note Convivio");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteNoteConvivioGraph: " + e);
		}
	}
	
	/**
	 * delete Contenuto Monarchia graph from database 
	 * */
	public void deleteContenutoMonarchiaGraph(){
		try {
			String iriMonarchiaContenuto = configFile.getProperty("iriMonarchiaContenuto");
			md = VirtModel.openDatabaseModel(iriMonarchiaContenuto, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo contenuto Monarchia");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteContenutoMonarchiaGraph: " + e);
		}
	}
	
	/**
	 * delete Contenuto DeVulgarieEloquentia graph from database 
	 * */
	public void deleteDeVulgarieEloquentiaContenutoGraph(){
		try {
			String iriDeVulgarieEloquentiaContenuto = configFile.getProperty("iriDeVulgarieEloquentiaContenuto");
			md = VirtModel.openDatabaseModel(iriDeVulgarieEloquentiaContenuto, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo contenuto De Vulgari Eloquentia");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteDeVulgarieEloquentiaContenutoGraph: " + e);
		}
	}
	
	/**
	 * delete Contenuto Vita Nova graph from database 
	 * */
	public void deleteVitaNovaContenutoGraph(){
		try {
			String iriVitaNovaContenuto = configFile.getProperty("iriVitaNovaContenuto");
			md = VirtModel.openDatabaseModel(iriVitaNovaContenuto, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo contenuto Vita Nova");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteVitaNovaContenutoGraph: " + e);
		}
	}
	
	/**
	 * delete Contenuto Vita Nuova De Robertis graph from database 
	 * */
	public void deleteVitaNuovaDeRobertisContenutoGraph(){
		try {
			String iriVitaNuovaDeRobertisContenuto = configFile.getProperty("iriVitaNuovaDeRobertisContenuto");
			md = VirtModel.openDatabaseModel(iriVitaNuovaDeRobertisContenuto, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("==deleted Contenuto VitaNuova De Robertis Graph==");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteVitaNuovaDeRobertisContenutoGraph: " + e);
		}
	}
	
	/**
	 * delete Note Monarchia graph from database 
	 * */
	public void deleteNoteMonarchiaGraph(){
		try {
			String iriMonarchiaNote = configFile.getProperty("iriMonarchiaNote");
			md = VirtModel.openDatabaseModel(iriMonarchiaNote, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo note Monarchia");
				}
		} catch (Exception e) {
			System.out.println("ECCEZIONE - VirtuosoDB.deleteNoteMonarchiaGraph: " + e);
		}
	}
	
	/**
	 * delete Contenuto Convivio graph from database 
	 * */
	public void deleteContenutoConvivioGraph(){
		try {
			String iriConvivioContenuto = configFile.getProperty("iriConvivioContenuto");
			md = VirtModel.openDatabaseModel(iriConvivioContenuto, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo contenuto Convivio");
				}
		} catch (Exception e) {
			System.out.println("Exception in deleting graph: "+e);
		}
	}
	
	/**
	 * delete DeVulgarieEloquentiaNote Graph from database 
	 * */
	public void deleteDeVulgarieEloquentiaNoteGraph(){
		try {
			String iriDeVulgarieEloquentiaNote = configFile.getProperty("iriDeVulgarieEloquentiaNote");
			md = VirtModel.openDatabaseModel(iriDeVulgarieEloquentiaNote, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo note De Vulgari Eloquentia");
				}
		} catch (Exception e) {
			System.out.println("Exception in deleting graph: "+e);
		}
	}
	
	/**
	 * delete VitaNovaNote Graph from database 
	 * */
	public void deleteVitaNovaNoteGraph(){
		try {
			String iriVitaNovaNote = configFile.getProperty("iriVitaNovaNote");
			md = VirtModel.openDatabaseModel(iriVitaNovaNote, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo note Vita Nova");
				}
		} catch (Exception e) {
			System.out.println("Exception in deleting graph: "+e);
		}
	}

	/**
	 * delete VitaNuovaDeRobertisNote Graph from database 
	 * */
	public void deleteVitaNuovaDeRobertisNoteGraph(){
		try {
			String iriVitaNuovaDeRobertisNote = configFile.getProperty("iriVitaNuovaDeRobertisNote");
			md = VirtModel.openDatabaseModel(iriVitaNuovaDeRobertisNote, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("==deleted VitaNuovaDeRobertisNoteGraph Note ==");
				}
		} catch (Exception e) {
			System.out.println("Exception in deleting graph: "+e);
		}
	}

	/**
	 * delete Soggettario graph from database 
	 * */
	public void deleteSoggettarioGraph(){
		try {
			String iriSoggettario = configFile.getProperty("iriSoggettario");
			md = VirtModel.openDatabaseModel(iriSoggettario, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo soggettario");
				}
		} catch (Exception e) {
			System.out.println("Exception in deleting graph: "+e);
		}
	}
	
	/*
	public void updateGraph(){
		try {
			String iriConvivioContenuto = configFile.getProperty("iriConvivioContenuto");
			Model md = VirtModel.openDatabaseModel(iriConvivioContenuto, conn_str, login, pw);
			String path = configFile.getProperty("dirOutputNoteConvivio");
		} catch (Exception e) {
			System.out.println("Exception in insert graph: "+e);
		}
	}
	*/
	
	/**
	 * list all graph in virtuoso
	 **/
	public void listNamesGraph(){
		try {
			Model md = VirtModel.openDatabaseModel("virt:perunaenciclopediadantescadigitale.eu/resource/convivio", conn_str, login, pw);
			md.read(new FileInputStream("C:\\OutputRDF\\ConvivioCompleto.rdf"), "http://www.exampleloredana.org/");
			System.out.println("==Inserted==");
		} catch (Exception e) {
			System.out.println("Exception in insert graph: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'Tutte le opere di Dante' and stores it into virtuoso.
	 * if exists one first delete and then store it 
	 **/
	public void insertOpereNoteDante() {
		try {
			deleteOpereNoteDanteGraph();
			String path = configFile.getProperty("dirNoteDanteOpere");
			System.out.println("path"+ path);
			String fileToLoad;
			File folder = new File(path+File.separator);
			File[] listOfFiles = folder.listFiles(); 
			String iriSoggettario = configFile.getProperty("iriEnciclopediadantescaNote");
			md = VirtModel.openDatabaseModel(iriSoggettario, conn_str, login, pw);
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if (listOfFiles[i].isFile()) 
				{
					fileToLoad = listOfFiles[i].getName();
					System.out.println("Loading file "+fileToLoad+" "+(i+1)+" of "+listOfFiles.length);
					if (!fileToLoad.endsWith(".rdf")){
						continue;
					}
					md.read(new FileInputStream(path+File.separator+fileToLoad), null);
				}
			}
			System.out.println("VIRTUOSO - Inserito nuovo grafo note opere Dante");
			md.close();
		}
		catch (JenaException e) 
			{
				System.out.println("Exception into insert Tutte le Opere di Dante graph: "+e);
			}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException:: "+ e);
		}
		
	}
	
	/**
	 * delete Tutte le Opere di Dante graph from database 
	 **/
	public void deleteOpereNoteDanteGraph(){
		try {
			String iriSoggettario = configFile.getProperty("iriEnciclopediadantescaNote");
			md = VirtModel.openDatabaseModel(iriSoggettario, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo note opere Dante");
				}
		} catch (Exception e) {
			System.out.println("Exception in deleting Tutte le Opere di Dante graph: "+e);
		}
	}
	
	/**
	 * creates a irigraph for 'Tutte le opere di Dante' and stores it into virtuoso.
	 * if exists one first delete and then store it 
	 **/
	public void insertOpereContenutoDante() {
		try {
			deleteOpereContenutoDanteGraph();
			String path = configFile.getProperty("dirContenutoDanteOpere");
			System.out.println("path"+ path);
			String fileToLoad;
			
			File folder = new File(path+File.separator);
			File[] listOfFiles = folder.listFiles(); 
			String iriSoggettario = configFile.getProperty("iriEnciclopediadantescaContenuto");
			md = VirtModel.openDatabaseModel(iriSoggettario, conn_str, login, pw);
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if (listOfFiles[i].isFile()) 
				{
					fileToLoad = listOfFiles[i].getName();
					md.read(new FileInputStream(path+File.separator+fileToLoad), null);
				}
			}
			System.out.println("VIRTUOSO - Inserito nuovo grafo contenuto opere Dante");
			md.close();
		}
		catch (JenaException e) 
			{
				System.out.println("Exception into insert Tutte le Opere di Dante graph: "+e);
			}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException:: "+ e);
		}
		
	}
	
	/**
	 * delete Tutte le Opere di Dante graph from database 
	 * */
	public void deleteOpereContenutoDanteGraph(){
		try {
			String iriSoggettario = configFile.getProperty("iriEnciclopediadantescaContenuto");
			md = VirtModel.openDatabaseModel(iriSoggettario, conn_str, login, pw);
			if(!md.isEmpty()){
				graph = md.getGraph();
			    graph.clear();
			    System.out.println("VIRTUOSO - Cancellato grafo contenuto opere Dante");
				}
		} catch (Exception e) {
			System.out.println("Exception in deleting Tutte le Opere di Dante Contenuto graph: "+e);
		}
	}
}
