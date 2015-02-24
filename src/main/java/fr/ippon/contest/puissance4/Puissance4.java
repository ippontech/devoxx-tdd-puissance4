package fr.ippon.contest.puissance4;
/**
 * Moteur pour le jeu puissance 4.
 */
public interface Puissance4 {

	public enum EtatJeu {
		EN_COURS, ROUGE_GAGNE, JAUNE_GAGNE, MATCH_NUL;
	}
	
	/**
	 * Vide la grille de jeu, et tire au sort le joeur qui commence.
	 */
	public void nouveauJeu();

	/**
	 * Charge une partie en cours.
	 * @param grille une grille de puissance 4 (6 lignes x 7 colonnes).
	 *  Une case vide est représentée par le caractère '-',
	 *  Une case occupée par un jeton rouge, par le caractère 'R'
	 *  Une case occupée par un jeton jaune, par le caractère 'J'.
	 * @param tour	 le joueur dont c'est le tour (J ou R)
	 * @throws IllegalArgumentException si la grille est invalide,
	 * ou si tour ne vaut ni J ni R.
	 */
	public void chargerJeu(final char[][] grille, final char tour);

	/**
	 * @return l'état dans lequel est le jeu :
	 * 	EN_COURS, ROUGE_GAGNE, JAUNE_GAGNE, MATCH_NUL
	 */
	public EtatJeu getEtatJeu();

	/**
	 * @return le joueur dont c'est le tour : 'R' pour rouge, 'J' pour jaune.
	 */
	public char getTour();

	/**
	 * @param ligne de 0 à 5
	 * @param colonne de 0 à 6
	 * @return la couleur - R(ouge) ou J(aune) - du jeton occupant la case
	 * aux coordonnées saisies en paramètre (si vide, '-')
	 * @throws IllegalArgumentException si les coordonnées sont invalides. 
	 */
	public char getOccupant(int ligne, int colonne);

	/**
	 * Un "coup" de puissance 4.
	 * @param colonne numéro de colonne où le joueur courant fait glisser son jeton
	 * (compris entre 0 et 6)
	 * @throws IllegalStateException si le jeu est déjà fini, où si la colonne est pleine
	 * @throws IllegalArgumentException si l'entier en paramètre est > 6 ou < 0.
	 */
	public void jouer(int colonne);
}
