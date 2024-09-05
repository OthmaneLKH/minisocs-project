// CHECKSTYLE:OFF 
package eu.telecomsudparis.csc4102.minisocs;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Cette classe réalise le concept d'utilisateur du système, à ne pas confondre
 * avec le concept de participant, sous-entendu à un réséeau social.
 * 
 * @author Denis Conan
 */
public class Utilisateur {
	/**
	 * le pseudonyme de l'utilisateur.
	 */
	private final String pseudonyme;
	/**
	 * le nom de l'utilisateur.
	 */
	private String nom;
	/**
	 * le prénom de l'utilisateur.
	 */
	private String prenom;
	/**
	 * l'adresse courriel de l'utilisateur.
	 */
	private String courriel;
	/**
	 * les memberships
	 */
	private final Map<String, Membership> memberships;
	
	/**
	 * état du compte de l'utilisateur.
	 */
	private EtatCompte etatCompte;
	/**
	 * statut du compte de l'utilisateur.
	 */
	private Statut statut;
	/**
	 * le prénom de l'utilisateur.
	 */
	private MonConsommateur monConsommateur ;

	/**
	 * construit un utilisateur.
	 * 
	 * @param pseudonyme le pseudonyme.
	 * @param nom        le nom.
	 * @param prenom     le prénom.
	 * @param courriel   l'adresse courriel de l'utilisateur.
	 */
	public Utilisateur(final String pseudonyme, final String nom, final String prenom, final String courriel) {
		if (pseudonyme == null || pseudonyme.isBlank()) {
			throw new IllegalArgumentException("pseudonyme ne peut pas être null ou vide");
		}
		if (nom == null || nom.isBlank()) {
			throw new IllegalArgumentException("nom ne peut pas être null ou vide");
		}
		if (prenom == null || prenom.isBlank()) {
			throw new IllegalArgumentException("prenom ne peut pas être null ou vide");
		}
		if (!EmailValidator.getInstance().isValid(courriel)) {
			throw new IllegalArgumentException("courriel ne respecte pas le standard RFC822");
		}
		this.pseudonyme = pseudonyme;
		this.nom = nom;
		this.prenom = prenom;
		this.courriel = courriel;
		this.etatCompte = EtatCompte.ACTIF;
		//this.memberships = new HashMap<>();
		this.memberships = new HashMap<>();
		this.monConsommateur = new MonConsommateur ( " 1 " ) ;
		assert invariant();
	}

	/**
	 * vérifie l'invariant de la classe.
	 * 
	 * @return {@code true} si l'invariant est respecté.
	 */
	public boolean invariant() {
		return pseudonyme != null && !pseudonyme.isBlank() && nom != null && !nom.isBlank() && prenom != null
				&& !prenom.isBlank() && EmailValidator.getInstance().isValid(courriel) && etatCompte != null;
	}

	/**
	 * obtient le pseudonyme.
	 * 
	 * @return le pseudonyme.
	 */
	public String getPseudonyme() {
		return pseudonyme;
	}
	
	public Membership getMembership(final String pseudo) {
		return memberships.get(pseudo);
	}

	/**
	 * l'état du compte.
	 * 
	 * @return l'énumérateur.
	 */
	public EtatCompte getEtatCompte() {
		return etatCompte;
	}
	

	/**
	 * rend inactif le compte de l'utilisateur. L'opération est
	 * idempotente. L'opération est refusée si le compte n'est pas actif.
	 */
	public void desactiverCompte() {
		if (etatCompte.equals(EtatCompte.DESACTIVE)) {
			return;
		}
		if (!etatCompte.equals(EtatCompte.ACTIF)) {
			throw new IllegalStateException("le compte n'est pas actif");
		}
		this.etatCompte = EtatCompte.DESACTIVE;
		assert invariant();
	}
	
	public void reactiverCompte() {
		if (etatCompte.equals(EtatCompte.ACTIF)) {
			return;
		}
		if (!etatCompte.equals(EtatCompte.DESACTIVE)) {
			throw new IllegalStateException("le compte est  actif");
		}
		this.etatCompte = EtatCompte.ACTIF;
		assert invariant();
	}
	
	public boolean reseauSocialExiste(String nom) {
	    for (Map.Entry<String, Membership> entry : memberships.entrySet()) {
	        Membership membership = entry.getValue();
	        if (membership.getReseausocial().getNomDuReseauSocial().equals(nom)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public boolean reseauSocialExiste(Reseausocial nom) {
	    for (Map.Entry<String, Membership> entry : memberships.entrySet()) {
	        Membership membership = entry.getValue();
	        if (membership.getReseausocial().equals(nom)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public Role getRoleDansReseauSocial(final String nomReseauSocial) {
	    for (Map.Entry<String, Membership> entry : memberships.entrySet()) {
	        Membership membership = entry.getValue();
	        if (membership.getReseausocial().getNomDuReseauSocial().equals(nomReseauSocial)) {
	            return membership.getRole();
	        }
	    }
	    return null;
	}	

	
	public Role getRoleDansReseauSocial(final Reseausocial nomReseauSocial) {
	    for (Map.Entry<String, Membership> entry : memberships.entrySet()) {
	        Membership membership = entry.getValue();
	        if (membership.getReseausocial().equals(nomReseauSocial)) {
	            return membership.getRole();
	        }
	    }
	    return null;
	}	
	
	public void ajouterMembership(final Reseausocial reseauSocial, final String pseudo_rs, Role role) {
		memberships.put(pseudo_rs, new Membership(this, reseauSocial, pseudo_rs, role));
	}


	

	/**
	 * bloque le comte de l'utilisateur. L'opération est idempotente.
	 */
	public void bloquerCompte() {
		this.etatCompte = EtatCompte.BLOQUE;
		assert invariant();
	}
	public MonConsommateur getMonConsommateur() {
		return monConsommateur;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	@Override
	public int hashCode() {
		return Objects.hash(pseudonyme);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Utilisateur)) {
			return false;
		}
		Utilisateur other = (Utilisateur) obj;
		return Objects.equals(pseudonyme, other.pseudonyme);
	}

	@Override
	public String toString() {
		return "Utilisateur [pseudonyme=" + pseudonyme + ", nom=" + nom + ", prenom=" + prenom + ", courriel="
				+ courriel + ", etatCompte=" + etatCompte + "]";
	}

}
