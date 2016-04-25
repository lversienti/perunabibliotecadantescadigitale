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
 * create  Monarchia Note graph and store it into virtuoso
 */
public class InsertMonarchiaNote {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertMonarchiaNote.class.getClassLoader().getResourceAsStream("config.properties"));
			String path = configFile.getProperty("dirInputNoteMonarchia");
			FromXMLNoteToGena xmlNotetoJena = new FromXMLNoteToGena();
			Notes notes = xmlNotetoJena.readXMLNotaFile(path);
			ModelJenaXMLNote modelXmlNote = new ModelJenaXMLNote(notes);
			String pathOutput = configFile.getProperty("dirNoteDanteOpere");
			String uriOpera = configFile.getProperty("baseUriMonarchia");
			String nomeFileNoteMonarchia= configFile.getProperty("nomeFileNoteMonarchia");
			modelXmlNote.createRDFGraphModelNote(pathOutput+File.separator+nomeFileNoteMonarchia, uriOpera);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertMonarchiaNoteGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertMonarchiaNote: "+e);
			e.printStackTrace();
		}
	}
}
