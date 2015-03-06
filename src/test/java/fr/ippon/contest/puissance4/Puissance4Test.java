package fr.ippon.contest.puissance4;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

import fr.ippon.contest.puissance4.Puissance4.EtatJeu;

public class Puissance4Test {

	private static final Logger log = Logger.getLogger(Puissance4Test.class);

	private Puissance4 jeu = new Puissance4Impl();

	@Test
	public void testsErreursChargerJeu() {
		char [][] grille = {{'-', '-', '-', '-', '-', '-'},
				 			{'-', '-', '-', '-', '-', '-'},
				 			{'-', '-', 'J', '-', '-', '-'},
				 			{'-', '-', 'R', 'J', 'R', '-'},
				 			{'-', 'J', 'R', 'R', 'J', '-'},
				 			{'R', 'J', 'J', 'J', 'R', 'R'}};
		try {
			jeu.chargerJeu(grille, 'J');
			fail("grille invalide - 6 colonnes seulement.");
		} catch (IllegalArgumentException e) {
			log.info(e.getMessage());
		}
		char [][] grille2 = {{'-', '-', '-', '-', '-', '-', '-'},
				 			 {'-', '-', '-', '-', '-', '-', '-'},
				 			 {'-', '-', 'J', '-', '-', '-', '-'},
				 			 {'-', '-', 'R', 'J', 'R', '-', '-'},
				 			 {'-', 'J', 'R', 'R', 'J', '-', '-'}};
		try {
			jeu.chargerJeu(grille2, 'R');
			fail("grille invalide - 5 lignes seulement.");
		} catch (IllegalArgumentException e) {
			log.info(e.getMessage());
		}
		char [][] grille3 = {{'-', '-', '-', '-', '-', '-', '-'},
				 			 {'-', '-', '-', '-', '-', '-', '-'},
				 			 {'-', '-', 'J', '-', '-', '-', '-'},
				 			 {'-', '-', 'R', 'J', 'R', '-', '-'},
				 			 {'-', 'J', 'R', 'R', 'J', '-', '-'},
				 			 {'R', 'J', 'J', 'J', 'R', 'R', '-'}};
		try {
			jeu.chargerJeu(grille3, '-');
			fail("grille invalide - joueur manquant.");
		} catch (IllegalArgumentException e) {
			log.info(e.getMessage());
		}
	}

