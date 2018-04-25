package BinomialHeap;

public class binomialHeapInpOut {

	public static void main(String[] args) throws Exception {

		int[] A = {5,25,50,75,80,90,95,21};
		int[] B = {3,6,70,85,88,98,32,41};

		BinomialHeap heapA = new BinomialHeap();
		for (int i : A) {
			heapA.insert(i);
		}
		heapA.print();


		BinomialHeap heapB = new BinomialHeap();
		for (int j : B) {
			heapB.insert(j);
		}
		heapB.print();

		heapA = heapA.union(heapB);
		heapA.print();

		int minA = heapA.extractMin();
		System.out.println(minA);
		System.out.println(" ");
		heapA.print();

		binomialHeapDataStructure n95 = heapA.find(95, heapA.head);
		heapA.decreaseKey(n95, 12);
		heapA.print();

		binomialHeapDataStructure n21 = heapA.find(21, heapA.head);
		heapA.delete(n21);
		heapA.print();

		System.out.println(heapA.minimum());


	}

}
