package perunaenciclopediadantescadigitale.prindante.eu;

import perunaenciclopediadantescadigitale.prindante.eu.database.VirtuosoDB;
/**
 * 
 * @author Loredana
 * Insert Soggettario into virtuoso 
 */
public class InsertSoggettario {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		VirtuosoDB virtDB = new VirtuosoDB();
		virtDB.initBDVirtuoso();
		virtDB.insertSoggettario();
	}

}
