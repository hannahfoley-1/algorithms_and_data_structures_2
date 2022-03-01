import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//Run time comparisons

/*
---------------------------------------------------------------------------
                        Insert | Selection | Quick | Merge Rec | Merge it |
----------------------------------------------------------------------------
numbers1000                 3ms |   4ms     |   1ms |     0ms  |   0ms     |
--------------------------------------------------------------------------
numbers1000Duplicates        8ms|  5ms      |   0ms  |   0ms   |  0ms      |
---------------------------------------------------------------------------
numbers10000                78ms|   33ms  |  3ms   |  3ms      |   4ms     |
----------------------------------------------------------------------------
nearlyOrdered1000           4ms |   5ms   |  0ms   |   0ms     |  1ms     |
---------------------------------------------------------------------------
numbersReverse1000          6ms |   4ms   |  0ms   |   0ms     |  0ms     |
---------------------------------------------------------------------------
numbersSorted1000           3ms |   4ms   |  1ms   |   0ms     |  0ms     |
---------------------------------------------------------------------------



a. Which of these sorting algorithms does the order of input have an impact on? Why?
Insertion sort will be worst affected if the order of the elements in the file is in reverse order.
This is because each element in the array will have to be moved the maximum number of times.
The smallest element will be at the very end of the array, so will move one index at a time all the way to the beginning of the array.
This will be the same then for the next smallest element which will move array length-1 indices.


b. Which algorithm has the biggest difference between the best and worst performance, based
on the type of input, for the input of size 1000? Why?
Insertion sort with 1000 random numbers takes on average 3 milliseconds. Insertion sort with 1000 numbers in reverse takes on average 6 milliseconds.
Again, a reverse order is the worst case input for the insertion sort sorting array.
This is because insertion sort moves all the elements that are less than the current element to the left of the current element.
However in a reverse order array, everything will be less than the current element and so each element will need to be moved.


c. Which algorithm has the best/worst scalability, i.e., the difference in performance time
based on the input size? Please consider only input files with random order for this answer.
Merge sort recursive went from sorting the 1000 element array in on average 0 milliseconds to also sorting the 10000 element array in on average 3 milliseconds.
This is very good in comparison to insertion sort went from sorting the 1000 element array in an average of 3 milliseconds to sorting the 10000 element array in 78 milliseconds on average.
This tells us that merge sort is a better option for very large input files as it won't take up a lot of run time.


d. Did you observe any difference between iterative and recursive implementations of merge sort?
Both versions of merge sort took 0 milliseconds to sort the array of size 1000.
However the recursive version took 3 milliseconds to sort the array of size 10000 and the iterative method took 4 milliseconds.
The iterative version will probably be slower in the long run as it involves nested for loops.


e. Which algorithm is the fastest for each of the 7 input files?
In my experiment, the recursive merge sort is the fastest sorting algorithm for all the files.
It sorts all the files in 0 milliseconds on average except for the file of size 10000.
Merge sort recursive also was the fastest algorithm at sorting the file of size 10000, at 3 milliseconds average.
Merge is a fast algorithm because it is a divide and conquer method, constantly splitting the work it has to perform down.

*/



//-------------------------------------------------------------------------
/**
 *  Test class for SortComparison.java
 *
 *  @author
 *  @version HT 2020
 */
@RunWith(JUnit4.class)
public class SortComparisonTest
{
    //~ Constructor ........................................................

