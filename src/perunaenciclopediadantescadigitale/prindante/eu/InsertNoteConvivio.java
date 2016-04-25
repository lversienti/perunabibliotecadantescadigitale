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
 * create  Note Convivio graph and store it into virtuoso
 */
public class InsertNoteConvivio {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 try{
			 Properties configFile = new Properties();
			 configFile.load(InsertNoteConvivio.class.getClassLoader().getResourceAsStream("config.properties"));
			 String path = configFile.getProperty("dirInputNoteConvivo");
			 FromXMLNoteToGena xmlNotetoJena = new FromXMLNoteToGena();
			 Notes notes = xmlNotetoJena.readXMLNotaFile(path);
			 ModelJenaXMLNote modelXmlNote = new ModelJenaXMLNote(notes);
			 String pathOutput = configFile.getProperty("dirNoteDanteOpere");
			 String uriOpera = configFile.getProperty("baseUriConvivio");
			 String nomeFileConvivio = configFile.getProperty("nomeFileNoteConvivio");
			 modelXmlNote.createRDFGraphModelNote(pathOutput+File.separator+nomeFileConvivio, uriOpera);
			 VirtuosoDB virtDB = new VirtuosoDB();
			 virtDB.initBDVirtuoso();
			 virtDB.insertNoteConvivoGraph();
		 }
		 catch (IOException e) {
			 System.out.println("ERROR into class InsertNoteConvivio: "+e);
			 e.printStackTrace();
		 }
	 }


}
