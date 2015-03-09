package fr.ippon.contest.puissance4;

import java.util.Scanner;
import java.util.InputMismatchException;

import fr.ippon.contest.puissance4.Puissance4.EtatJeu;


public class App {
	
	public static void main(String[] args) {
		Puissance4 jeu = new Puissance4Impl();
		try (Scanner scanner = new Scanner (System.in)) {
			bienvenue();
			while (jeu.getEtatJeu() == EtatJeu.EN_COURS) {
				afficheGrille(jeu);
				System.out.println();
				afficheInvite(jeu);
				try {
					int col = scanner.nextInt();
					jeu.jouer(col);
				} catch (InputMismatchException ime) {
					String line = scanner.nextLine();
					System.err.println("Erreur, veuillez entrer un entier plutôt que: " + line);      
				} catch (Exception e) {
					System.err.println("Erreur - " + e.getMessage());
				}
			}
			afficherResultat(jeu);
		}
	}

	private static void afficherResultat(final Puissance4 jeu) {
		EtatJeu etat = jeu.getEtatJeu();
		if (etat == EtatJeu.MATCH_NUL) {
			System.out.println("Match nul !");
			return;
		}
		if (etat == EtatJeu.JAUNE_GAGNE) {
			System.out.println("Jaune a gagné !");
			return;
		}
		if (etat == EtatJeu.ROUGE_GAGNE) {
			System.out.println("Rouge a gagné !");
			return;
		}
	}

	private static void afficheInvite(final Puissance4 jeu) {
		System.out.println("A votre tour, " + jeu.getTour() + " entrez le numéro de colonne où vous voulez jouer : ");
	}

	private static void afficheGrille(final Puissance4 jeu) {
		System.out.println("0123456");
		for (int ligne = 0; ligne < 6; ligne++) {
			for (int colonne = 0; colonne < 7; colonne++) {
				System.out.print(jeu.getOccupant(ligne, colonne));
			}
			System.out.println();
		}
	}

	private static void bienvenue() {
		String msg = "*****************************\n"
					+"* Bienvenue sur Puissance 4 *\n"
					+"*****************************";		
		System.out.println(msg);
	}
}
