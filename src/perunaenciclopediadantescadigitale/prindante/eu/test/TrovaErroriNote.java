package perunaenciclopediadantescadigitale.prindante.eu.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.hp.hpl.jena.util.FileUtils;

import perunaenciclopediadantescadigitale.prindante.eu.parser.FromXMLContenutoToGena;


public class TrovaErroriNote {
	
	public TrovaErroriNote(){
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TrovaErroriNote trovaErrori = new TrovaErroriNote();
		trovaErrori.readXMLNotaFile();
	}



	public void readXMLNotaFile() {
		try{
			
			String files;
			 Properties configFile = new Properties();
			 configFile.load(FromXMLContenutoToGena.class.getClassLoader().getResourceAsStream("config.properties"));
			String path = configFile.getProperty("dirInputNoteConvivo");
			File folder = new File(path); //Settare la directory dove è contenuto il file XML da controllare es "C:\\OutputRDF\\NoteConvivioXML";
			File[] listOfFiles = folder.listFiles(); 
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if (listOfFiles[i].isFile()) 
				{
					files = listOfFiles[i].getName();
					//prendo file/files xml da parsare
					   FileInputStream fileInputStream = new FileInputStream(path+"\\"+files);
					   XMLInputFactory inputFactory = XMLInputFactory.newInstance(); 
					   Reader r = FileUtils.asBufferedUTF8(fileInputStream);
		               XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(r);
		               
		               String operaUri = null;
		               String titoloOpera = null;
		               String nameAutore = null;
		               String uriAutore = null;
		               String tipoOpera = null;
		               String tipoCitazione = null;
		               String operaURICitata = null;
		               String titoloOperaCitata = null;
		               String tipoOperaCitata = null;
		               String nomeAutoreOperaCitata = null;
		               String uriAutoreOperaCitata = null;
		             
		             
		               boolean isInfoOpera = true;
		              
					   while (xmlReader.hasNext()) {
					         int event = xmlReader.next();
					         if (event == XMLStreamConstants.START_ELEMENT)
					         {
					             String element = xmlReader.getLocalName();
					             
					             
					             if (element.equals("OperaURI") && isInfoOpera==true )
					             {
					            	 operaUri = xmlReader.getElementText();
					            	 if(!operaUri.trim().isEmpty()){
						            	 if(!operaUri.contains("http://")){
						            		 System.out.println("ERRORE è ammessa solo l'URI dell'opera e non la stringa " +operaUri);
						            	 }
					        		 }
					            	 if(operaUri.trim().isEmpty()){
					            		 System.out.println("ERRORE operaUri non è stata settata" );
				        		 }
						             continue;
					             }
					             
					             //es convivio
					             if (element.equals("TitoloOpera") && isInfoOpera==true)
					             {
					            	 titoloOpera = xmlReader.getElementText();
					            	 if((titoloOpera.trim()).contains("'"))
					            		 System.out.println("ERRORE carattere non ammesso nell'opere " +titoloOpera);
					            	 if(titoloOpera.trim().isEmpty()){
					            		 System.out.println("ERRORE titolo Opera non è stato settato" );
				        		 }
					            	continue;
					             }
					             
					             if (element.equals("Edizione")  && isInfoOpera==true )
					             {
					            	 String edizione = xmlReader.getElementText();
					            	 if(edizione.trim().isEmpty())
					            		 System.out.println("ERRORE edizione dell'opera non è stata settata" );
					            		 if(edizione.contains("http://")){
						            		 System.out.println("ERRORE nell'edizione dell'opera solo stringhe sono ammesse" );
						            	 }
					            	continue;
					             }

					             if (element.equals("TipoOpera") && isInfoOpera==true)
					             {
					            	 
					            	 tipoOpera = xmlReader.getElementText();
					            	 if(!(tipoOpera.trim().equals("WORK") || tipoOpera.trim().equals("EXPRESSION") || tipoOpera.trim().equals("CONCEPT") ))
						            	   System.out.println("ERRORE!!! non c'è nessuna tipo citazione ammessa nel modello "+tipoOpera);
					            	continue;
					             }
					             
					             // nome dell'autore dell'opera annotata es Dante
					             if (element.equals("Name") && isInfoOpera==true )
					             {
						             nameAutore = xmlReader.getElementText();
					            	 if(!nameAutore.trim().isEmpty()){
						            	 if(nameAutore.contains("http://")){
						            		 System.out.println("ERRORE il nome autore " +nameAutore+" non è corretto solo stringhe sono ammesse");
						            	 }
					        		 }
					            	 if((nameAutore.trim()).contains("'"))
					            		 System.out.println("ERRORE carattere non ammesso per il nome dell'autore " +nameAutore);
					            	 
					            	 if(nameAutore.trim().isEmpty())
					            		 System.out.println("ERRORE nameAutore  non è stato settato" );
				        		 
						             continue;						           
					             }
					             
					             // URI dell'autore dell'opera annotata es Dante
					             if (element.equals("URI") && isInfoOpera==true )
					             {
					            	 uriAutore = xmlReader.getElementText();
					            	 if(!uriAutore.trim().isEmpty()){
						            	 if(!uriAutore.contains("http://")){
						            		 System.out.println("ERRORE l'URI autore " +uriAutore+" non è corretto");
						            	 }
					        		 }
					            	 if(uriAutore.trim().isEmpty())
					            		 System.out.println("ERRORE uriAutore  non è stato settato" );
						             continue;
					             }
					             
					          // area dell'opera annotata es convivio
					             if (element.equals("Area") && isInfoOpera==true)
					             {
						             String area = xmlReader.getElementText();
						             if(!area.trim().isEmpty()){
						            	 if(!area.contains("http://")){
						            		 System.out.println("ERRORE area errata è ammesso solo l'URI" +area);
						            	 }
					        		 }
						             if(area.trim().isEmpty())
					            		 System.out.println("ERRORE area  non è stata settata" );
						             continue;
					             }
					             
					           
					             
					             if (element.equals("Tipo"))
					             {
					            	 tipoCitazione = xmlReader.getElementText();
					            	 if(!(tipoCitazione.trim().equals("CONCORDANZA STRINGENTE") || tipoCitazione.trim().equals("CONCORDANZA GENERICA") || tipoCitazione.trim().equals("CITAZIONE ESPLICITA") ))
					            	   System.out.println("ERRORE!!! non c'è nessuna tipo citazione ammessa nel modello "+tipoCitazione);
						             continue;
					             }
					             
					             /*******************************************/
					             /******** Sezione InfoOperaCitata *********/
					             /*****************************************/
					             
					             if (element.equals("OperaURI"))
					             {
					            	 operaURICitata = xmlReader.getElementText();
					            	 if(!operaURICitata.trim().isEmpty()){
						            	 if(!operaURICitata.contains("http://")){
						            		 System.out.println("ERRORE l'URI dell'opera " +operaURICitata+" non è corretto");
						            	 }
					        		 }
					            	  if(operaURICitata.trim().isEmpty())
						            		 System.out.println("ERRORE operaURICitata  non è stata settata" );
						             continue;
					             }
					             
					             if (element.equals("TitoloOpera"))
					             {
					            	 titoloOperaCitata = xmlReader.getElementText();
					            	 if(!titoloOperaCitata.trim().isEmpty()){
						            	 if(titoloOperaCitata.contains("http://")){
						            		 System.out.println("ERRORE Titolo dell'opera " +titoloOperaCitata+" non è corretto");
						            	 }
					        		 }
					            	 if(titoloOperaCitata.trim().isEmpty()){
						            		 System.out.println("ERRORE Titolo dell'opera non è stata settata" );
					        		 }
					            	/* if((titoloOperaCitata.trim()).contains("'")){
						            		 System.out.println("ERRORE carattere non ammesso nell'opere " +titoloOperaCitata);
					        		 }*/
						             continue;
					             }
					             
					             if (element.equals("Edizione"))
					             {
					            	 String edizioneOperaPrimaria = xmlReader.getElementText();
					            	 if(edizioneOperaPrimaria.trim().isEmpty()){
					            		 System.out.println("ERRORE edizione dell'opera non è stata settata" );
					            		 if(edizioneOperaPrimaria.contains("http://")){
						            		 System.out.println("ERRORE edizione dell'opera solo stringhe sono ammesse" );
						            	 }
				        		 }
					            	continue;
					             }
					             
					             if (element.equals("Area"))
					             {
					            	 String areaOperaCitata = xmlReader.getElementText();
					            	 if(!areaOperaCitata.trim().isEmpty()){
						            	 if(!areaOperaCitata.contains("http://")){
						            		 System.out.println("ERRORE Area dell'opera " +areaOperaCitata+" non è corretta");
						            	 }
					        		 }
					            	 if(areaOperaCitata.trim().isEmpty())
					            		 System.out.println("ERRORE operaURICitata  non è stata settata" );
					            	continue;
					             }
					             
					             if (element.equals("TipoOpera"))
					             {
					            	 tipoOperaCitata = xmlReader.getElementText();
					            	 if(!(tipoOperaCitata.trim().equals("WORK") || tipoOperaCitata.trim().equals("EXPRESSION") || tipoOperaCitata.trim().equals("CONCEPT") ))
					            	   System.out.println("ERRORE!!! non c'è nessuna tipo citazione ammessa nel modello "+tipoOperaCitata);
						             continue;
					             }
					             
					             
					             if (element.equals("Name"))
					             {
					            	 nomeAutoreOperaCitata = xmlReader.getElementText();
					            	 if(!nomeAutoreOperaCitata.trim().isEmpty()){
						            	 if(nomeAutoreOperaCitata.contains("http://"))
						            		 System.out.println("ERRORE nome Autore OperaCitata " +nomeAutoreOperaCitata+" non è corretto");
					            	 }
					            	 if(nomeAutoreOperaCitata.trim().isEmpty())
					            		 System.out.println("ERRORE il nome dell'autore non è stato settato" );
					            	 if((nomeAutoreOperaCitata.trim()).contains("'"))
					            		 System.out.println("ERRORE carattere non ammesso nell'opere " +nomeAutoreOperaCitata);
					            	continue;
					             }
					             
					             if (element.equals("URI"))
					             {
					            	 uriAutoreOperaCitata = xmlReader.getElementText();
					            	 if(!uriAutoreOperaCitata.trim().isEmpty()){
						            	 if(!uriAutoreOperaCitata.contains("http://"))
						            		 System.out.println("ERRORE URI Autore OperaCitata " +uriAutoreOperaCitata+" non è corretto");
					            	 }
					            	 if(uriAutoreOperaCitata.trim().isEmpty()){
					            		 System.out.println("ERRORE URI del nome dell'autore non è stato settato" );
				        		 }
					            	continue;
					             }
					             
				                    }
					         
					         if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementInfoOpera = xmlReader.getLocalName();
					        	 if (elementInfoOpera.equals("InfoOpera")){
					        		isInfoOpera = false;
					        		  continue;
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}//chiude classe



 
