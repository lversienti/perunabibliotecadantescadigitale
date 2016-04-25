package perunaenciclopediadantescadigitale.prindante.eu;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import perunaenciclopediadantescadigitale.prindante.eu.database.VirtuosoDB;
import perunaenciclopediadantescadigitale.prindante.eu.model.ModelJenaXMLNote;
import perunaenciclopediadantescadigitale.prindante.eu.parser.FromXMLNoteToGena;
import perunaenciclopediadantescadigitale.prindante.eu.parser.Notes;

/**
 * 
 * @author Loredana
 * create Note DeVulgarieEloquentia graph and store it into virtuoso
 */

public class InsertRimeNote {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertDeVulgarieEloquentiaNote.class.getClassLoader().getResourceAsStream("config.properties"));
			String path = configFile.getProperty("dirInputNoteRime");
			FromXMLNoteToGena xmlNotetoJena = new FromXMLNoteToGena();
			Notes notes = xmlNotetoJena.readXMLNotaFile(path);
			ModelJenaXMLNote modelXmlNote = new ModelJenaXMLNote(notes);
			String pathOutput = configFile.getProperty("dirNoteDanteOpere");
			String uriOpera = configFile.getProperty("baseUriRime");
			String nomeFileNoteRime = configFile.getProperty("nomeFileNoteRime");
			modelXmlNote.createRDFGraphModelNote(pathOutput+File.separator+nomeFileNoteRime, uriOpera);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertNoteRimeGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertRimeNote: "+e);
			e.printStackTrace();
		}
	}

}
