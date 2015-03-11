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
	private final String ETAT_JEU = "getEtatJeu";
	private final String TOUR = "getTour";
	private final String OCCUPANT = "getOccupant";
	private final String JOUER = "jouer";

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
	public void nouveauJeu() {
		long top = System.nanoTime();
		jeu.nouveauJeu();
		synchronized (statsExec) {
			if (statsExec.containsKey(NOUVEAU_JEU)) {
				statsExec.get(NOUVEAU_JEU).add(System.nanoTime() - top);
			} else {
				List<Long> execTimes = new ArrayList<Long>();
				execTimes.add(System.nanoTime() - top);
				statsExec.put(NOUVEAU_JEU, execTimes);
			}
		}
	}

	@Override
	public void chargerJeu(char[][] grille, char tour) {
		long top = System.nanoTime();
		jeu.chargerJeu(grille, tour);
		synchronized (statsExec) {
			if (statsExec.containsKey(CHARGER_JEU)) {
				statsExec.get(CHARGER_JEU).add(System.nanoTime() - top);
			} else {
				List<Long> execTimes = new ArrayList<Long>();
				execTimes.add(System.nanoTime() - top);
				statsExec.put(CHARGER_JEU, execTimes);
			}
		}
	}

	@Override
	public EtatJeu getEtatJeu() {
		long top = System.nanoTime();
		EtatJeu etat = jeu.getEtatJeu();
		synchronized (statsExec) {
			if (statsExec.containsKey(ETAT_JEU)) {
				statsExec.get(ETAT_JEU).add(System.nanoTime() - top);
			} else {
				List<Long> execTimes = new ArrayList<Long>();
				execTimes.add(System.nanoTime() - top);
				statsExec.put(ETAT_JEU, execTimes);
			}
		}
		return etat;
	}

	@Override
	public char getTour() {
		long top = System.nanoTime();
		char tour = jeu.getTour();
		synchronized (statsExec) {
			if (statsExec.containsKey(TOUR)) {
				statsExec.get(TOUR).add(System.nanoTime() - top);
			} else {
				List<Long> execTimes = new ArrayList<Long>();
				execTimes.add(System.nanoTime() - top);
				statsExec.put(TOUR, execTimes);
			}
		}
		return tour;
	}

	@Override
	public char getOccupant(int ligne, int colonne) {
		long top = System.nanoTime();
		char occupant = jeu.getOccupant(ligne, colonne);
		synchronized (statsExec) {
			if (statsExec.containsKey(OCCUPANT)) {
				statsExec.get(OCCUPANT).add(System.nanoTime() - top);
			} else {
				List<Long> execTimes = new ArrayList<Long>();
				execTimes.add(System.nanoTime() - top);
				statsExec.put(OCCUPANT, execTimes);
			}
		}
		return occupant;
	}

	@Override
	public void jouer(int colonne) {
		long top = System.nanoTime();
		jeu.jouer(colonne);
		synchronized (statsExec) {
			if (statsExec.containsKey(JOUER)) {
				statsExec.get(JOUER).add(System.nanoTime() - top);
			} else {
				List<Long> execTimes = new ArrayList<Long>();
				execTimes.add(System.nanoTime() - top);
				statsExec.put(JOUER, execTimes);
			}
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
