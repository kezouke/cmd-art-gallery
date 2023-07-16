package representation_instruments.work_with_firebase;

import db_objects.iShortInfo;

import java.util.Collection;
import java.util.Iterator;

public class ArtObjectIterator<E extends iShortInfo>
        implements Iterator<ArtObjectIterator<E>> {
    private final Collection<E> artObjects;
    public final int currentStart;
    private final int step;

    public ArtObjectIterator(Collection<E> artObjects, int step) {
        this.artObjects = artObjects;
        this.step = step;
        currentStart = 0;
    }

    public ArtObjectIterator(Collection<E> artObjects,
                             int currentStart,
                             int step) {
        this.artObjects = artObjects;
        this.currentStart = currentStart;
        this.step = step;
    }

    public boolean isEmpty() {
        return artObjects.isEmpty();
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return currentStart != artObjects.size();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return new iterator for next step art objects
     */
    @Override
    public ArtObjectIterator<E> next() {
        int currentEnd = Math.min(currentStart + step, artObjects.size());
        for (int i = currentStart; i < currentEnd; i++) {
            iShortInfo artObj = (iShortInfo) artObjects.toArray()[i];
            System.out.println(artObj.shortInfo());
        }
        return new ArtObjectIterator<>(artObjects, currentEnd, step);
    }

    /**
     * This method is similar to prev() method,
     * but this one doesn't change iterator
     * position backward.
     * <p>
     * It just shows prev step pictures to the user.
     */
    public void showArtObjects() {
        int currentS = Math.max(currentStart - step, 0);
        for (int i = currentS; i < currentStart; i++) {
            iShortInfo artObh = (iShortInfo) artObjects.toArray()[i];
            System.out.println(artObh.shortInfo());
        }
    }

    /**
     * Returns {@code true} if the iteration has more elements from current position
     * to the first art object.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    public boolean hasPrev() {
        return currentStart > step;
    }

    /**
     * Returns the previous element in the iteration.
     *
     * @return new iterator for next step art objects
     */
    public ArtObjectIterator<E> back() {
        int prevStart = Math.max(currentStart - 2 * step, 0);
        return new ArtObjectIterator<>(artObjects, prevStart, step).next();
    }
}
