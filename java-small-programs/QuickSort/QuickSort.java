public class QuickSort {
	
	public static void sort(int [] array, int p, int q) {
		if (p < q) {		
			int i = partition(array, p, q);
			sort(array, p , i - 1);
			sort(array, i + 1, q);
		}
	}

	private static int partition(int [] array, int p, int q) {
		int x = array[q];
		int i = p - 1;
	
		for (int j = p; j < q; j++) {
			if (array[j] <= x) {
				i = i + 1;
				swap(array, i, j);
			}
		}
		
		i = i + 1;
		swap(array, i, q);

		return i;
	}

	private static void swap (int array[], int indexA, int indexB) {
		int temp = array[indexA]; 
		array[indexA] = array[indexB]; 
		array[indexB] = temp;
	}
}