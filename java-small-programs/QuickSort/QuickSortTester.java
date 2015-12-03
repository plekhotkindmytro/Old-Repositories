class QuickSortTester {
	
	public static void main(String [] args) {
		int [] testArray = {1, 5 ,10, 5, 113, 234, 55, 33, 11, 4, 5, 6, 8,8,8, 18 ,1 ,1 ,9 ,9 ,8,8, 1, 0, 4, 0, 4, -13, 43, 2, 42,421,44};
		int [] testArraySorted = {-2,-2,-1,0,0,1,1,3,4,5,6,7,8,9,9,9};

		System.out.println("***** Quick sort *****");

		QuickSort.sort(testArray, 0, testArray.length-1);
		printArray(testArray);

		QuickSort.sort(testArraySorted, 0, testArraySorted.length-1);
		printArray(testArraySorted);
	}

	private static void printArray(int [] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");	
		}
		System.out.println();
	}
}