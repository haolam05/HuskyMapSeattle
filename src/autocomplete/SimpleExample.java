package autocomplete;
import java.util.*;

public class SimpleExample {
    public static void main(String[] args) {
        List<CharSequence> terms = new ArrayList<>();
        terms.add("alpha");
        terms.add("delta");
        terms.add("do");
        terms.add("cats");
        terms.add("dodgy");
        terms.add("pilot");
        terms.add("dog");

        // Choose your Autocomplete implementation.
        //      TreeSetAutocomplete                     (default)
        //      SequentialSearchAutocomplete
        //      BinarySearchAutocomplete
        //      TernarySearchTreeAutocomplete
        Autocomplete autocomplete = new TreeSetAutocomplete();
        autocomplete.addAll(terms);

        // Choose your prefix string.
        CharSequence prefix = "do";
        List<CharSequence> matches = autocomplete.allMatches(prefix);
        for (CharSequence match : matches) {
            System.out.println(match);
        }
    }
}



//                                 DOES CONSIDER CONSTANT NUMBER OF TERMS
// 1: fastest
// 4: slowest
// ?: unsure(difficult to tell from graphs)
//                                  WORST CASE
// Color  | Implementations              | Prefix | #addAll     | #allMatches |
// --------------------------------------|--------|-------------|-------------|
// Purple | SequentialSearchAutocomplete | Sea    | 1 O(1)      | 4 O(N)      |
// Blue   | BinarySearchAutocomplete     |        | 3 O(NlogN)  | ? O(N)      |
// Black  | TernarySearchAutocomplete    |        | 4 O(N)      | ? O(N)      |
// Orange | TreeSetAutoComplete          |        | 2 O(logN)   | ? O(N)      |
// -------|------------------------------|--------|-------------|-------------|
// Purple | SequentialSearchAutocomplete | S      | 1 O(1)      | 4 O(N)      |
// Blue   | BinarySearchAutocomplete     |        | 3 O(NlogN)  | 2 O(N)      |
// Black  | TernarySearchAutocomplete    |        | 4 O(N)      | 3 O(N)      |
// Orange | TreeSetAutoComplete          |        | 2 O(logN)   | 1 O(N)      |
//

//                                 DOES NOT CONSIDER CONSTANT NUMBER OF TERMS
// 1: fastest
// 4: slowest
// ?: unsure(difficult to tell from graphs)
//                                  WORST CASE
// Color  | Implementations              | Prefix | #addAll     | #allMatches |
// --------------------------------------|--------|-------------|-------------|
// Purple | SequentialSearchAutocomplete | Sea    | 1 O(N)      | 4 O(N)      |
// Blue   | BinarySearchAutocomplete     |        | 3 O(N^2)    | ? O(N)      |
// Black  | TernarySearchAutocomplete    |        | 4 O(N^2)    | ? O(N)      |
// Orange | TreeSetAutoComplete          |        | 2 O(NlogN)  | ? O(N)      |
// -------|------------------------------|--------|-------------|-------------|
// Purple | SequentialSearchAutocomplete | S      | 1 O(N)      | 4 O(N)      |
// Blue   | BinarySearchAutocomplete     |        | 3 O(N^2)    | 2 O(N)      |
// Black  | TernarySearchAutocomplete    |        | 4 O(N^2)    | 3 O(N)      |
// Orange | TreeSetAutoComplete          |        | 2 O(NlogN)  | 1 O(N)      |
//
