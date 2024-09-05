package eu.telecomsudparis.csc4102.minisocs;

import java.util.Objects;

public class Message {

	/**
	 * le contenu du message.
	 */
	private String contenu;

	/**
	 * l'auteur du message.
	 */
	private final Utilisateur auteur;

	/**
	 * Le réseau social dans lequel le message est posté.
	 */
	private final String reseausocial;

	/**
	 * Le statut du message lors de la modération.
	 */
	private Statut_mes statutMes;

	/**
	 * L'état de visibilité du message.
	 */
	private Etat_mes etatMes;

	/**
	 * construit un message.
	 * 
	 * @param contenu      Le contenu du message.
	 * @param utilisateur  L'auteur du message.
	 * @param reseausocial Le réseau social dans lequel le message est posté.
	 */
	public Message(final String contenu, final Utilisateur utilisateur, final String reseausocial) {
		if (contenu == null || contenu.isBlank()) {
			throw new IllegalArgumentException("Le contenu ne peut pas être null ou vide");
		}
		if (utilisateur.getEtatCompte() == EtatCompte.BLOQUE) {
			throw new IllegalStateException("Le compte de l'auteur ne peut pas être bloqué");
		}
		if (utilisateur.getEtatCompte() == EtatCompte.DESACTIVE) {
			throw new IllegalStateException("Le compte de l'auteur ne peut pas être desactivé");
		}
		this.setContenu(contenu);
		this.reseausocial = reseausocial;
		this.auteur = utilisateur;
		this.setStatutMes(Statut_mes.ENATTENTEDEMODERATION);
		this.setEtatMes(Etat_mes.NONVISIBLE);

		assert invariant();
	}

	/**
	 * Accepter le message.
	 * 
	 */
	public void accepterMessage() {
			this.setStatutAccepte();
			this.setEtatMesVisible();
		
	}
	/**
	 * Refuser le message.
	 * 
	 */
	public void refuserMessage() {
			this.setStatutNonAccepte();
			this.setEtatMesNonVisible();
		
	}


	/**
	 * met le statut du message en attente.
	 */
	public void setStatutEnAttente() {
		this.setStatutMes(Statut_mes.ENATTENTEDEMODERATION);
	}

	/**
	 * met le statut du message accepté.
	 */
	public void setStatutAccepte() {
		this.setStatutMes(Statut_mes.ACCEPTE);
	}

	/**
	 * met le statut du message non accepté.
	 */
	public void setStatutNonAccepte() {
		this.setStatutMes(Statut_mes.NONACCEPTE);
	}

	/**
	 * retourne le statut du message.
	 * 
	 * @return Statut_mes
	 */
	public Statut_mes getStatutMessage() {
		return getStatutMes();
	}

	/**
	 * met l'état du message à visible.
	 */
	public void setEtatMesVisible() {
		this.setEtatMes(Etat_mes.VISIBLE);
	}

	/**
	 * met l'état du message à non visible.
	 */
	public void setEtatMesNonVisible() {
		this.setEtatMes(Etat_mes.NONVISIBLE);
	}

	/**
	 * vérifie les invariants.
	 * 
	 * @return boolean
	 */
	public boolean invariant() {
		return getContenu() != null && !getContenu().isBlank() && getAuteur() != null && getStatutMes() != null
				&& getEtatMes() != null;
	}

	/**
	 * retourne l'auteur du message.
	 * 
	 * @return Utilisateur
	 */
	public Utilisateur getAuteur() {
		return auteur;
	}

	/**
	 * retourne l'état du message.
	 * 
	 * @return Etat_mes
	 */
	public Etat_mes getEtatMes() {
		return etatMes;
	}

	/**
	 * définit l'état du message.
	 * 
	 * @param etatMes
	 */
	public void setEtatMes(final Etat_mes etatMes) {
		this.etatMes = etatMes;
	}

	/**
	 * retourne le statut du message.
	 * 
	 * @return Statut_mes
	 */
	public Statut_mes getStatutMes() {
		return statutMes;
	}

	/**
	 * définit le statut du message.
	 * 
	 * @param statutMes
	 */
	public void setStatutMes(final Statut_mes statutMes) {
		this.statutMes = statutMes;
	}

	/**
	 * retourne un reseausocial.
	 * @return le reseausocial auquel appartient le message
	 */
	public String getReseausocial() {
		return reseausocial;
	}

	/**
	 * @return contenu
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * @param contenu
	 */
	public void setContenu(final String contenu) {
		this.contenu = contenu;
	}
	@Override
	public String toString() {
		return "Message [auteur=" + getAuteur() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAuteur().getPseudonyme(), getContenu(), getReseausocial());
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Message)) {
			return false;
		}
		Message other = (Message) obj;
		return Objects.equals(getAuteur(), other.getAuteur()) && Objects.equals(getContenu(), other.getContenu())
				&& Objects.equals(getReseausocial(), other.getReseausocial());
	}

}
