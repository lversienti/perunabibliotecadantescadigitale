package perunaenciclopediadantescadigitale.prindante.eu;

import perunaenciclopediadantescadigitale.prindante.eu.database.VirtuosoDB;

/**
 * 
 * Insert all dante's opere into virtuoso 
 */
public class InsertOpereContenuto {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VirtuosoDB virtDB = new VirtuosoDB();
		virtDB.initBDVirtuoso();
		virtDB.insertOpereContenutoDante();
	}

}
