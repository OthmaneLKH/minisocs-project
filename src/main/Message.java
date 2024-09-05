package eu.telecomsudparis.csc4102.minisocs;


import java.util.HashMap;
import java.util.Map;
import eu.telecomsudparis.csc4102.minisocs.Membership;
import eu.telecomsudparis.csc4102.minisocs.EtatCompte;


public class Message {

	public String contenu;

	public final Utilisateur auteur;

	private final String reseausocial;
	
	public Statut_mes statut_mes;
	
	public Etat_mes etat_mes;

	/**
	 * construit une instance du système.
	 * 
	 * @param nomDuSysteme le nom.
	 */
	public Message(final String contenu,final Utilisateur utilisateur, final String reseausocial) {
		if (contenu == null || contenu.isBlank()) {
			throw new IllegalArgumentException("Le contenu ne peut pas être null ou vide");
		}
		if (utilisateur.getEtatCompte()==EtatCompte.BLOQUE) {
			throw new IllegalStateException("Le compte de l'auteur ne peut pas être bloqué");
		}
		if (utilisateur.getEtatCompte()==EtatCompte.DESACTIVE) {
			throw new IllegalStateException("Le compte de l'auteur ne peut pas être desactivé");
		}
		this.contenu = contenu;
		this.reseausocial = reseausocial;
		this.auteur = utilisateur;
		this.statut_mes=Statut_mes.ENATTENTEDEMODERATION;
		this.etat_mes = Etat_mes.NONVISIBLE;
		
		//assert invariant();
	}
	
	public void setStatutEnAttente() {
		this.statut_mes = Statut_mes.ENATTENTEDEMODERATION;
	}
	
	public Statut_mes getStatutMessage() {
		return statut_mes;
	}
	

	public boolean invariant() {
		return contenu != null && !contenu.isBlank() && auteur != null && statut_mes != null && etat_mes != null ;
	}
	
}