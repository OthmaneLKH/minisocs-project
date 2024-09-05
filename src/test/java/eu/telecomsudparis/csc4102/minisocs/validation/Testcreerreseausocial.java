// CHECKSTYLE:OFF
package eu.telecomsudparis.csc4102.minisocs.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import eu.telecomsudparis.csc4102.minisocs.Etat_res;
import eu.telecomsudparis.csc4102.minisocs.MiniSocs;
import eu.telecomsudparis.csc4102.util.OperationImpossible;

class Testcreerreseausocial {
	private MiniSocs miniSocs;
	private String pseudo_rs;
	private String nom;
	private String createur;

	@BeforeEach
	void setUp() {
		miniSocs = new MiniSocs("MiniSocs");
		pseudo_rs = "utilisateur1";
		nom = "nom1";
		createur = "createur1";
	}

	@AfterEach
	void tearDown() {
		miniSocs = null;
		pseudo_rs = null;
		nom = null;
		createur = null;
	}

	@Test
	void creerreseausocialTest1Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseauSocial(null, pseudo_rs, createur));
	}

	@Test
	void creerreseausocialTest1Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseauSocial("", pseudo_rs, createur));
	}

	@Test
	void creerreseausocialTest2Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseauSocial(nom, null, createur));
	}

	@Test
	void creerreseausocialTest2Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseauSocial(nom, "", createur));
	}

	@Test
	void creerreseausocialTest3Jeu1() throws Exception {
		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseauSocial(nom, pseudo_rs, null));
	}

	@Test
	void creerreseausocialTest3Jeu2() throws Exception {
		Assertions.assertThrows(OperationImpossible.class, () -> miniSocs.creerReseauSocial(nom, pseudo_rs, ""));
	}


	@Test
	void creerreseausocialTest4Puis4() throws Exception {
		miniSocs.ajouterUtilisateur(createur, nom, "prénom1", "bon@courriel.fr");
		Assertions.assertTrue(miniSocs.listerReseauSocial().isEmpty());
		miniSocs.creerReseauSocial(nom, pseudo_rs, createur);
		Assertions.assertFalse(miniSocs.listerReseauSocial().isEmpty());
		Assertions.assertEquals(1, miniSocs.listerReseauSocial().size());
		Assertions.assertTrue(miniSocs.listerReseauSocial().get(0).contains(nom));
		Assertions.assertTrue(miniSocs.listerReseauSocial().get(0).contains(createur));
		Assertions.assertTrue(miniSocs.listerReseauSocial().get(0).contains(Etat_res.OUVERT.toString()));

		Assertions.assertThrows(OperationImpossible.class,
				() -> miniSocs.creerReseauSocial(nom, pseudo_rs, createur));
	}
}
