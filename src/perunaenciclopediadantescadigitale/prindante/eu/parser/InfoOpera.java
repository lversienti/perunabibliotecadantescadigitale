package perunaenciclopediadantescadigitale.prindante.eu.parser;

import java.util.ArrayList;

/**
 * represent data contains into XML <InfoOpera> section e.g tipoOpera
 */
public class InfoOpera {
	
	private String operaURI;
	private String titoloOpera;
	private ArrayList<String> edizioneList;
	private ArrayList<String> areaList;
	private Autore autore;
	private String tipoOpera;
	private String date;
	
	 public void setOperaURI(String operaURI){
			this.operaURI = operaURI;
		}
	 
	 
	 public void setTitoloOpera(String titoloOpera){
			this.titoloOpera = titoloOpera;
		}
	 
	 public void setEdizioneList(ArrayList<String> edizioneList){
			this.edizioneList = edizioneList;
		}
	 
	 public void setAreaList(ArrayList<String> areaList){
			this.areaList = areaList;
		}
	 
	 public void setAutore(Autore autore){
			this.autore = autore;
		}
	 
	 public void setDate(String date) {
			this.date = date;
		}
	 
	 public void setTipoOpera(String tipoOpera){
			this.tipoOpera = tipoOpera;
		}
	 
	 public String getOperaURI(){
			return operaURI;
		}
	 
	 public String getTitoloOpera(){
			return titoloOpera;
		}
	 
	 public ArrayList<String> getEdizioneList(){
			return edizioneList;
		}
	 
	 public ArrayList<String> getAreaList(){
			return areaList;
		}
	 
	 public Autore getAutore(){
			return autore;
		}
	
	 public String getTipoOpera(){
			return tipoOpera;
		}
	 
	 public String getDate(){
			return date;
		} 
}
