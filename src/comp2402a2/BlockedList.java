package comp2402a2;

import java.util.AbstractList;

/**
 * @author morin
 *
 * @param <T> the type of objects stored in the List
 */
public class BlockedList<T> extends AbstractList<T> {
	/**
	 * keeps track of the class of objects we store
	 */
	Factory<T> f;

	/**
	 * array to store elements
	 */

	protected T[] elements;

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
	public BlockedList(Class<T> t, int b) {
		f = new Factory<T>(t);
		n = 0;
		// allocate int b size memory (block)
		elements = f.newArray(b);
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
		return elements[i];
	}

	public T set(int i, T x) {
		if (i < 0 || i > n - 1)
			throw new IndexOutOfBoundsException();
		// set & return ith element in i/b th block
		elements[i] = x;
		return x;
	}

	public void add(int i, T x) {
		if (i < 0 || i > n)
			throw new IndexOutOfBoundsException();
		// queue is already full so remove first and add at last
		if (i == b && n == b) {
			for (int j = 1; j < b; j++) {
				elements[j - 1] = elements[j];
			}
			elements[b - 1] = x;
		}
		// add at last position
		else if (i == n && n < b) {
			elements[i] = x;
			n++;
		}
		// add at ith position
		else {
			for (int j = n; j > i; j--) {
				elements[j] = elements[j - 1];
			}
			elements[i] = x;
			n++;
		}
	}

	public T remove(int i) {
		if (i < 0 || i > n - 1)
			throw new IndexOutOfBoundsException();
		T x = elements[i];
		// remove last element
		if (i == (n - 1)) {
			elements[n - 1] = null;
		} else {
			// remove ith element so shift left from ith to last
			for (int j = i; j < (n - 1); j++) {
				elements[j] = elements[j + 1];
			}
			// make last element as null
			elements[n - 1] = null;
		}
		n--;
		return x;
	}
}
