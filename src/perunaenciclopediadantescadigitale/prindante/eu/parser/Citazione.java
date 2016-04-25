package perunaenciclopediadantescadigitale.prindante.eu.parser;


/**
 * set data to represent the 'Citazione' e.g frammento, tipoCitazione
 */
public class Citazione {
	
	private String frammento;
	private String tipoCitazione;
	private InfoOpera infoOpera;
	private InfoOperaCitata infoOperaCitata;
	
	public void setFrammento(String frammento){
		this.frammento = frammento;
	}
	
	public void setTipoCitazione(String tipoCitazione){
		this.tipoCitazione = tipoCitazione;
	}
	
	public void setInfoOpera(InfoOpera infoOpera){
		this.infoOpera = infoOpera;
	}
	
	public void setInfoOperaCitata(InfoOperaCitata infoOperaCitata){
		this.infoOperaCitata = infoOperaCitata;
	}
	
	public String getFrammento( ){
		return frammento;
	}

	public String getTipoCitazione(){
		return tipoCitazione;
	}
	
	public InfoOpera getInfoOpera(){
		return infoOpera;
	}
	
	public InfoOperaCitata getInfoOperaCitata(){
		return infoOperaCitata;
	}
}
