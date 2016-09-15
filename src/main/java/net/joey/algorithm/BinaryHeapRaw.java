package net.joey.algorithm;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
public class BinaryHeapRaw {
    private int maxSize = 100;
    private int[] heap = new int[maxSize];
    private int size = 0;
    private Comparator<Integer> comparator;

    BinaryHeapRaw(Comparator<Integer> comparator) {
        this.comparator = comparator;
    }

    public void delete() throws InterruptedException {
        // root 값이 min 값이므로 제거후 recursive하게 재정렬 한다
        if (size == 0)
            return;

        int rootIndex = 1;
        int rootValue = heap[rootIndex];
        heap[rootIndex] = heap[size];
        size--;

        log.info("pop: {}", rootValue);

        int parentIndex = rootIndex;
        // rearrange
        rearrange(parentIndex);

        printHeap();

    }

    public void rearrange(int parentIndex) {
        int leftChildIndex = parentIndex * 2;
        int rightChildIndex = leftChildIndex + 1;

        if (leftChildIndex > size) return;

        if (this.comparator.compare(heap[parentIndex], heap[leftChildIndex]) >= 0) {
            if (this.comparator.compare(heap[parentIndex], heap[rightChildIndex]) >= 0) {
                swap(parentIndex, this.comparator.compare(heap[leftChildIndex], heap[rightChildIndex]) >= 0 ? rightChildIndex : leftChildIndex);
            } else {
                swap(parentIndex, leftChildIndex);
            }
        } else if (this.comparator.compare(heap[parentIndex], heap[rightChildIndex]) >= 0) {
            swap(parentIndex, rightChildIndex);
        }
        // recursive
        rearrange(leftChildIndex);
    }

    public void insert(Integer target) throws InterruptedException {
        if (size > maxSize - 1) {
            throw new ArrayIndexOutOfBoundsException("heap full.");
        }

        heap[++size] = target;

        int childIndex = size;
        int parentIndex = childIndex / 2;
        changeByComparator(parentIndex, childIndex);

        printHeap();

    }

    public void changeByComparator(int parentIndex, int childIndex) {
        // 부모를 보면서 값이 작은지 큰지 비교하여 교체
        if (childIndex == 1) return;

        if (this.comparator.compare(heap[parentIndex], heap[childIndex]) >= 0) {
            swap(parentIndex, childIndex);
            changeByComparator(parentIndex / 2, parentIndex);
        }
    }

    public void swap(int parentIndex, int childIndex) {
        int tmpValue = heap[parentIndex];
        heap[parentIndex] = heap[childIndex];
        heap[childIndex] = tmpValue;
    }

    private void printHeap() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);

        for (int i = 1; i <= size; i++) {
            log.info("heap[{}] = {}", i, heap[i]);
        }
        log.info("---------------------------------");
    }
}
