package net.c2001.dm.base;

import java.util.ArrayList;
import java.util.List;
import net.c2001.dm.gmf.Pattern;
import net.c2001.utils.CommonOps;

/**
 * The abstract Apriori algorithm. {@code D} stands for the input data type,
 * {@code P} stands for data type to hold the mining result, {@code R} stands
 * for the data type of a pattern in the result. Apriori always generate a set
 * of patterns, each pattern is of type {@code R}, all the patterns mined out
 * are save in an object of type {@code P}.
 * 
 * @author Lin Dong
 * 
 */
public abstract class AbstractApriori<D extends net.c2001.dm.gmf.Data<P>, P extends Pattern, R>
		extends net.c2001.dm.gmf.Algorithm<P> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6974882791254785952L;

	/**
	 * Statistic information of frequent k-{@code R} mining.
	 * 
	 * @author Lin Dong
	 * 
	 */
	protected class Statistics {
		/**
		 * Size of {@code R}, that is k.
		 */
		public short size;
		/**
		 * Number of candidates.
		 */
		public int candidateCount;
		/**
		 * Number of frequents.
		 */
		public int frequentCount;
		/**
		 * Time consumed by generating candidates, counting support of
		 * candidates, and the total time to get k-results in milliseconds.
		 */
		public long[] timeCosts = new long[3];
	}

	/**
	 * Statistics of the mining progress.
	 */
	protected List<Statistics> statistics = null;

	/**
	 * Show frequents patterns. This method can work only when debug output is
	 * enabled.
	 * 
	 * @param data
	 *            the mining data.
	 * @param frequents
	 *            frequents patterns.
	 */
	protected void showFrequents(D data, List<R> frequents) {
		if (frequents == null)
			this.text(this, "No frequent pattern.");
		else if (frequents.size() == 0)
			this.text(this, "No frequent pattern.");
		else {
			this.text(this, frequents.size() + " frequents patterns:");
			for (R r : frequents) {
				this.text(resolvePattern(r, data));
			}
		}
	}

	/**
	 * Resolve a pattern to text.
	 * 
	 * @param r
	 *            pattern.
	 * @param data
	 *            the mining data. Sometimes the resolving rely on data.
	 * @return the text representation of the pattern.
	 */
	abstract protected String resolvePattern(R r, D data);

	/**
	 * Show candidate patterns. This method can work only when debug output is
	 * enabled.
	 * 
	 * @param data
	 *            the mining data.
	 * @param candidates
	 *            candidate patterns.
	 */
	protected void showCandidates(D data, List<R> candidates) {
		if (candidates == null)
			this.text(this, "No candidate pattern.");
		else if (candidates.size() == 0)
			this.text(this, "No candidate pattern.");
		else {
			this.text(this, candidates.size() + " candidate patterns:");
			for (R r : candidates) {
				this.text(resolvePattern(r, data));
			}
		}
	}

	/**
	 * Generate candidate k-patterns.
	 * 
	 * @param k
	 *            the size of pattern to generate.
	 * @param data
	 *            data to analysis.
	 * @param results
	 *            all known frequent patterns.
	 * @return list of candidate patterns. Always return a {@link List} object,
	 *         if there're no frequent patterns it should be empty.
	 */
	protected List<R> makeCandidate(int k, D data, List<List<R>> results) {
		List<R> candidates = null;
		if (k == 1)
			candidates = makeCandidate1(data);
		else
			/*
			 * results.get(0) should be frequent 1-patterns, when generate
			 * candidate 2-patterns results.get(0) should be used, that means
			 * the index should be (k-2).
			 */
			candidates = makeCandidatek(k, results.get(k - 2));
		return candidates;
	}

	/**
	 * Generate candidate k-patterns according to frequent (k-1)-patterns.
	 * 
	 * @param k
	 *            size of candidate pattern to generate.
	 * @param list
	 *            frequent (k-1)-patterns.
	 * @return candidate k-patterns in a list. Always return a {@link List}
	 *         object, if there're no candidate patterns it should be empty.
	 */
	abstract protected List<R> makeCandidatek(int k, List<R> list);

	/**
	 * Create candidate 1-patterns.
	 * 
	 * @param data
	 *            data data to analysis.
	 * @return candidate 1-patterns in a list. Always return a {@link List}
	 *         object, if there're no candidate patterns it should be empty.
	 */
	abstract protected List<R> makeCandidate1(D data);

	/**
	 * Filtrate candidate patterns. This will be called after candidates
	 * generated.
	 * 
	 * @param candidates
	 *            candidate patterns.
	 */
	abstract protected void filtrateCandidates(List<R> candidates);

	/**
	 * Filtrate frequent patterns. This will be called after frequents
	 * generated.
	 * 
	 * @param frequents
	 *            frequent patterns.
	 */
	abstract protected void filtrateFrequents(List<R> frequents);

	public List<List<R>> minePatterns(D data, double threshold) {
		this.progress(0, "Apriori", "Start mining");
		List<List<R>> results = new ArrayList<>();
		statistics = new ArrayList<>();
		for (int i = 0;; i++) {
			this.progress(-1, "Apriori", "Create candidate " + (i + 1)
					+ "-patterns");
			Statistics st = new Statistics();
			st.size = (short) (i + 1);
			// total time start
			st.timeCosts[2] = CommonOps.tick();
			// candidate time start
			st.timeCosts[0] = CommonOps.tick();
			List<R> candidates = makeCandidate(i + 1, data, results);
			filtrateCandidates(candidates);
			// candidate time stop
			st.timeCosts[0] = CommonOps.tock(st.timeCosts[0]);
			st.candidateCount = candidates.size();
			if (this.getDebugOutputStatus())
				showCandidates(data, candidates);
			this.progress(-1, "Apriori", "Find frequent " + (i + 1)
					+ "-patterns");
			// frequent time start
			st.timeCosts[1] = CommonOps.tick();
			List<R> frequents = getFrequent(data, candidates, threshold);
			filtrateFrequents(frequents);
			// frequent time stop
			st.timeCosts[1] = CommonOps.tock(st.timeCosts[1]);
			// total time stop
			st.timeCosts[2] = CommonOps.tock(st.timeCosts[2]);
			st.frequentCount = frequents.size();
			statistics.add(st);
			if (this.getDebugOutputStatus())
				showFrequents(data, frequents);
			if (frequents.size() == 0)
				break;
			results.add(frequents);
		}
		this.progress(99.9, "Apriori", "Frequent patterns found.");
		return results;
	}

	/**
	 * Find frequent patterns in candidates according to given threshold.
	 * 
	 * @param data
	 *            data to analysis.
	 * @param candidates
	 *            candidate patterns.
	 * @param threshold
	 *            the threshold for mining.
	 * @return list of frequent patterns. Always return a {@link List} object,
	 *         if there're no frequent patterns it should be empty.
	 */
	abstract protected List<R> getFrequent(D data, List<R> candidates,
			double threshold);

}
