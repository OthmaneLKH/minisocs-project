package eu.telecomsudparis.csc4102.minisocs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.routines.EmailValidator;

import eu.telecomsudparis.csc4102.util.OperationImpossible;

/**
 * Cette classe est la façade du logiciel.
 * 
 * @author Denis Conan
 */
public class MiniSocs {
	/**
	 * le nom du système.
	 */
	private final String nomDuSysteme;
	/**
	 * les utilisateurs.
	 */
	private final Map<String, Utilisateur> utilisateurs;
	
	private final Map<String, Reseausocial> reseauxsociaux;

	/**
	 * construit une instance du système.
	 * 
	 * @param nomDuSysteme le nom.
	 */
	public MiniSocs(final String nomDuSysteme) {
		this.nomDuSysteme = nomDuSysteme;
		this.utilisateurs = new HashMap<>();
		this.reseauxsociaux = new HashMap<>();
	}

	/**
	 * l'invariant de la façade.
	 * 
	 * @return {@code true} si l'invariant est respecté.
	 */
	public boolean invariant() {
		return nomDuSysteme != null && !nomDuSysteme.isBlank() && utilisateurs != null;
	}

	/**
	 * ajoute un utilisateur.
	 * 
	 * @param pseudo   le pseudo de l'utilisateur.
	 * @param nom      le nom de l'utilisateur.
	 * @param prenom   le prénom de l'utilisateur.
	 * @param courriel le courriel de l'utilisateur.
	 * @throws OperationImpossible en cas de problème sur les pré-conditions.
	 */
	public void ajouterUtilisateur(final String pseudo, final String nom, final String prenom, final String courriel)
			throws OperationImpossible {
		if (pseudo == null || pseudo.isBlank()) {
			throw new OperationImpossible("pseudo ne peut pas être null ou vide");
		}
		if (nom == null || nom.isBlank()) {
			throw new OperationImpossible("nom ne peut pas être null ou vide");
		}
		if (prenom == null || prenom.isBlank()) {
			throw new OperationImpossible("prenom ne peut pas être null ou vide");
		}
		if (courriel == null || courriel.isBlank()) {
			throw new OperationImpossible("courriel ne peut pas être null ou vide");
		}
		if (!EmailValidator.getInstance().isValid(courriel)) {
			throw new OperationImpossible("courriel ne respecte pas le standard RFC822");
		}
		Utilisateur u = utilisateurs.get(pseudo);
		if (u != null) {
			throw new OperationImpossible(pseudo + "déjà un utilisateur");
		}
		utilisateurs.put(pseudo, new Utilisateur(pseudo, nom, prenom, courriel));
		assert invariant();
	}
	
	
	public void creerreseausocial(final String nom, final String pseudo_rs, final String createur )	
			throws OperationImpossible {
		if (pseudo_rs == null || pseudo_rs.isBlank()) {
			throw new OperationImpossible("pseudo ne peut pas être null ou vide");
		}
		if (nom == null || nom.isBlank()) {
			throw new OperationImpossible("nom ne peut pas être null ou vide");
		}
		if (createur == null || createur.isBlank()) {
			throw new OperationImpossible("nom ne peut pas être null ou vide");
		}
		Utilisateur u = utilisateurs.get(createur);
		if (u == null) {
			throw new OperationImpossible(createur + "n'est pas un utilisateur"); // à ajouter dans le diag de seq
		} 
	    if (u.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
	    	throw new OperationImpossible("le compte n'est pas actif");
		} 
	    Reseausocial rs = reseauxsociaux.get(nom);
		if (rs != null) {
			throw new OperationImpossible(nom + "déjà un reseau social"); 
		} 
		
		reseauxsociaux.put(nom, new Reseausocial(nom, utilisateurs.get(createur)));
		reseauxsociaux.get(nom).ajouterMembership(utilisateurs.get(createur), pseudo_rs, Role.MODERATEUR);
		utilisateurs.get(createur).ajouterMembership(reseauxsociaux.get(nom), pseudo_rs, Role.MODERATEUR);
		
		assert invariant(); 
	}
	

	
	public void ajouterMembreaReseausocial(final String utilisateurquiAjoute, final String utilisateuraAjouter, final String pseudo_rs, final String reseausocial)	
			throws OperationImpossible {
		if (pseudo_rs == null || pseudo_rs.isBlank()) {
			throw new OperationImpossible("pseudo ne peut pas être null ou vide");
		}
		if (utilisateuraAjouter == null || utilisateuraAjouter.isBlank()) {
			throw new OperationImpossible("utilisateur à ajouter ne peut pas être null ou vide");
		}
		if (reseausocial == null || reseausocial.isBlank()) {
			throw new OperationImpossible("l'attribut passer en paramétre reseausocial ne peut pas être null ou vide");
		}
		if (utilisateurquiAjoute == null || utilisateurquiAjoute.isBlank()) {
			throw new OperationImpossible("l'attribut passer en paramétre UtilisateurquiAjoute ne peut pas être null ou vide");
		}
	    Reseausocial rs = reseauxsociaux.get(reseausocial);
		if (rs == null) {
			throw new OperationImpossible(reseausocial + "pas de reseau social existant à ce nom"); 
		}
		Utilisateur u = utilisateurs.get(utilisateuraAjouter);
	    if (u.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
	    	throw new OperationImpossible("le compte n'est pas actif");
		} 
		if (!utilisateurs.get(utilisateurquiAjoute).getRoleDansReseauSocial(reseausocial).equals(Role.MODERATEUR)) {
			throw new OperationImpossible("l'uilisateur n'est pas modérateur");
		}
		Membership membre = utilisateurs.get(utilisateuraAjouter).getMembership(pseudo_rs);
		if (membre != null) {
			throw new OperationImpossible("la membership existe deja");
		} // pour verifier si cette membership n'existe pas deja ?
		// + verif si compte pas bloqué
		
		//chaque membre corresponds à un utilisateur
		reseauxsociaux.get(reseausocial).ajouterMembership(utilisateurs.get(utilisateuraAjouter), pseudo_rs, Role.SIMPLEMEMBRE);
		//memberships.add(new Membership(utilisateuraAjouter, rs.getNomDuReseauSocial(), pseudo_rs, Role.SIMPLEMEMBRE));
		utilisateurs.get(utilisateurquiAjoute).ajouterMembership(reseauxsociaux.get(reseausocial), pseudo_rs, Role.MODERATEUR);
		assert invariant();
	}
	
