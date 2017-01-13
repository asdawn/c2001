package net.c2001.dm.ar.spatial.temporal.snapshots;

import java.util.ArrayList;
import java.util.List;
import net.c2001.dm.ar.spatial.AreaCalculator;

/**
 * Measure calculator for {@link SnapShots}.
 * @author Lin Dong
 *
 * @param <S> data type.
 * @param <E> snapshots type.
 */
abstract public class SnapshotsCalc<S,E extends SnapShots<S>> extends AreaCalculator<E> {

	private static final long serialVersionUID = 775338126869889024L;

	/**
	 * Create a {@link SnapshotsCalc}.
	 * @param ds snapshot items, instance of {@link SnapShots}.
	 */
	public SnapshotsCalc(List<E> ds) {
		super(ds);		
	}

	@Override
	protected E getIntersection(List<E> ds) {
		if(ds == null || ds.get(0) == null)
			return null;
		int dsCount = ds.size();
		int lCount = ds.get(0).size();
		List<S> out = new ArrayList<>();
		for(int i = 0; i< lCount;i++){
			List<S> temp = new ArrayList<>();
			for(int j=0; j < dsCount; j++){
				temp.add(ds.get(j).getLayer(i));
			}
			out.add(getLayerIntersection(temp));
			temp.clear();
		}
		return createSnapshots(out);
	}

	/**
	 * Create a snapshots item using given layers.
	 * @param layers layers of type {@code S}.
	 * @return {@link SnapShots} of type {@code E}.
	 */
	abstract protected E createSnapshots(List<S> layers); 

	/**
	 * Get intersection of a group of snapshot layers.
	 * @param layers snapshot layers of type {@code S}.
	 * @return the intersection of these layers.
	 */
	abstract protected S getLayerIntersection(List<S> layers);

	@Override
	protected double getArea(E dataset) {
		double measure = 0;
		if(dataset == null)
			return 0;
		for(int i=0;i<dataset.size();i++){
			measure += measure(dataset.getLayer(i));
		}
		return measure;
	}

	/**
	 * Get the measure of a snapshot layer.
	 * @param layer a snapshot layer.
	 * @return measure of the layer on success, 0 on failure.
	 */
	abstract protected double measure(S layer);




	

}
