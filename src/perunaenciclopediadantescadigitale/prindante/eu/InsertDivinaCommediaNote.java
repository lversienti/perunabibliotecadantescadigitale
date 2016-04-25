package perunaenciclopediadantescadigitale.prindante.eu;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import perunaenciclopediadantescadigitale.prindante.eu.database.VirtuosoDB;
import perunaenciclopediadantescadigitale.prindante.eu.model.ModelJenaXMLNote;
import perunaenciclopediadantescadigitale.prindante.eu.parser.FromXMLNoteToGena;
import perunaenciclopediadantescadigitale.prindante.eu.parser.Notes;

/**
 * @author Loredana
 * create  Divina Commedia Notes graph and store it into virtuoso
 */
public class InsertDivinaCommediaNote {
	
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertNoteConvivio.class.getClassLoader().getResourceAsStream("config.properties"));
			String path = configFile.getProperty("dirInputNoteDivinaCommedia");
			FromXMLNoteToGena xmlNotetoJena = new FromXMLNoteToGena();
			Notes notes = xmlNotetoJena.readXMLNotaFile(path);
			ModelJenaXMLNote modelXmlNote = new ModelJenaXMLNote(notes);
			String pathOutput = configFile.getProperty("dirNoteDanteOpere");
			String uriOpera = configFile.getProperty("baseUriDivinaCommedia");
			String nomeFileNoteDivinaCommedia= configFile.getProperty("nomeFileNoteDivinaCommedia");
			modelXmlNote.createRDFGraphModelNote(pathOutput+File.separator+nomeFileNoteDivinaCommedia, uriOpera);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertDivinaCommediaNoteGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertDivinaCommediaNote: "+e);
			e.printStackTrace();
		}
	}

}
