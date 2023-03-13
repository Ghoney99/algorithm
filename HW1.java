import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HW1 {

	public static void main(String[] args) {

		int wordNum = 0;
		long startTime, endTime;

		String filePlace = "D:\\지헌 과제\\3-1\\알고리즘\\과제1\\";

		System.out.println("입력 파일 이름?");
		String fileName = new Scanner(System.in).nextLine();

		try {
			FileReader file = new FileReader(filePlace + fileName);
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				StringTokenizer st = new StringTokenizer(scan.next(), " ");
				wordNum += st.countTokens();
			}

		} catch (FileNotFoundException e) {
			System.err.println("파일없음");
		}

		String a[] = new String[wordNum];

		try {
			FileReader file = new FileReader(filePlace + fileName);
			Scanner scan = new Scanner(file);
			int i = 0;
			while (scan.hasNext()) {
				a[i] = scan.next();
				i++;
			}
		} catch (FileNotFoundException e) {
			System.err.println("파일없음");
		}

		String b[] = a.clone();
		System.out.println("1. 단어의 수 = " + wordNum);

		startTime = System.currentTimeMillis();
		Selection.sort(b);
		endTime = System.currentTimeMillis();
		System.out.print((2) + ". " + "선택정렬");
		System.out.println(": 정렬 여부 = " + AbstractSort.isSorted(b) + ", 소요시간 = " + (endTime - startTime) + "ms");
		b = a.clone();

		startTime = System.currentTimeMillis();
		Insertion.sort(b);
		endTime = System.currentTimeMillis();
		System.out.print((3) + ". " + "삽입정렬");
		System.out.println(": 정렬 여부 = " + AbstractSort.isSorted(b) + ", 소요시간 = " + (endTime - startTime) + "ms");
		b = a.clone();

		startTime = System.currentTimeMillis();
		Shell.sort(b);
		endTime = System.currentTimeMillis();
		System.out.print((4) + ". " + "Shell정렬");
		System.out.println(": 정렬 여부 = " + AbstractSort.isSorted(b) + ", 소요시간 = " + (endTime - startTime) + "ms");
		b = a.clone();

		startTime = System.currentTimeMillis();
		MergeTD.sort(b);
		endTime = System.currentTimeMillis();
		System.out.print((5) + ". " + "Top Down 합병정렬");
		System.out.println(": 정렬 여부 = " + AbstractSort.isSorted(b) + ", 소요시간 = " + (endTime - startTime) + "ms");
		b = a.clone();

		startTime = System.currentTimeMillis();
		MergeBU.sort(b);
		endTime = System.currentTimeMillis();
		System.out.print((6) + ". " + "Bottom Up 합병정렬");
		System.out.println(": 정렬 여부 = " + AbstractSort.isSorted(b) + ", 소요시간 = " + (endTime - startTime) + "ms");


	}
}

abstract class AbstractSort {
	public static void sort(Comparable[] a) {
	};

	protected static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	protected static void exch(Comparable[] a, int i, int j) {
		Comparable t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	protected static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");
		System.out.println();
	}

	protected static boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}
};

class Selection extends AbstractSort {
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 0; i < N - 1; i++) {
			int min = i;
			for (int j = i + 1; j < N; j++) {
				if (less(a[j], a[min]))
					min = j;
			}
			exch(a, i, min);
		}
		assert isSorted(a);
	}
};

class Insertion extends AbstractSort {
	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int i = 1; i < N; i++) {
			for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
		}
		assert isSorted(a);
	}
};

class Shell extends AbstractSort {
	public static void sort(Comparable[] a) {
		int N = a.length;
		int h = 1;
		while (h < N / 3)
			h = 3 * h + 1;
		while (h >= 1) {
			for (int i = h; i < N; i++)
				for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
					exch(a, j, j - h);
			h /= 3;
		}
	}

};

class MergeTD extends AbstractSort {
	static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

		for (int k = lo; k <= hi; k++)
			aux[k] = a[k];
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				a[k] = aux[j++];
			else if (j > hi)
				a[k] = aux[i++];
			else if (less(aux[j], aux[i]))
				a[k] = aux[j++];
			else
				a[k] = aux[i++];
		}
	}

	public static void sort(Comparable[] a) {
		Comparable[] aux = new Comparable[a.length];
		sort(a, aux, 0, a.length - 1);
	}

	private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
		if (hi <= lo)
			return;
		int mid = lo + (hi - lo) / 2;
		sort(a, aux, lo, mid);
		sort(a, aux, mid + 1, hi);
		merge(a, aux, lo, mid, hi);
	}
}

class MergeBU extends AbstractSort {
	private static void merge(Comparable[] in, Comparable[] out, int lo, int mid, int hi) {
		int i = lo, j = mid + 1;
		for (int k = lo; k <= hi; k++) {
			if (i > mid)
				out[k] = in[j++];
			else if (j > hi)
				out[k] = in[i++];
			else if (less(in[j], in[i]))
				out[k] = in[j++];
			else
				out[k] = in[i++];
		}
	}

	public static void sort(Comparable[] a) {
		Comparable[] src = a, dst = new Comparable[a.length], tmp;
		int N = a.length;
		for (int n = 1; n < N; n *= 2) {
			for (int i = 0; i < N; i += 2 * n)
				merge(src, dst, i, i + n - 1, Math.min(i + 2 * n - 1, N - 1));
			tmp = src;
			src = dst;
			dst = tmp;
		}
		if (src != a)
			System.arraycopy(src, 0, a, 0, N);
	}
}
