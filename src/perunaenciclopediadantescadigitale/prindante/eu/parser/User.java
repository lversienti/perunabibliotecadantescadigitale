package perunaenciclopediadantescadigitale.prindante.eu.parser;

/**
 * set data to represent the User e.g name, URI
 */
public class User {
	
	 private String name;
	 private String URI;
		
		 public void setName(String name){
				this.name = name;
			}
			
			public void setURI(String URI){
				this.URI = URI;
			}
			
			
			public String getName(){
				return name;
			}
			
			public String getURI(){
				return URI;
			}
}
