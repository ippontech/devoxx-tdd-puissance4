package fr.ippon.contest.puissance4;


public class Puissance4StatsDecorator extends StatsDecorator implements Puissance4 {
	
	/*
	 * Constantes representant le nom des méthodes de l'API.
	 */
	private final String NOUVEAU_JEU = "nouveauJeu";
	private final String CHARGER_JEU = "chargerJeu";
	private final String ETAT_JEU 	 = "getEtatJeu";
	private final String TOUR 		 = "getTour";
	private final String OCCUPANT 	 = "getOccupant";
	private final String JOUER 		 = "jouer";

	/**
	 * Instance à décorer
	 */
	private Puissance4 jeu;
	

	public Puissance4StatsDecorator(Puissance4 jeuADecorer) {
		jeu = jeuADecorer;
	}

	@Override
	public void nouveauJeu() {
		collectMethodStats(NOUVEAU_JEU, () -> { jeu.nouveauJeu(); });
	}

	@Override
	public void chargerJeu(char[][] grille, char tour) {
		collectBiConsumerStats(CHARGER_JEU, (g, t) -> { 
			jeu.chargerJeu(g,t); 
		}, grille, tour);
	}

	@Override
	public EtatJeu getEtatJeu() {
		return collectSupplierStats(ETAT_JEU, () -> {
			return jeu.getEtatJeu();
		});
	}

	@Override
	public char getTour() {
		return collectSupplierStats(TOUR, () -> {
			return jeu.getTour();
		});
	}

	@Override
	public char getOccupant(int ligne, int colonne) {
		return collectBiFunctionStats(OCCUPANT, (l, c) -> {
			return jeu.getOccupant(l, c);
		}, ligne, colonne);
	}

	@Override
	public void jouer(int colonne) {
		collectConsumerStats(JOUER, c -> { 
			jeu.jouer(c); 
		}, colonne);
	}
}
