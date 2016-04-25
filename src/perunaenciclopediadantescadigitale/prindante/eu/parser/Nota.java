package perunaenciclopediadantescadigitale.prindante.eu.parser;

import java.util.ArrayList;


public class Nota {
	
	private String testo;
	private String body;
	private Source source;
	private ArrayList<Citazione> citazioneList;
	private Selettore selettore;

	
	public void setTestoCitazione(String testo){
		this.testo = testo;
	}

	public void setBodyCitazione(String body){
		this.body = body;
	}

	public void setSource(Source source){
		this.source = source;
	}

	public void setCitazioneList(ArrayList<Citazione> citazioneList){
		this.citazioneList = citazioneList;
	}
	
	public void setSelettore(Selettore selettore){
		this.selettore = selettore;
	}
	
	
	public String getTestoCitazione(){
		return testo;
	}

	public String getBodyCitazione(){
		return body;
	}
	
	public Source getSource(){
		return source;
	}
	
	
	public ArrayList<Citazione> getCitazioneList(){
		return citazioneList;
	}
	
	public Selettore getSelettore(){
		return selettore;
	}
	
}
