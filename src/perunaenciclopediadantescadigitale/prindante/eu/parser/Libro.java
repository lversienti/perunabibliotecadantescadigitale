package perunaenciclopediadantescadigitale.prindante.eu.parser;

import java.util.ArrayList;

/**
 * @author Loredana
 * set data to represent the book e.g title, book number
 */
public class Libro {
	
private String numLibro;
private String titoloLibro;
private CapitoloInfo capitoloInfo;
private ArrayList<CapitoloInfo> capitoliList;

public void setNumLibro(String numLibro){
	this.numLibro = numLibro;
}

public void setTitoloLibro(String titoloLibro){
	this.titoloLibro = titoloLibro;
}

public void setCapitoloInfo(CapitoloInfo capitoloInfo){
	this.capitoloInfo = capitoloInfo;
}

public void setCapitoliList(ArrayList<CapitoloInfo> capitoliList){
	this.capitoliList = capitoliList;
}

public String getNumLibro(){
	return numLibro;
}

public CapitoloInfo getCapitoloInfo(){
	return capitoloInfo;
}

public String getTitoloLibro(){
	return titoloLibro;
}

public ArrayList<CapitoloInfo> getcapitoliList(){
	return capitoliList;
}


}
