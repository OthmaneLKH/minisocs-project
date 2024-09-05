package eu.telecomsudparis.csc4102.minisocs;

import java.util.HashMap;
import java.util.Map;

import eu.telecomsudparis.csc4102.util.OperationImpossible;

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
	public void SetRoleModerateur() {
		this.role=Role.MODERATEUR;
	}
	public void SetRoleSimpleMembre() {
		this.role=Role.SIMPLEMEMBRE;
	}

	
	public String toString() {
		return "Utilisateur [pseudonyme=" + pseudo_rs + ", utilisateur=" + utilisateur.getPseudonyme() + ", reseauSocial=" + reseauSocial.getNomDuReseauSocial() + ", role=" + role + "]";
	}
	
	/**
	 * l'invariant de la façade.
	 * 
	 * @return {@code true} si l'invariant est respecté.
	 */
	public boolean invariant() {
		return this.pseudo_rs != null && !(this.pseudo_rs.isBlank()) && this.role != null;
	}
}

