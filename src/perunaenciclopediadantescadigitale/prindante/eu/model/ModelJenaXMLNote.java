package perunaenciclopediadantescadigitale.prindante.eu.model;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

import perunaenciclopediadantescadigitale.prindante.eu.parser.Citazione;
import perunaenciclopediadantescadigitale.prindante.eu.parser.InfoOperaCitata;
import perunaenciclopediadantescadigitale.prindante.eu.parser.Nota;
import perunaenciclopediadantescadigitale.prindante.eu.parser.Notes;
import perunaenciclopediadantescadigitale.prindante.eu.utils.VocabolaryURI;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.ValidityReport.Report;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC_11;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.XSD;

/**
 * @author Loredana
 * @author Daniele
 */
public class ModelJenaXMLNote {
	
	Notes notes;

	public ModelJenaXMLNote(Notes notes){	
		this.notes = notes;
	}

	/**
	 * @param path 
	 * @param danteWork
	 */
	public void createRDFGraphModelNote(String path, String danteWork) {

		try{
			// CREAZIONE MODELLO
			Model model = ModelFactory.createOntologyModel();
			
			// WRITER RDF/XML
			Writer toRDFXML = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
			RDFWriter writerXML = model.getWriter("RDF/XML");
			writerXML.setProperty("showXmlDeclaration", "true");
			writerXML.setProperty("tab", "8");
			
			// WRITER TURTLE
			Writer toTurtle = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.replace("rdf", "ttl")), "UTF-8"));
			RDFWriter writerTurtle = model.getWriter("TURTLE");
			writerTurtle.setProperty("tab", "8");
						
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

			// CREAZIONE RISORSE E PROPRIETÀ
			
			/******************************************/
			/****   Common Resources/Properties   *****/
			/******************************************/
			Resource efrbrooResource_ExpressionFragment = model.createResource(VocabolaryURI.efrbroo + "ExpressionFragment");
			Resource efrbrooResource_Expression = model.createResource(VocabolaryURI.efrbroo + "Expression");
			Resource efrbrooResource_Work = model.createResource(VocabolaryURI.efrbroo + "Work");
			Resource oaResource_Annotation = model.createResource(VocabolaryURI.oa + "Annotation");
			Resource oaResource_Body = model.createResource(VocabolaryURI.oa + "Body");
			Resource foafResource_Person = model.createResource(VocabolaryURI.foaf + "Person");
			Resource skosResource_Concept  = model.createResource(VocabolaryURI.skos + "Concept");
			Property cntProperty_ContentAsText = model.createProperty(VocabolaryURI.dctypes, "ContentAsText");
			
			/******************************************/
			/************   oa:hasBody   **************/
			/******************************************/ 
			Property oaProperty_hasBody = model.createProperty(VocabolaryURI.oa, "hasBody");
			model.add(oaProperty_hasBody, RDFS.domain, oaResource_Annotation);
			model.add(oaProperty_hasBody, RDFS.range,  oaResource_Body);
					
			/******************************************/
			/***********   oa:hasTarget   *************/
			/******************************************/ 
			Property oaProperty_hasTarget = model.createProperty(VocabolaryURI.oa, "hasTarget"); 
			model.add(oaProperty_hasBody, RDFS.domain, oaResource_Annotation);
			model.add(oaProperty_hasBody, RDFS.range,  oaResource_Body);
		
			/******************************************/
			/************   dc:subject   **************/
			/******************************************/ 
			model.add(DC_11.subject, RDFS.domain, efrbrooResource_Work);
			model.add(DC_11.subject, RDFS.range,  skosResource_Concept);

			/******************************************/
			/***********   tipo citazione   ***********/
			/******************************************/ 
			Property dntProperty_tipoCitazione = model.createProperty(VocabolaryURI.dnt, "tipoCitazione");
			model.add(dntProperty_tipoCitazione, RDFS.domain, efrbrooResource_ExpressionFragment);
			model.add(dntProperty_tipoCitazione, RDFS.range,  XSD.xstring);

			/******************************************/
			/*************   bodyFormat   *************/
			/******************************************/ 
			Property dntProperty_bodyFormat = model.createProperty(VocabolaryURI.dnt, "bodyFormat");
			model.add(dntProperty_bodyFormat, RDFS.domain, oaResource_Body);
			model.add(dntProperty_bodyFormat, RDFS.range,  XSD.xstring);
			model.add(dntProperty_bodyFormat, RDFS.subPropertyOf, DC_11.format);

			/******************************************/
			/**************   efFormat   **************/
			/******************************************/ 
			Property dntProperty_efFormat = model.createProperty(VocabolaryURI.dnt, "efFormat");
			model.add(dntProperty_efFormat, RDFS.domain, efrbrooResource_ExpressionFragment);
			model.add(dntProperty_efFormat, RDFS.range,  XSD.xstring);
			model.add(dntProperty_bodyFormat, RDFS.subPropertyOf, DC_11.format);

			/******************************************/
			/************   hasSelector   *************/
			/******************************************/
			Property oaProperty_hasSelector = model.createProperty(VocabolaryURI.oa, "hasSelector");
			Resource oaResource_selector= model.createResource(VocabolaryURI.oa + "Selector");
			model.add(oaProperty_hasSelector, RDFS.domain, efrbrooResource_ExpressionFragment);
			model.add(oaProperty_hasSelector, RDFS.range, oaResource_selector);

			/******************************************/
			/***************   hasNote   **************/
			/******************************************/
			Property aoProperty_hasNote = model.createProperty(VocabolaryURI.crm, "hasNote");
			model.add(aoProperty_hasNote, RDFS.domain, efrbrooResource_ExpressionFragment);
			model.add(aoProperty_hasNote, RDFS.range, oaResource_Body);

			/******************************************/
			/**************   hasSource   *************/
			/******************************************/
			Property aoProperty_hasSource = model.createProperty(VocabolaryURI.oa, "hasSource");
			Resource oaResource_Source = model.createResource(VocabolaryURI.oa + "Source");
			model.add(aoProperty_hasSource, RDFS.domain, efrbrooResource_ExpressionFragment);
			model.add(aoProperty_hasSource, RDFS.range, oaResource_Source);

			/******************************************/
			/**************   isPartOf   **************/
			/******************************************/
			Property rdfProperty_isPartOf = model.createProperty(VocabolaryURI.frbr + "isPartOf");

			/******************************************/
			/***  numChapter, numParagraph, numBook  **/
			/******************************************/
			Property dntProperty_numChapter = model.createProperty(VocabolaryURI.dnt, "numChapter");
			Property dntProperty_numParagraph = model.createProperty(VocabolaryURI.dnt, "numParagraph");
			Property dntProperty_numBook = model.createProperty(VocabolaryURI.dnt, "numBook");

			/******************************************/
			/*************   isChapterOf   ************/
			/******************************************/
			Property rdfProperty_isChapterOf = model.createProperty(VocabolaryURI.dnt, "isChapterOf");
			Resource domainChapter = model.createResource(VocabolaryURI.doco + "Chapter");
			Resource subPropertyOfisPartOf = model.createResource(VocabolaryURI.frbr + "isPartOf");
			Resource rangeBook = model.createResource(VocabolaryURI.fabio + "Book");
			model.add(rdfProperty_isChapterOf, RDFS.domain, domainChapter);
			model.add(rdfProperty_isChapterOf, RDFS.range, rangeBook);
			model.add(rdfProperty_isChapterOf, RDFS.subPropertyOf, subPropertyOfisPartOf);

			/******************************************/
			/***********   isParagraphOf   ************/
			/******************************************/
			Property rdfResource_isParagraphOf = model.createProperty(VocabolaryURI.dnt, "isParagraphOf");
			Resource domainParagraph = model.createResource(VocabolaryURI.doco + "Paragraph");
			model.add(rdfResource_isParagraphOf, RDFS.domain, domainParagraph);
			model.add(rdfResource_isParagraphOf, RDFS.range, domainChapter);
			model.add(rdfResource_isParagraphOf, RDFS.subPropertyOf, subPropertyOfisPartOf);

			/******************************************/
			/*************   publisher   **************/
			/******************************************/
			model.add(DC_11.publisher, RDFS.domain, efrbrooResource_Expression);
			model.add(DC_11.publisher, RDFS.range,  XSD.xstring);

			/******************************************/
			/**************   isBookOf   **************/
			/******************************************/
			Property rdfResource_isBookOf = model.createProperty(VocabolaryURI.dnt, "isBookOf");
			model.add(rdfResource_isBookOf, RDFS.domain, rangeBook);
			model.add(rdfResource_isBookOf, RDFS.range, efrbrooResource_Work);
			model.add(rdfResource_isBookOf, RDFS.subPropertyOf, subPropertyOfisPartOf);
			
			/******************************************/
			/*************   citesWork   **************/
			/******************************************/
			Property dntProperty_citesWork = model.createProperty(VocabolaryURI.dnt, "citesWork");
			Property citoProperty_cites = model.createProperty(VocabolaryURI.cito, "cites");
			model.add(dntProperty_citesWork, RDFS.domain, efrbrooResource_ExpressionFragment);
			model.add(dntProperty_citesWork, RDFS.range, efrbrooResource_Work);
			model.add(dntProperty_citesWork, RDFS.subPropertyOf, citoProperty_cites);

			/******************************************/
			/************   citesConcept   ************/
			/******************************************/
			Property dntProperty_concept = model.createProperty(VocabolaryURI.dnt, "citesConcept");
			model.add(dntProperty_concept, RDFS.domain, efrbrooResource_ExpressionFragment);
			model.add(dntProperty_concept, RDFS.range, skosResource_Concept);
			model.add(dntProperty_concept, RDFS.subPropertyOf, citoProperty_cites);
			
			/******************************************/
			/**********   citesExpression   ***********/
			/******************************************/
			Property dntProperty_expression = model.createProperty(VocabolaryURI.dnt, "citesExpression");
			model.add(dntProperty_expression, RDFS.domain, efrbrooResource_ExpressionFragment);
			model.add(dntProperty_expression, RDFS.range, efrbrooResource_Expression);
			model.add(dntProperty_expression, RDFS.subPropertyOf, citoProperty_cites);

			/******************************************/
			/************   workCreator   *************/
			/******************************************/
			Property dntProperty_workCreator = model.createProperty(VocabolaryURI.dnt, "workCreator");
			model.add(dntProperty_workCreator, RDFS.domain, efrbrooResource_Work);
			model.add(dntProperty_workCreator, RDFS.range, foafResource_Person);
			model.add(dntProperty_workCreator, RDFS.subPropertyOf, DC_11.creator);

			/******************************************/
			/**********   conceptCreator   ************/
			/******************************************/
			Property dntProperty_conceptCreator= model.createProperty(VocabolaryURI.dnt, "conceptCreator");
			model.add(dntProperty_conceptCreator, RDFS.domain, skosResource_Concept);
			model.add(dntProperty_conceptCreator, RDFS.range, foafResource_Person);
			model.add(dntProperty_conceptCreator, RDFS.subPropertyOf, DC_11.creator);

			/******************************************/
			/**********   nameWorkCreator   ***********/
			/******************************************/
			Property dntProperty_creatorName = model.createProperty(VocabolaryURI.dnt, "creatorName");
			model.add(dntProperty_creatorName, RDFS.domain, efrbrooResource_Work);
			model.add(dntProperty_creatorName, RDFS.range, XSD.xstring);
			model.add(dntProperty_creatorName, RDFS.subPropertyOf, FOAF.name);

			/******************************************/
			/**********   workExpression   ************/
			/******************************************/
			Property dntProperty_workExpression= model.createProperty(VocabolaryURI.dnt, "workExpression");
			model.add(dntProperty_workExpression, RDFS.domain, efrbrooResource_Expression);
			model.add(dntProperty_workExpression, RDFS.range, foafResource_Person);
			model.add(dntProperty_workExpression, RDFS.subPropertyOf, DC_11.creator);

			/******************************************/
			/******   citesCitazioneEsplicita   *******/
			/******************************************/
			Property dntProperty_citesCitazioneEsplicita = model.createProperty(VocabolaryURI.dnt, "citesCitazioneEsplicita");
			model.add(dntProperty_citesCitazioneEsplicita, RDFS.domain, efrbrooResource_ExpressionFragment);
			//model.add(dntProperty_citesCitazioneEsplicita, RDFS.range, efrbrooResource_Work);
			model.add(dntProperty_citesCitazioneEsplicita, RDFS.subPropertyOf, citoProperty_cites);

			/******************************************/
			/******   citesConcordanzaGenerica   ******/
			/******************************************/
			Property dntProperty_citesConcordanzaGenerica = model.createProperty(VocabolaryURI.dnt, "citesConcordanzaGenerica");
			model.add(dntProperty_citesConcordanzaGenerica, RDFS.domain, efrbrooResource_ExpressionFragment);
			//model.add(dntProperty_citesConcordanzaGenerica, RDFS.range, efrbrooResource_Work);
			model.add(dntProperty_citesConcordanzaGenerica, RDFS.subPropertyOf, citoProperty_cites);

			/******************************************/
			/******   citesConcordanzaStringente   ****/
			/******************************************/
			Property dntProperty_citesConcordanzaStringente = model.createProperty(VocabolaryURI.dnt, "citesConcordanzaStringente");
			model.add(dntProperty_citesConcordanzaStringente, RDFS.domain, efrbrooResource_ExpressionFragment);
			//model.add(dntProperty_citesConcordanzaGenerica, RDFS.range, efrbrooResource_Work);
			model.add(dntProperty_citesConcordanzaStringente, RDFS.subPropertyOf, citoProperty_cites);

			/******************************************/
			/*********   hasCitingFragment   **********/
			/******************************************/
			Property dntProperty_hasCitingFragment = model.createProperty(VocabolaryURI.dnt, "hasCitingFragment");
			Property oaProperty_hasFragment = model.createProperty(VocabolaryURI.efrbroo, "hasFragment");
			model.add(dntProperty_hasCitingFragment, RDFS.domain, oaResource_Body);
			model.add(dntProperty_hasCitingFragment, RDFS.range, efrbrooResource_ExpressionFragment);
			model.add(dntProperty_hasCitingFragment, RDFS.subPropertyOf, oaProperty_hasFragment);

			/******************************************/
			/**************   bodyChars   *************/
			/******************************************/
			Property dntProperty_bodyChars = model.createProperty(VocabolaryURI.dnt, "bodyChars");
			Property cntProperty_chars = model.createProperty(VocabolaryURI.cnt, "chars");
			model.add(dntProperty_bodyChars, RDFS.domain, oaResource_Body);
			model.add(dntProperty_bodyChars, RDFS.range, XSD.xstring);
			model.add(dntProperty_bodyChars, RDFS.subPropertyOf, cntProperty_chars);
			
			/******************************************/
			/**********   broaderTransitive   *********/
			/******************************************/
			Property skosProperty_broaderTransitive = model.createProperty(VocabolaryURI.skos, "broaderTransitive");
			model.add(skosProperty_broaderTransitive, RDFS.domain, skosResource_Concept);
			model.add(skosProperty_broaderTransitive, RDFS.range, skosResource_Concept);

			/******************************************/
			/**************   efChars   ***************/
			/******************************************/
			Property dntProperty_efChars = model.createProperty(VocabolaryURI.dnt, "efChars");
			model.add(dntProperty_efChars, RDFS.subPropertyOf, cntProperty_chars);
			model.add(dntProperty_efChars, RDFS.domain, efrbrooResource_ExpressionFragment);
			model.add(dntProperty_efChars, RDFS.range, XSD.xstring);

			/******************************************/
			/*********   dcterms:created   ************/
			/******************************************/
			Property dcProperty_created = model.createProperty(VocabolaryURI.dcterms, "created");
			model.add(dcProperty_created, RDFS.domain, efrbrooResource_Work);
			model.add(dcProperty_created, RDFS.range, XSD.xstring);
			
			// IL DOMINIO DELLA PROPRIETÀ ALTERNATIVE PUÒ ESSERE WORK O CONCEPT
			
			/******************************************/
			/********   dcterms:alternative   *********/
			/******************************************/
			Property dcProperty_alternative = model.createProperty(VocabolaryURI.dcterms, "alternative");
			//model.add(dntProperty_alternative, RDFS.domain, efrbrooResource_Work);
			model.add(dcProperty_alternative, RDFS.range, XSD.xstring);

			// INIZIALIZZA VARIABILI
			int t = 0;
			int numTitolo = 0;
			int numAutore = 0;
			int numAutoreWork = 0;
			
			// RECUPERA LISTA NOTE
			ArrayList<Nota> notesList = notes.getNoteList();
			
			// RECUPERA DATI OPERA
			String danteAuthorName = notes.getInfoOpera().getAutore().getName();
			String danteAuthorURI = notes.getInfoOpera().getAutore().getURI();
			String danteWorkEdition = notes.getInfoOpera().getEdizioneList().get(0);
			String danteWorkURI = notes.getInfoOpera().getOperaURI();
			String danteTitle = notes.getInfoOpera().getTitoloOpera();
			String danteDate = notes.getInfoOpera().getDate();
			
			// INIZIALIZZA RISORSE
			Resource resource_FontePrimaria = null;
			Resource resource_AutoreURI = null;
			Resource resource_Chapter = null;
			Resource resource_ExprFrag1 = null;
			Resource resource_danteAuthor = null;
			
			try {
				// PER OGNI NOTA...
				for (int j = 0; j < notesList.size(); j++) {
					
					// RECUPERA DATI NOTA
					Nota nota = notesList.get(j);
					String testo = nota.getTestoCitazione().trim();
					String body = nota.getBodyCitazione().trim();
					String danteBook = nota.getSource().getNumLibro();
					String danteChapter = nota.getSource().getNumCapitolo();
					String danteParagraph = nota.getSource().getNumParagrafo();
					Literal literalNumBook = model.createTypedLiteral(new Integer(danteBook), XSDDatatype.XSDint);
					Literal literalNumChapter = model.createTypedLiteral(new Integer(danteChapter), XSDDatatype.XSDint);
					Literal literalNumParagraph = model.createTypedLiteral(new Integer(danteParagraph), XSDDatatype.XSDint);
					Literal literal_format = model.createLiteral("text/plain");
					
					// CREA URI
					String exprFragURI = danteWork + "/libro/" + danteBook + "/capitolo/" + danteChapter + "/paragrafo/" + danteParagraph + "/exprFrag/" + j;
					String bodyURI = danteWork + "/libro/" + danteBook + "/capitolo/" + danteChapter + "/paragrafo/" + danteParagraph + "/body/" + j;
					String annotationURI = danteWork + "/libro/" + danteBook + "/capitolo/" + danteChapter + "/paragrafo/" + danteParagraph + "/annotation/" + j;
					String sourceParagraphURI = danteWork + "/libro/" + danteBook + "/capitolo/" + danteChapter + "/paragrafo/" + danteParagraph;
					String uriSelectorURI = danteWork + "/selector/" + j;
					String danteWorkBookURI =  danteWork + "/libro/" + danteBook;
					
					// CREA RISORSE
					Resource resource_Body = model.createResource(bodyURI);
					Resource resource_ExprFrag = model.createResource(exprFragURI);
					Resource resource_Annotation = model.createResource(annotationURI);
					Resource resource_SourceParagrafo = model.createResource(sourceParagraphURI);
					Resource resource_Selector = model.createResource(uriSelectorURI);
					Resource resource_Book =  model.createResource(danteWorkBookURI);
					Resource resource_danteWork = model.createResource(danteWorkURI);

					// EXPRESSION FRAGMENT
					Literal literal_fragment = model.createLiteral(testo);
					model.add(resource_ExprFrag, aoProperty_hasNote, resource_Body);
					model.add(resource_ExprFrag, oaProperty_hasSelector, resource_Selector);
					model.add(resource_ExprFrag, dntProperty_efChars, literal_fragment);
					model.add(resource_ExprFrag, dntProperty_efFormat, literal_format);
					model.add(resource_ExprFrag, aoProperty_hasSource, resource_SourceParagrafo);
					model.add(resource_SourceParagrafo, dntProperty_numParagraph, literalNumParagraph);
					
					// BODY
					Literal literal_body = model.createLiteral(body);
					model.add(resource_Body, dntProperty_bodyChars, literal_body);
					model.add(resource_Body, dntProperty_bodyFormat, "text/plain");
					model.add(resource_Body, RDF.type, cntProperty_ContentAsText);

					// ANNOTATION
					model.add(resource_Annotation, oaProperty_hasTarget, resource_ExprFrag);
					model.add(resource_Annotation, oaProperty_hasBody, resource_Body);
					model.add(resource_Annotation, RDF.type, oaResource_Annotation);

					// BOOK
					Literal literalDanteTitle = model.createLiteral(danteTitle);
					Literal literalDanteDate = model.createLiteral(danteDate);
					Literal literalDanteAuthorName = model.createLiteral(danteAuthorName);
					model.add(resource_Book, dntProperty_numBook, literalNumBook);
					model.add(resource_Book, rdfResource_isBookOf, resource_danteWork);
					resource_danteAuthor = model.createResource(danteAuthorURI);
					model.add(resource_danteWork, dntProperty_workCreator, resource_danteAuthor);
					model.add(resource_danteWork, dcProperty_alternative, literalDanteTitle);
					model.add(resource_danteWork, dcProperty_created, literalDanteDate);
					model.add(resource_danteWork, DC_11.publisher, danteWorkEdition);
					model.add(resource_danteAuthor, dntProperty_creatorName, literalDanteAuthorName);

					// CHAPTER
					String chapterURI = danteWorkBookURI + "/capitolo/" + danteChapter;
					resource_Chapter = model.createResource(chapterURI);
					model.add(resource_Chapter, rdfProperty_isPartOf, resource_Book);
					model.add(resource_SourceParagrafo, rdfResource_isParagraphOf, resource_Chapter);
					model.add(resource_Chapter, dntProperty_numChapter, literalNumChapter);
					model.add(resource_Chapter, rdfProperty_isChapterOf, resource_Book);

					// SELETTORE
					String startCursor = nota.getSelettore().getStart();
					String endCursor = nota.getSelettore().getEnd();
					
					if (startCursor!=null && endCursor!=null) {
						Property oaProperty_TextPositionSelector= model.createProperty(VocabolaryURI.oa, "TextPositionSelector");
						Property oaProperty_start= model.createProperty(VocabolaryURI.oa, "start");
						Property oaProperty_end= model.createProperty(VocabolaryURI.oa, "end");
						model.add(resource_Selector, oaProperty_start, startCursor);
						model.add(resource_Selector, oaProperty_end, endCursor);
						model.add(resource_Selector, RDF.type, oaProperty_TextPositionSelector); 
					}
					
					// RECUPERA LISTA CITAZIONI
					ArrayList<Citazione> citazioni = nota.getCitazioneList();
					
					try {
						// PER OGNI CITAZIONE...
						for (int i = 0; i < citazioni.size(); i++) {
							
							// RECUPERA CITAZIONE
							Citazione citazione = citazioni.get(i);
							InfoOperaCitata operaCitata = citazione.getInfoOperaCitata();
							String frammento = citazione.getFrammento().trim();
							String tipoOpera = operaCitata.getTipoOpera();
							String titolo = operaCitata.getTitoloOpera().trim();
							String autoreName = operaCitata.getAutore().getName();
							String autoreUri = operaCitata.getAutore().getURI();
							String tipoCitazione = citazione.getTipoCitazione();
							String opera = operaCitata.getOperaURI();
							
							// GESTIONE TITOLI VUOTI
							if (titolo.isEmpty()) {
								titolo = "Senza titolo";
							}
							
							// GESTIONE URI OPERA MANCANTI
							if (!opera.trim().contains("http://")) {
								opera = danteWork + "/titolo/inesistente/" + numTitolo;
								numTitolo = numTitolo + 1;
							}
							
							// CREA RISORSA OPERA
							resource_FontePrimaria = model.createResource(opera);

							try {
								// EXPRESSION FRAGMENT 1
								String exprFrag1 = danteWork + "/libro/" + danteBook + "/capitolo/" + danteChapter + "/paragrafo/" + danteParagraph + "/exprFrag1/" + t;
								resource_ExprFrag1 = model.createResource(exprFrag1);
								Literal literal_fragment1 = model.createLiteral(frammento);
								model.add(resource_ExprFrag1, dntProperty_efChars, literal_fragment1);
								model.add(resource_ExprFrag1, dntProperty_efFormat, literal_format);
								model.add(resource_ExprFrag1, aoProperty_hasSource, resource_Body);
								t = t + 1;
								
								// CITAZIONE ESPLICITA
								if (tipoCitazione.equals("CITAZIONE ESPLICITA")) {
									model.add(resource_Body, dntProperty_hasCitingFragment, resource_ExprFrag1);
									model.add(resource_ExprFrag1, dntProperty_citesCitazioneEsplicita, resource_FontePrimaria);
									model.add(resource_ExprFrag1, dntProperty_tipoCitazione, tipoCitazione);
								}
								
								// CONCORDANZA GENERICA
								else if (tipoCitazione.equals("CONCORDANZA GENERICA")) {
									model.add(resource_Body, dntProperty_hasCitingFragment, resource_ExprFrag1);
									model.add(resource_ExprFrag1, dntProperty_citesConcordanzaGenerica, resource_FontePrimaria);
									model.add(resource_ExprFrag1, dntProperty_tipoCitazione, tipoCitazione);
								}
								
								// CONCORDANZA STRINGENTE
								else if (tipoCitazione.equals("CONCORDANZA STRINGENTE")) {
									model.add(resource_Body, dntProperty_hasCitingFragment, resource_ExprFrag1);
									model.add(resource_ExprFrag1, dntProperty_citesConcordanzaStringente, resource_FontePrimaria);
									model.add(resource_ExprFrag1, dntProperty_tipoCitazione, tipoCitazione);
								}
								
								// GESTIONE TIPO CITAZIONE ERRATO
								else {
									System.out.println("ERRORE — Il seguente tipo citazione non è ammesso: " + tipoCitazione);
								}
							}
							
							// GESTIONE ECCEZIONI
							catch (Exception e)
							{
								System.out.println("exp " + e);
							}
							
							model.add(resource_FontePrimaria, dcProperty_alternative, titolo);

							// OPERA PRIMARIA DI TIPO CONCEPT
							if (tipoOpera.equals("CONCEPT")) {
								model.add(resource_FontePrimaria, RDF.type, skosResource_Concept);
								
								if (!autoreUri.trim().isEmpty()) {
									resource_AutoreURI = model.createResource(autoreUri.trim());
									model.add(resource_FontePrimaria, dntProperty_conceptCreator, resource_AutoreURI);
									Literal literal_autoreName = model.createLiteral(autoreName);
									model.add(resource_AutoreURI, dntProperty_creatorName, literal_autoreName);
								}
								
								else {
									autoreUri = danteWork+"autore/inesistente/" + numAutore;
									resource_AutoreURI = model.createResource(autoreUri);
									model.add(resource_FontePrimaria, dntProperty_conceptCreator, resource_AutoreURI);
									Literal literal_autoreName = model.createLiteral("Non disponibile");
									model.add(resource_AutoreURI, dntProperty_creatorName, literal_autoreName);
									numAutore = numAutore +1;
								}

								// GESTIONE AREE MULTIPLE
								ArrayList<String> areaList = operaCitata.getAreaList();
								for (int k = 0; k < areaList.size(); k++) {
									String areaUri = operaCitata.getAreaList().get(k);//<!- broaderTransitive ->
									Resource areaResource = model.createResource(areaUri);
									model.add(resource_FontePrimaria, skosProperty_broaderTransitive, areaResource);
								}
								
								// GESTIONE EDIZIONI
								/* ArrayList<String> edizioneList = operaCitata.getEdizioneList();
    		 					   for(int k = 0; k<edizioneList.size(); k++){
    			 				   // per ora metto sempre la stessa uri per l'area 
    			 				   String edizione = "http://it.wikipedia.org/wiki/edizione";
    			 				   // String edizione = operaCitata.edizioneList().get(k);
    			 				   //   if(edizione!=null)
    			   				   // 	 model.add(operaUriConcept_Resource, DC.publisher, edizione);
    			 						 model.add(resource_OperaURI, DC.publisher, edizione);
    		 					}*/
							}
							
							// OPERA PRIMARIA DI TIPO WORK
							else if (tipoOpera.equals("WORK")) {
								if (!autoreUri.trim().isEmpty()) {
									resource_AutoreURI = model.createResource(autoreUri.trim());
									model.add(resource_FontePrimaria, dntProperty_workCreator, resource_AutoreURI);
									Literal literal_autoreName = model.createLiteral(autoreName);
									model.add(resource_AutoreURI, dntProperty_creatorName, literal_autoreName);
								}
								else {
									autoreUri = danteWork+"autore/inesistente/"+numAutoreWork;
									resource_AutoreURI = model.createResource(autoreUri);
									model.add(resource_FontePrimaria, dntProperty_workCreator, resource_AutoreURI);
									Literal literal_autoreName = model.createLiteral("Non disponibile");
									model.add(resource_AutoreURI, dntProperty_creatorName, literal_autoreName);
									numAutoreWork = numAutoreWork +1;
								}

								// GESTIONE AREE MULTIPLE
								ArrayList<String> areaList = operaCitata.getAreaList();
								for (int k = 0; k<areaList.size(); k++) {
									String areaUri = operaCitata.getAreaList().get(k);//<!- dc:subject ->
									Resource areaResource = model.createResource(areaUri.trim());
									model.add(resource_FontePrimaria, DC_11.subject, areaResource);
								}
								
								// GESTIONE EDIZIONI
								/* ArrayList<String> edizioneList = operaCitata.getEdizioneList();
    		 					   for(int k = 0; k<edizioneList.size(); k++) {
    			 				   // per ora metto sempre la stessa uri per l'area 
    			 				   String edizione = "http://it.wikipedia.org/wiki/edizione";
    			 				   // String areaUri = operaCitata.getAreaList().get(k);//<!- adc:subject ->
    			 				   model.add(resource_OperaURI, DC.publisher, edizione);
    		 					}*/
							}
							
							// OPERA PRIMARIA DI TIPO EXPRESSION
							else if (tipoOpera.equals("EXPRESSION")) {
								model.add(resource_ExprFrag1, RDF.type, efrbrooResource_Expression);
								/**
								 *  Manca perché nel modello RDFS non è stata trovata ancora una property 
								 *  che indicasse l'expressionCreator come si è fatto per es. con workCreator
								 **/
								/* if(!autoreUri.trim().isEmpty()) {
    			 				   resource_AutoreURI = model.createResource(autoreUri);
    			 				   model.add(resource_OperaURI, dntProperty_workCreator, resource_AutoreURI);
    			 				   Literal literal_autoreName = model.createLiteral(autoreName);
    			 				   model.add(resource_AutoreURI, dntProperty_nameWorkCreator, literal_autoreName);
    		 					   }
								 */

								// GESTIONE AREE MULTIPLE
								ArrayList<String> areaList = operaCitata.getAreaList();
								for (int k = 0; k<areaList.size(); k++) {
									String areaUri = operaCitata.getAreaList().get(k);//<!- adc:subject ->
									Resource areaResource = model.createResource(areaUri.trim());
									model.add(resource_FontePrimaria, DC_11.subject, areaResource);
								}
								
								// GESTIONE EDIZIONI
								/* ArrayList<String> edizioneList = operaCitata.getEdizioneList();
    		 					   for(int k = 0; k<edizioneList.size(); k++){
    			 				   // per ora metto sempre la stessa uri per l'area 
    			 				   String edizione = "http://it.wikipedia.org/wiki/edizione";
    			 				   // String areaUri = operaCitata.getAreaList().get(k);//<!- adc:subject ->
    			 				   model.add(resource_OperaURI, DC.publisher, edizione);
    		 					}*/
							}
						}}
					
					// GESTIONE ECCEZIONI IN CICLO CITAZIONI
					catch (Exception e) {
						System.out.println("ECCEZIONE - Ciclo citazioni di ModelJenaXMLNote: " + e);
						e.getStackTrace();
					}

				}}
			
			// GESTIONE ECCEZIONI IN CICLO NOTE
			catch (Exception e) {
				System.out.println("ECCEZIONE - Ciclo note di ModelJenaXMLNote: " + e);
				e.getStackTrace();
			} 

			// VALIDAZIONE MODELLO
			InfModel infmodel = ModelFactory.createRDFSModel(model);
			ValidityReport validity = infmodel.validate();
			if (validity.isValid()) {
				System.out.println("INSERT - Validazione: il modello è valido\n");
			}
			else {
				// STAMPA ERRORI DI VALIDAZIONE
				Iterator<Report> reports = validity.getReports();
				while(reports.hasNext()) {
					System.out.println("\nERRORE - Il modello non è valido: " + reports.next() + "\n");
				}
			}

			// OUTPUT RDF/XML
			writerXML.write(model, toRDFXML, null);
			toRDFXML.close();
			
			// OUTPUT TURTLE 
			writerTurtle.write(model, toTurtle, null);
			toTurtle.close();
		}
		
		// GESTIONE ECCEZIONI PRINCIPALE
		catch (IOException e) {
			System.out.println("ECCEZIONE - In ModelJenaXMLNote: " + e.getMessage());
			e.getStackTrace();
		}
	}
}
