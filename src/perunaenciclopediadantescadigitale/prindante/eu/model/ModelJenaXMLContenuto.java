package perunaenciclopediadantescadigitale.prindante.eu.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

import perunaenciclopediadantescadigitale.prindante.eu.parser.CapitoloInfo;
import perunaenciclopediadantescadigitale.prindante.eu.parser.Libro;
import perunaenciclopediadantescadigitale.prindante.eu.parser.OperaContenutoInfo;
import perunaenciclopediadantescadigitale.prindante.eu.parser.ParagrafoInfo;
import perunaenciclopediadantescadigitale.prindante.eu.utils.VocabolaryURI;

import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.ValidityReport.Report;
import com.hp.hpl.jena.vocabulary.DC_11;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

/**
 * @author Loredana
 * @author Daniele
 * create Model RDF and store on file system
 */
public class ModelJenaXMLContenuto {
	
	OperaContenutoInfo operaContenutoInfo;
	
	public ModelJenaXMLContenuto(OperaContenutoInfo operaContenutoInfo){
		this.operaContenutoInfo = operaContenutoInfo;
		}

	/**
	 * @param path 
	 * @param operaURI
	 */
	public void createRDFGraphModel(String path, String operaURI){
		try {
			// CREAZIONE MODELLO
			Model model = ModelFactory.createOntologyModel();
			RDFWriter writer = model.getWriter("RDF/XML");
			writer.setProperty("showXmlDeclaration", "true");
			writer.setProperty("tab", "8");

			// CREAZIONE PREFISSI
			model.setNsPrefix("rdf",             VocabolaryURI.rdf);
			model.setNsPrefix("foaf",            VocabolaryURI.foaf);
			model.setNsPrefix("oa",              VocabolaryURI.oa);
			model.setNsPrefix("efrbroo",         VocabolaryURI.efrbroo);
			model.setNsPrefix("dnt",             VocabolaryURI.dnt);
			model.setNsPrefix("cnt",             VocabolaryURI.cnt);
			model.setNsPrefix("crm",             VocabolaryURI.crm);
			model.setNsPrefix("dc",              VocabolaryURI.dc);
			model.setNsPrefix("doco",            VocabolaryURI.doco);
			model.setNsPrefix("fabio",           VocabolaryURI.fabio);
			model.setNsPrefix("skos",            VocabolaryURI.skos);
			model.setNsPrefix("frbr",            VocabolaryURI.frbr);
			model.setNsPrefix("cito",            VocabolaryURI.cito);
			model.setNsPrefix("dctypes",         VocabolaryURI.dctypes);
			model.setNsPrefix("owl",             VocabolaryURI.owl);
			model.setNsPrefix("rdfs",            VocabolaryURI.rdfs);
			model.setNsPrefix("dcterms",         VocabolaryURI.dcterms);

			// CREAZIONE RISORSE E PROPRIETA'

			/******************************************/
			/**********   Common Resources   **********/
			/******************************************/
	    	Resource efrbrooResource_Work = model.createResource(VocabolaryURI.efrbroo + "Work");
    		Resource fabioResource_Book = model.createResource(VocabolaryURI.fabio + "Book");
	      	Resource docoResource_Chapter = model.createResource(VocabolaryURI.doco + "Chapter");
	      	Resource docoResource_Paragraph = model.createResource(VocabolaryURI.doco + "Paragraph");
	    	Resource oaResource_Body = model.createResource(VocabolaryURI.oa + "Body");
	    	Resource cntProperty_ContentAsText = model.createResource(VocabolaryURI.dctypes + "ContentAsText");
			
			/******************************************/
			/**********   Common Properties   *********/
			/******************************************/
	      	Property frbrProperty_isPartOf = model.createProperty(VocabolaryURI.frbr + "isPartOf");
	    	Property aoProperty_hasBody = model.createProperty(VocabolaryURI.oa, "hasBody"); 

			/******************************************/
			/*************   dnt:numBook   ************/
			/******************************************/
    		Property dntProperty_numBook = model.createProperty(VocabolaryURI.dnt, "numBook");
    		model.add(dntProperty_numBook, RDFS.domain, fabioResource_Book);
			model.add(dntProperty_numBook, RDFS.range, XSD.integer);

			/******************************************/
			/***********   dnt:numChapter   ***********/
			/******************************************/
			Property dntProperty_numChapter = model.createProperty(VocabolaryURI.dnt, "numChapter");
    		model.add(dntProperty_numBook, RDFS.domain, docoResource_Chapter);
			model.add(dntProperty_numBook, RDFS.range, XSD.integer);

			/******************************************/
			/**********   dnt:numParagraph   **********/
			/******************************************/
			Property dntProperty_numParagraph = model.createProperty(VocabolaryURI.dnt, "numParagraph");
    		model.add(dntProperty_numBook, RDFS.domain, docoResource_Paragraph);
			model.add(dntProperty_numBook, RDFS.range, XSD.integer);

			/******************************************/
			/************   dnt:isBookOf   ************/
			/******************************************/
			Property rdfProperty_isBookOf = model.createProperty(VocabolaryURI.dnt, "isBookOf");
			model.add(rdfProperty_isBookOf, RDFS.domain, fabioResource_Book);
			model.add(rdfProperty_isBookOf, RDFS.range, efrbrooResource_Work);
			model.add(rdfProperty_isBookOf, RDFS.subPropertyOf, frbrProperty_isPartOf);

	      	/******************************************/
	    	/**********   dnt:isChapterOf   ***********/
	    	/******************************************/
	        Property rdfProperty_isChapterOf = model.createProperty(VocabolaryURI.dnt, "isChapterOf");
	      	model.add(rdfProperty_isChapterOf, RDFS.domain, docoResource_Chapter);
	      	model.add(rdfProperty_isChapterOf, RDFS.range, fabioResource_Book);
	      	model.add(rdfProperty_isChapterOf, RDFS.subPropertyOf, frbrProperty_isPartOf);

	      	/******************************************/
	     	/**********   dnt:isParagraphOf   *********/
	     	/******************************************/
	      	Property rdfResource_isParagraphOf = model.createProperty(VocabolaryURI.dnt, "isParagraphOf");
	      	model.add(rdfResource_isParagraphOf, RDFS.domain, docoResource_Paragraph);
	      	model.add(rdfResource_isParagraphOf, RDFS.range, docoResource_Chapter);
	      	model.add(rdfResource_isParagraphOf, RDFS.subPropertyOf, frbrProperty_isPartOf);
	      	
	      	/******************************************/
	     	/***********   dnt:bodyChars   ************/
	     	/******************************************/
			Property dntProperty_bodyChars = model.createProperty(VocabolaryURI.dnt, "bodyChars");
	    	Property cntProperty_chars = model.createProperty(VocabolaryURI.cnt, "chars");
	    	model.add(dntProperty_bodyChars, RDFS.domain, oaResource_Body);
	    	model.add(dntProperty_bodyChars, RDFS.range, XSD.xstring);
	    	model.add(dntProperty_bodyChars, RDFS.subPropertyOf, cntProperty_chars);
	    	
			/******************************************/
			/***********   dnt:bodyFormat   ***********/
			/******************************************/ 
			Property dntProperty_bodyFormat = model.createProperty(VocabolaryURI.dnt, "bodyFormat");
			model.add(dntProperty_bodyFormat, RDFS.domain, oaResource_Body);
			model.add(dntProperty_bodyFormat, RDFS.range,  XSD.xstring);
			model.add(dntProperty_bodyFormat, RDFS.subPropertyOf, DC_11.format);

			/******************************************/
			/********   dcterms:alternative   *********/
			/******************************************/
			Property dntProperty_alternative = model.createProperty(VocabolaryURI.dcterms, "alternative");
			model.add(dntProperty_alternative, RDFS.domain, efrbrooResource_Work);
			model.add(dntProperty_alternative, RDFS.range, XSD.xstring);

	    	Resource resource_Book = null;
	    	Resource resource_Chapter = null;
	    	Resource resource_Paragraph = null;
	    	Resource resource_Body = null;
	    	String bookURI = null;
	    	String chapterURI = null;
	    	String paragraphURI = null;
	    	
	    	// OPERA
    		Resource resource_Work = model.createResource(operaURI);
    		Literal literalWorkTitle = model.createLiteral(operaContenutoInfo.getTitoloOpera());
    		model.add(resource_Work, dntProperty_alternative, literalWorkTitle);
    		
    		// RECUPERO LISTA LIBRI
	    	ArrayList<Libro> libriList = operaContenutoInfo.getLibroList();
	    	
	    	// PER OGNI LIBRO...
	    	for (int j = 0; j < libriList.size(); j++) {
	    		
	    		// RECUPERO DATI LIBRO
	    		Libro libro =  libriList.get(j);
	    		int bookNumber = Integer.parseInt(libro.getNumLibro());
	    		String bookTitle = libro.getTitoloLibro();
	    		Literal literalBookNumber = model.createTypedLiteral(bookNumber);
	    		Literal literalBookTitle = model.createLiteral(bookTitle);
	    		bookURI = operaURI + "/libro/" + bookNumber;
	    		
	    		// CREAZIONE RISORSA LIBRO
	    		resource_Book = model.createResource(bookURI);
	    		model.add(resource_Book, RDF.type, fabioResource_Book);
	    		model.add(resource_Book, dntProperty_numBook, literalBookNumber);
	    		model.add(resource_Book, DC_11.title, literalBookTitle);
	    		model.add(resource_Book, rdfProperty_isBookOf, resource_Work);
	    		
	    		// RECUPERO LISTA CAPITOLI
	    		ArrayList<CapitoloInfo> chapterList = libro.getcapitoliList();
	    		
	    		// PER OGNI CAPITOLO...
	    		for (int i = 0; i < chapterList.size(); i++) {
	    			
	    			// RECUPERO DATI CAPITOLO
	    			CapitoloInfo chapter =  chapterList.get(i);
	    			int chapterNumber = Integer.parseInt(chapter.getNumeroCapitolo());
	    			String chapterTitle = chapter.getTitoloCapitolo();
	    			Literal literalChapterNumber = model.createTypedLiteral(chapterNumber);
	    			Literal literalChapterTitle = model.createLiteral(chapterTitle);
	    			chapterURI = bookURI + "/capitolo/" + chapterNumber;
	    			
	    			// CREAZIONE RISORSA CAPITOLO
	    			resource_Chapter = model.createResource(chapterURI);
	    			model.add(resource_Chapter, RDF.type, docoResource_Chapter);
	    			model.add(resource_Chapter, rdfProperty_isChapterOf, resource_Book);
	    			model.add(resource_Chapter, dntProperty_numChapter, literalChapterNumber);
	    			model.add(resource_Chapter, DC_11.title, literalChapterTitle);
	    	
	    			// RECUPERO LISTA PARAGRAFI
	    			ArrayList<ParagrafoInfo> paragraphList = chapter.getParagrafiList();
	    			
	    			// PER OGNI PARAGRAFO...
	    			for (int h = 0; h < paragraphList.size(); h++) {
	    				
	    				// RECUPERO DATI PARAGRAFO
	    				ParagrafoInfo paragraph = paragraphList.get(h);
	    				int paragraphNumber = Integer.parseInt(paragraph.getNumeroParagrafo());
	    				Literal literalParagraphNumber = model.createTypedLiteral(paragraphNumber);
	    				paragraphURI = chapterURI + "/paragrafo/" + paragraphNumber;
	    				
	    				// CREAZIONE RISORSA PARAGRAFO
	    				resource_Paragraph = model.createResource(paragraphURI);
	    				model.add(resource_Paragraph, RDF.type, docoResource_Paragraph);
	    				model.add(resource_Paragraph, rdfResource_isParagraphOf, resource_Chapter);
	    				model.add(resource_Paragraph, dntProperty_numParagraph, literalParagraphNumber);
	    				
	    				// CREAZIONE RISORSA BODY
	    		    	resource_Body = model.createResource(paragraphURI + "/body");
						Literal literal_Body = model.createLiteral(paragraph.getTestoParagrafo());
						model.add(resource_Body, dntProperty_bodyChars, literal_Body);
						model.add(resource_Body, dntProperty_bodyFormat, "text/plain");
						model.add(resource_Body, RDF.type, cntProperty_ContentAsText);
						model.add(resource_Paragraph, aoProperty_hasBody, resource_Body);
	    			}
	    		}
	    	}
	    	
			// VALIDAZIONE MODELLO
			InfModel infmodel = ModelFactory.createRDFSModel(model);
			ValidityReport validity = infmodel.validate();
			if (validity.isValid()) {
				System.out.println("VALIDAZIONE - Il modello è valido\n");
			}
			else {
				// STAMPA ERRORI DI VALIDAZIONE
				Iterator<Report> reports = validity.getReports();
				while(reports.hasNext()) {
					System.out.println("\nERRORE - Il modello non è valido: " + reports.next() + "\n");
				}
			}
		    
			// OUTPUT RDF/XML
			FileOutputStream out=new FileOutputStream(path);
		    Writer writerOut= new OutputStreamWriter(out, "UTF-8");
		    writer.write( model, out, "http://someurl.org/base/" );
		    writerOut.close();
		    out.close();			  
		}
		
		// GESTIONE ECCEZIONI
		catch (IOException e) {
			System.out.println("ECCEZIONE - In ModelJenaXMLContenuto: " + e.getMessage());
		}
	}	
}
