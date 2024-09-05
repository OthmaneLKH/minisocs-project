// CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;


public enum Statut_mes {

	ENATTENTEDEMODERATION("en attente de modération"),

	NONACCEPTE("non accepté"),
	
	ACCEPTE("accepté");
	

	private String nom;

	/**
	 * construit un énumérateur.
	 * 
	 * @param nom le nom de l'état.
	 */
	Statut_mes(final String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}
}
