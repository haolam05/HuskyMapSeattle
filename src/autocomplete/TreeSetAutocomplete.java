package autocomplete;

import java.util.*;

/**
 * {@link TreeSet} implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TreeSetAutocomplete implements Autocomplete {
    /**
     * {@link NavigableSet} of added autocompletion terms.
     */
    private final NavigableSet<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public TreeSetAutocomplete() {
        this.terms = new TreeSet<>(CharSequence::compare);
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        this.terms.addAll(terms);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();

        if (prefix == null || prefix.length() == 0) {
            return result;
        }

        // TreeSet#ceiling() : returns the least element that is >= parameter(prefix). returns null if no such element
        CharSequence start = terms.ceiling(prefix);
        if (start == null) {
            return result;
        }

        // TreeSet#tailSet() : returns an array of elements that have values >= parameter(start)
        for (CharSequence term : terms.tailSet(start)) {
            if (Autocomplete.isPrefixOf(prefix, term)) {
                result.add(term);
            } else {
                return result;
            }
        }

        return result;
    }
}

// #addAll
//      Time = O(NlogN) --- Worst
//      longest time to add an element is O(logN) and there are N elements => NlogN

// #allMatches
//      Time = O(N) --- Worst
//      in the worst case, the prefix is just 1 small letter(ex: 'a') and all words have prefixed 'a'
//          => TreeSet#tailSet(start) will return the array with the size almost N => takes O(N) time to search and add
//          to resulting array
//
//      compared to BinarySearch method, the time it takes to search for an element(worst case) using #ceiling is faster
//          , specifically O(logN) because TreeSet uses a Left Leaning Red Black tree(self-balancing tree), which avoids
//          the worst case runtime(ex: when all elements are added in increasing orders or decreasing orders)