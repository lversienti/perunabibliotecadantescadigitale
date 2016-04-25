package perunaenciclopediadantescadigitale.prindante.eu.parser;

import java.util.ArrayList;


public class Notes {
	private InfoOpera infoOpera;
	private ArrayList<Nota> noteList;
	
	public void setInfoOpera(InfoOpera infoOpera){
		this.infoOpera = infoOpera;
	}
	
	public void setNoteList(ArrayList<Nota> noteList){
		this.noteList = noteList;
	}
	
	public InfoOpera getInfoOpera(){
		return infoOpera;
	}

	public ArrayList<Nota> getNoteList(){
		return noteList;
	}
	
}
