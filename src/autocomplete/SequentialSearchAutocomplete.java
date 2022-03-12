package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        // add all terms into ArrayList terms, ignore order
        this.terms.addAll(terms);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        // ArrayList matches contains all terms that matched with given prefix
        List<CharSequence> matches = new ArrayList<>();

        // Scan through the whole ArrayList terms and add terms that start with given prefix to ArrayList matches
        for (CharSequence term : terms) {
            if (Autocomplete.isPrefixOf(prefix, term)) {
                matches.add(term);
            }
        }

        return matches;
    }
}

// #addAll
//      Time = O(N) --- Worst

// #allMatches
//      Time = O(N) --- Worst

