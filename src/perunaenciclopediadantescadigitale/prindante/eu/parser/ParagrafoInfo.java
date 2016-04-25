package perunaenciclopediadantescadigitale.prindante.eu.parser;

/**
 * set data to represent the paragraph e.g title, paragraph number
 */
public class ParagrafoInfo {
	
	private String numeroParagrafo;
	private String titoloParagrafo;
	private String testoParagrafo;
	
	public void setNumeroParagrafo(String numeroParagrafo){
		this.numeroParagrafo = numeroParagrafo;
	}
	
	public void setTitoloParagrafo(String titoloParagrafo){
		this.titoloParagrafo = titoloParagrafo;
	}
	
	public void setTestoParagrafo(String testoParagrafo){
		this.testoParagrafo = testoParagrafo;
	}

	public String getNumeroParagrafo(){
		return numeroParagrafo;
	}
	
	public String getTitoloParagrafo(){
		return titoloParagrafo;
	}
	
	public String getTestoParagrafo(){
		return testoParagrafo;
	}
	
	
	
}
