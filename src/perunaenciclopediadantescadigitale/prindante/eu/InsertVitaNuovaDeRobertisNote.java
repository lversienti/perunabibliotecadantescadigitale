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
 * create Note VitaNuova De Robertis graph and store it into virtuoso
 */

public class InsertVitaNuovaDeRobertisNote {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Properties configFile = new Properties();
			configFile.load(InsertVitaNuovaDeRobertisNote.class.getClassLoader().getResourceAsStream("config.properties"));
			String path = configFile.getProperty("dirInputNoteVitaNuovaDr");
			FromXMLNoteToGena xmlNotetoJena = new FromXMLNoteToGena();
			Notes notes = xmlNotetoJena.readXMLNotaFile(path);
			ModelJenaXMLNote modelXmlNote = new ModelJenaXMLNote(notes);
			String pathOutput = configFile.getProperty("dirNoteDanteOpere");
			String uriOpera = configFile.getProperty("baseUriVitaNuovaDeRobertis");
			String nomeFileNoteVitaNuovaDr = configFile.getProperty("nomeFileNoteVitaNuovaDr");
			modelXmlNote.createRDFGraphModelNote(pathOutput+File.separator+nomeFileNoteVitaNuovaDr, uriOpera);
			VirtuosoDB virtDB = new VirtuosoDB();
			virtDB.initBDVirtuoso();
			virtDB.insertVitaNuovaDeRobertisNoteGraph();
		}
		catch (IOException e) {
			System.out.println("ERROR into class InsertVitaNuovaDeRobertisNote: "+e);
			e.printStackTrace();
		}
	}

}
