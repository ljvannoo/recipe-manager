//----------------------------------------------------------------------
//Copyright © 2004 Northrop Grumman Corporation -- All Rights Reserved
//
//This material may be reproduced by or for the U.S. Government pursuant
//to the copyright license under the clause at Defense Federal
//Acquisition Regulation Supplement (DFARS) 252.227-7014 (June 1995).
//----------------------------------------------------------------------

/*
 * Created on Jun 22, 2005
 */
package com.blackmud.tools.RecipeManager;

import java.util.Comparator;
import java.util.Vector;

/**
 * The Class Sorter.
 * 
 * @author lukasv
 * 
 * This class implements various sorting algorithms against a supplied vector. The data
 * is sorted by applying the supplied comparator.  The code for the various algorithms
 * has been adapted from code found at http://www.cs.ubc.ca/spider/harrison/Java/sorting-demo.html
 */
public class Sorter {
    public static final int BUBBLE_SORT = 0;
    public static final int BI_DIRECTIONAL_BUBBLE_SORT = 1;
    public static final int SELECTION_SORT = 2;
    public static final int SHAKER_SORT = 3;
    public static final int INSERTION_SORT = 4;
    public static final int IN_PLACE_MERGE_SORT = 5;
    public static final int COMB_SORT = 6;
    public static final int SHELL_SORT = 7;
    public static final int HEAP_SORT = 8;
    public static final int QUICK_SORT = 9;
    public static final int FAST_QUICK_SORT = 10;
    
    private static final String[] TYPE_NAMES = {"BUBBLE_SORT               ",
            									"BI_DIRECTIONAL_BUBBLE_SORT",
            									"SELECTION_SORT            ",
            									"SHAKER_SORT               ", 
            									"INSERTION_SORT            ", 
            									"IN_PLACE_MERGE_SORT       ",
            									"COMB_SORT                 ", 
            									"SHELL_SORT                ", 
            									"HEAP_SORT                 ", 
            									"QUICK_SORT                ",
            									"FAST_QUICK_SORT           "};
    private Vector sortData = null;
    private Comparator c = null;
    private long lastSortMillis = 0;
    
    /**
     * Constructs a new sorter.
     * 
     * @param sortData The data to be sorted by this sorter
     * @param c A comparator that will be used to compare the data in <code>sortData</code>.
     * Given two data objects, o1 and o2, o1 is considered to be greater than o2 if the
     * comparator returns a value greater than 0. o1 is considered to be equal to o2 if
     * the comparator returns a value equal to 0.
     */
    public Sorter(Vector sortData, Comparator c) {
        this.sortData = sortData;
        this.c = c;        
    }
    
    /**
     * Initiates the specified sorting algorithm on the data associated with
     * this sorter. Once the sort routine is complete, the data is in a sorted state.
     * 
     * @param sortType One of the following sort types:<P>
     * BUBBLE_SORT<BR>
     * BI_DIRECTIONAL_BUBBLE_SORT<BR>
     * SELECTION_SORT<BR>
     * SHAKER_SORT<BR>
     * INSERTION_SORT<BR>
     * IN_PLACE_MERGE_SORT<BR>
     * COMB_SORT<BR>
     * SHELL_SORT<BR>
     * HEAP_SORT<BR>
     * QUICK_SORT<BR>
     * FAST_QUICK_SORT<BR>
     * 
     * @return The data associated with this sorter after it has been sorted.
     */
    public Vector sort(int sortType) {
        long time = System.currentTimeMillis();
        switch(sortType) {
        	case BUBBLE_SORT:
        	    bubbleSort(sortData);
        	    break;
        	case BI_DIRECTIONAL_BUBBLE_SORT:
        	    biDirectionalBubbleSort(sortData);
        	    break;
        	case SELECTION_SORT:
        	    selectionSort(sortData);
        	    break;
        	case SHAKER_SORT:
        	    shakerSort(sortData);
        	    break;
        	case INSERTION_SORT:
        	    insertionSort(sortData, 0, sortData.size()-1);
        	    break;
        	case IN_PLACE_MERGE_SORT:
        	    inPlaceMergeSort(sortData, 0, sortData.size()-1);
        	    break;
        	case COMB_SORT:
        	    combSort(sortData);
        	    break;
        	case SHELL_SORT:
        	    shellSort(sortData);
        	    break;
        	case HEAP_SORT:
        	    heapSort(sortData);
        	    break;
        	case QUICK_SORT:
        	    quickSort(sortData, 0, sortData.size()-1);
        	    break;
        	case FAST_QUICK_SORT:
        	default:
        	    fastQuickSort(sortData, 0, sortData.size()-1);
            	insertionSort(sortData, 0, sortData.size()-1);
                break;
        }
        time = System.currentTimeMillis()-time;
        lastSortMillis = time;
        
        return sortData;
    }
    