    @Test
    public void testConstructor()
    {
        new SortComparison();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check that the methods work for empty arrays
     */
    @Test
    public void testEmpty()
    {
        double[] input = {};
        double[] result = {};

        SortComparison compare = new SortComparison();

        //test insertion sort
        double[] sorted = compare.insertionSort(input);
        assertArrayEquals("Checking insertion sort with empty array", result, sorted, 0);

        //test selection sort
        sorted = compare.selectionSort(input);
        assertArrayEquals("Checking selection sort with empty array", result, sorted, 0);

        //test merge sort
        sorted = compare.mergeSortRecursive(input);
        assertArrayEquals("Checking merge sort recursive with empty array", result, sorted, 0);

        //test merger sort iterative
        sorted = compare.mergeSortIterative(input);
        assertArrayEquals("Checking merge sort iterative with empty array", result, sorted, 0);

        //test quick sort
        sorted = compare.quickSort(input);
        assertArrayEquals("Checking quick sort with empty array", result, sorted, 0);
    }

    @Test
    public void testNull()
    {
        double[] input = null;
        double[] result = null;

        SortComparison compare = new SortComparison();

        //test insertion sort
        double[] sorted = compare.insertionSort(input);
        assertArrayEquals("Checking insertion sort with null array", result, sorted, 0);

        //test selection sort
        sorted = compare.selectionSort(input);
        assertArrayEquals("Checking selection sort with null array", result, sorted, 0);

        //test merge sort
        sorted = compare.mergeSortRecursive(input);
        assertArrayEquals("Checking merge sort recursive with null array", result, sorted, 0);

        //test merger sort iterative
        sorted = compare.mergeSortIterative(input);
        assertArrayEquals("Checking merge sort iterative with null array", result, sorted, 0);

        //test quick sort
        sorted = compare.quickSort(input);
        assertArrayEquals("Checking quick sort with null array", result, sorted, 0);


    }

    @Test
    public void testOneElement()
    {

        double [] input = {1};
        double [] result = {1};

        SortComparison compare = new SortComparison();

        //test insertion sort
        double[] sorted = compare.insertionSort(input);
        assertArrayEquals("Checking insertion sort with array with one element", result, sorted, 0);

        //test selection sort
        sorted = compare.selectionSort(input);
        assertArrayEquals("Checking selection sort with array with one element", result, sorted, 0);

        //test merge sort
        sorted = compare.mergeSortRecursive(input);
        assertArrayEquals("Checking merge sort recursive with array with one element", result, sorted, 0);

        //test merger sort iterative
        sorted = compare.mergeSortIterative(input);
        assertArrayEquals("Checking merge sort iterative with array with one element", result, sorted, 0);

        //test quick sort
        sorted = compare.quickSort(input);
        assertArrayEquals("Checking quick sort with array with one element", result, sorted, 0);


    }

    @Test
    public void testAllElements()
    {
        double [] input = {3, 5, 7, 2, 1, 8};
        double [] result = {1, 2, 3, 5, 7, 8};

        SortComparison compare = new SortComparison();
        double[] sorted = compare.insertionSort(input);

        //test insertion sort
        assertArrayEquals("Checking insertion sort with unsorted array", result, sorted, 0);

        //test selection sort
        //first I must shuffle the array so that it is not in order and we can get an accurate measurement of time taken
        compare.shuffleArray(input);
        sorted = compare.selectionSort(input);
        assertArrayEquals("Checking selection sort with array with unsorted array", result, sorted, 0);

        //test merge sort
        compare.shuffleArray(input);
        sorted = compare.mergeSortRecursive(input);
        assertArrayEquals("Checking merge sort recrusive with array with unsorted array", result, sorted, 0);

        //test merger sort iterative
        compare.shuffleArray(input);
        sorted = compare.mergeSortIterative(input);
        assertArrayEquals("Checking merge sort iterative with array with unsorted array", result, sorted, 0);

        //test quick sort
        compare.shuffleArray(input);
        sorted = compare.quickSort(input);
        assertArrayEquals("Checking quick sort with array with unsorted array", result, sorted, 0);
    }

    @Test
    public void testSwap()
    {
        double [] input = {3, 5, 7, 2, 1, 8};
        double [] result = {3, 7, 5, 2, 1, 8};

        SortComparison compare = new SortComparison();

        //where i is less than 0
        compare.swap(input, -1, 2);
        assertArrayEquals("Testing swap when i is less than 1. This should leave the array unchanged", input, input, 0);

        //where j is less than 0
        compare.swap(input, 2, -1);
        assertArrayEquals("Testing swap when j is less than 1. This should leave the array unchanged", input, input, 0);

        //where i is greater than the array length
        compare.swap(input, 10, 2);
        assertArrayEquals("TEsting swap when i is greater than the length of the array. This should leave the array unchanged", input, input, 0);

        //where j is greater than the array length
        compare.swap(input, 2, 10);
        assertArrayEquals("Testing swap when j is greater than the length of the array. This should leave the array unchanged", input, input, 0);

        //where both i and j are both in bounds
        compare.swap(input, 1, 2);
        assertArrayEquals("Testing swap, swapping elements at index 1 and 2 in the array", input, result, 0);
    }


    // ----------------------------------------------------------
    /**
     *  Main Method.
     *  Use this main method to create the experiments needed to answer the experimental performance questions of this assignment.
     *
     */
    public static void main(String[] args) throws IOException {
        //get array from file
        SortComparison sort = new SortComparison();
        double[] currentArray = makeArrayFromFile("C:\\Users\\hanna\\OneDrive\\Desktop\\2ND YEAR\\ALGORITHMS\\2\\Assignment1 2022\\numbersSorted1000.txt");

        /*
        long timeTaken = 0;
        long startTime = System.currentTimeMillis();
        sort.insertionSort(currentArray);
        long endTime = System.currentTimeMillis();
        timeTaken = endTime - startTime;
        System.out.print("Time taken to sort the array using insertion sort: " + timeTaken + " milliseconds" +'\n');
        */

        long repetitions = 3;

        long timeTotal = 0;

        //insertion sort
        for(int i = 0; i < repetitions; i++)
        {
            long startTime = System.currentTimeMillis();
            sort.insertionSort(currentArray);
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            System.out.println(timeTaken);
            timeTotal += timeTaken;
        }
        long average = timeTotal/repetitions;

        System.out.print("Time taken to sort the array using insertion sort: " + average + " milliseconds" +'\n');

        //selection sort
        timeTotal = 0;
        for(int i = 0; i < repetitions; i++)
        {
            long startTime = System.currentTimeMillis();
            sort.selectionSort(currentArray);
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            System.out.println(timeTaken);
            timeTotal += timeTaken;
        }
        average = timeTotal/repetitions;

        System.out.print("Time taken to sort the array using selection sort: " + average + " milliseconds " + '\n');

        //quick sort
        timeTotal = 0;
        for(int i = 0; i < repetitions; i++)
        {
            long startTime = System.currentTimeMillis();
            sort.quickSort(currentArray);
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            System.out.println(timeTaken);
            timeTotal += timeTaken;
        }
        average = timeTotal/repetitions;

        System.out.print("Time taken to sort the array using quick sort " + average + " milliseconds " +'\n');


        //merge sort iterative
        timeTotal = 0;
        for(int i = 0; i < repetitions; i++)
        {
            long startTime = System.currentTimeMillis();
            sort.mergeSortIterative(currentArray);
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            System.out.println(timeTaken);
            timeTotal += timeTaken;
        }
        average = timeTotal/repetitions;

        System.out.print("Time taken to sort the array using iterative merge sort " + average + " milliseconds " +'\n');

        //merge sort recursive
        timeTotal = 0;
        for(int i = 0; i < repetitions; i++)
        {
            long startTime = System.currentTimeMillis();
            sort.mergeSortRecursive(currentArray);
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            System.out.println(timeTaken);
            timeTotal += timeTaken;
        }
        average = timeTotal/repetitions;

        System.out.print("Time taken to sort the array using recursive merge sort " + average + " milliseconds " +'\n');

        //test each function with each type of sorting
    }

    public static double[] makeArrayFromFile(String file) throws IOException {
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        ArrayList<Double> arr = new ArrayList<>();

        while(scanner.hasNextLine())
        {
            double input = Double.parseDouble(scanner.nextLine());
            arr.add(input);
        }

        double [] array = new double[arr.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = arr.get(i);
        }
        return array;
    }
}


