package perunaenciclopediadantescadigitale.prindante.eu.parser;

import java.util.ArrayList;

/**
 * set data to represent the chapter e.g title, chapter number
 */
public class CapitoloInfo {
	
	private String numeroCapitolo;
	private String titoloCapitolo;
	private ArrayList<ParagrafoInfo> paragrafiList;
	
	public CapitoloInfo(){
	}
	
	
	public void setTitoloCapitolo(String titoloCapitolo){
		this.titoloCapitolo = titoloCapitolo;
	}
	
	public void setNumeroCapitolo(String numeroCapitolo){
		this.numeroCapitolo = numeroCapitolo;
	}

	public void setParagrafoList(ArrayList<ParagrafoInfo> paragrafiList){
		this.paragrafiList = paragrafiList;
	}
	
	public String getNumeroCapitolo(){
		return numeroCapitolo;
	}
	
	public String getTitoloCapitolo(){
		return titoloCapitolo;
	}
	
	public ArrayList<ParagrafoInfo> getParagrafiList(){
		return paragrafiList;
	}
	
}
