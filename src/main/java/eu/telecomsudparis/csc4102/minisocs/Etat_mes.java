// CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;

/**
 * Ce type énuméré modélise l'état d'un message.
 */
public enum Etat_mes {

	VISIBLE("visible"),

	NONVISIBLE("non visible");

	/**
	 * le nom de l'état à afficher.
	 */
	private String nom;

	/**
	 * construit un énumérateur.
	 * 
	 * @param nom le nom de l'état.
	 */
	Etat_mes(final String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}
}
