package perunaenciclopediadantescadigitale.prindante.eu.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 * must have a unique file not ContenutoConvivioLibro1, ContenutoConvivioLIbro2,.... 
 * We use a directory for comfort so the user is free to set the file name as you like 
 */
public class XMLContentToJena {
	
	public OperaContenutoInfo readXMLContentFile(String filePath) {
		OperaContenutoInfo operaContenuto = null;
	       ArrayList<ParagrafoInfo> paragrafiList = null;
           ArrayList<CapitoloInfo> capitoliList = null;
           String numeroCapitolo = null;
           String titoloCapitolo = null;
           String testoParagrafo = null;
           String titoloParagrafo = null;
           String numParagrafo = null;
           String numLibro = null;
           String titoloLibro = null;
           String operaUri = null;
           String titoloOpera = null;
           String nameAutore = null;
           String uriAutore = null;
           String tipoOpera = null;
           String nameCommentatore = null;
           String uriCommentatore = null;
           String nameTraduttore = null;
           String uriTraduttore =  null;
           boolean isAutore = true;
           boolean  isComm = true;   
           boolean  isTrad = true;  
           Autore autore = null;
           Commentatore comm = null;
           Traduttore trad = null;
           ArrayList<String> edizioneList = null;
           ArrayList<String> areaList = null;
           ArrayList<Commentatore> commentatoreList = null;
           ArrayList<Traduttore> traduttoreList = null;
           ArrayList<Libro> libroList = null;
           Info info = null;
           XMLInputFactory inputFactory = XMLInputFactory.newInstance(); 
           XMLStreamReader xmlReader;
		try{		
			File file = new File(filePath);
			if (file.isFile())
			{
				String fileName = file.getName();
				if ((fileName.endsWith(".xml")) || (fileName.endsWith(".XML"))) {
				   FileInputStream fileInputStream = new FileInputStream(filePath);		
	               xmlReader = inputFactory.createXMLStreamReader(FileUtils.asBufferedUTF8(fileInputStream));		

					   while (xmlReader.hasNext()) {
					         int event = xmlReader.next();
					         if (event == XMLStreamConstants.START_ELEMENT)
					         {
					             String element = xmlReader.getLocalName();
					             
					             if (element.equals("Opera") )
					             {
					            	 commentatoreList = new ArrayList<>();
					            	 traduttoreList = new ArrayList<>();
					            	 continue;
					             }

					             if (element.equals("Info") )
					             {
					            	 edizioneList = new ArrayList<>();
					            	 areaList = new ArrayList<>();
					            	 libroList = new ArrayList<>();
					            	 continue;
					             }
					             
					             if (element.equals("Edizione"))
					             {
						             String edizione = xmlReader.getElementText();
						             edizioneList.add(edizione);
						             continue;
					             }
					             
					             if (element.equals("Area"))
					             {
						             String area = xmlReader.getElementText();
						             areaList.add(area);
						             continue;
						             
					             }
					             
					             if (element.equals("Name") && isAutore==true )
					             {
						             nameAutore = xmlReader.getElementText();
						            // System.out.println("name autore "+nameAutore);
						             continue;
					             }
					                               
					             if (element.equals("URI") && isAutore==true )
					             {
					            	 uriAutore = xmlReader.getElementText();
					            	 //System.out.println("URI uri "+uriAutore);
					            	 continue;
					             }
					             
					             
					             if (element.equals("OperaURI"))
				                    {
					            	 operaUri= xmlReader.getElementText();
					        		//  System.out.println("operaUri "+operaUri);
					        		  continue;
				                    }
					             
					             if (element.equals("TitoloOpera"))
				                    {
					            	 	titoloOpera = xmlReader.getElementText();
					            	 	continue;
				                    }
					             
					             if (element.equals("TipoOpera"))
				                    {
					            	 tipoOpera= xmlReader.getElementText();
					        		  System.out.println("tipoOpera "+tipoOpera);
					        		  continue;
				                    }
					             
					             //traduttore commentatore
					             if (element.equals("Commentatore"))
				                    {
					            	  isComm = true;
				                    }
					             
					             if (element.equals("Traduttore"))
				                    {
					            	  isTrad = true;
				                    }
					            
					            if (element.equals("Name") && isComm==true)
					             {
						             nameCommentatore = xmlReader.getElementText();
						             comm = new Commentatore();
						             comm.setName(nameCommentatore);
						           //  System.out.println("name commentatore "+nameCommentatore);
						             continue;
					             } 
					                               
					            if (element.equals("URI") && isComm==true )
					             {
						             uriCommentatore = xmlReader.getElementText();
						             comm.setURI(uriCommentatore);
						            // System.out.println("URI comm uri "+uriCommentatore);
						             continue;
					             }

					            if (element.equals("Name") && isTrad==true )
					             {
						             nameTraduttore = xmlReader.getElementText();
						             trad = new Traduttore();
						             trad.setName(nameTraduttore);
						             //System.out.println("name traduttore "+nameTraduttore);
						             continue;
					             }
					                               
					             if (element.equals("URI") && isTrad==true )
					             {
						             uriTraduttore = xmlReader.getElementText();
						             trad.setURI(uriTraduttore);
						           //  System.out.println("URI trad uri "+uriTraduttore);
						             continue;
					             }
					            /* if (element.equals("Contenuto"))
				                    {
					            	  libroList = new ArrayList<>();
					            	  continue;
				                    }*/
					             
					             
					             if (element.equals("Libro"))
				                    {
					            	 //dentro il tag Libro si crea la lista di Capitoli
					            	  capitoliList = new ArrayList<>();
					            	  continue;
				                    }
					             
					             if (element.equals("Capitolo"))
				                    {
					            	//dentro il tag Libro si crea la lista di Paragrafi del capitolo su cui si sta facendo il parser
					            	 paragrafiList = new ArrayList<>();
					            	 continue;
				                    }
					             
					             if (element.equals("NumLibro"))
				                    {
					            	 numLibro= xmlReader.getElementText();
					        		 // System.out.println("NumLibro "+numLibro);
					        		  continue;
				                    }
					             
					             if (element.equals("TitoloLibro"))
				                    {
					            	 titoloLibro= xmlReader.getElementText();
					        		  //System.out.println("TitoloLibro "+titoloLibro);
					        		  continue;
				                    }
					             
					             
					        	  if (element.equals("TitoloCapitolo"))
				                    {
					        		  titoloCapitolo= xmlReader.getElementText();
					        		  continue;
					        		 // System.out.println("TitoloCapitolo "+titoloCapitolo);
				                    }
					        	  if (element.equals("NumCapitolo"))
				                    {
					        		 // paragrafiList = new ArrayList<>();
					        		  numeroCapitolo = xmlReader.getElementText();
				                        continue;
				                    }
					        	  
					        	  
					        	  if (element.equals("NumParagrafo"))
				                    {
					        		  numParagrafo = xmlReader.getElementText();
				                        continue;
				                    }
					        	  
					        	  if (element.equals("TitoloParagrafo"))
				                    {
					        		  titoloParagrafo = xmlReader.getElementText();
				                        continue;
				                    }
					        	  
					        	  if (element.equals("Testo"))
				                    {
					        		  testoParagrafo = xmlReader.getElementText();
				                        continue;
				                    }
					        	  
				                    }//chiude if capitolo
					        	  
					      
					        // If we reach the end of an currOperaContenutoInfo element, we add it to the list
					         if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementParagrafo = xmlReader.getLocalName();
					        	 if (elementParagrafo.equals("Paragrafo")){
					        		 ParagrafoInfo paragrafoInfo = new ParagrafoInfo();
					        		  paragrafoInfo.setNumeroParagrafo(numParagrafo);
					        		  paragrafoInfo.setTitoloParagrafo(titoloParagrafo);
					        		  paragrafoInfo.setTestoParagrafo(testoParagrafo);
					        		  paragrafiList.add(paragrafoInfo);
					        		  continue;
					        	 }
					        }
					        	 
					        if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementCapitolo = xmlReader.getLocalName();
					        	 if (elementCapitolo.equals("Capitolo")){
					        		 CapitoloInfo capitoloInfo = new CapitoloInfo();
					        		 capitoloInfo.setNumeroCapitolo(numeroCapitolo);
					        		 capitoloInfo.setTitoloCapitolo(titoloCapitolo);
					        		 capitoloInfo.setParagrafoList(paragrafiList);
					        		 capitoliList.add(capitoloInfo);
					        		 continue;
					        		// System.out.println(" %%%%%%%%%%%%%% numero di paragrafi per capitolo "+capitoloInfo.getParagrafiList().size()+" %%%%%%%%%%%%%%%%%%%");
					        	 }
					        }
					        if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementLibro = xmlReader.getLocalName();
					        	 if (elementLibro.equals("Libro")){
					        		// System.out.println(" End Libro ");
					        		 Libro libro = new Libro();
					        		 libro.setNumLibro(numLibro);
					        		 libro.setTitoloLibro(titoloLibro);
					        		 libro.setCapitoliList(capitoliList);
					        		 libroList.add(libro);
					        		 continue;
					        	 }
					        }
					        
					        
					        if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementAutore = xmlReader.getLocalName();
					        	 if (elementAutore.equals("Autore")){
					        		// System.out.println(" End Autore ");
					        	     autore = new Autore();
					        		 autore.setName(nameAutore);
					        		 autore.setURI(uriAutore);
					        		 isAutore = false;
					        	//	 System.out.println(" %%%%%%%%%%%%%% autore fineeeeeeeeeeeeeeeee ");
					        		 continue;
					        	 }
					        }
					        
					        if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementInfo = xmlReader.getLocalName();
					        	 if (elementInfo.equals("Info")){
					        		// System.out.println(" End Info ");
					        		info = new Info();
					        		info.setAutore(autore);
					        		info.setTitoloOpera(titoloOpera);
					        		info.setOperaURI(operaUri);
					        		info.setTipoOpera(tipoOpera);
					        		info.setAreaList(areaList);
					        		info.setEdizioneList(edizioneList);
					        		 continue;
					        		 //System.out.println(" %%%%%%%%%%%%%% info nome autore "+info.getAutore().getName() +"\n uriautore "+ info.getAutore().getURI()+"\n info TitoloOpera "+info.getTitoloOpera()
					        		//		 +"\n dimensione aree list  "+ areaList.size() +"\n dimensione edizione  list  "+ edizioneList.size());
					        	 }
					        }
					        
