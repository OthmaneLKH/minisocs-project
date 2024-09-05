package eu.telecomsudparis.csc4102.minisocs;

/**
 * Ce type énuméré modélise l'état d'un réseau social.
 */
public enum Etat_res {

	FERME("fermé"),

	OUVERT("ouvert");

	/**
	 * le nom de l'état à afficher.
	 */
	private String nom;

	/**
	 * construit un énumérateur.
	 * 
	 * @param nom le nom de l'état.
	 */
	Etat_res(final String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}
}
