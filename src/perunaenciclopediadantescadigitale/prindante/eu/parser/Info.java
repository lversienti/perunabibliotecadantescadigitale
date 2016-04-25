package perunaenciclopediadantescadigitale.prindante.eu.parser;


/**
 * @author Loredana
 * represent data contains into XML <Info> section e.g area, Autore, opera URI
 */
public class Info extends InfoOpera {
	
	 private String operaSorgente;
	 
	
		
		public void setOperaSorgente(String operaSorgente){
			this.operaSorgente = operaSorgente;
		}
		
		public String getOperaSorgente(){
			return operaSorgente;
		}
}
