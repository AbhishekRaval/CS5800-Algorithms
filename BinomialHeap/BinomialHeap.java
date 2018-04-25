package BinomialHeap;

public class BinomialHeap {
	public binomialHeapDataStructure head;

	public BinomialHeap() {
		head = null;
	}

	public binomialHeapDataStructure minimum() throws Exception {
		if (head == null) {
			throw new Exception("This Heap is empty");
		} else {
			binomialHeapDataStructure min = head;
			binomialHeapDataStructure x = min.sibling;

			while (x != null) {
				if (x.value < min.value) {
					min = x;
				}
				x = x.sibling;
			}

			return min;
		}
	}

	private void binomialLink(binomialHeapDataStructure y, binomialHeapDataStructure z) {
		y.parent = z;
		y.sibling = z.child;
		z.child = y;
		z.amountOfChilds++;
	}

	public BinomialHeap union(BinomialHeap heapToMerge) {
		BinomialHeap h = new BinomialHeap();
		h.head = binomialHeapMerge(this, heapToMerge);
		head = null;
		heapToMerge.head = null;

		if (h.head == null) {
			return h;
		}

		binomialHeapDataStructure prev = null;
		binomialHeapDataStructure current = h.head;
		binomialHeapDataStructure next = current.sibling;

		while (next != null) {
			if (current.amountOfChilds != next.amountOfChilds
					|| (next.sibling != null && next.sibling.amountOfChilds == current.amountOfChilds)) {
				// If current and next does not have equal amount of childs, or if current, and next two siblings have the same amount of childs
				prev = current;
				current = next;
			} else {
				if (current.value < next.value) {
					// If current and next have equal amount of childs and
					// current has the smaller value, so next will be linked to current
					current.sibling = next.sibling;
					binomialLink(next, current);
				} else {
					// In this case, next holds smaller value, so current will be
					// linked to next.
					if (prev == null) {
						h.head = next;
					} else {
						prev.sibling = next;
					}

					binomialLink(current, next);
					current = next;
				}
			}

			next = current.sibling;
		}

		return h;
	}

	private static binomialHeapDataStructure binomialHeapMerge(BinomialHeap heap1, BinomialHeap heap2) {
		// check if either heaps are empty.
		if (heap1.head == null) {
			return heap2.head;
		} else if (heap2.head == null) {
			return heap1.head;
		} else {
			binomialHeapDataStructure head;
			binomialHeapDataStructure tail;
			binomialHeapDataStructure heap1Next = heap1.head;
			binomialHeapDataStructure heap2Next = heap2.head;

			if (heap1.head.amountOfChilds <= heap2.head.amountOfChilds) {
				head = heap1.head;
				heap1Next = heap1Next.sibling;
			} else {
				head = heap2.head;
				heap2Next = heap2Next.sibling;
			}

			tail = head;

			while (heap1Next != null && heap2Next != null) {
				if (heap1Next.amountOfChilds <= heap2Next.amountOfChilds) {
					tail.sibling = heap1Next;
					heap1Next = heap1Next.sibling;
				} else {
					tail.sibling = heap2Next;
					heap2Next = heap2Next.sibling;
				}
				tail = tail.sibling;
			}
			if (heap1Next != null) {
				tail.sibling = heap1Next;
			} else {
				tail.sibling = heap2Next;
			}
			return head;
		}
	}

	public void insert(int value) {
		binomialHeapDataStructure x = new binomialHeapDataStructure(value);
		BinomialHeap hPrime = new BinomialHeap();
		hPrime.head = x;
		BinomialHeap newHeap = this.union(hPrime);
		head = newHeap.head;
	}

	public int extractMin() throws Exception {
		// checking if the heap has values
		if (head == null) {
			throw new Exception("This Heap is empty");
		}

		// Find the root x with the minimum key in the root list.
		binomialHeapDataStructure target = head;        // node with minimum key
		binomialHeapDataStructure cur = target.sibling;    // current node being examined
		binomialHeapDataStructure pred = target;        // y's predecessor
		binomialHeapDataStructure tarPred = null;    // predecessor of target

		while (cur != null) {
			if (cur.value < target.value) {
				target = cur;
				tarPred = pred;
			}
			pred = cur;
			cur = cur.sibling;
		}

		removeFromRootList(target, tarPred);
		return target.value;
	}

	//Method to remove a node from the root list    
	private void removeFromRootList(binomialHeapDataStructure target, binomialHeapDataStructure pred) {
		// Splice out x.
		if (target == head) {
			head = target.sibling;
		} else {
			pred.sibling = target.sibling;
		}

		BinomialHeap hPrime = new BinomialHeap();

		// Reverse the order of x's children
		binomialHeapDataStructure temp = target.child;
		while (temp != null) {
			binomialHeapDataStructure next = temp.sibling;
			temp.parent = null;
			temp.sibling = hPrime.head;
			hPrime.head = temp;
			temp = next;
		}

		BinomialHeap newHeap = this.union(hPrime);
		head = newHeap.head;
	}

	public void decreaseKey(binomialHeapDataStructure target, int newKey) {
		if (target.value < newKey) {
			System.out.println("New key greater than the old key, could not decrease.");
			return;
		}
		target.value = newKey;    // update x's key
		bubbleUp(target);    // bubble it up until it's in the right place
	}

	//Bubbles the value in node up to the right place the binomial heap.
	public binomialHeapDataStructure bubbleUp(binomialHeapDataStructure x) {
		binomialHeapDataStructure current = x.parent;

		while (current != null && (x.value < current.value)) {
			int yValue = x.value;
			x.value = current.value;
			current.value = yValue;

			x = current;
			current = x.parent;
		}
		return x;
	}

	public void delete(binomialHeapDataStructure target) throws Exception {
		decreaseKey(target, Integer.MIN_VALUE);
		extractMin();
	}

	@Override
	public String toString() {
		String result = "";

		binomialHeapDataStructure x = head;
		while (x != null) {
			result += x.printTree(0);
			x = x.sibling;
		}

		return result;
	}


	public binomialHeapDataStructure find(int key, binomialHeapDataStructure binomialHeapNode) {
		if (null == binomialHeapNode) {
			return null;
		}
		if (key == binomialHeapNode.value) {
			return binomialHeapNode;
		}
		binomialHeapDataStructure n1 = find(key, binomialHeapNode.sibling);
		binomialHeapDataStructure n2 = find(key, binomialHeapNode.child);
		if (n1 != null || n2 != null) {
			if (n1 != null) {
				return n1;
			} else {
				return n2;
			}
		} else {
			return null;
		}
	}


	public void print() {
		System.out.println("Binomial heap:");
		if (head != null) {
			print(0, this.head);
		}
	}

	public void print(int level, binomialHeapDataStructure curr) {
		while (curr != null) {
			StringBuilder sb = new StringBuilder();
			System.out.println("level: " + level );
			for (int i = 0; i < level; i++) {
				sb.append(" ");
			}
			if (level != 0) {
				sb.append("\\");
			}

			sb.append(String.valueOf(curr.value));
			System.out.println(sb.toString());
			if (curr.child != null) {
				print(level + 1, curr.child);
			}
			curr = curr.sibling;
		}
	}


}
