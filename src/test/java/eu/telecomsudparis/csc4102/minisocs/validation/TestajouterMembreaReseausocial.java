// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import eu.telecomsudparis.csc4102.minisocs.MiniSocs;
import eu.telecomsudparis.csc4102.minisocs.Role;
import eu.telecomsudparis.csc4102.minisocs.Utilisateur;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class TestajouterMembreaReseausocial {
	private MiniSocs miniSocs;
	private String pseudo_rs;
	private String utilisateurquiAjoute;
	private String utilisateuraAjouter;
	private String utilisateurTiersTest;
	private String reseausocial;
	private Utilisateur utilisateur;
	private Utilisateur utilisateur2;
	
	
	private String nom1;
	private String prenom1;
	private String courriel1;

	private String pseudo_rs2;
	private String nom2;
	private String prenom2;
	private String courriel2;
	private String courriel3;
	@BeforeEach
	void setUp() throws OperationImpossible {
		miniSocs = new MiniSocs("MiniSocs");
		
		utilisateurquiAjoute = "utilisateurquiAjoute1";
		utilisateuraAjouter = "utilisateuraAjouter1";
		utilisateurTiersTest = "utilisateurtest";
		pseudo_rs = "pseudors";
		reseausocial = "rs1";

		
		nom1 = "nom1";
		prenom1 = "prenom1";
		courriel1 = "bon1@courriel.fr";

		pseudo_rs2 = "pseudors2";
		nom2 = "nom2";
		prenom2 = "prenom2";
		courriel2 = "bon2@courriel.fr";
		courriel3 = "bon2@courriell.fr";
		
		utilisateur = new Utilisateur(utilisateurquiAjoute, nom1, prenom1, courriel1);
		utilisateur2 = new Utilisateur(utilisateuraAjouter, nom2, prenom2, courriel2);

		miniSocs.ajouterUtilisateur(utilisateurquiAjoute, nom1, prenom1, courriel1);
		miniSocs.ajouterUtilisateur(utilisateuraAjouter, nom2, prenom2, courriel2);
		miniSocs.ajouterUtilisateur(utilisateurTiersTest, "noo", "prenoo", courriel3);

		
		miniSocs.creerReseauSocial(reseausocial, pseudo_rs, utilisateurquiAjoute);
		
		

	}

	@AfterEach
	void tearDown() {
		miniSocs = null;
		utilisateurquiAjoute = null;
		utilisateuraAjouter = null;
		pseudo_rs = null;
		reseausocial = null;
		

		nom1 = null;
		prenom1 = null;
		courriel1 = null;

		nom2 = null;
		prenom2 = null;
		courriel2 = null;
	}

	@Test
	void ajouterMembreaReseausocialTest1Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreaReseausocial(null, utilisateuraAjouter, pseudo_rs,reseausocial));
	}

	@Test
	void ajouterMembreaReseausocialTest1Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreaReseausocial("", utilisateuraAjouter, pseudo_rs,reseausocial));
	}

	@Test
	void ajouterMembreaReseausocialTest2Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, null, pseudo_rs,reseausocial));
	}

	@Test
	void ajouterMembreaReseausocialTest2Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, "", pseudo_rs,reseausocial));
	}

	@Test
	void ajouterMembreaReseausocialTest3Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, null,reseausocial));
	}
	
	@Test
	void ajouterMembreaReseausocialTest3Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, "",reseausocial));
	}
	
	@Test
	void ajouterMembreaReseausocialTest4Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, pseudo_rs,null));
	}

	@Test
	void ajouterMembreaReseausocialTest4Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class, 
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, pseudo_rs,""));
	}
	
	void ajouterMembreaReseausocialTest4Jeu3() throws Exception {
		Assertions.assertThrows(OperationImpossible.class, 
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, pseudo_rs,"random"));// test si on ajoute pas danns un rs inexistant  
	}
	
	void ajouterMembreaReseausocialTest5Jeu1() throws Exception {
		utilisateur.desactiverCompte();
		Assertions.assertThrows(OperationImpossible.class, 
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, pseudo_rs,reseausocial));
	}
	
	void ajouterMembreaReseausocialTest5Jeu2() throws Exception {//test ajout d'un membre par un non modérateur
		Assertions.assertThrows(OperationImpossible.class, 
				() -> miniSocs.ajouterMembreaReseausocial(utilisateuraAjouter, utilisateurTiersTest, "xyz",reseausocial));
	}

	void ajouterMembreaReseausocialTest5Jeu3() throws Exception {//test utilisateur à ajouter bloqué
		utilisateur2.bloquerCompte();
		Assertions.assertThrows(OperationImpossible.class, 
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, pseudo_rs,reseausocial));
	}
	
	void ajouterMembreaReseausocialTest5Jeu4() throws Exception {
		utilisateur2.desactiverCompte();
		Assertions.assertThrows(OperationImpossible.class, 
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, pseudo_rs,reseausocial));
	}


	@Test
	void ajouterMembreaReseausocialTest4Puis4() throws Exception {
		// on creer pas les utilisateurs dans le setUp pour pouvoir tester si quand ceux ci n'existent pas, est ce qu'une Exception est levé
		//utilisateur.reactiverCompte();
		//Assertions.assertEquals(1, miniSocs.listerMembershipDansReseauSocial(reseausocial).size());
		miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, pseudo_rs2, reseausocial);
		Assertions.assertEquals(2, miniSocs.listerMembershipDansReseauSocial(reseausocial).size());
		Assertions.assertTrue(miniSocs.listerMembershipDansReseauSocial(reseausocial).get(1).contains(pseudo_rs2));
		Assertions.assertTrue(miniSocs.listerMembershipDansReseauSocial(reseausocial).get(1).contains(utilisateuraAjouter));
		Assertions.assertTrue(miniSocs.listerMembershipDansReseauSocial(reseausocial).get(1).contains(reseausocial));
		Assertions.assertTrue(miniSocs.listerMembershipDansReseauSocial(reseausocial).get(1).contains(Role.SIMPLEMEMBRE.toString()));
		Assertions.assertThrows(OperationImpossible.class, //exeption parce que le membre est deja existant
				() -> miniSocs.ajouterMembreaReseausocial(utilisateurquiAjoute, utilisateuraAjouter, pseudo_rs2,reseausocial)); 
	} 
}
