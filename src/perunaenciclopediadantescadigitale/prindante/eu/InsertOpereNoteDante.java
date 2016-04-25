package perunaenciclopediadantescadigitale.prindante.eu;

import perunaenciclopediadantescadigitale.prindante.eu.database.VirtuosoDB;

/**
 * 
 * @author Loredana
 * Insert all dante's opere into virtuoso 
 */
public class InsertOpereNoteDante {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VirtuosoDB virtDB = new VirtuosoDB();
		virtDB.initBDVirtuoso();
		virtDB.insertOpereNoteDante();
	}

}
