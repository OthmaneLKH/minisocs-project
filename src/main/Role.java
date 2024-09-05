package eu.telecomsudparis.csc4102.minisocs;


public enum Role {

	SIMPLEMEMBRE("simple membre"),

	MODERATEUR("modérateur");

	private String nom;

	/**
	 * construit un énumérateur.
	 * 
	 * @param nom le nom de l'état.
	 */
	Role(final String nom) {
		this.nom = nom;
	}

	@Override
	public String toString() {
		return nom;
	}
}

