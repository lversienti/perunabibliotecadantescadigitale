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
 * create Note DeVulgariEloquentia graph and store it into virtuoso
 */

public class InsertDeVulgariEloquentiaNote {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertDeVulgariEloquentiaNote.class.getClassLoader().getResourceAsStream("config.properties"));
			String path = configFile.getProperty("dirInputNoteDeVulgariEloquentia");
			FromXMLNoteToGena xmlNotetoJena = new FromXMLNoteToGena();
			Notes notes = xmlNotetoJena.readXMLNotaFile(path);
			ModelJenaXMLNote modelXmlNote = new ModelJenaXMLNote(notes);
			String pathOutput = configFile.getProperty("dirNoteDanteOpere");
			String uriOpera = configFile.getProperty("baseUriDeVulgariEloquentia");
			String nomeFileNoteDVE = configFile.getProperty("nomeFileNoteDVE");
			modelXmlNote.createRDFGraphModelNote(pathOutput+File.separator+nomeFileNoteDVE, uriOpera);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertDeVulgariEloquentiaNoteGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertDeVulgariEloquentiaNote: "+e);
			e.printStackTrace();
		}
	}

}



