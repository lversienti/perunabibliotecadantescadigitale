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
 * create  Contenuto Convivio graph and store it into virtuoso
 */
public class InsertContenutoConvivo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertContenutoConvivo.class.getClassLoader().getResourceAsStream("config.properties"));
			String xmlFileDir = configFile.getProperty("dirInputContenutoConvivio");
			FromXMLContenutoToGena XMLtoJena = new FromXMLContenutoToGena();
			OperaContenutoInfo operaContenutoInfo=  XMLtoJena.readXMLFiles(xmlFileDir);
			ModelJenaXMLContenuto modelXmlJena = new ModelJenaXMLContenuto(operaContenutoInfo);
			String pathOutput = configFile.getProperty("dirOutputContenutoConvivo");
			String operaURI = configFile.getProperty("baseUriConvivio");
			String nomeFileConvivio = configFile.getProperty("nomeFileContenutoConvivio");
			modelXmlJena.createRDFGraphModel(pathOutput+File.separator+nomeFileConvivio,operaURI);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertContenutoConvivioGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertContenutoConvivo: "+e);
			e.printStackTrace();
		}
	}

}
