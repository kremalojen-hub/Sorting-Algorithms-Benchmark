    String[] cases = {"Random", "Reversely Sorted", "Partially Sorted", "Fully Sorted"};
    String[] algorithms = {"Selection Sort", "Insertion Sort", "Bubble Sort", "Merge Sort", "Quick Sort"};

    long[][] results = new long[5][4];

    for (int c = 0; c < 4; c++) {
        int[] source;
        if (c == 0) source = originalRandom;
        else if (c == 1) source = originalReversed;
        else if (c == 2) source = originalPartially;
        else source = originalSorted;

        // 1. Selection Sort (Note: O(N^2) can be very slow for 100k, handled or tested)
        int[] arr1 = Arrays.copyOf(source, source.length);
        long start = System.nanoTime();
        selectionSort(arr1);
        results[0][c] = (System.nanoTime() - start) / 1000;

        // 2. Insertion Sort
        int[] arr2 = Arrays.copyOf(source, source.length);
        start = System.nanoTime();
        insertionSort(arr2);
        results[1][c] = (System.nanoTime() - start) / 1000;

        // 3. Bubble Sort
        int[] arr3 = Arrays.copyOf(source, source.length);
        start = System.nanoTime();
        bubbleSort(arr3);
        results[2][c] = (System.nanoTime() - start) / 1000;

        // 4. Merge Sort
        int[] arr4 = Arrays.copyOf(source, source.length);
        start = System.nanoTime();
        mergeSort(arr4, 0, arr4.length - 1);
        results[3][c] = (System.nanoTime() - start) / 1000;

        // 5. Quick Sort
        int[] arr5 = Arrays.copyOf(source, source.length);
        start = System.nanoTime();
        quickSort(arr5, 0, arr5.length - 1);
        results[4][c] = (System.nanoTime() - start) / 1000;
    }

    // Print Results Table matching the assignment format
    System.out.println("-----------------------------------------------------------------------------------------");
    System.out.printf("%-20s | %-18s | %-16s | %-16s | %-12s\n", "الخوارزمية", "غير مرتبة (Random)", "مرتبة عكسياً", "مرتبة جزئياً", "مرتبة تماماً");
    System.out.println("-----------------------------------------------------------------------------------------");
    for (int i = 0; i < algorithms.length; i++) {
        System.out.printf("%-20s | %-15d µs | %-13d µs | %-13d µs | %-10d µs\n", 
            algorithms[i], results[i][0], results[i][1], results[i][2], results[i][3]);
    }
    System.out.println("-----------------------------------------------------------------------------------------");
}

// Helper generation methods
public static int[] generateRandomArray(int n) {
    Random rand = new Random();
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) arr[i] = rand.nextInt(1000000);
    return arr;
}

public static int[] generateReversedArray(int[] src) {
    int[] arr = Arrays.copyOf(src, src.length);
    Arrays.sort(arr);
    for (int i = 0; i < arr.length / 2; i++) {
        int temp = arr[i];
        arr[i] = arr[arr.length - 1 - i];
        arr[arr.length - 1 - i] = temp;
    }
    return arr;
}

public static int[] generatePartiallySortedArray(int[] src) {
    int[] arr = Arrays.copyOf(src, src.length);
    Arrays.sort(arr);
    Random rand = new Random();
    for (int i = 0; i < arr.length * 0.2; i++) {
        int idx1 = rand.nextInt(arr.length);
        int idx2 = rand.nextInt(arr.length);
        int temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }
    return arr;
}

public static int[] generateSortedArray(int[] src) {
    int[] arr = Arrays.copyOf(src, src.length);
    Arrays.sort(arr);
    return arr;
}

// Sorting Implementations
public static void selectionSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        int minIdx = i;
        for (int j = i + 1; j < arr.length; j++) {
if (arr[j] < arr[minIdx]) minIdx = j;
        }
        int temp = arr[minIdx];
        arr[minIdx] = arr[i];
        arr[i] = temp;
    }
}

public static void insertionSort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        int key = arr[i];
        int j = i - 1;
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j = j - 1;
        }
        arr[j + 1] = key;
    }
}

public static void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        boolean swapped = false;
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;
    }
}

public static void mergeSort(int[] arr, int l, int r) {
    if (l < r) {
        int m = l + (r - l) / 2;
        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);
        merge(arr, l, m, r);
    }
}

public static void merge(int[] arr, int l, int m, int r) {
    int n1 = m - l + 1;
    int n2 = r - m;
    int[] L = new int[n1];
    int[] R = new int[n2];
    for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
    for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];
    int i = 0, k = l;
    int j = 0;
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) arr[k++] = L[i++];
        else arr[k++] = R[j++];
    }
    while (i < n1) arr[k++] = L[i++];
    while (j < n2) arr[k++] = R[j++];
}

public static void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

public static int partition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int i = (low - 1);
    for (int j = low; j < high; j++) {
        if (arr[j] < pivot) {
            i++;
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    int temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;
    return i + 1;
}
