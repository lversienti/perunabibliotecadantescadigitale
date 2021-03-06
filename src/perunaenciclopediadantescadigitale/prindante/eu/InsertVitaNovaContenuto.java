package perunaenciclopediadantescadigitale.prindante.eu;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import perunaenciclopediadantescadigitale.prindante.eu.database.VirtuosoDB;
import perunaenciclopediadantescadigitale.prindante.eu.model.ModelJenaXMLContenuto;
import perunaenciclopediadantescadigitale.prindante.eu.parser.FromXMLContenutoToGena;
import perunaenciclopediadantescadigitale.prindante.eu.parser.OperaContenutoInfo;

/**
 * 
 * @author Paola
 * create  Contenuto Vita Nova graph and store it into virtuoso
 */
public class InsertVitaNovaContenuto {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertVitaNovaContenuto.class.getClassLoader().getResourceAsStream("config.properties"));
			String xmlFileDir = configFile.getProperty("dirInputContenutoVitaNova");
			FromXMLContenutoToGena XMLtoJena = new FromXMLContenutoToGena();
			System.out.println("xml dir:"+xmlFileDir);
			OperaContenutoInfo operaContenutoInfo=  XMLtoJena.readXMLFiles(xmlFileDir);
			ModelJenaXMLContenuto modelXmlJena = new ModelJenaXMLContenuto(operaContenutoInfo);
			String pathOutput = configFile.getProperty("dirOutputContenutoVitaNova");
			String uriOpera = configFile.getProperty("baseUriVitaNova");
			String nomeFileVitaNova = configFile.getProperty("nomeFileContenutoVitaNova");
			modelXmlJena.createRDFGraphModel(pathOutput+File.separator+nomeFileVitaNova, uriOpera);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertVitaNovaContenutoGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertVitaNovaContenuto: "+e);
			e.printStackTrace();
		}

	}

}
