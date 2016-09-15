package net.joey.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

/**
 * Date: 2016. 9. 14.
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class BinaryHeapRawMain {

    public static void main(String[] args) throws InterruptedException {
        // scenario : 13->8->2->3->5->1->4->9->6 순서대로 넣어본다

        Comparator cMin = (o1, o2) -> (int) o1 < (int) o2 ? -1 : 1;     // 앞의 것이 작으면 -1
        Comparator cMax = (o1, o2) -> (int) o1 > (int) o2 ? -1 : 1;     // 앞의 것이 크면 -1

        BinaryHeapRaw binaryMinHeap = new BinaryHeapRaw(cMin);
        binaryMinHeap.insert(13);
        binaryMinHeap.insert(8);
        binaryMinHeap.insert(2);
        binaryMinHeap.insert(3);
        binaryMinHeap.insert(5);
        binaryMinHeap.insert(1);
        binaryMinHeap.insert(4);
        binaryMinHeap.insert(9);
        binaryMinHeap.insert(6);

        while (binaryMinHeap.getSize() > 0) {
            binaryMinHeap.delete();
        }

        BinaryHeapRaw binaryMaxHeap = new BinaryHeapRaw(cMax);
        binaryMaxHeap.insert(13);
        binaryMaxHeap.insert(8);
        binaryMaxHeap.insert(2);
        binaryMaxHeap.insert(3);
        binaryMaxHeap.insert(5);
        binaryMaxHeap.insert(1);
        binaryMaxHeap.insert(4);
        binaryMaxHeap.insert(9);
        binaryMaxHeap.insert(6);

        while (binaryMaxHeap.getSize() > 0) {
            binaryMaxHeap.delete();
        }
    }

}
