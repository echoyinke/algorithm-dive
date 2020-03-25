package com.keyin.algorithms.sorts;

import java.util.Arrays;

/**
 * Merge sort is an O(n log n) comparison-based sorting algorithm. Most
 * implementations produce a stable sort, which means that the implementation
 * preserves the input order of equal elements in the sorted output. 
 * <p>
 * Family: Merging.<br>
 * Space: In-place.<br>
 * Stable: True.<br>
 * <p>
 * Average case = O(n*log n)<br>
 * Worst case = O(n*log n)<br>
 * Best case = O(n*log n)<br>
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Merge_sort">Merge Sort (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class MergeSort<T extends Comparable<T>> {

    public static enum SPACE_TYPE { IN_PLACE, NOT_IN_PLACE }

    private MergeSort() { }

    public static <T extends Comparable<T>> T[] sort(SPACE_TYPE type, T[] unsorted) {
        sort(type, 0, unsorted.length, unsorted);
        return unsorted;
    }

    private static <T extends Comparable<T>> void sort(SPACE_TYPE type, int start, int length, T[] unsorted) {
        if (length > 2) {
            int aLength = (int) Math.floor(length / 2);
            int bLength = length - aLength;
            sort(type, start, aLength, unsorted);
            sort(type, start + aLength, bLength, unsorted);
            if (type == SPACE_TYPE.IN_PLACE)
                mergeInPlace(start, aLength, start + aLength, bLength, unsorted);
            else
                mergeWithExtraStorage(start, aLength, start + aLength, bLength, unsorted);
        } else if (length == 2) {
            T e = unsorted[start + 1];
            if (e.compareTo(unsorted[start]) < 0) {
                unsorted[start + 1] = unsorted[start];
                unsorted[start] = e;
            }
        }
    }



    private static <T extends Comparable<T>> void mergeInPlace(int aStart, int aLength, int bStart, int bLength, T[] unsorted) {
        int i = aStart;
        int j = bStart;
        int aSize = aStart + aLength;
        int bSize = bStart + bLength;
        while (i < aSize && j < bSize) {
            T a = unsorted[i];
            T b = unsorted[j];
            if (b.compareTo(a) < 0) {
                // Shift everything to the right one spot
                System.arraycopy(unsorted, i, unsorted, i+1, j-i);
                unsorted[i] = b;
                i++;
                j++;
                aSize++;
            } else {
                i++;
            }
        }
    }

    private static <T extends Comparable<T>> void mergeWithExtraStorage(int aStart, int aLength, int bStart, int bLength, T[] unsorted) {
        int count = 0;
        T[] output = (T[]) new Comparable[aLength + bLength];
        int i = aStart;
        int j = bStart;
        int aSize = aStart + aLength;
        int bSize = bStart + bLength;
        while (i < aSize || j < bSize) {
            T a = null;
            if (i < aSize) {
                a = unsorted[i];
            }
            T b = null;
            if (j < bSize) {
                b = unsorted[j];
            }
            if (a != null && b == null) {
                output[count++] = a;
                i++;
            } else if (b != null && a == null) {
                output[count++] = b;
                j++;
            } else if (b != null && b.compareTo(a) <= 0) {
                output[count++] = b;
                j++;
            } else {
                output[count++] = a;
                i++;
            }
        }
        int x = 0;
        int size = aStart + aLength + bLength;
        for (int y = aStart; y < size; y++) {
            unsorted[y] = output[x++];
        }
    }

    public static  Integer[] mergeSort(Integer[] target){
        mergeSortAlgorithm(target, 0, target.length-1);
        return target;
    }

    private static void mergeSortAlgorithm(Integer[] target, int start, int end) {
        if (start<end) {
         return;
        }
            int mid = (end+start)/2;
            mergeSortAlgorithm(target, start, mid);
            mergeSortAlgorithm(target, mid+1, end);
            merge(target, start, mid, end);


    }

    private static void merge(Integer[] target, int start, int mid, int end) {
        Integer[] left = Arrays.copyOfRange(target, start, mid+1);
        Integer[] right = Arrays.copyOfRange(target, mid+1, end+1);
        int i=0,j=0,k=start;
        while(i< left.length && j< right.length){
            if(left[i]<right[j]){
                target[k]=left[i];
                i++;
                k++;
            } else {
                target[k]=right[j];
                j++;
                k++;
            }
        }
        while (i<left.length){
            target[k]=left[i];
            i++;
            k++;
        }
        while (j<right.length){
            target[k]=right[j];
            j++;
            k++;
        }





    }


//    private static void mergeSortAlgorithm(Integer[] target, int start, int end){
//        if (start < end) {
//            int mid = (start+end)/2;
//            // 递归左子数组做归并排序
//            mergeSortAlgorithm(target, start, mid);
//            // 递归右子数组做归并排序
//            mergeSortAlgorithm(target, mid+1, end);
//            // 归并数组，最终使得A[start,..,q]均小于A[q,end]
//            merge(target, start, mid, end);
//        }
//    }

//    private static  void merge(Integer[] target, int start, int mid, int end){
//
//        Integer[] leftPart ;
//        Integer[] rightPart ;
//        //赋值,初始化左边数组和右边数组
//        leftPart=  Arrays.copyOfRange(target, start, mid+1);
//        rightPart=  Arrays.copyOfRange(target, mid+1, end+1);
//
//        // 对于左半部分和右半部分和原来的数组中元素对比，按照规则set对应值。
//        int i=0,j=0, k=start;
//        while(i<leftPart.length && j<rightPart.length){
//            if(leftPart[i]<=rightPart[j]) {
//                target[k]= leftPart[i];
//                k++;
//                i++;
//            } else {
//                target[k]= rightPart[j];
//                k++;
//                j++;
//            }
//
//        }
//        while (i < leftPart.length) {
//            target[k] = leftPart[i];
//            i++;
//            k++;
//        }
//        while (j < rightPart.length) {
//            target[k] = rightPart[j];
//            k++;
//            j++;
//        }
//
//    }




}
