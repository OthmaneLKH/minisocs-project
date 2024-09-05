// CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;
/**
 * Ce type énuméré modélise le statut d'un utilisateur.
 */
public enum Statut {
	ADMINISTRATEUR("administrateur"),

	SIMPLE_UTILISATEUR("simple utilisateur");

	/**
	 * le nom de l'état à afficher.
	 */
	private String nom;

	/**
	 * construit un énumérateur.
	 * 
	 * @param nom le nom de l'état.
	 */
	Statut(final String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}

}
