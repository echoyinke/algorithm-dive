package com.keyin.algorithms.search;

import org.springframework.util.Assert;

/**
 * In computer science, binary search, also known as half-interval search or logarithmic search, is a search algorithm that finds the position of a target value within a sorted array. Binary search 
 * compares the target value to the middle element of the array; if they are unequal, the half in which the target cannot lie is eliminated and the search continues on the remaining half until it is 
 * successful or the remaining half is empty.
 * <p>
 * Worst-case performance      O(log n)<br>
 * Best-case performance       O(1)<br>
 * Average performance         O(log n)<br>
 * Worst-case space complexity O(1)<br>
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Binary_search_algorithm">Binary Search (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class BinarySearch {

    private static final int SWITCH_TO_BRUTE_FORCE = 200;

    private static int[] sorted = null;

    // Assuming the array is sorted
    public static final int find(int value, int[] array, boolean optimize) {
        BinarySearch.sorted = array;
        try {
            return recursiveFind(value, 0, BinarySearch.sorted.length - 1, optimize);
        } finally {
            BinarySearch.sorted = null;
        }
    }
    // 旋转有序数组后的二分查找。
    public static int biSer(int target, int[] arr, int low, int high) {
        if (low>=high) {
            return high;
        }

        int mid = low +(high-low+1)/2;


        // 1. 特殊情况，因为有序，low的值会大于
        if (arr[low] > arr[mid] ) {
            if(target>=arr[low]) {
                return biSer(target,arr,low, mid-1);
            } else {
                return biSer(target, arr, mid, high);
            }

        }else {
            if(target<arr[mid]) {
                return biSer(target,arr,low, mid-1);
            }else {
                return biSer(target, arr, mid, high);
            }
        }

    }

    public static int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length-1;
        while (low<=high) {
            int mid = low+((high-low)>>1);
            int midVal = arr[mid];
            if (target==midVal) {
                return mid;
            }
            if (target<midVal) {
                high=mid-1;
            }
            if (target>midVal) {
                low=mid+1;
            }
        }
        return -1;
    }

    private static int recursiveFind(int value, int start, int end, boolean optimize) {
        if (start == end) {
            int lastValue = sorted[start]; // start==end
            if (value == lastValue)
                return start; // start==end
            return Integer.MAX_VALUE;
        }

        final int low = start;
        final int high = end + 1; // zero indexed, so add one.
        final int middle = low + ((high - low) / 2);

        final int middleValue = sorted[middle];
        if (value == middleValue)
            return middle;
        if (value > middleValue) {
            if (optimize && (end - middle) <= SWITCH_TO_BRUTE_FORCE)
                return linearSearch(value, middle + 1, end);
            return recursiveFind(value, middle + 1, end, optimize);
        }
        if (optimize && (end - middle) <= SWITCH_TO_BRUTE_FORCE)
            return linearSearch(value, start, middle - 1);
        return recursiveFind(value, start, middle - 1, optimize);
    }

    private static final int linearSearch(int value, int start, int end) {
        for (int i = start; i <= end; i++) {
            int iValue = sorted[i];
            if (value == iValue)
                return i;
        }
        return Integer.MAX_VALUE;
    }
    
    
    
    public  static int bsFromScratch(int[] arr, int lowIndex, int highIndex, int target) {
        int midIndex = (lowIndex + highIndex)>>1;
        Assert.isTrue(midIndex>=lowIndex, "midIndex should greater than 0");
        Assert.isTrue(midIndex<= highIndex, "midIndex should lower than highIndex");

        int midVal = arr[midIndex];
        if (target <= midVal) {
            highIndex = midIndex;
        } else {
            lowIndex = midIndex;
        }
        return midVal == target ?  midIndex :  bsFromScratch(arr, lowIndex, highIndex, target);
    }
    public  static int bsFromScratch(int[] arr, int target) {
        int lowIndex = 0;
        int highIndex = arr.length - 1;
        return bsFromScratch(arr, lowIndex, highIndex, target);
    }
    public  static int bsFromScratch2(int[] arr, int target) {
        int lowIndex = 0;
        int highIndex = arr.length - 1;
        while (lowIndex<=highIndex) {
            int midIndex = (highIndex - lowIndex)/2;
            if (target == arr[midIndex]) {
                return midIndex;
            }
            if (arr[midIndex] < target) {
                lowIndex = midIndex;
            }
            if (arr[midIndex] > target) {
                highIndex = midIndex;
            }
        }
        return -1;
    }
    /*
    * 如上述代码所示，我们根据 nums[mid] 与 target 的大小关系，可以得知 target 是在 mid 的左边还是右边，从而来调整左右边界 lo 和 hi。

但是，对于旋转数组，我们无法直接根据 nums[mid] 与 target 的大小关系来判断 target 是在 mid 的左边还是右边，因此需要「分段讨论」。于是方法三呼之欲出！

方法三：先根据 nums[mid] 与 nums[lo] 的关系判断 mid 是在左段还是右段，接下来再判断 target 是在 mid 的左边还是右边，从而来调整左右边界 lo 和 hi。

    * */

    public int search1(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1, mid = 0;
        while (lo <= hi) {
            mid = lo + ((hi - lo) >> 1);
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }

    public static int search2FromScratch(int[] nums, int target) {
        
        int low = 0, mid=0;
        int high = nums.length - 1;
        
        
        while(low <= high) {
             mid = low + (high - low)/2;
            if (target == mid) {
                return nums[mid];
            }
            if (target > mid) {
                low = mid;
            }
            if (target < mid) {
                high = mid;
            }
        }
        return -1;

    }
    
    /*
    * 不管mid是啥，总归有半段是有序的，然后根据有序那半边就可以判断出后续的一个分割方向，就是说二分，你只需要知道不在这一半，
    * 就肯定在另外一半
    * */
    public static int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1, mid = 0;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            // 先根据 nums[mid] 与 nums[lo] 的关系判断 mid 是在左段还是右段
            // >=则是在左半段
            if (nums[mid] >= nums[lo]) {
                // 再判断 target 是在 mid 的左边还是右边，从而调整左右边界 lo 和 hi
                if (target >= nums[lo] && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
                
            } else {
                // 不然mid在就是右半段，然后至少右半段是递增的，看下target是否在[mid, high]之间
                if (target > nums[mid] && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }

    
    /*
    * 其实思想是一样的，这里不过是巧妙的先设置最大值，然后后比较，最中还是说让搜索通过排除法进入目标段。
    * */
    public static int search3(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            // 先根据 nums[0] 与 target 的关系判断目标值是在左半段还是右半段
            if (target >= nums[0]) {
                // 目标值在左半段时，若 mid 在右半段，则将 mid 索引的值改成 inf
                if (nums[mid] < nums[0]) {
                    nums[mid] = Integer.MAX_VALUE;
                }
            } else {
                // 目标值在右半段时，若 mid 在左半段，则将 mid 索引的值改成 -inf
                if (nums[mid] >= nums[0]) {
                    nums[mid] = Integer.MIN_VALUE;
                }
            }

            if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }
    


    

    public static void main(String[] args) {
        int res = binarySearch(new int[] {1,2,3,5,7,9}, 1);
        System.out.println(res);
        int res2 = bsFromScratch2(new int[] {1,2,3,5,7,9}, 1);
        System.out.println(res2);
        int res3 = search2FromScratch(new int[] {1,2,3,5,7,9}, 1);
        System.out.println(res3);

        int res4 = search3(new int[] {  7, 8, 9, 0, 1, 2,3, 4, 5, 6}, 9);
        System.out.println(res4);
        
        

    }
}
