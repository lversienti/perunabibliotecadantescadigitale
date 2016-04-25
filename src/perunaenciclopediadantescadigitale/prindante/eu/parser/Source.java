package perunaenciclopediadantescadigitale.prindante.eu.parser;

public class Source {
	
	private String numLibro;
	private String numCapitolo;
	private String numParagrafo;
	
	
	public void setNumLibro(String numLibro){
		this.numLibro = numLibro;
	}

	public void setNumCapitolo(String numCapitolo){
		this.numCapitolo = numCapitolo;
	}

	public void setNumParagrafo(String numParagrafo){
		this.numParagrafo = numParagrafo;
	}
	
	public String getNumLibro(){
		return numLibro;
	}

	public String getNumCapitolo(){
		return numCapitolo;
	}

	public String getNumParagrafo(){
		return numParagrafo;
	}

}
