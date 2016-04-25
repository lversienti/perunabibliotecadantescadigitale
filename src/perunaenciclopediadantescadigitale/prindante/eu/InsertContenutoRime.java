package perunaenciclopediadantescadigitale.prindante.eu;

import java.io.IOException;
import java.util.Properties;

import perunaenciclopediadantescadigitale.prindante.eu.database.VirtuosoDB;
import perunaenciclopediadantescadigitale.prindante.eu.model.ModelJenaXMLContenuto;
import perunaenciclopediadantescadigitale.prindante.eu.parser.FromXMLContenutoToGena;
import perunaenciclopediadantescadigitale.prindante.eu.parser.OperaContenutoInfo;

/**
 * 
 * @author Loredana
 * create  Contenuto DeVulgarieEloquentia graph and store it into virtuoso
 */
public class InsertContenutoRime {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertDeVulgarieEloquentiaContenuto.class.getClassLoader().getResourceAsStream("config.properties"));
			String xmlFileDir = configFile.getProperty("dirInputContenutoRime");
			FromXMLContenutoToGena XMLtoJena = new FromXMLContenutoToGena();
			OperaContenutoInfo operaContenutoInfo=  XMLtoJena.readXMLFiles(xmlFileDir);
			ModelJenaXMLContenuto modelXmlJena = new ModelJenaXMLContenuto(operaContenutoInfo);
			String pathOutput = configFile.getProperty("dirOutputContenutoRime");
			String uriOpera = configFile.getProperty("baseUriRime");
			modelXmlJena.createRDFGraphModel(pathOutput, uriOpera);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertDeVulgarieEloquentiaContenutoGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertContenutoRime: "+e);
			e.printStackTrace();
		}

	}

}