    /**
     * Gets the number of milliseconds that elapsed during the last sort procedure.
     * 
     * @return Elapsed time in milliseconds.
     */
    public long getLastSortMillis() {
        return lastSortMillis;
    }
    
    /**
     * Gets the string representation of a sort type.
     * 
     * @param sortType One of the following sort types:<P>
     * BUBBLE_SORT<BR>
     * BI_DIRECTIONAL_BUBBLE_SORT<BR>
     * SELECTION_SORT<BR>
     * SHAKER_SORT<BR>
     * INSERTION_SORT<BR>
     * IN_PLACE_MERGE_SORT<BR>
     * COMB_SORT<BR>
     * SHELL_SORT<BR>
     * HEAP_SORT<BR>
     * QUICK_SORT<BR>
     * FAST_QUICK_SORT<BR>
     * 
     * @return The string representation of the sort type
     */
    public String getTypeName(int sortType) {
        return TYPE_NAMES[sortType];
    }
    
    
    /**
     * Retrieves the data associated with this sorter.  If this method is
     * called before the <code>sort()</code> method has been called, the
     * data will not be in a sorted state.
     * 
     * @return A vector of data
     */
    public Vector getData() {
        return sortData;
    }
    
    /**
     * Bubble sort.
     * 
     * @param data the data to be sorted
     */
    private void bubbleSort(Vector data) {
        for (int i = data.size(); --i >= 0; ) {
            boolean flipped = false;
		    for (int j = 0; j<i; j++) {
				if (c.compare(data.get(j), data.get(j+1)) > 0) {
				    swap(data, j, j+1);
				    flipped = true;
				}
		    }
		    if (!flipped) {
		        return;
		    }
        }
    }
    
    /**
     * Bi directional bubble sort.
     * 
     * @param data the data to be sorted
     */
    private void biDirectionalBubbleSort(Vector data) {
        int j;
        int limit = data.size();
        int st = -1;
        while (st < limit) {
            boolean flipped = false;
            st++;
            limit--;
            for (j = st; j < limit; j++) {
                if(c.compare(data.get(j), data.get(j+1)) > 0) {
                    swap(data, j, j+1);
		            flipped = true;
		        }
            }
            if (!flipped) {
                return;
            }
            for (j = limit; --j >= st;) {
		        if(c.compare(data.get(j), data.get(j+1)) > 0) {
		            swap(data, j, j+1);
		            flipped = true;
		        }
            }
            if (!flipped) {
                return;
            }
        }
    }
    
    /**
     * Selection sort.
     * 
     * @param data the data to be sorted
     */
    private void selectionSort(Vector data) {
        for (int i = 0; i < data.size(); i++) {
            int min = i;
            int j;

            /*
             *  Find the smallest element in the unsorted list
             */
            for (j = i + 1; j < data.size(); j++) {
                if(c.compare(data.get(j), data.get(min)) < 0) {
                    min = j;
                }
            }

            /*
             *  Swap the smallest unsorted element into the end of the
             *  sorted list.
             */
            swap(data, min, i);
        }
    }
    
    /**
     * Shaker sort.
     * 
     * @param data the data to be sorted
     */
    private void shakerSort(Vector data) {
        int i = 0;
        int k = data.size() - 1;
        while (i < k) {
            int min = i;
            int max = i;
            int j;
            for (j = i + 1; j <= k; j++) {
                if(c.compare(data.get(j), data.get(min)) < 0) {
                    min = j;
                }
                if(c.compare(data.get(j), data.get(max)) > 0) {
                    max = j;
                }
            }
            swap(data, min, i);

            if (max == i) {
                swap(data, min, k);
            } else {
                swap(data, max, k);
            }
            i++;
            k--;
        }
    }
    
