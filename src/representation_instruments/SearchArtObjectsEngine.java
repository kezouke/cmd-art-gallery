package representation_instruments;

import db_objects.iMatchWhileSearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SearchArtObjectsEngine<E extends iMatchWhileSearch> {
    private final List<E> artObjects;

    public SearchArtObjectsEngine(List<E> artObjects) {
        this.artObjects = artObjects;
    }

    public List<E> searchArtObj(String keyword) {
        List<E> matchingArtObjects = new ArrayList<>();
        for (E artObj : artObjects) {
            if (artObj.isMatch(keyword)) {
                matchingArtObjects.add(artObj);
            }
        }
        return matchingArtObjects;
    }
}
