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
 * @author Paola
 * create Note VitaNova graph and store it into virtuoso
 */

public class InsertVitaNovaNote {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertVitaNovaNote.class.getClassLoader().getResourceAsStream("config.properties"));
			String path = configFile.getProperty("dirInputNoteVitaNova");
			FromXMLNoteToGena xmlNotetoJena = new FromXMLNoteToGena();
			Notes notes = xmlNotetoJena.readXMLNotaFile(path);
			ModelJenaXMLNote modelXmlNote = new ModelJenaXMLNote(notes);
			String pathOutput = configFile.getProperty("dirNoteDanteOpere");
			String uriOpera = configFile.getProperty("baseUriVitaNova");
			String nomeFileNoteVitaNova = configFile.getProperty("nomeFileNoteVitaNova");
			modelXmlNote.createRDFGraphModelNote(pathOutput+File.separator+nomeFileNoteVitaNova, uriOpera);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertVitaNovaNoteGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertVitaNovaNote: "+e);
			e.printStackTrace();
		}
	}

}
