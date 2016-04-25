package perunaenciclopediadantescadigitale.prindante.eu;

import java.io.IOException;
import java.util.Properties;

import perunaenciclopediadantescadigitale.prindante.eu.database.VirtuosoDB;
import perunaenciclopediadantescadigitale.prindante.eu.model.ModelJenaXMLContenuto;
import perunaenciclopediadantescadigitale.prindante.eu.parser.FromXMLContenutoToGena;
import perunaenciclopediadantescadigitale.prindante.eu.parser.OperaContenutoInfo;

public class InsertDivinaCommediaContenuto {
	/**
	 * 
	 * @author Loredana
	 * create  Contenuto Divina Commedia graph and store it into virtuoso
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertContenutoConvivo.class.getClassLoader().getResourceAsStream("config.properties"));
			String xmlFileDir = configFile.getProperty("dirInputContenutoDivinaCommedia");
			FromXMLContenutoToGena XMLtoJena = new FromXMLContenutoToGena();
			OperaContenutoInfo operaContenutoInfo=  XMLtoJena.readXMLFiles(xmlFileDir);
			ModelJenaXMLContenuto modelXmlJena = new ModelJenaXMLContenuto(operaContenutoInfo);
			String pathOutput = configFile.getProperty("dirOutputContenutoDivinaCommedia");
			String operaURI = configFile.getProperty("baseUriDivinaCommedia");
			modelXmlJena.createRDFGraphModel(pathOutput, operaURI);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertDivinaCommediaContenutoGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertDivinaCommediaContenuto: "+e);
			e.printStackTrace();
		}
	}

}