package com.keyin.algorithms.sorts;

import com.keyin.algorithms.sorts.test.MarkedInteger;

import java.util.Random;

/**
 * Quicksort is a sorting algorithm which, on average, makes O(n*log n) comparisons to sort
 * n items. In the worst case, it makes O(n^2) comparisons, though this behavior is
 * rare. Quicksort is often faster in practice than other algorithms. 
 * <p>
 * Family: Divide and conquer.<br> 
 * Space: In-place.<br>
 * Stable: False.<br>
 * <p>
 * Average case = O(n*log n)<br>
 * Worst case = O(n^2)<br>
 * Best case = O(n) [three-way partition and equal keys]<br>
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Quick_sort">Quicksort (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class QuickSort<T extends Comparable<T>> {

    private static final Random RAND = new Random();

    public static void quickSort(int low, int high, Integer[] target) {
        if(low >= high){
            return;
        }
        int i=low, j=high ;
        Integer p=target[low];
        while (i<j){

            while (i<j &&target[j].compareTo(p) >=0 ){
                j--;
            }
            while (i<j&&target[i].compareTo(p) <=0 ){
                i++;
            }
            swap( i, j, target);
        }
        swap(low, i, target);
        quickSort(low, i, target);
        quickSort(i+1, high,target);

    }

    public static enum PIVOT_TYPE {
        FIRST, MIDDLE, RANDOM
    }

    public static PIVOT_TYPE type = PIVOT_TYPE.RANDOM;

    private QuickSort() { }

    public static <T extends Comparable<T>> T[] sort(PIVOT_TYPE pivotType, T[] unsorted) {
        int pivot = 0;
        if (pivotType == PIVOT_TYPE.MIDDLE) {
            pivot = unsorted.length/2;
        } else if (pivotType == PIVOT_TYPE.RANDOM) {
            pivot = getRandom(unsorted.length);  
        }
        sort(pivot, 0, unsorted.length - 1, unsorted);
        return unsorted;
    }

    private static <T extends Comparable<T>> void sort(int index, int start, int finish, T[] unsorted) {
        int pivotIndex = start + index;
        T pivot = unsorted[pivotIndex];
        int s = start;
        int f = finish;
        while (s <= f) {
            while (unsorted[s].compareTo(pivot) < 0)
                s++;
            while (unsorted[f].compareTo(pivot) > 0)
                f--;
            if (s <= f) {
                swap(s, f, unsorted);
                s++;
                f--;
            }
        }
        if (start < f) {
            pivotIndex = getRandom((f - start) + 1);
            sort(pivotIndex, start, f, unsorted);
        }
        if (s < finish) {
            pivotIndex = getRandom((finish - s) + 1);
            sort(pivotIndex, s, finish, unsorted);
        }
    }


//    public static void quickSort(int low, int high, MarkedInteger[] target) {
//        if (low > high) {
//            return;
//        }
//
//        Integer p = target[low].num;
//        // p 是第一个，所以i+1是肯定的
//        int i = low;
//        int j = high;
//
//        // 跳出循环的 临界条件是 i==j
//        while (i < j) {
//            // 越界时候， i=j,且 target[i]>p
//            while (i < j && target[j].num.compareTo(p) >= 0) {
//                j--;
//            }
//            while (i < j && target[i].num.compareTo(p) <= 0) {
//                i++;
//            }
//
//
//            swap(i, j, target);
//
//        }
//
//        target[low].label="Y";
//        swap(low, i , target);
//        int s=1;
//        quickSort(low, i-1, target);
//        quickSort(i+1, high, target);
//    }




    private static final int getRandom(int length) {
        if (type == PIVOT_TYPE.RANDOM && length > 0)
            return RAND.nextInt(length);
        if (type == PIVOT_TYPE.FIRST && length > 0)
            return 0;
        return length / 2;
    }

    private static <T extends Comparable<T>> void swap(int index1, int index2, T[] unsorted) {
        T index2Element = unsorted[index1];
        unsorted[index1] = unsorted[index2];
        unsorted[index2] = index2Element;
    }
    private static void swap(int index1, int index2, MarkedInteger[] unsorted) {
        MarkedInteger index2Element = unsorted[index1];
        unsorted[index1] = unsorted[index2];
        unsorted[index2] = index2Element;
    }
}