	@Test
	public void testChargerJeu() {
		char [][] grille = {{'-', '-', 'J', '-', '-', '-', '-'},
		 					{'-', '-', 'R', '-', '-', '-', '-'},
		 					{'-', '-', 'J', '-', '-', '-', '-'},
		 					{'-', '-', 'R', 'J', 'R', '-', '-'},
		 					{'-', 'J', 'R', 'R', 'J', '-', '-'},
		 					{'R', 'J', 'J', 'J', 'R', 'R', '-'}};
		jeu.chargerJeu(grille, 'R');
		assertTrue(jeu.getEtatJeu()==EtatJeu.EN_COURS);
		assertTrue(jeu.getTour()=='R');
		assertTrue(jeu.getOccupant(4, 4) == 'J');
	}
	@Test
	public void testJouer() {
		char [][] grille = {{'-', '-', 'J', '-', '-', '-', '-'},
	 			 			{'-', '-', 'R', '-', '-', '-', '-'},
	 			 			{'-', '-', 'J', '-', '-', '-', '-'},
	 			 			{'-', '-', 'R', 'J', 'R', '-', '-'},
	 			 			{'-', 'J', 'R', 'R', 'J', '-', '-'},
	 			 			{'R', 'J', 'J', 'J', 'R', 'R', '-'}};
		jeu.chargerJeu(grille, 'R');
		try {
			jeu.jouer(2);
			fail("colonne pleine.");
		} catch (IllegalStateException e) {
			log.info(e.getMessage());
		}
		try {
			jeu.jouer(7);
			fail("colonne invalide.");
		} catch (IllegalArgumentException e) {
			log.info(e.getMessage());
		}
		try {
			jeu.jouer(-1);
			fail("colonne invalide.");
		} catch (IllegalArgumentException e) {
			log.info(e.getMessage());
		}
		jeu.jouer(3);
		assertTrue(jeu.getEtatJeu()==EtatJeu.EN_COURS);
		assertTrue(jeu.getTour()=='J');
		assertTrue(jeu.getOccupant(2, 3) == 'R');
	}
	@Test
	public void testChargerLigneGagnante() {
		char [][] grille = {{'-', '-', '-', '-', '-', 'J', 'R'},
				 			{'-', '-', 'J', 'R', '-', 'R', 'J'},
				 			{'-', 'R', 'R', 'R', '-', 'R', 'J'},
				 			{'-', 'J', 'J', 'J', 'J', 'J', 'J'},
				 			{'-', 'R', 'J', 'R', 'R', 'J', 'R'},
				 			{'J', 'R', 'J', 'J', 'R', 'J', 'R'}};
		jeu.chargerJeu(grille, 'R');
		assertTrue(jeu.getEtatJeu()==EtatJeu.JAUNE_GAGNE);
	}
	@Test
	public void testChargerColonneGagnante() {
		char [][] grille = {{'-', '-', '-', '-', '-', '-', '-'},
				 			{'-', '-', '-', '-', '-', '-', '-'},
				 			{'-', '-', 'J', '-', '-', '-', '-'},
				 			{'-', '-', 'J', 'J', 'R', '-', '-'},
				 			{'-', '-', 'J', 'R', 'R', 'R', '-'},
				 			{'-', '-', 'J', 'R', 'R', 'J', '-'}};
		jeu.chargerJeu(grille, 'R');
		assertTrue(jeu.getEtatJeu()==EtatJeu.JAUNE_GAGNE);
	}
	@Test
	public void testChargerDiagonaleGagnante() {
		char [][] grille = {{'-', '-', 'J', '-', '-', '-', '-'},
		 					{'-', '-', 'R', '-', '-', '-', '-'},
		 					{'-', 'R', 'J', '-', '-', '-', '-'},
		 					{'-', 'J', 'R', 'J', 'R', '-', '-'},
		 					{'R', 'J', 'R', 'R', 'J', '-', '-'},
		 					{'R', 'J', 'J', 'J', 'R', 'R', '-'}};
		jeu.chargerJeu(grille, 'J');
		assertTrue(jeu.getEtatJeu()==EtatJeu.ROUGE_GAGNE);
		
		char [][] grille2 = {{'-', '-', '-', '-', '-', '-', '-'},
							 {'R', 'R', 'R', 'J', '-', '-', 'R'},
							 {'J', 'J', 'J', 'R', 'J', 'R', 'J'},
							 {'R', 'R', 'J', 'J', 'R', 'J', 'J'},
							 {'R', 'J', 'R', 'R', 'R', 'J', 'R'},
							 {'R', 'J', 'J', 'R', 'R', 'J', 'J'}};
		jeu.chargerJeu(grille2, 'J');
		assertTrue(jeu.getEtatJeu()==EtatJeu.ROUGE_GAGNE);
	}
	@Test
	public void testjouerLigneGagnante() {
		char [][] grille = {{'-', '-', '-', '-', '-', '-', '-'},
				 			{'-', '-', '-', '-', '-', '-', '-'},
				 			{'-', '-', 'R', '-', '-', '-', '-'},
				 			{'-', '-', 'J', 'J', 'J', 'R', '-'},
				 			{'-', '-', 'J', 'R', 'R', 'R', '-'},
				 			{'-', '-', 'J', 'R', 'R', 'J', 'J'}};
		jeu.chargerJeu(grille, 'R');
		assertTrue(jeu.getEtatJeu()==EtatJeu.EN_COURS);
		jeu.jouer(6);
		assertTrue(jeu.getEtatJeu()==EtatJeu.ROUGE_GAGNE);
	}
	@Test
	public void testJouerColonneGagnante() {
		char [][] grille = {{'-', '-', '-', '-', '-', '-', '-'},
				 			{'-', '-', 'J', '-', '-', '-', '-'},
				 			{'-', '-', 'J', 'J', 'R', '-', '-'},
				 			{'-', '-', 'J', 'R', 'R', '-', '-'},
				 			{'-', 'R', 'R', 'J', 'J', '-', '-'},
				 			{'R', 'J', 'R', 'R', 'J', '-', '-'}};
		jeu.chargerJeu(grille, 'J');
		assertTrue(jeu.getEtatJeu()==EtatJeu.EN_COURS);
		jeu.jouer(2);
		assertTrue(jeu.getEtatJeu()==EtatJeu.JAUNE_GAGNE);
	}
	@Test
	public void testJouerDiagonaleGagnante() {
		char [][] grille1 = {{'-', '-', '-', '-', '-', '-', '-'},
				 			 {'-', '-', 'R', '-', '-', '-', '-'},
				 			 {'-', '-', 'J', 'J', '-', '-', '-'},
				 			 {'-', 'R', 'J', 'R', '-', '-', '-'},
				 			 {'J', 'R', 'R', 'J', 'J', '-', '-'},
				 			 {'R', 'J', 'R', 'R', 'J', '-', '-'}};
		jeu.chargerJeu(grille1, 'J');
		assertTrue(jeu.getEtatJeu()==EtatJeu.EN_COURS);
		jeu.jouer(1);
		assertTrue(jeu.getEtatJeu()==EtatJeu.JAUNE_GAGNE);
		
		char [][] grille2 = {{'-', '-', '-', '-', '-', '-', '-'},
				 			 {'-', '-', '-', '-', '-', '-', '-'},
				 			 {'-', '-', 'J', 'J', '-', '-', '-'},
				 			 {'-', '-', 'R', 'R', 'R', 'J', '-'},
				 			 {'-', '-', 'R', 'J', 'J', 'J', '-'},
				 			 {'-', 'R', 'R', 'J', 'J', 'R', '-'}};
		jeu.chargerJeu(grille2, 'R');
		assertTrue(jeu.getEtatJeu()==EtatJeu.EN_COURS);
		jeu.jouer(4);
		assertTrue(jeu.getEtatJeu()==EtatJeu.ROUGE_GAGNE);
	}
	@Test
	public void testJouerNul() {
		char [][] grille = {{'R', '-', 'J', 'R', 'J', 'R', 'J'},
							{'J', 'R', 'R', 'J', 'R', 'R', 'R'},
							{'R', 'J', 'J', 'J', 'R', 'J', 'J'},
							{'J', 'R', 'R', 'J', 'R', 'R', 'R'},
							{'J', 'J', 'R', 'R', 'J', 'J', 'J'},
							{'R', 'J', 'J', 'J', 'R', 'R', 'R'}};
		jeu.chargerJeu(grille, 'J');
		assertTrue(jeu.getEtatJeu()==EtatJeu.EN_COURS);
		jeu.jouer(1);
		assertTrue(jeu.getEtatJeu()==EtatJeu.MATCH_NUL);
	}
	@Test
	public void testChargerNul() {
		char [][] grille = {{'R', 'J', 'J', 'R', 'J', 'R', 'J'},
							{'J', 'R', 'R', 'J', 'R', 'R', 'R'},
							{'R', 'J', 'J', 'J', 'R', 'J', 'J'},
							{'J', 'R', 'R', 'J', 'R', 'R', 'R'},
							{'J', 'J', 'R', 'R', 'J', 'J', 'J'},
							{'R', 'J', 'J', 'J', 'R', 'R', 'R'}};
		jeu.chargerJeu(grille, 'J');
		assertTrue(jeu.getEtatJeu()==EtatJeu.MATCH_NUL);
	}
}
