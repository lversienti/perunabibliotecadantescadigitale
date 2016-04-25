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
 * @author Loredana
 * create Monarchia Contenuto graph and store it into virtuoso
 */
public class InsertMonarchiaContenuto {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertMonarchiaContenuto.class.getClassLoader().getResourceAsStream("config.properties"));
			String xmlFileDir = configFile.getProperty("dirInputContenutoMonarchia");
			FromXMLContenutoToGena XMLtoJena = new FromXMLContenutoToGena();
			OperaContenutoInfo operaContenutoInfo=  XMLtoJena.readXMLFiles(xmlFileDir);
			ModelJenaXMLContenuto modelXmlJena = new ModelJenaXMLContenuto(operaContenutoInfo);
			String pathOutput = configFile.getProperty("dirOutputContenutoMonarchia");
			String operaURI = configFile.getProperty("baseUriMonarchia");
			String nomeFileMonarchia = configFile.getProperty("nomeFileContenutoMonarchia");
			modelXmlJena.createRDFGraphModel(pathOutput+File.separator+nomeFileMonarchia,operaURI);
			//modelXmlJena.createRDFGraphModel(pathOutput, operaURI);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertMonarchiaContenutoGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertMonarchiaContenuto: "+e);
			e.printStackTrace();
		}

	}
}