	/*public void ajouterMembershipModerateurpourTest(final String utilisateur, final String reseausocial, final String pseudo_rs)	{
		utilisateurs.get(utilisateur).ajouterMembership(utilisateurs.get(utilisateur), reseauxsociaux.get(reseausocial), pseudo_rs, Role.MODERATEUR);
	} //méthode pour ajouter un membre à la collection de utilisateur pour le test 
*/
	
	
	public void posterMessage(final Utilisateur utilisateurquiPoste, final String contenu, final String reseausocial)	
			throws OperationImpossible {
		if (contenu == null || contenu.isBlank()) {
			throw new OperationImpossible("contenu ne peut pas être null ou vide");
		}
		if (utilisateurquiPoste == null) {
			throw new OperationImpossible("l'utilisateur qui poste ne peut pas être null ou vide");
		}
		if (reseausocial == null || reseausocial.isBlank()) {
			throw new OperationImpossible("le reseau social ne peut pas être null ou vide");
		}
		Utilisateur u = utilisateurs.get(utilisateurquiPoste);
		if (u == null) {
			throw new OperationImpossible(utilisateurquiPoste + "n'est pas un utilisateur"); // à ajouter dans le diag de seq
		}
		Reseausocial rs = reseauxsociaux.get(reseausocial);
		if (rs == null) {
			throw new OperationImpossible(reseausocial + "pas de reseau social existant à ce nom"); 
		}
		if (!utilisateurs.get(utilisateurquiPoste).getEtatCompte().equals(EtatCompte.ACTIF)) {
			throw new OperationImpossible("le compte n'est pas actif");
		}
		if (utilisateurs.get(utilisateurquiPoste).getRoleDansReseauSocial(reseausocial).equals(Role.MODERATEUR)) {
			reseauxsociaux.get(reseausocial).ajouterMessage(contenu,utilisateurquiPoste,reseausocial);
		}
		else if(utilisateurs.get(utilisateurquiPoste).getRoleDansReseauSocial(rs).equals(Role.SIMPLEMEMBRE)) {
			//on met le message en statut attente de modération et tant que le statut n'est pas différent de EnAttente alors on attent
			reseauxsociaux.get(reseausocial).setStatutEnAttente(contenu);
			while (reseauxsociaux.get(reseausocial).getStatutMessage(contenu).equals(Statut_mes.ENATTENTEDEMODERATION)) {}
			if (reseauxsociaux.get(reseausocial).getStatutMessage(contenu).equals(Statut_mes.ACCEPTE)) {
				reseauxsociaux.get(reseausocial).ajouterMessage(contenu,utilisateurquiPoste,reseausocial);
			} else if (reseauxsociaux.get(reseausocial).getStatutMessage(contenu).equals(Statut_mes.NONACCEPTE)) {
				return;
			}

		}
		// + verif si compte pas bloqué
		
		
		assert invariant();
	}
	
	
	/**
	 * liste les utilisateurs.
	 * 
	 * @return la liste des pseudonymes des utilisateurs.
	 */
	public List<String> listerUtilisateurs() {
		return utilisateurs.values().stream().map(Utilisateur::toString).toList();
	}
	
	public List<String> listerReseauSocial() {
		return reseauxsociaux.values().stream().map(Reseausocial::toString).toList();
	}
	
	public List<String> listerMembershipDansReseauSocial(final String reseausocial) {
		return reseauxsociaux.get(reseausocial).listerMembershipDansReseauSocial();
	}

	/**
	 * désactiver son compte utilisateur.
	 * 
	 * @param pseudo le pseudo de l'utilisateur.
	 * @throws OperationImpossible en cas de problèmes sur les pré-conditions.
	 */
	public void desactiverCompteUtilisateur(final String pseudo) throws OperationImpossible {
		if (pseudo == null || pseudo.isBlank()) {
			throw new OperationImpossible("pseudo ne peut pas être null ou vide");
		}
		Utilisateur u = utilisateurs.get(pseudo);
		if (u == null) {
			throw new OperationImpossible("utilisateur inexistant avec ce pseudo (" + pseudo + ")");
		}
		if (u.getEtatCompte().equals(EtatCompte.BLOQUE)) {
			throw new OperationImpossible("le compte est bloqué");
		}
		u.desactiverCompte();
		assert invariant();
	}

	/**
	 * obtient le nom du projet.
	 * 
	 * @return le nom du projet.
	 */
	public String getNomDeProjet() {
		return nomDuSysteme;
	}

	@Override
	public String toString() {
		return "MiniSocs [nomDuSysteme=" + nomDuSysteme + ", utilisateurs=" + utilisateurs + "]";
	}
}
