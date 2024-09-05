package eu.telecomsudparis.csc4102.minisocs;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import eu.telecomsudparis.csc4102.minisocs.Membership;


public class Reseausocial {

	private final String nomDuReseauSocial;
	
	private final Utilisateur createur;

	//private final Map<String, Membership> membership;
	private Map<String, Membership> memberships; // Utilisateurs à sa hashmap de membership aussi auquel il peut acceder  
	//private List<Membership> memberships;
	
	private Etat_res etat_res;
	
	public Map<String,Message> messages; // le string fait référence au contenu

	/**
	 * construit une instance du système.
	 * 
	 * @param nomDuSysteme le nom.
	 */
	public Reseausocial(final String nom, final Utilisateur createur) {
		this.createur = createur;
		this.nomDuReseauSocial = nom;
		this.memberships = new HashMap<>();
		//memberships.put(pseudo_rs, new Membership(createur, this, pseudo_rs, Role.MODERATEUR));
		//this.memberships = new ArrayList<>();
		this.etat_res = Etat_res.OUVERT;
		this.messages = new HashMap<>();
	//	assert invariant();
	}
	
	public void ajouterMembership(final Utilisateur utilisateur, final String pseudo_rs, Role role) {
		memberships.put(pseudo_rs, new Membership(utilisateur, this, pseudo_rs, role));
	}
	
	public void ajouterMessage(final String contenu, Utilisateur utilisateur, final String reseauSocial) {
		Message msg = new Message(contenu,utilisateur,reseauSocial);
		messages.put(contenu,msg);
	}
	
	public void setStatutEnAttente(final String contenu) {
		messages.get(contenu).setStatutEnAttente();
	}
	
	public Statut_mes getStatutMessage(final String contenu) {
		return messages.get(contenu).getStatutMessage();
	}
	
	public String getNomDuReseauSocial() {
		return this.nomDuReseauSocial;
	} 
	
	public boolean invariant() {
		return nomDuReseauSocial != null && !nomDuReseauSocial.isBlank() && etat_res != null ;
	}
	
	public List<String> listerMembershipDansReseauSocial() {
		return memberships.values().stream().map(Membership::toString).toList(); 
	}
	
	public String toString() {
		return "Reseausocial [nom=" + nomDuReseauSocial + ", créateur=" + createur + ", role=" + etat_res.toString() + "]";
	}
	
}