package eu.telecomsudparis.csc4102.minisocs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.Collectors;
import org.apache.commons.validator.routines.EmailValidator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

	/**
	 * les réseaux sociaux.
	 */
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
		return nomDuSysteme != null && !nomDuSysteme.isBlank() && getUtilisateurs() != null;
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
		Utilisateur u = getUtilisateurs().get(pseudo);
		if (u != null) {
			throw new OperationImpossible(pseudo + "déjà un utilisateur");
		}
		getUtilisateurs().put(pseudo, new Utilisateur(pseudo, nom, prenom, courriel));
		assert invariant();
	}

	/**
	 * creer un réseau social dans minisocs.
	 * 
	 * @param nom
	 * @param pseudoRs
	 * @param createur
	 * @throws OperationImpossible
	 */
	public void creerReseauSocial(final String nom, final String pseudoRs, final String createur)
			throws OperationImpossible {
		if (pseudoRs == null || pseudoRs.isBlank()) {
			throw new OperationImpossible("pseudo ne peut pas être null ou vide");
		}
		if (nom == null || nom.isBlank()) {
			throw new OperationImpossible("nom ne peut pas être null ou vide");
		}
		if (createur == null || createur.isBlank()) {
			throw new OperationImpossible("nom ne peut pas être null ou vide");
		}
		Utilisateur u = getUtilisateurs().get(createur);
		if (u == null) {
			throw new OperationImpossible(createur + "n'est pas un utilisateur"); // à ajouter dans le diag de seq
		}
		if (u.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
			throw new OperationImpossible("le compte n'est pas actif");
		}
		Reseausocial rs = getReseauxsociaux().get(nom);
		if (rs != null) {
			throw new OperationImpossible(nom + "déjà un reseau social");
		}
		SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
		getReseauxsociaux().put(nom, new Reseausocial(nom, getUtilisateurs().get(createur), publisher)); 
		getReseauxsociaux().get(nom).ajouterMembership(getUtilisateurs().get(createur), pseudoRs, Role.MODERATEUR);
		getUtilisateurs().get(createur).ajouterMembership(getReseauxsociaux().get(nom), pseudoRs, Role.MODERATEUR);

		assert invariant();
	}

	/**
	 * ajouter un utilisateur à un réseau social via son pseudo.
	 * 
	 * @param utilisateurquiAjoute
	 * @param utilisateuraAjouter
	 * @param pseudoRs
	 * @param reseausocial
	 * @throws OperationImpossible
	 * @throws InterruptedException 
	 */
	public void ajouterMembreaReseausocial(final String utilisateurquiAjoute, final String utilisateuraAjouter,
			final String pseudoRs, final String reseausocial) throws OperationImpossible, InterruptedException {
		if (pseudoRs == null || pseudoRs.isBlank()) {
			throw new OperationImpossible("pseudo ne peut pas être null ou vide");
		}
		if (utilisateuraAjouter == null || utilisateuraAjouter.isBlank()) {
			throw new OperationImpossible("utilisateur à ajouter ne peut pas être null ou vide");
		}
		if (reseausocial == null || reseausocial.isBlank()) {
			throw new OperationImpossible("l'attribut passer en paramétre reseausocial ne peut pas être null ou vide");
		}
		if (utilisateurquiAjoute == null || utilisateurquiAjoute.isBlank()) {
			throw new OperationImpossible(
					"l'attribut passer en paramétre UtilisateurquiAjoute ne peut pas être null ou vide");
		}
		Utilisateur u1 = getUtilisateurs().get(utilisateuraAjouter);
		Utilisateur u2 = getUtilisateurs().get(utilisateurquiAjoute);
		if (u1.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
			throw new OperationImpossible("le compte de l'utilisateur à ajouter n'est pas actif");
		}
		if (!getUtilisateurs().get(utilisateurquiAjoute).getRoleDansReseauSocial(reseausocial).equals(Role.MODERATEUR)) {
			throw new OperationImpossible("l'uilisateur n'est pas modérateur");
		}
		Membership membre = getUtilisateurs().get(utilisateuraAjouter).getMembership(pseudoRs);
		if (membre != null) {
			throw new OperationImpossible("la membership existe deja");
		} // pour verifier si cette membership n'existe pas deja ?
		if (u2.getEtatCompte().equals(EtatCompte.DESACTIVE)) {
			throw new OperationImpossible("le compte de l'utilisateur qui ajoute n'est pas actif");
		}
		if (u2.getEtatCompte().equals(EtatCompte.BLOQUE)) {
			throw new OperationImpossible("le compte de l'utilisateur qui ajoute est bloqué");
		}

		Reseausocial rs = getReseauxsociaux().get(reseausocial);
		rs.ajouterMembership(getUtilisateurs().get(utilisateuraAjouter), pseudoRs,
				Role.SIMPLEMEMBRE);
		u1.ajouterMembership(getReseauxsociaux().get(reseausocial), pseudoRs,
				Role.SIMPLEMEMBRE);
		if (!rs.getProducteur().getSubscribers().contains(u2.getMonConsommateur())) {
            rs.getProducteur().subscribe(u2.getMonConsommateur());
        }
		final int delai = 100;
		Thread.sleep(delai);
		assert invariant();
	}


	/**
	 * poster un message dans un réseau social.
	 * 
	 * @param utilisateurquiPoste
	 * @param contenu
	 * @param reseausocial
	 * @throws OperationImpossible
	 * @throws InterruptedException 
	 */
	public void posterMessage(final String utilisateurquiPoste, final String contenu, final String reseausocial)
			throws OperationImpossible, InterruptedException {
		if (contenu == null || contenu.isBlank()) {
			throw new OperationImpossible("contenu ne peut pas être null ou vide");
		}
		if (utilisateurquiPoste == null) {
			throw new OperationImpossible("l'utilisateur qui poste ne peut pas être null ou vide");
		}
		if (reseausocial == null || reseausocial.isBlank()) {
			throw new OperationImpossible("le reseau social ne peut pas être null ou vide");
		}
		Utilisateur u = getUtilisateurs().get(utilisateurquiPoste);
		if (u == null) {
			throw new OperationImpossible(utilisateurquiPoste + "n'est pas un utilisateur"); 
		}
		Reseausocial rs = getReseauxsociaux().get(reseausocial);
		if (rs == null) {
			throw new OperationImpossible(reseausocial + "pas de reseau social existant à ce nom");
		}
		rs.ajouterMessage(contenu, u, reseausocial);
		Message message = rs.messages.get(contenu);
		if (!u.getEtatCompte().equals(EtatCompte.ACTIF)) {
			throw new OperationImpossible("le compte n'est pas actif");
		}
	    if (u.getRoleDansReseauSocial(rs)
				.equals(Role.SIMPLEMEMBRE)) {
			// on met le message en statut attente de modération et tant que le statut n'est
			// pas différent de EnAttente alors on attend
			message.setStatutEnAttente();
			message.setEtatMesNonVisible();

		}
		
		 LocalDateTime maintenant = LocalDateTime.now();
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	     String horodatage = maintenant.format(formatter);
	     String publicationInfo = "Date: " + horodatage + ", Réseau social: " + reseausocial + ", Utilisateur: " + utilisateurquiPoste + ", Contenu: " + contenu;
	     rs.getProducteur().submit(publicationInfo);
	     final int delai = 100;
	     Thread.sleep(delai);
	     rs.getProducteur().close();

		assert invariant();
	}
	/**
	 * Modere le message.
	 * 
	 * @param pseudomoderateur
	 * @param reseausocial
	 * @param contenu
	 * @param bool
	 * @throws OperationImpossible 
	 */
	public void modererMessage(final String pseudomoderateur, final String reseausocial, final String contenu, final boolean bool) throws OperationImpossible {
		if (contenu == null || contenu.isBlank()) {
			throw new OperationImpossible("Le contenu du message ne peut pas être null ou vide"); 
		}		
		Reseausocial rs = reseauxsociaux.get(reseausocial);
		Message message = rs.messages.get(contenu);
		if (message.getEtatMes().equals(Etat_mes.VISIBLE)) {
			throw new OperationImpossible("Le message est déja visible et ne peut être remodérer");
		}
		if (message.getStatutMes().equals(Statut_mes.ACCEPTE)) {
			throw new OperationImpossible("Le message a déjà été accepté");
		}
		if (message.getStatutMes().equals(Statut_mes.NONACCEPTE)) {
			throw new OperationImpossible("Le message a déjà été refusé");
		}
		Utilisateur moderateur = getUtilisateurs().get(pseudomoderateur);
		if (moderateur.getRoleDansReseauSocial(reseausocial) == Role.MODERATEUR) {
			if (bool) {
				message.accepterMessage();
			} else {
				message.refuserMessage();
			}
		} else {
			throw new OperationImpossible("Ce membre n'est pas un modérateur");
		}
		assert invariant();
	}

	/**
	 * liste les utilisateurs.
	 * 
	 * @return la liste des pseudonymes des utilisateurs.
	 */
	public List<String> listerUtilisateurs() {
		return getUtilisateurs().values().stream().map(Utilisateur::toString).toList();
	}

	/**
	 * liste les réseaux sociaux.
	 * 
	 * @return la liste des noms des réseaux sociaux.
	 */
	 public List<String> listerReseauSocial() {
	        Map<String, Reseausocial> reseauxsociaux = getReseauxsociaux(); 
	        
	        return reseauxsociaux.values().stream().map(Reseausocial::toString).collect(Collectors.toList());
	    }
	
	/**
	 * liste les memberships.
	 * 
	 * @param reseausocial
	 * @return les pseudos des membres.
	 */
	public List<String> listerMembershipDansReseauSocial(final String reseausocial) {
		return getReseauxsociaux().get(reseausocial).listerMembershipDansReseauSocial();
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
		Utilisateur u = getUtilisateurs().get(pseudo);
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
		return "MiniSocs [nomDuSysteme=" + nomDuSysteme + ", utilisateurs=" + getUtilisateurs() + "]";
	}

	/**
	 * @return reseauxsociaux
	 */
	public Map<String, Reseausocial> getReseauxsociaux() {
		return reseauxsociaux;
	}

	/**
	 * @return utilisateurs
	 */
	public Map<String, Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}
	
	
	
	
}
