// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs.validation;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eu.telecomsudparis.csc4102.minisocs.Utilisateur;
import eu.telecomsudparis.csc4102.minisocs.Reseausocial;
import eu.telecomsudparis.csc4102.minisocs.Membership;
import eu.telecomsudparis.csc4102.minisocs.MiniSocs;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestPosterMessage {
	private MiniSocs miniSocs;
    private String pseudo_rs;
    private String pseudoCreateur;
    private String pseudoUtilisateur;
    private String contenu;
    private String nomreseausocial;
    @BeforeEach
	void setUp() throws OperationImpossible, InterruptedException { 
    	miniSocs = new MiniSocs("MiniSocs");
    	pseudo_rs ="pseudo1";
    	pseudoCreateur="pseudoCreateur";
    	pseudoUtilisateur="pseudoUtilisateur";
    	contenu="contenu";
    	nomreseausocial="nomreseausocial";
    	miniSocs.ajouterUtilisateur(pseudoUtilisateur, "nom1", "prenom1","bon@courriel.fr");
    	miniSocs.ajouterUtilisateur(pseudoCreateur, "nom2","prenom2","bon2@courriel.fr");   
    	miniSocs.creerReseauSocial(nomreseausocial, "pseudocreateur", pseudoCreateur);
    	miniSocs.ajouterMembreaReseausocial(pseudoCreateur, pseudoUtilisateur, pseudo_rs, nomreseausocial);
    }
    @AfterEach
	void tearDown() { 
    	miniSocs=null;
    	pseudo_rs=null;
    	contenu=null;
    	pseudoCreateur=null;
    	pseudoUtilisateur=null;
    	nomreseausocial=null;
    	
    }
    @Test
    void PosterMessageTest1Jeu1() {
        Assertions.assertThrows(OperationImpossible.class, () -> 
            miniSocs.posterMessage(pseudo_rs, null, nomreseausocial));    
    }
    @Test
    void PosterMessageTest1Jeu2() {
        Assertions.assertThrows(OperationImpossible.class, () -> 
            miniSocs.posterMessage(pseudo_rs, "", nomreseausocial));    
    }
    @Test
    void PosterMessageTest2() {
        Assertions.assertThrows(OperationImpossible.class, () -> 
            miniSocs.posterMessage(null, contenu, nomreseausocial));    
    }
    @Test
    void PosterMessageTest3() {
        Assertions.assertThrows(OperationImpossible.class, () -> 
            miniSocs.posterMessage(pseudo_rs, contenu, null));    
    }
    @Test
    void PosterMessageTest4Jeu1() {
        Utilisateur u = miniSocs.getUtilisateurs().get(pseudoUtilisateur);
        u.bloquerCompte();
        Assertions.assertThrows(OperationImpossible.class, () -> 
            miniSocs.posterMessage(pseudo_rs, contenu, nomreseausocial));
    }
    @Test
    void PosterMessageTest4Jeu2() {
    	Utilisateur u = miniSocs.getUtilisateurs().get(pseudoUtilisateur);
        u.desactiverCompte();
        Assertions.assertThrows(OperationImpossible.class, () -> 
            miniSocs.posterMessage(pseudo_rs, contenu, nomreseausocial));
    }
    @Test
    void PosterMessageTest5Jeu1() throws OperationImpossible, InterruptedException {
    	Utilisateur u = miniSocs.getUtilisateurs().get(pseudoUtilisateur);
    	Reseausocial r = miniSocs.getReseauxsociaux().get(nomreseausocial);
        Membership membership = u.getMembership(pseudo_rs);
        membership.setRoleModerateur();
        miniSocs.posterMessage(pseudoUtilisateur, contenu, nomreseausocial);
        Assertions.assertTrue(r.messages.containsKey(contenu));
    }
    
}
