package perunaenciclopediadantescadigitale.prindante.eu.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.hp.hpl.jena.util.FileUtils;

/**
 * @author Loredana
 * read the XML file, stored into file system. The directory path is taken from properties file
 * The file to parser had to be a single file, eg if you want to parser a Convivo's Note you 
 * must have a unique file not NoteConvivioLibro1, NoteConvivioLIbro2,.... 
 * We use a directory for comfort so the user is free to set the file name as you like 
 */
public class FromXMLNoteToGena {

	public Notes readXMLNotaFile(String dirFile) {
		Notes notes = null;
        //info per settare le info del file xml
        
        String numCapitolo = null;
        String body = null;
        String numParagrafo = null;
        String numLibro = null;
        String testo = null;
        String operaUri = null;
        String titoloOpera = null;
        String dataOpera = null;
        String nameAutore = null;
        String uriAutore = null;
        String tipoOpera = null;
        String frammento = null;
        String tipoCitazione = null;
        String operaURICitata = null;
        String titoloOperaCitata = null;
        String tipoOperaCitata = null;
        String nomeAutoreOperaCitata = null;
        String uriAutoreOperaCitata = null;
        String start = null;
        String end = null;
        
        ArrayList<String> edizioneList = null;
        ArrayList<String> areaList = null;
        ArrayList<String> edizioneOperaCitataList = null;
        ArrayList<String> areaOperaCitataList = null;
        ArrayList<Citazione> citazioniList = null; 
        ArrayList<Nota> notesList = null; 
        boolean isInfoOpera = true;
       
        Autore autore = null;
        Autore autoreInfoOperaCitata = null;
        
        Source source= null;
        Citazione citazione = null;
        InfoOpera infoOpera = null;
        InfoOperaCitata infoOperaCitata = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance(); 
        XMLStreamReader xmlReader;
        Reader r ;
        //br = new BufferedReader(new FileReader("C:\\testing.txt"));
		try{
			String files;
			File folder = new File(dirFile);
			File[] listOfFiles = folder.listFiles(); 
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if (listOfFiles[i].isFile())
				{
					files = listOfFiles[i].getName();
					if ((files.endsWith(".xml")) || (files.endsWith(".XML"))) {
					   FileInputStream fileInputStream = new FileInputStream(dirFile+File.separator+files);
					   r = FileUtils.asBufferedUTF8(fileInputStream);
		               xmlReader = inputFactory.createXMLStreamReader(r);		
            		   while (xmlReader.hasNext()) {
					         int event = xmlReader.next();
					         if (event == XMLStreamConstants.START_ELEMENT)
					         {
					             String element = xmlReader.getLocalName();
					             
					             /***********************************/
					             /******** Sezione Notes*************/
					             /***********************************/
					             if (element.equals("Notes"))
					             {
					            	 notesList = new ArrayList<Nota>();
					            	 notes = new Notes();
					                 continue;
					             }
					             
					             /***********************************/
					             /******** Sezione InfoOpera********/
					             /***********************************/
					             
					             if (element.equals("InfoOpera"))
					             {
					               edizioneList = new ArrayList<>();
					               areaList = new ArrayList<>();
					               isInfoOpera = true; 
					               continue;
					             }
					             
					             if (element.equals("OperaURI") && isInfoOpera==true )
					             {
					            	 operaUri = xmlReader.getElementText().trim();
						             continue;
					             }
					             
					             //es convivio
					             if (element.equals("TitoloOpera") && isInfoOpera==true)
					             {
					            	 titoloOpera = xmlReader.getElementText();
					            	continue;
					             }
					             
					             if (element.equals("data")  && isInfoOpera==true )
					             {
					            	 dataOpera = xmlReader.getElementText();
					            	continue;
					             }
					             
					             if (element.equals("Edizione")  && isInfoOpera==true )
					             {
					            	 String edizione = xmlReader.getElementText();
						             edizioneList.add(edizione);
					            	continue;
					             }

					             if (element.equals("TipoOpera") && isInfoOpera==true)
					             {
					            	 tipoOpera = xmlReader.getElementText();
					            	continue;
					             }
					             
					             // nome dell'autore dell'opera annotata es Dante
					             if (element.equals("Name") && isInfoOpera==true )
					             {
						             nameAutore = xmlReader.getElementText();
						             continue;
					             }
					             
					             // URI dell'autore dell'opera annotata es Dante
					             if (element.equals("URI") && isInfoOpera==true )
					             {
					            	 uriAutore = xmlReader.getElementText();
					            	 continue;
					             }
					             
					          // area dell'opera annotata es convivio
					             if (element.equals("Area") && isInfoOpera==true)
					             {
						             String area = xmlReader.getElementText();
						             areaList.add(area.trim());
						             continue;
					             }
					             
					             /************************************/
					             /******** Sezione Nota   ***********/
					             /***********************************/
					             
					             if (element.equals("Nota"))
					             {
					            	 citazioniList = new ArrayList<Citazione>(); 
						             continue;
					             }
					             
					             if (element.equals("Testo"))
					             {
						             testo = xmlReader.getElementText();
						             continue;
					             }
					             
					             if (element.equals("Body"))
					             {
						             body = xmlReader.getElementText();
						             continue;
					             }
					             
					             /************************************/
					             /******** Sezione Source   *********/
					             /**********************************/
					             
					             if (element.equals("Libro"))
					             {
						             numLibro = xmlReader.getElementText();
						             continue;
					             }
					             
					             if (element.equals("Capitolo"))
					             {
					            	 numCapitolo = xmlReader.getElementText();
						             continue;
					             }
					             
					             if (element.equals("Paragrafo"))
					             {
					            	 numParagrafo = xmlReader.getElementText();
						             continue;
					             }
					             
					             /************************************/
					             /******** FINE Sezione Source ******/
					             /***********************************/
					            
					             
					             /***************************************/
					             /******** Sezione Citazione   *********/
					             /*************************************/
					             
					             if (element.equals("Frammento"))
					             {
					            	 frammento = xmlReader.getElementText();
					            	 if(frammento.equals(""))
					            		 frammento ="NOT AVAILABLE";
						             continue;
					             }
					             
					             if (element.equals("Tipo"))
					             {
					            	 tipoCitazione = xmlReader.getElementText();
						             continue;
					             }
					             
					             /*******************************************/
					             /******** Sezione InfoOperaCitata *********/
					             /*****************************************/
					             if (element.equals("InfoOperaCitata"))
					             {
					            	   edizioneOperaCitataList = new ArrayList<>();
						               areaOperaCitataList = new ArrayList<>();
					            	
						             continue;
					             }
					             
					             if (element.equals("OperaURI"))
					             {
					            	 operaURICitata = xmlReader.getElementText().trim();
						             continue;
					             }
					             
					             if (element.equals("TitoloOpera"))
					             {
					            	 titoloOperaCitata = xmlReader.getElementText();
						             continue;
					             }
					             
					             if (element.equals("Edizione"))
					             {
					            	 String edizione = xmlReader.getElementText();
						             edizioneOperaCitataList.add(edizione);
					            	continue;
					             }
					             
					             if (element.equals("Area"))
					             {
					            	 String areaOperaCitata = xmlReader.getElementText();
						             areaOperaCitataList.add(areaOperaCitata.trim());
					            	continue;
					             }
					             
					             if (element.equals("TipoOpera"))
					             {
					            	 tipoOperaCitata = xmlReader.getElementText();
					            	continue;
					             }
					             
					             
					             if (element.equals("Name"))
					             {
					            	 nomeAutoreOperaCitata = xmlReader.getElementText();
					            	continue;
					             }
					             
					             if (element.equals("URI"))
					             {
					            	 uriAutoreOperaCitata = xmlReader.getElementText().trim();
					            	continue;
					             }
					             
					             
					             /************************************************/
					             /******** FINE Sezione InfoOperaCitata *********/
					             /**********************************************/
					             
					             
					             /*********************************************/
					             /******** Sezione Selettore   ***************/
					             /*******************************************/
					             if (element.equals("start"))
					             {
					            	 start = xmlReader.getElementText();
						            // System.out.println("start  "+start);
					            	continue;
					             }
					             
					             if (element.equals("end"))
					             {
					            	 end = xmlReader.getElementText();
						            // System.out.println("end  "+end);
					            	continue;
					             }
					             /******************************************/
					             /******** FINE Sezione selettore *********/
					             /****************************************/
					             
					             /*****************************************/
					             /******** FINESezione Citazione *********/
					             /***************************************/
					             
				                    }//chiude if capitolo
					        	  
					      
					        // If we reach the end of an currOperaContenutoInfo element, we add it to the list
					         if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementAutore = xmlReader.getLocalName();
					        	 if (elementAutore.equals("Autore") && isInfoOpera == true){
					        	     autore = new Autore();
					        		 autore.setName(nameAutore.trim());
					        		 autore.setURI(uriAutore.trim());
					        		 continue;
					        	 }
					        }
					         
					         
					         if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementInfoOpera = xmlReader.getLocalName();
					        	 if (elementInfoOpera.equals("InfoOpera")){
					        		infoOpera = new InfoOpera();
					        		infoOpera.setOperaURI(operaUri);
					        		infoOpera.setTipoOpera(tipoOpera);
					        		infoOpera.setTitoloOpera(titoloOpera);
					        		infoOpera.setDate(dataOpera);
					        		infoOpera.setAutore(autore);
					        		infoOpera.setAreaList(areaList);
					        		infoOpera.setEdizioneList(edizioneList);
					        		isInfoOpera = false;
					        		  continue;
					        	 }
					        }
					         if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementInfoOpera = xmlReader.getLocalName();
					        	 if (elementInfoOpera.equals("Source")){
					        		source = new Source();
					        		source.setNumCapitolo(numCapitolo);
					        		source.setNumLibro(numLibro);
					        		source.setNumParagrafo(numParagrafo);
					        		  continue;
					        	 }
					        }	 
					         
					         if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementAutore = xmlReader.getLocalName();
					        	 if (elementAutore.equals("Autore")){
					        	     autoreInfoOperaCitata = new Autore();
					        		 autoreInfoOperaCitata.setName(nomeAutoreOperaCitata.trim());
					        		 autoreInfoOperaCitata.setURI(uriAutoreOperaCitata.trim());
					        		 continue;
					        	 }
					        }
					         
					         if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementNota = xmlReader.getLocalName();
					        	 if (elementNota.equals("InfoOperaCitata")){
					        		infoOperaCitata = new InfoOperaCitata();
					        		infoOperaCitata.setOperaURI(operaURICitata.trim());
					        		infoOperaCitata.setTipoOpera(tipoOperaCitata);
					        		infoOperaCitata.setEdizioneList(edizioneOperaCitataList);
					        		infoOperaCitata.setAreaList(areaOperaCitataList);
					        		infoOperaCitata.setAutore(autoreInfoOperaCitata);
					        		infoOperaCitata.setTitoloOpera(titoloOperaCitata);
					        		 continue;
					        	 }
					        }
					         
