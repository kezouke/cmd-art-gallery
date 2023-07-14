package representation_instruments;

import db_objects.iShortInfo;

import java.util.Collection;
import java.util.Iterator;

public class ArtObjectIterator<E extends iShortInfo>
        implements Iterator<ArtObjectIterator<E>> {
    private final Collection<E> artObjects;
    public final int currentStart;

    public ArtObjectIterator(Collection<E> artObjects) {
        this.artObjects = artObjects;
        currentStart = 0;
    }

    public ArtObjectIterator(Collection<E> artObjects, int currentStart) {
        this.artObjects = artObjects;
        this.currentStart = currentStart;
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
     * @return new iterator for next 5 art objects
     */
    @Override
    public ArtObjectIterator<E> next() {
        int currentEnd = Math.min(currentStart + 5, artObjects.size());
        for (int i = currentStart; i < currentEnd; i++) {
            iShortInfo artObh = (iShortInfo) artObjects.toArray()[i];
            System.out.println(artObh.shortInfo());
        }
        return new ArtObjectIterator<>(artObjects, currentEnd);
    }

    /**
     * This method is similar to prev() method,
     * but this one doesn't change iterator
     * position backward.
     * <p>
     * It just shows prev 5 pictures to the user.
     */
    public void showArtObjects() {
        int currentEnd = Math.max(currentStart - 5, 0);
        for (int i = currentStart - 1; i >= currentEnd; i--) {
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
        return currentStart > 5;
    }

    /**
     * Returns the previous element in the iteration.
     *
     * @return new iterator for next 5 art objects
     */
    public ArtObjectIterator<E> back() {
        int currentEnd = Math.max(0, currentStart - 5);
        for (int i = currentStart - 1; i >= currentEnd; i--) {
            iShortInfo artObh = (iShortInfo) artObjects.toArray()[i];
            System.out.println(artObh.shortInfo());
        }
        return new ArtObjectIterator<>(artObjects, currentStart);
    }
}
