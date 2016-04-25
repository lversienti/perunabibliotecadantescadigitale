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
 * create  Contenuto Vita Nuova De Robertis graph and store it into virtuoso
 */
public class InsertVitaNuovaDeRobertisContenuto {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertVitaNuovaDeRobertisContenuto.class.getClassLoader().getResourceAsStream("config.properties"));
			String xmlFileDir = configFile.getProperty("dirInputContenutoVitaNuovaDr");
			FromXMLContenutoToGena XMLtoJena = new FromXMLContenutoToGena();
			System.out.println("xml dir:"+xmlFileDir);
			OperaContenutoInfo operaContenutoInfo=  XMLtoJena.readXMLFiles(xmlFileDir);
			ModelJenaXMLContenuto modelXmlJena = new ModelJenaXMLContenuto(operaContenutoInfo);
			String pathOutput = configFile.getProperty("dirOutputContenutoVitaNuovaDeRobertis");
			String uriOpera = configFile.getProperty("baseUriVitaNuovaDeRobertis");
			String nomeFileVitaNuovaDr = configFile.getProperty("nomeFileContenutoVitaNuovaDr");
			modelXmlJena.createRDFGraphModel(pathOutput+File.separator+nomeFileVitaNuovaDr, uriOpera);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertVitaNuovaDeRobertisContenutoGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertVitaNuovaDeRobertisContenuto: "+e);
			e.printStackTrace();
		}

	}

}
