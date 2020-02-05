package test;

import java.util.AbstractList;
import java.util.ArrayList;

import comp2402a2.Factory;

/**
 * @author morin
 *
 * @param <T> the type of objects stored in the List
 */
public class BlockedList1<T> extends AbstractList<T> {
	/**
	 * keeps track of the class of objects we store
	 */
	Factory<T> f;

	/**
	 * store block as blocks
	 */

	protected ArrayList<T[]> blocks;
	/**
	 * array to store elements
	 */
	protected T[] block;

	/**
	 * The number of elements stored
	 */
	int n;

	/**
	 * The block size
	 */
	int b;

	/**
	 * Constructor
	 * 
	 * @param t the type of objects that are stored in this list
	 * @param b the block size
	 */
	public BlockedList1(Class<T> t, int b) {
		f = new Factory<T>(t);
		n = 0;
		// allocate int b size memory (block)
		block = f.newArray(b);
		// add block into array list (blocks)
		blocks = new ArrayList<>();

		// save size of array into b
		this.b = b;
	}

	public int size() {
		return n;
	}

	public T get(int i) {
		if (i < 0 || i > n - 1)
			throw new IndexOutOfBoundsException();
		// returns i/b th blocks i%b element
		T[] blk = blocks.get(i / b);
		return blk[i % b];
	}

	public T set(int i, T x) {
		if (i < 0 || i > n - 1)
			throw new IndexOutOfBoundsException();
		// set & return ith element in i/b th block
		blocks.get(i / b)[i % b] = x;
		return x;
	}

	public void add(int i, T x) {
		if (i < 0 || i > n)
			throw new IndexOutOfBoundsException();

		// if have to added in last & new block needed
		if (i == n && (i % b) == 0) {
			block = f.newArray(b);
			block[0] = x;
			blocks.add(block);
			n++;
			return;
		}

		int noOfBlocks = blocks.size();
		int nthBlockElementsToModify = i / b;

		// get max element in temp and shift all elements right
		T temp = blocks.get(nthBlockElementsToModify)[b - 1];
		for (int j = b - 1; j >= (i % b); j--) {
			blocks.get(nthBlockElementsToModify)[j] = blocks.get(nthBlockElementsToModify)[j - 1];
		}
		// set ith element to x
		blocks.get(nthBlockElementsToModify)[i % b] = x;

		// take last as temp2 & shift all elements to right
		for (int j = nthBlockElementsToModify + 1; j < (noOfBlocks - 1); j++) {
			T temp2 = blocks.get(j)[b - 1];

			for (int k = b - 1; k > 0; k--) {
				blocks.get(j)[k] = blocks.get(j)[k - 1];
			}

			// set first element to last of previous block
			blocks.get(j)[0] = temp;
			// mark as last of previous block to store into first of next block
			temp = temp2;
		}

		for (int j = n % b; j >= 0; j--) {
			blocks.get(noOfBlocks - 1)[j] = blocks.get(noOfBlocks - 1)[j - 1];
		}
		
		blocks.get(noOfBlocks - 1)[0] = temp;
		n++;

	}

	public T remove(int i) {
		if (i < 0 || i > n - 1)
			throw new IndexOutOfBoundsException();

		T x;

		int noOfBlocks = blocks.size();
		int nthBlockElementsToModify = i / b;

		if (nthBlockElementsToModify == (noOfBlocks - 1)) {
			// if last element of last block remove the block
			if (i == (n - 1)) {
				x = blocks.get(nthBlockElementsToModify)[i % b];
				blocks.remove(nthBlockElementsToModify);
				n--;
				return x;
			}
			// if not last element but in last block
			x = blocks.get(nthBlockElementsToModify)[i % b];
			for (int j = (i % b); j < ((n - 1) % b); j++) {
				blocks.get(nthBlockElementsToModify)[j] = blocks.get(nthBlockElementsToModify)[j + 1];
			}
			// make last element empty (null)
			blocks.get(nthBlockElementsToModify)[(n - 1) % b] = null;
			n--;
			return x;
		}

		x = blocks.get(nthBlockElementsToModify)[i % b];
		// shift left after i%b elements
		for (int j = (i % b); j < (b - 1); j++) {
			blocks.get(nthBlockElementsToModify)[j] = blocks.get(nthBlockElementsToModify)[j + 1];
		}
		blocks.get(nthBlockElementsToModify)[b - 1] = blocks.get(nthBlockElementsToModify + 1)[0];

		// iterate over blocks after nthBlockElementsToModify and shift all elements by
		// 1 to left
		for (int j = nthBlockElementsToModify + 1; j < (noOfBlocks - 1); j++) {
			for (int k = 0; k < (b - 1); k++) {
				blocks.get(j)[k] = blocks.get(j)[k + 1];
			}
			blocks.get(j)[b - 1] = blocks.get(j + 1)[0];
		}
		// for last block
		for (int j = 0; j < ((n - 1) % b); j++) {
			blocks.get(noOfBlocks - 1)[j] = blocks.get(noOfBlocks - 1)[j + 1];
		}
		// make last element empty (null)
		blocks.get(noOfBlocks - 1)[((n - 1) % b)] = null;
		n--;
		return x;
	}
}