    /**
     * Insertion sort.
     * 
     * @param data the data to be sorted
     * @param leftIndex the left index
     * @param rightIndex the right index
     */
    private void insertionSort(Vector data, int leftIndex, int rightIndex) {
	    int i;
	    int j;
	    Object temp;
	
	    for (i=leftIndex+1;i<=rightIndex;i++) {
	        temp = data.get(i);
            j = i;
            while ((j > leftIndex) && (c.compare(data.get(j-1), temp) > 0)) {
                    data.set(j,data.get(j-1));
                    j--;
            }
            data.set(j, temp);
	    }
    }
    
    /**
     * In place merge sort.
     * 
     * @param data the data to be sorted
     * @param leftIndex the left index
     * @param rightIndex the right index
     */
    private void inPlaceMergeSort(Vector data, int leftIndex, int rightIndex) {
        int lo = leftIndex;
        int hi = rightIndex;

        
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) / 2;

        /*
         *  Partition the list into two lists and sort them recursively
         */
        inPlaceMergeSort(data, lo, mid);
        inPlaceMergeSort(data, mid + 1, hi);

        /*
         *  Merge the two sorted lists
         */
        int end_lo = mid;
        int start_hi = mid + 1;
        while ((lo <= end_lo) && (start_hi <= hi)) {
            if(c.compare(data.get(lo), data.get(start_hi)) < 0) {
                lo++;
            } else {
                /*  
                 *  a[lo] >= a[start_hi]
                 *  The next element comes from the second list, 
                 *  move the a[start_hi] element into the next 
                 *  position and shuffle all the other elements up.
                 */
                Object temp = data.get(start_hi);
                for (int k = start_hi - 1; k >= lo; k--) {
                    data.set(k+1, data.get(k));
                }
                data.set(lo, temp);
                lo++;
                end_lo++;
                start_hi++;
            }
        }
    }
        
    /**
     * Comb sort.
     * 
     * @param data the data to be sorted
     */
    private void combSort(Vector data) {
        final float SHRINKFACTOR = (float)1.3;
        boolean flipped = false;
        int gap, top;
        int i, j;

        gap = data.size();
        do {
            gap = (int) ((float) gap / SHRINKFACTOR);
            switch (gap) {
	            case 0: /* the smallest gap is 1 - bubble sort */
	                gap = 1;
	                break;
	            case 9: /* this is what makes this Combsort11 */
	            case 10: 
	                gap = 11;
	                break;
	            default: break;
            }
            flipped = false;
            top = data.size() - gap;
            for (i = 0; i < top; i++) {
                j = i + gap;
                if(c.compare(data.get(i), data.get(j)) > 0) {
                    swap(data, i, j);
                    flipped = true;
                }
            }
        } while (flipped || (gap > 1));
        /* like the bubble and shell sorts we check for a clean pass */
    }
    
    /**
     * Shell sort.
     * 
     * @param data the data to be sorted
     */
    private void shellSort(Vector data) {
        int h = 1;
        /* 
         * find the largest h value possible 
         */
        while ((h * 3 + 1) < data.size()) {
		    h = 3 * h + 1;
		}

        /* 
         * while h remains larger than 0 
         */
        while( h > 0 ) {
            /* 
             * for each set of elements (there are h sets)
             */
            for (int i = h - 1; i < data.size(); i++) {
                /* 
                 * pick the last element in the set
                 */
                Object B = data.get(i);
                int j = i;
                /*
                 * compare the element at B to the one before it in the set
                 * if they are out of order continue this loop, moving
                 * elements "back" to make room for B to be inserted.
                 */
                for( j = i; (j >= h) && (c.compare(data.get(j-h), B) > 0); j -= h) {
                    data.set(j, data.get(j-h));
                }
                /*
                 *  insert B into the correct place
                 */
                data.set(j, B);
            }
            /* 
             * all sets h-sorted, now decrease set size
             */
            h = h / 3;
        }
    }
    
    /**
     * Heap sort.
     * 
     * @param data the data to be sorted
     */
    private void heapSort(Vector data) {
        int N = data.size();
        for (int k = N/2; k > 0; k--) {
            downheap(data, k, N);
        }
        do {
            swap(data, 0, N-1);
            N = N - 1;
            downheap(data, 1, N);
        } while (N > 1);
    }

    /**
     * Downheap.
     * 
     * @param data the data to be sorted
     * @param k the k
     * @param N the n
     */
    private void downheap(Vector data, int k, int N) {
        Object temp = data.get(k-1);
        while (k <= N/2) {
	        int j = k + k;
	        if ((j < N) && (c.compare(data.get(j-1), data.get(j)) < 0)) {
	            j++;
	        }
	        if (c.compare(temp, data.get(j-1)) >= 0) {
	            break;
	        } else {
	            data.set(k-1, data.get(j-1));
		        k = j;
	        }
        }
        data.set(k-1, temp);
    }
    
    /**
     * Quick sort.
     * 
     * @param data the data to be sorted
     * @param leftIndex the left index
     * @param rightIndex the right index
     */
    private void quickSort(Vector data, int leftIndex, int rightIndex) {
        int lo = leftIndex;
        int hi = rightIndex;
        if (lo >= hi) {
            return;
        } else if( lo == hi - 1 ) {
            /*
             *  sort a two element list by swapping if necessary 
             */
            if(c.compare(data.get(lo), data.get(hi)) > 0) {
                swap(data, lo, hi);
            }
            return;
        }

        /*
         *  Pick a pivot and move it out of the way
         */
        Object pivot = data.get((lo + hi) / 2);
        swap(data, (lo + hi) / 2, hi);

        while( lo < hi ) {
            /*
             *  Search forward from a[lo] until an element is found that
             *  is greater than the pivot or lo >= hi 
             */
            while ((c.compare(data.get(lo), pivot) <= 0) && lo < hi) {
                lo++;
            }

            /*
             *  Search backward from a[hi] until element is found that
             *  is less than the pivot, or lo >= hi
             */
            while ((c.compare(pivot, data.get(hi)) <= 0) && lo < hi ) {
                hi--;
            }

            /*
             *  Swap elements a[lo] and a[hi]
             */
            if( lo < hi ) {
                swap(data, lo, hi);
            }

        }

        /*
         *  Put the median in the "center" of the list
         */
        data.set(rightIndex, data.get(hi));
        data.set(hi, pivot);

        /*
         *  Recursive calls, elements a[lo0] to a[lo-1] are less than or
         *  equal to pivot, elements a[hi+1] to a[hi0] are greater than
         *  pivot.
         */
        quickSort(data, leftIndex, lo-1);
        quickSort(data, hi+1, rightIndex);
    }
    
    /**
     * Fast quick sort.
     * 
     * @param data the data to be sorted
     * @param leftIndex the left index
     * @param rightIndex the right index
     */
    private void fastQuickSort(Vector data, int leftIndex, int rightIndex) {
        int M = 4;
        int i;
        int j;
        Object temp;

        if ((rightIndex-leftIndex) > M) {
                i = (rightIndex+leftIndex)/2;
                
                // Tri-Median method!
                if (c.compare(data.get(leftIndex), data.get(i)) > 0)
                    swap(data, leftIndex,i);
                if (c.compare(data.get(leftIndex), data.get(rightIndex)) > 0)
                    swap(data, leftIndex,rightIndex);
                if (c.compare(data.get(i), data.get(rightIndex)) > 0)
                    swap(data, i,rightIndex);

                j = rightIndex-1;
                swap(data, i,j);
                i = leftIndex;
                temp = data.get(j);
                for(;;) {
                    //while(c.compare(data.get(++i), temp) < 0);  OLD CODE: Broken? Increments past right limit -LV
                    while(i<rightIndex && c.compare(data.get(++i), temp) < 0); // is this right? -LV
                    while(c.compare(data.get(--j), temp) > 0);
                    if (j<i) break;
                    swap(data, i,j);
                }
                swap(data, i,rightIndex-1);
                fastQuickSort(data, leftIndex,j);
                fastQuickSort(data, i+1,rightIndex);
        }
    }
    
    /**
     * Swap.
     * 
     * @param data the data to be sorted
     * @param index1 the index1
     * @param index2 the index2
     */
    private void swap(Vector data, int index1, int index2) {
        Object temp = data.get(index1);
        data.set(index1, data.get(index2));
        data.set(index2, temp);
    }
}
