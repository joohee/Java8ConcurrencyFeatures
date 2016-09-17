package net.joey.algorithm;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * Created with Java8ConcurrencyFeatures.
 * User: neigie
 * Date: 2016. 9. 7.
 * Time: 14:54
 * <p>
 * BinaryHeap 구현체입니다.
 * 최대값/최소값을 찾는데 특화되어 있습니다.
 * <p>
 * 1. insert : 마지막에 원소를 넣고 parent와  recursive하게 비교합니다.
 * 1) min heap : parent.value > child.value then change value.
 * 2) max heap : parent.value < child.value then change value.
 * <p>
 * 2. delete(min/max) : 최초에 정의할 때 이미 min/max heap이 결정되어 있으므로 delete시에는 root 원소를 반환합니다
 * 이후 가장 마지막에 있는 원소를 root 자리에 넣고, children과 값을 비교합니다.
 * - child index 1 : parent index * 2
 * - child index 2 : parent index * 2 + 1 (or child index 1 + 1)
 * <p>
 * 1) min heap
 *  1> child index1.value < parent index.value
 *      1-1> child index2.value < parent_index.value
 *      -> 둘 중 작은 값과 바꾸고 반복한다
 *  1-2> child index2.value > parent_index.value
 *      -> child index1.value와 바꾸고 parent를 child index1로 변경하여 반복한다.
 *  2> child index1.value > parent index.value
 *      2-1> child index2.value < parent_index.value
 *      -> child index2.value와 바꾸고 parent를 child index2로 변경하여 반복한다.
 *      2-2> child index2.value > parent_index.value
 *      -> 변경할 필요 없이 끝낸다.
 * <p>
 * 2) max heap
 * - min heap과 방식을 동일하되 큰 값으로 변경한다.
 *
 * @see https://ko.wikipedia.org/wiki/%ED%9E%99_(%EC%9E%90%EB%A3%8C_%EA%B5%AC%EC%A1%B0)
 */
@Data
@Slf4j
public class BinaryHeapRaw {
    private int maxSize = 100;
    private Object[] heap = new Object[maxSize];
    private int size = 0;
    private Comparator<Object> comparator;

    BinaryHeapRaw(Comparator<Object> comparator) {
        this.comparator = comparator;
    }

    void delete() throws InterruptedException {
        // root 값이 min 값이므로 제거후 재정렬 한다
        if (size == 0)
            return;

        int rootIndex = 1;
        Object rootValue = heap[rootIndex];
        heap[rootIndex] = heap[size--];
        log.info("pop: {}", rootValue);

        // rearrange
        rearrange(rootIndex);
        printHeap();
    }

    private void rearrange(int parentIndex) {
        int leftChildIndex = parentIndex * 2;
        int rightChildIndex = leftChildIndex + 1;

        if (leftChildIndex > size) return;

        if (comparator.compare(heap[parentIndex], heap[leftChildIndex]) >= 0) {
            if (comparator.compare(heap[parentIndex], heap[rightChildIndex]) >= 0) {
                swap(parentIndex, comparator.compare(heap[leftChildIndex], heap[rightChildIndex]) >= 0 ? rightChildIndex : leftChildIndex);
            } else {
                swap(parentIndex, leftChildIndex);
            }
        } else if (comparator.compare(heap[parentIndex], heap[rightChildIndex]) >= 0) {
            swap(parentIndex, rightChildIndex);
        }
        // recursive
        rearrange(leftChildIndex);
    }

    void insert(Integer target) throws InterruptedException {
        if (size > maxSize - 1) {
            throw new ArrayIndexOutOfBoundsException("heap is full.");
        }

        heap[++size] = target;

        int childIndex = size;
        int parentIndex = childIndex / 2;
        changeByComparator(parentIndex, childIndex);

        printHeap();

    }

    private void changeByComparator(int parentIndex, int childIndex) {
        // 부모를 보면서 값이 작은지 큰지 비교하여 교체
        if (childIndex == 1) return;

        if (this.comparator.compare(heap[parentIndex], heap[childIndex]) >= 0) {
            swap(parentIndex, childIndex);
            changeByComparator(parentIndex / 2, parentIndex);
        }
    }

    private void swap(int parentIndex, int childIndex) {
        Object tmpValue = heap[parentIndex];
        heap[parentIndex] = heap[childIndex];
        heap[childIndex] = tmpValue;
    }

    private void printHeap() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);

        for (int i = 1; i <= size; i++) {
            log.info("heap[{}] = {}", i, heap[i]);
        }
        log.info("---------------------------------");
    }
}
