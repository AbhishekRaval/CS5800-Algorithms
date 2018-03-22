public class hashDataStructures {
	public static void main(String[] args) {
		// test to test all the data structures here.
	}
}

// Node stores all the words, which has 2 items, count and pointer to next.
class Node {
	String textContent;
	int count;
	Node next;

	Node() {
		this.textContent = null;
		this.count = 0;
		this.next = null;
	}

	Node(String data, int count) {
		this.textContent = data;
		this.count = count;
		this.next = null;
	}
}

// Inspired From : http://bit.ly/2IEAbkq
// This is a chaining data structure, it contains all the linked list of nodes.
// NodeChainList is representation of chains in buckets.

class NodeChainList {
	private Node head;
	private Node tail;
	private int size;

	//Using Sentinel in LinkedList as described in CLRS chapter 12.
	//Initially wordList is empty and head will point to last node
	NodeChainList() {
		this.head = new Node();
		this.tail = head;
	}

	// Returns total elements in bucket
	public int retsize() {
		return this.size;
	}

	// returns true, if current node is empty.
	public Boolean isEmptyNodelist() {
		return size == 0;
	}

	// Inserting current node as head ! or say in front of list
	public void insertAsHead(Node word) {
		if (isEmptyNodelist()) {
			// if list is empty, we'll initialize head and tail as word.
			head.next = word;
			tail = word;
			++size;
		} else {
			// otherwise we have to assign current head's successor to word.
			// word will be new head.
			word.next = head.next;
			head.next = word;
			++size;
		}
	}

	
	// Code to insert in the Hash Table, it will insert the key ! if key already it
	// will increase the key
	public void insert(Node word) {
		boolean keyExists = false;
		if (isEmptyNodelist()) {
			//NodeList is empty, which implies that key doesn't exist, and thus it will insert the key.
			insertAsHead(word);
			keyExists = true;
		} else {
			// Traverse the list to check if the key already exists, if the key already exists, update the key
			Node currentWord = head.next;
			while (currentWord != null) {
				if ((word.textContent).equals(currentWord.textContent)) {
					currentWord.count = word.count;
					keyExists = true;
					break;
				}
				currentWord = currentWord.next;
			}
			//we traversed the list and still key isn't found anywhere so we'll insert at the head.
			if (!keyExists) {
				Node word2 = new Node(word.textContent, 1);
				insertAsHead(word2);
			}
		}
	}
	
	// Code to increase value count of node by 1 ! if key doesn't exist, it will insert the key with count 1.
	public void increase(String key) {
		boolean keyExists = false;
		if (isEmptyNodelist()) {
			//NodeList is empty, which implies that key doesn't exist, and thus it will insert the key.
			Node word = new Node(key, 1);
			insertAsHead(word);
			keyExists = true;
		} else {
			Node currentWord = head.next;
			// Traverse the list to check if the key already exists, if the key already exists, increases the count of key
			while (currentWord != null) {
				if ((key).equals(currentWord.textContent)) {
					++currentWord.count;
					keyExists = true;
					break;
				}
				currentWord = currentWord.next;
			}
			//we traversed the list and still key isn't found anywhere so we'll insert at the head.
			if (!keyExists) {
				Node word = new Node(key, 1);
				insertAsHead(word);
			}
		}
	}

	// Finds the current key and returns the count of words for current key
	// If no key exists in system, it will return 0
	int find(String key) {
		int count = 0;
		if (isEmptyNodelist()) {
			return 0;
		} else {
			Node currentWord = head.next;
			while (currentWord != null) {
				if ((currentWord.textContent).equals(key)) {
					count = currentWord.count;
					break;
				}
				currentWord = currentWord.next;
			}
		}
		return count;
	}
	
	//Code to delete a key in list.
	void delete(String key) throws Exception {
		boolean keyDeleted = false;
		if (size == 1) {
			//only one element in the list, we'll delete it
			head.next = null;
			tail = head;
			--size;
			keyDeleted = true;
		} else {
			Node currentWord = head.next;
			Node previousWord = head;
			while (currentWord != null) {
				if (currentWord == tail && (currentWord.textContent).equals(key)) {
					//key found at the end of the Linked List, deleting the pointer to the keys
					previousWord.next = null;
					tail = previousWord;
					--size;
					keyDeleted = true;
					break;
				} else if ((currentWord.textContent).equals(key)) {
					//Key found in the middle somewhere of the linkedlist 
					previousWord.next = currentWord.next;
					--size;
					keyDeleted = true;
					break;
				}
				//Traversing ahead
				previousWord = currentWord;
				currentWord = currentWord.next;
			}
		}
		if (!keyDeleted) {
			throw new Exception("Key not Found");
		}
	}
	
	//Traverse the hashTable and append all the keys
	StringBuilder nodeListToString() {
		StringBuilder listToString = new StringBuilder();
		if (size > 0) {
			Node currentWord = head.next;
			while (currentWord != null) {
				listToString.append("Word =>  " + currentWord.textContent);
				listToString.append(" : Count => " + currentWord.count  + "\n");
				currentWord = currentWord.next;
			}
		}
		return listToString;
	}
}