					         if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementNota = xmlReader.getLocalName();
					        	 if (elementNota.equals("Citazione")){
					        		citazione = new Citazione();
					        		citazione.setFrammento(frammento);;
					        		citazione.setTipoCitazione(tipoCitazione);
					        		citazione.setInfoOperaCitata(infoOperaCitata);
					        		citazioniList.add(citazione);
					        		 continue;
					        	 }
					        }
					         
					        if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementNota = xmlReader.getLocalName();
					        	 if (elementNota.equals("Nota")){
					        		Nota nota = new Nota();
					        		nota.setTestoCitazione(testo);
					        		nota.setBodyCitazione(body);
					        		nota.setSource(source);
					        		nota.setCitazioneList(citazioniList);
					        		
					        		Selettore selector = new Selettore();
					        		selector.setEnd(end);
					        		selector.setStart(start);
					        		nota.setSelettore(selector);
					        		notesList.add(nota);
					        		 continue;
					        	 }
					        }
					        
					        if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementNota = xmlReader.getLocalName();
					        	 if (elementNota.equals("Notes")){
					        		notes.setInfoOpera(infoOpera);
					      		    notes.setNoteList(notesList);
					        		 continue;
					        	 }
					        }
					   }
					}
				}
				}
		}
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException:: "+ e);
		}
	
		 catch (XMLStreamException e) {
			 e.printStackTrace();
			System.out.println("XMLStreamException during parser :: "+ e );
		}
		return notes;
	}
}
