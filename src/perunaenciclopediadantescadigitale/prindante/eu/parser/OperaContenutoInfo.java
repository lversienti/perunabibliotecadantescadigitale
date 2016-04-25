package perunaenciclopediadantescadigitale.prindante.eu.parser;

import java.util.ArrayList;

/**
 * 
 * @author Loredana
 *
 */
public class OperaContenutoInfo {

	private Info info;
	private ArrayList<Commentatore> commentatoreList;
	private ArrayList<Traduttore> traduttoreList;
	private ArrayList<Libro> libroList;
	private String titoloOpera;	

	public void setInfo(Info info){
		this.info = info;
	}
	
	public Info getInfo(){
		return info;
	}
	
	public void setCommentatoreList(ArrayList<Commentatore> commentatoreList){
		this.commentatoreList = commentatoreList;
	}
	
	public ArrayList<Commentatore> getCommentatoreList(){
		return commentatoreList;
	}
	
	public ArrayList<Libro> getLibroList(){
		return libroList;
	}
	
	public void setLibroList(ArrayList<Libro> libroList){
		 this.libroList = libroList;
	}
	
	public void setTraduttoreList(ArrayList<Traduttore> traduttoreList){
		this.traduttoreList = traduttoreList;
	}
	
	public ArrayList<Traduttore> getTraduttoreList(){
		return traduttoreList;
	}
	
	public void setTitoloOpera(String titoloOpera){
		this.titoloOpera = titoloOpera;
	}
	 
	public String getTitoloOpera(){
		return titoloOpera;
	}
	
}
