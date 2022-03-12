package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of item-priority pairs.
     */
    private final List<PriorityNode<T>> items;
    /**
     * {@link Map} of each item to its associated index in the {@code items} heap.
     */
    private final Map<T, Integer> itemToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }

        // TODO: Replace with your code
        itemToIndex.put(item, size());
        items.add(new PriorityNode<>(item, priority));
        swimsUp(size() - 1);
    }

    @Override
    public boolean contains(T item) {
        // TODO: Replace with your code
        return itemToIndex.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }

        // TODO: Replace with your code
        return items.get(0).item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }

        // TODO: Replace with your code
        T smallestItem = peekMin();         // get smallest item
        swap(0, size() - 1);                // swaps with item at end of arrayList

        items.remove(size() - 1);     // pop node at tail
        itemToIndex.remove(smallestItem);   // removes the smallest item key in the hashMap

        sinksDown(0);               // sinks down new root until valid
        return smallestItem;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }

        // TODO: Replace with your code
        int itemIdx = itemToIndex.get(item);                                    // get item idx
        swap(itemIdx, size() - 1);                                              // swaps itemToDelete with last item in array
        items.remove(size() - 1);                                         // removes the item at end of array
        sinksDown(itemIdx);                                                     // sinksDown the item we just swapped until valid
        itemToIndex.remove(item);                                               // removes item with old priority from hashMap
        add(item, priority);                                                    // add same item with NEW priority
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        return items.size();
    }

    private void swimsUp(int item_index) {
        int parentIdx; Boolean valid = true;

        while (valid) {
            parentIdx = getParentIdx(item_index);

            if (isRoot(item_index) || items.get(parentIdx).priority() <= items.get(item_index).priority()) {
                valid = false;
            } else {
                swap(parentIdx, item_index);
                item_index = parentIdx;
            }
        }
    }

    private  void sinksDown(int item_idx) {
        int leftIdx; int rightIdx; int chosen_idx; Boolean valid = true;

        while (valid) {
            leftIdx  = getLeftChildIdx(item_idx);
            rightIdx = getRightChildIdx(item_idx);

            if (!accessible(leftIdx) && !accessible(rightIdx)                                                                                                                                           ||
                 accessible(leftIdx) &&  accessible(rightIdx) && items.get(item_idx).priority() <= items.get(leftIdx).priority() && items.get(item_idx).priority() <= items.get(rightIdx).priority()    ||
                !accessible(leftIdx) &&  accessible(rightIdx) && items.get(item_idx).priority() <= items.get(rightIdx).priority()                                                                       ||
                 accessible(leftIdx) && !accessible(rightIdx) && items.get(item_idx).priority() <= items.get(leftIdx).priority()) {
                valid = false;
            } else {
                if (!accessible(leftIdx) || !accessible(rightIdx)) {
                    chosen_idx = !accessible(leftIdx) ? rightIdx : leftIdx;
                } else {
                    chosen_idx = items.get(leftIdx).priority() < items.get(rightIdx).priority() ? leftIdx : rightIdx;
                }

                swap(item_idx, chosen_idx);
                item_idx = chosen_idx;
            }
        }
    }

    private void swap(int idx1, int idx2) {
        itemToIndex.put(items.get(idx1).item(), idx2);
        itemToIndex.put(items.get(idx2).item(), idx1);

        PriorityNode temp = items.get(idx1);
        items.set(idx1, items.get(idx2));
        items.set(idx2, temp);
    }

    private int getParentIdx(int idx) {
        return (idx - 1) / 2;
    }

    private int getLeftChildIdx(int idx) {
        return 2 * idx + 1;
    }

    private int getRightChildIdx(int idx) {
        return 2 * idx + 2;
    }

    private boolean isRoot(int idx) {
        return idx == 0;
    }

    private boolean accessible(int idx) {
        return idx >= 0 && idx < size();
    }
}

// removeMin()      --- O(logN)
// changePriority() --- O(logN)