					       if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementCommentatore = xmlReader.getLocalName();
					        	 if (elementCommentatore.equals("Commentatore")){
					        		 commentatoreList.add(comm);
					        		// System.out.println(" End Commentatore ");
					        		   isComm = false;  
					        		   continue;
					        	 }
					        }
					        
					        if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementTraduttore = xmlReader.getLocalName();
					        	 if (elementTraduttore.equals("Traduttore")){
					        		 traduttoreList.add(trad);
					        		// System.out.println(" End Traduttore ");
					        		   isTrad = true;    
					        		   continue;
					        	 }
					        }
					        
					        
					        if (event == XMLStreamConstants.END_ELEMENT) {
					        	 String elementOpera = xmlReader.getLocalName();
					        	 if (elementOpera.equals("Opera")) {
					        		 operaContenuto = new OperaContenutoInfo();
					        		 operaContenuto.setInfo(info);
					        		 operaContenuto.setTraduttoreList(traduttoreList);
					        		 operaContenuto.setCommentatoreList(commentatoreList);
					        		 operaContenuto.setLibroList(libroList);
					        		 operaContenuto.setTitoloOpera(titoloOpera);
					        		 continue;
					        	 }
					        }
					       
					   }//chiude while
				}
			}//chiude for
		}//chiude try
		catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException:: "+ e);
		}
		 catch (XMLStreamException e) {
			 e.printStackTrace();
			System.out.println("XMLStreamException during parser :: "+ e );
		}
		/*System.out.println("operaContenuto getLibro size "+operaContenuto.getLibroList().size());
		System.out.println("operaContenuto libro 0 getLibro titolo  "+operaContenuto.getLibroList().get(0).getTitoloLibro());
		System.out.println("operaContenuto libro 0 num capitoli size "+operaContenuto.getLibroList().get(0).getcapitoliList().size());
		System.out.println("operaContenuto operaURI "+operaContenuto.getInfo().getOperaURI());
		System.out.println("operaContenuto tipo opera "+operaContenuto.getInfo().getTipoOpera());
		System.out.println("operaContenuto traduttoreList size "+operaContenuto.getTraduttoreList().size());
		System.out.println("operaContenuto getCommentatoreList size "+operaContenuto.getCommentatoreList().size());*/
		return operaContenuto;
	}
	
	   
	   
	   
}//chiude classe
