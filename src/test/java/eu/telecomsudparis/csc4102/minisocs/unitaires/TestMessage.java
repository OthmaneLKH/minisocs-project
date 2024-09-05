// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs.unitaires;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.minisocs.*;



class TestMessage {
	Utilisateur auteur= new Utilisateur("pseudonyme", "nom", "prenom", "courriel@courriel.fr");

	@BeforeEach
	void setUp() {
	}

	@AfterEach
	void tearDown() {
	}
	@Test
	void constructeurMessageTest1Jeu1() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Message(null, auteur, "reseausocial"));
	}
	@Test
	void constructeurMessageTest1Jeu2() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Message("", auteur, "reseausocial"));
	}
	@Test
	void constructeurMessageTest2Jeu1() {
		Message message = new Message("contenu", auteur, "reseausocial");
		auteur.bloquerCompte();
		Assertions.assertEquals(EtatCompte.BLOQUE, message.getAuteur().getEtatCompte());
		Assertions.assertThrows(IllegalStateException.class, () -> new Message("contenu", auteur, "reseausocial")); 
		
	}
	@Test
	void constructeurMessageTest2Jeu2() {
		Message message = new Message("contenu", auteur, "reseausocial");
		auteur.desactiverCompte();
		Assertions.assertEquals(EtatCompte.DESACTIVE, message.getAuteur().getEtatCompte());
		Assertions.assertThrows(IllegalStateException.class, () -> new Message("contenu", auteur, "reseausocial")); 
		
	}
	@Test 
	void constructeurMessageTest3() {
		Message message = new Message("contenu", auteur, "reseausocial");
		Assertions.assertNotNull(message);
		Assertions.assertEquals(message.getStatutMes(), Statut_mes.ENATTENTEDEMODERATION);
		
	}
	@Test
	void constructeurMessageTest4() {
		Message message = new Message("contenu", auteur, "reseausocial");
		Assertions.assertNotNull(message);
		Assertions.assertEquals(message.getEtatMes(), Etat_mes.NONVISIBLE);
	}
	@Test
	void modererMessageTest1() {
		Message message = new Message("contenu", auteur, "reseausocial");
		Role role = auteur.getRoleDansReseauSocial("reseausocial");
		if(role==Role.MODERATEUR) {
		Assertions.assertEquals(message.getStatutMes(), Statut_mes.ACCEPTE);
		Assertions.assertEquals(message.getEtatMes(), Etat_mes.VISIBLE);}}
	
	@Test
	void modererMessageTest2() {
		Message message = new Message("contenu", auteur, "reseausocial");
		Role role = auteur.getRoleDansReseauSocial("reseausocial");
		if(role==Role.SIMPLEMEMBRE) {
		Assertions.assertEquals(message.getStatutMes(), Statut_mes.ENATTENTEDEMODERATION);
	}}
	@Test
	void modererMessageTest3() {
		Message message = new Message("contenu", auteur, "reseausocial");
		Role role = auteur.getRoleDansReseauSocial("reseausocial");
		if(role==Role.SIMPLEMEMBRE) {
	    message.accepterMessage();
		Assertions.assertEquals(message.getStatutMes(), Statut_mes.ACCEPTE);
		Assertions.assertEquals(message.getEtatMes(), Etat_mes.VISIBLE);
	}}
	@Test
	void modererMessageTest4() {
		Message message = new Message("contenu", auteur, "reseausocial");
		Role role = auteur.getRoleDansReseauSocial("reseausocial");
		if(role==Role.SIMPLEMEMBRE) {
	    message.refuserMessage();
		Assertions.assertEquals(message.getStatutMes(), Statut_mes.NONACCEPTE);
		Assertions.assertEquals(message.getEtatMes(), Etat_mes.NONVISIBLE);
	}}
	
	
}
