package fr.ippon.contest.puissance4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.log4j.Logger;

public class StatsDecorator {
	/**
	 * LOGGER.
	 */
	private final static Logger LOG = Logger.getLogger("API Stats");
	
	/**
	 * Map stockant les temps d'exécution pour chaque méthode de l'API décorée.
	 */
	private Map<String, List<Long>> statsExec;
	
	protected interface Method {
		void process() ;
	}
	
	public StatsDecorator() {
		statsExec = new HashMap<String, List<Long>>();
	}
	

	public final synchronized <R> R collectSupplierStats(String supplierName, Supplier<R> supplier) {
		long top = System.nanoTime();
		R result = supplier.get();
		collectStats(supplierName, top);
		return result;
	}
	
	public final synchronized <T, U, R> R collectBiFunctionStats(String supplierName, BiFunction<T, U, R> biFunction, T arg1, U arg2) {
		long top = System.nanoTime();
		R result = biFunction.apply(arg1, arg2);
		collectStats(supplierName, top);
		return result;
	}	
	
	public final synchronized <T> void collectConsumerStats(String supplierName, Consumer<T> consumer, T arg) {
		long top = System.nanoTime();
		consumer.accept(arg);
		collectStats(supplierName, top);
	}
	
	public final synchronized void collectMethodStats(String supplierName, Method method) {
		long top = System.nanoTime();
		method.process();
		collectStats(supplierName, top);
	}
	
	public final synchronized <T, U> void collectBiConsumerStats(String supplierName, BiConsumer<T, U> consumer, T arg1, U arg2) {
		long top = System.nanoTime();
		consumer.accept(arg1, arg2);
		collectStats(supplierName, top);
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
