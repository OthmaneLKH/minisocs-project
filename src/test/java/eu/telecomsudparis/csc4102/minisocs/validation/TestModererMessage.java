// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import eu.telecomsudparis.csc4102.minisocs.Message;
import eu.telecomsudparis.csc4102.minisocs.Reseausocial;
import eu.telecomsudparis.csc4102.minisocs.MiniSocs;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestModererMessage {
	private MiniSocs miniSocs;
	private String rs;
	private String contenu;
	private String pseudoAuteur;
	private String pseudoModerateur;
	private String pseudoCreateur;
	@BeforeEach
	void SetUp () throws OperationImpossible, InterruptedException {
		miniSocs = new MiniSocs("MiniSocs");
		contenu="contenu";
		rs="rs";
		pseudoAuteur="pseudoAuteur";
		pseudoModerateur="pseudoModerateur";
		pseudoCreateur="pseudoCreateur"; 
		miniSocs.ajouterUtilisateur(pseudoAuteur, "nom2", "prenom2", "bon2@courriel.fr");
		miniSocs.ajouterUtilisateur(pseudoCreateur, "nom", "prenom", "bon@courriel.fr");
		miniSocs.ajouterUtilisateur(pseudoModerateur, "nom3", "prenom3", "bon3@courriel.fr");
		miniSocs.creerReseauSocial(rs, "pseudocreateur", pseudoCreateur);
		miniSocs.ajouterMembreaReseausocial(pseudoCreateur, pseudoAuteur, "pseudors1", rs);
		miniSocs.ajouterMembreaReseausocial(pseudoCreateur, pseudoModerateur, "pseudors2", rs);
		
}
	@AfterEach
	void tearDown() { 
		miniSocs=null;
		contenu=null;
		rs=null;
		pseudoAuteur=null;
		pseudoModerateur=null;
	
	}
	
	@Test
	void ModererMessageTest1Jeu1() throws OperationImpossible, InterruptedException {
		miniSocs.posterMessage(pseudoAuteur, contenu, rs);
		Assertions.assertThrows(OperationImpossible.class, () -> 
        miniSocs.modererMessage(pseudoModerateur, rs, null, true));   
			
	}
	@Test
	void ModererMessageTest1Jeu2() throws OperationImpossible, InterruptedException {
		miniSocs.posterMessage(pseudoAuteur, contenu, rs);
		Assertions.assertThrows(OperationImpossible.class, () -> 
        miniSocs.modererMessage(pseudoModerateur, rs, "", true));    
			
	}
	@Test
	void ModererMessageTest2() throws OperationImpossible, InterruptedException {
		miniSocs.posterMessage(pseudoAuteur, contenu, rs);
		Reseausocial reseausocial = miniSocs.getReseauxsociaux().get(rs);
		Message message = reseausocial.messages.get(contenu);
		message.setEtatMesVisible();
		Assertions.assertThrows(OperationImpossible.class, () -> 
        miniSocs.modererMessage(pseudoModerateur, rs, contenu, true));
	}
	@Test
	void ModererMessageTest3() throws OperationImpossible, InterruptedException {
		miniSocs.posterMessage(pseudoAuteur, contenu, rs);
		Reseausocial reseausocial = miniSocs.getReseauxsociaux().get(rs);
		Message message = reseausocial.messages.get(contenu);
		message.setStatutAccepte();
		Assertions.assertThrows(OperationImpossible.class, () -> 
        miniSocs.modererMessage(pseudoModerateur, rs, contenu, true));
	}
	@Test
	void ModererMessageTest4() throws OperationImpossible, InterruptedException {
		miniSocs.posterMessage(pseudoAuteur, contenu, rs);
		Reseausocial reseausocial = miniSocs.getReseauxsociaux().get(rs);
		Message message = reseausocial.messages.get(contenu);
		message.setStatutNonAccepte();
		Assertions.assertThrows(OperationImpossible.class, () -> 
        miniSocs.modererMessage(pseudoModerateur, rs, contenu, true));
	}
	
	
	}
