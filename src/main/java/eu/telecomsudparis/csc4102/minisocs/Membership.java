// CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;


import java.util.Objects;


public class Membership {
	
	private final String pseudo_rs;
	
	private Utilisateur utilisateur;
	
	private Reseausocial reseauSocial;
	
	private Role role;
    

	/**
	 * construit une instance du système.
	 * 
	 * @param nomDuSysteme le nom.
	 */
	public Membership(Utilisateur utilisateur, Reseausocial reseauSocial,final String pseudo_rs, Role role) {
		this.pseudo_rs = pseudo_rs;
		this.utilisateur = utilisateur;
		this.reseauSocial = reseauSocial; // new Reseausocial(reseauSocial,utilisateur.get)
		this.role = role;
		assert invariant();
	}
	
	public Reseausocial getReseausocial() {
		return this.reseauSocial;
	} 
	
	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	} 
	
	public Role getRole() {
		return this.role;
	} 
	public void setRoleModerateur() {
		this.role=Role.MODERATEUR;
	}
	public void setRoleSimpleMembre() {
		this.role=Role.SIMPLEMEMBRE;
	}
	/**
	 * l'invariant de la façade.
	 * 
	 * @return {@code true} si l'invariant est respecté.
	 */
	public boolean invariant() {
		return this.pseudo_rs != null && !(this.pseudo_rs.isBlank()) && this.role != null;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(pseudo_rs, reseauSocial, utilisateur);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Membership)) {
			return false;
		}
		Membership other = (Membership) obj;
		return Objects.equals(pseudo_rs, other.pseudo_rs) && Objects.equals(reseauSocial, other.reseauSocial)
				&& Objects.equals(utilisateur, other.utilisateur);
	}

	public String toString() {
		return "Utilisateur [pseudonyme=" + pseudo_rs + ", utilisateur=" + utilisateur.getPseudonyme() + ", reseauSocial=" + reseauSocial.getNomDuReseauSocial() + ", role=" + role + "]";
	}
	
	
}

