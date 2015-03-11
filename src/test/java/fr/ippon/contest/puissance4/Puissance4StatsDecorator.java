package fr.ippon.contest.puissance4;

import org.apache.log4j.Logger;

import java.util.*;

public class Puissance4StatsDecorator implements Puissance4 {
	/**
	 * LOGGER.
	 */
	private final static Logger LOG = Logger.getLogger("Puissance4");
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
	/**
	 * Map stockant les temps d'exécution pour chaque méthode de l'API.
	 */
	private Map<String, List<Long>> statsExec;

	public Puissance4StatsDecorator(Puissance4 jeuADecorer) {
		jeu = jeuADecorer;
		statsExec = new HashMap<String, List<Long>>();
	}

	@Override
	public synchronized void nouveauJeu() {
		long top = System.nanoTime();
		jeu.nouveauJeu();
		collectStats(NOUVEAU_JEU, top);
	}

	@Override
	public synchronized void chargerJeu(char[][] grille, char tour) {
		long top = System.nanoTime();
		jeu.chargerJeu(grille, tour);
		collectStats(CHARGER_JEU, top);
	}

	@Override
	public synchronized EtatJeu getEtatJeu() {
		long top = System.nanoTime();
		EtatJeu etat = jeu.getEtatJeu();
		collectStats(ETAT_JEU, top);
		return etat;
	}

	@Override
	public synchronized char getTour() {
		long top = System.nanoTime();
		char tour = jeu.getTour();
		collectStats(TOUR, top);
		return tour;
	}

	@Override
	public synchronized char getOccupant(int ligne, int colonne) {
		long top = System.nanoTime();
		char occupant = jeu.getOccupant(ligne, colonne);
		collectStats(OCCUPANT, top);
		return occupant;
	}

	@Override
	public synchronized void jouer(int colonne) {
		long top = System.nanoTime();
		jeu.jouer(colonne);
		collectStats(JOUER, top);
	}
	/**
	 * Fonction appellée pour la maj des stats d'une méthode
	 * 
	 * @param methodName nom de la méthode qui a été appelée
	 * @param top		 top donné avant l'appel par System.nanoTime()
	 */
	private void collectStats(String methodName, long top) {
		if (statsExec.containsKey(methodName)) {
			statsExec.get(methodName).add(System.nanoTime() - top);
		} else {
			List<Long> execTimes = new ArrayList<Long>();
			execTimes.add(System.nanoTime() - top);
			statsExec.put(methodName, execTimes);
		}
	}

	public void printStats() {
		if (LOG.isDebugEnabled()) {
			synchronized (statsExec) {
				for (String key : statsExec.keySet()) {
					List<Long> statsMethode = statsExec.get(key);
					LOG.debug(key + " : " + statsMethode.size() + " appels, de durée moyenne " + Math.round(statsMethode.stream().mapToLong(x -> x).average().getAsDouble()) + " ns");
				}
			}
		}
	}
}
