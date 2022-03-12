package autocomplete;

import java.util.*;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        // adds all terms to ArrayList terms, then re-sort the array
        this.terms.addAll(terms);
        Collections.sort(this.terms, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        List<CharSequence> matches = new ArrayList<>();

        // find index of the closest match
        int closetMatchIdx = Collections.binarySearch(terms, prefix, CharSequence::compare);

        // closetMatchIdx >  0  IF  there is     exact match. Ex: prefix = "do" and term = "do"
        // closetMatchIdx <= 0  IF  there is NO  exact match. Ex: prefix = "d"  and term = "do" => resets closetMatchIdx accordingly
        if (closetMatchIdx < 0) {
            closetMatchIdx = -closetMatchIdx - 1;
        }

        // Tree is sorted => matches for a given prefix must be next to each other
        // starts from closetMatchIdx, while the prefix still match the term, add it to ArrayList match
        // prefix NOT FOUND if closetMatchIdx >= size of ArrayList terms
        while (closetMatchIdx < terms.size() && Autocomplete.isPrefixOf(prefix, terms.get(closetMatchIdx))) {
            matches.add(terms.get(closetMatchIdx));
            closetMatchIdx++;
        }

        return matches;
    }
}

