// -------------------------------------------------------------------------

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.min;

/**
 *  This class contains static methods that implementing sorting of an array of numbers
 *  using different sort algorithms.
 *
 *  @author Hannah Foley
 *  @version HT 2020
 *
 *  References:
 *  https://www.geeksforgeeks.org/iterative-merge-sort/
 *  https://www.hackerearth.com/practice/algorithms/sorting/quick-sort/tutorial/
 *
 */

class SortComparison {

    /**
     * Sorts an array of doubles using InsertionSort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order.
     *
     */
    static double [] insertionSort (double a[]){
        double temp;

        //if the array is null, return the empty array
        if (a == null)
        {
            return a;
        }

        //swap elements with the element next to it if the element next to it is smaller
        for(int i = 1; i < a.length; i++)
        {
            for (int j = i; j > 0; j--)
            {
                if(a[j] < a[j-1])
                {
                    temp = a[j];
                    a[j] = a[j-1];
                    a[j-1] =  temp;
                }
            }
        }

        //return sorted array
        return a;
    }//end insertionsort

    /**
     * Sorts an array of doubles using Selection Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static double [] selectionSort (double a[]){
        //if the array is null, return the empty array
        if (a == null)
        {
            return a;
        }

        int n = a.length;

        //find smallest element in the remainder of the array and swap with current element
        for(int i = 0; i < n-1; i++)
        {
            int min_idx =  i;
            for(int j = i+1; j < n; j++)
            {
                if (a[j] < a[min_idx])
                {
                    min_idx = j;
                }
            }
            double temp = a[min_idx];
            a[min_idx] = a[i];
            a[i] = temp;
        }

        //return sorted array
        return a;
    }//end selectionsort

    /*
     * Sorts an array of doubles using Quick Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static double [] quickSort (double [] array){
        //if the array is null, return the empty array
        if (array == null || array.length <= 1)
        {
            return array;
        }

        //shuffle array to make sure that it's not already sorted
        shuffleArray(array);
        //sort
        quickSortRecursive(array, 0, array.length-1);

        //return sorted array
        return array;
    }//end quicksort

    public static void shuffleArray(double[] array)
    {
        Random random = ThreadLocalRandom.current();
        for(int i = array.length - 1; i > 0; i--)
        {
            int index = random.nextInt(i+1);
            double temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public static void quickSortRecursive(double[] arr, int low, int hi)
    {
        if(low < hi)
        {
            int pivot = partition(arr, low, hi);
            //LHS of pivot
            quickSortRecursive(arr, low, pivot-1);
            //RHS of pivot
            quickSortRecursive(arr, pivot+1, hi);
        }
    }

    public static void swap(double[] arr, int i, int j)
    {
        if(i >= 0 && j >= 0 && i < arr.length && j < arr.length)
        {
            double temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    private static int partition(double[] array, int low, int high)
    {
        int i = low + 1;

        //make the first element the pivot
        double pivot = array[low];

        for(int j = low + 1; j <= high; j++)
        {
            //put elements that are less than the pivot on the LHS of the pivot
            //and elements that are greater than on the RHS
            if(array[j] < pivot)
            {
                swap(array, i, j);
                i++;
            }
        }

        //put the pivot element in its proper place
        swap(array, low, i-1);

        //return position of pivot
        return i-1;
    }


    /*
     * Sorts an array of doubles using Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    /*
     * Sorts an array of doubles using iterative implementation of Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     *
     * @param a: An unsorted array of doubles.
     * @return after the method returns, the array must be in ascending sorted order.
     */
    static double[] mergeSortIterative (double a[]) {
        //if the array is null, return the empty array
        if (a == null)
        {
            return a;
        }

        int N = a.length;
        double [] aux = new double[N];
        for(int sz = 1; sz < N; sz = sz+sz)
        {
            for(int lo = 0; lo < N-sz; lo+= sz+sz)
            {
                merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
            }
        }
        return a;
    }//end mergesortIterative

    /**
     * Sorts an array of doubles using recursive implementation of Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     *
     * @param a: An unsorted array of doubles.
     * @return after the method returns, the array must be in ascending sorted order.
     */
    static double[] mergeSortRecursive (double a[]) {
        //if the array is null, return the empty array
        if(a == null)
        {
            return a;
        }
        double[] copy = new double[a.length];

        //send into recursive method
        mergeSortR(a, copy,0, a.length-1);

        //return sorted array
        return a;
    }//end mergeSortRecursive

    private static void mergeSortR(double[] a, double[] copy, int lo, int hi)
    {
        if(hi <= lo)
        {
            return;
        }
        int mid = lo + (hi - lo)/2;
        //recursively sort LHS
        mergeSortR(a, copy, lo, mid);
        //recursively sort RHS
        mergeSortR(a, copy,mid+1, hi);
        //merge the sorted halves
        merge(a, copy, lo, mid, hi);
    }

    private static void merge(double[] a, double [] copy, int lo, int mid, int high)
    {
        for(int k = lo; k <= high; k++)
        {
            copy[k] = a[k];
        }
        int i = lo;
        int j = mid + 1;

        for(int k = lo; k <= high; k++)
        {
            if(i > mid)
            {
                a[k] = copy[j++];
            }
            else if(j > high)
            {
                a[k] = copy[i++];
            }
            else if(a[j] < copy[i])
            {
                a[k] = copy[j++];
            }
            else
            {
                a[k] = copy[i++];
            }
        }
    }

}//end class


