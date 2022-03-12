package autocomplete;

import java.util.*;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        // add all terms into TernarySearchTree
        for (CharSequence term : terms) {
            if (overallRoot == null) {
                overallRoot = new Node(term.charAt(0));
                overallRoot = overallRoot.insertWord(overallRoot, term, 1);
            } else {
                overallRoot = overallRoot.insertWord(overallRoot, term, 0);
            }
        }
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        return overallRoot.findAllMatches(overallRoot, prefix);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node    left;
        private Node    mid;
        private Node    right;

        public Node(char data) {
            this.data   = data;
            this.isTerm = false;
            this.left   = null;
            this.mid    = null;
            this.right  = null;
        }

        // Logic: when inserting each character, the character is ONLY be counted IF AND ONLY IF it goes down(middle branch)
        public Node insertWord(Node node, CharSequence word, int index) {
            Node currNode = node;

            int idx = index;
            while (idx < word.length()) {
                char character = word.charAt(idx);

                if (character > currNode.data) {

                    if (currNode.right == null) {
                        currNode.right = new Node(character);
                    }
                    currNode = currNode.right;

                } else if (character < currNode.data) {

                    if (currNode.left == null) {
                        currNode.left = new Node(character);
                    }
                    currNode = currNode.left;

                } else {

                    if (currNode.mid == null) {
                        currNode.mid = new Node(character);
                    }
                    currNode = currNode.mid;
                    idx++;

                }
            }

            currNode.isTerm = true;
            return node;
        }

        public List<CharSequence> findAllMatches(Node node, CharSequence prefix) {
            ArrayList matches = new ArrayList<>();
            Node nodeAtPrefix = nodeAtPrefix(node, prefix);                 // find Node of prefix's last character

            // no matches for given prefix
            if (nodeAtPrefix == null) {
                return matches;
            }

            Stack<Node> nodes = new Stack<>();
            Stack<String> strings = new Stack<>();

            strings.push(prefix.toString());                                // starting node
            nodes.add(nodeAtPrefix);                                        // string(data) of starting node

            while (!nodes.empty() || !strings.empty()) {
                Node currNode = nodes.pop();                                // current node
                String currString = strings.pop();                          // string(data) of current node

                // end of term => adds to resulting matches array
                if (currNode.isTerm) {
                    matches.add(currString);
                }

                // mid is not empty => add mid-child to stack, child #1
                if (currNode.mid != null) {
                    nodes.push(currNode.mid);
                    strings.push(currString + currNode.mid.data);       // only count char if we go down mid-branch
                }

                // left is not empty => add left-child to stack, child #2
                if (currNode.left != null) {
                    nodes.push(currNode.left);
                    strings.push(currString);                               // go left  => don't count char
                }

                // right is not empty => add right-child to stack, child #3
                if (currNode.right != null) {
                    nodes.push(currNode.right);
                    strings.push(currString);                               // go right  => don't count char
                }
            }

            return matches;
        }

        // node that has data = last char in prefix
        public Node nodeAtPrefix(Node node, CharSequence prefix) {
            Node currNode = node;

            // empty prefix => short circuit
            if (prefix.isEmpty()) {
                return null;
            }

            int idx = 0;
            while (idx < prefix.length()) {

                // no matches
                if (currNode == null) {
                    return null;
                }

                char character = prefix.charAt(idx);
                if (character > currNode.data) {
                    currNode = currNode.right;
                } else if (character < currNode.data) {
                    currNode = currNode.left;
                } else {
                    currNode = currNode.mid;
                    idx++;
                }
            }

            return currNode;
        }
    }
}

//                                              [alpha  delta  do cats dodgy pilot dog]
//
//                                                a
//                                                l           d
//                                                p     c     e                 p
//                                                h     a     l   o(*)          i
//                                                a(*)  t     t   d             l
//                                                     s(*)  a(*) g     g(*)    o
//                                                                y(*)          t(*)