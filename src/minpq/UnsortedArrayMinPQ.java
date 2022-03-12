package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }

        // TODO: Replace with your code
        items.add(new PriorityNode(item, priority));
    }

    @Override
    public boolean contains(T item) {
        // TODO: Replace with your code
        for (PriorityNode node : items) {
            if (node.item() == item) {
                return true;
            }
        }

        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }

        // TODO: Replace with your code
        return (T) findSmallestNode().item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }

        // TODO: Replace with your code
        PriorityNode smallestItem = findSmallestNode();
        items.remove(smallestItem);

        return (T) smallestItem.item();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }

        // TODO: Replace with your code
        for (PriorityNode node : items) {
            if (node.item() == item) {
                node.setPriority(priority);
                break;
            }
        }
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        return items.size();
    }

    private PriorityNode findSmallestNode() {
        PriorityNode smallestNode = items.get(0);

        for (PriorityNode node : items) {
            if (node.priority() < smallestNode.priority()) {
                smallestNode = node;
            }
        }

        return smallestNode;
    }
}

// removeMin()      --- O(N)
// changePriority() --- O(N)