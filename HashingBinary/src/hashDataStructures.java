public class hashDataStructures {
	public static void main(String[] args) {
		//test to test all the data structures here.
		}
}
//Node stores all the words, which has 2 items, count and pointer to next.
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

//Inspired From : http://bit.ly/2IEAbkq
// This is a chaining data structure, it contains all the linked list of nodes.
//NodeChainList is representation of chains in buckets.

class NodeChainList {
  private Node head;
  private Node tail;
  private int size;

/*Using Sentinel to Have an Empty Node as Header.
  * Initially the WordList will be empty and the head will point to the last Node.*/
   NodeChainList() {
      this.head = new Node();
      this.tail = head;
  }
   //Returns total elements in bucket
   public int retsize() {
  	 return this.size;
   }
   
   //returns true, if current node is empty.
   public Boolean isEmptyNodelist(){
	   return size==0;
   }
   
   // Inserting current node as head ! or say in front of list
   public void insertAsHead(Node word) {
       if(isEmptyNodelist()) {
    	   // if list is empty, we'll initialize head and tail as word.
           head.next = word;
           tail = word;
           ++size;
       } else {
    	   //otherwise we have to assign current head's successor to word.
    	   //word will be new head.
           word.next = head.next;
           head.next = word;
           ++size;
       }
   }
   
   // Inserting current node as tail ! or say in the end of the list
   public void insertAsTail(Node word) {
       if (isEmptyNodelist()) {
    	// if list is empty, we'll initialize head and tail as word.
           head.next = word;
           tail = word;
           ++size;
       } else {
    	   //we have to assign word as tail's successor
    	   //word will be new tail
           tail.next = word;
           tail = word;
           ++size;
       }
   }

   /*This will check if the given word is already in the list*/
   public void insertOrUpdate(Node word) {
       if (isEmptyNodelist()) {
           insertAsHead(word);
       } else {
    	   //Traverse the list to check if the key already exists
           Node currentWord = head.next;
           while (currentWord != null) {
               if ((word.textContent).equals(currentWord.textContent)) {
                   currentWord.count = word.count;
                   break;
               }
               currentWord = currentWord.next;
           }
       }
   }

   /*Finds the count for the key given*/
   int find(String key) throws Exception {
      int count = 0;
      if (size == 0) {
          throw new Exception("Key not Found");
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

   void delete(String key) throws Exception {
      boolean deleted = false;
      if (size == 1) {
          head.next = null;
          tail = head;
          --size;
          deleted = true;
      } else {
          Node currentWord = head.next;
          Node previousWord = head;
          while (currentWord != null) {
              if (currentWord == tail &&(currentWord.textContent).equals(key)) {
                  previousWord.next = null;
                  tail = previousWord;
                  --size;
                  deleted = true;
                  break;
              } else if ((currentWord.textContent).equals(key)) {
                  previousWord.next = currentWord.next;
                  --size;
                  deleted = true;
                  break;
              }
              previousWord = currentWord;
              currentWord = currentWord.next;
          }
      }
      if (!deleted) {
          throw new Exception("Key not Found");
      }
  }

   public void increase(String key) {
       boolean keyFound = false;
       if (size == 0) {
           Node word = new Node(key, 1);
           insertAsHead(word);
           keyFound = true;
       } else {
           Node currentWord = head.next;
           while (currentWord != null) {
               if ((key).equals(currentWord.textContent)) {
                   ++currentWord.count;
                   keyFound = true;
                   break;
               }
               currentWord = currentWord.next;
           }
           if (!keyFound) {
               Node word = new Node(key, 1);
               insertAsTail(word);
           }
       }
   }


   StringBuilder getAvailableKeyValues() {
      StringBuilder sb = new StringBuilder();
      if (size > 0) {
          Node currentWord = head.next;
          while (currentWord != null) {
              sb.append("Key: ");
              sb.append(currentWord.textContent);
              sb.append("; Value: ");
              sb.append(currentWord.count);
              sb.append("\n");
              currentWord = currentWord.next;
          }
      }
      return sb;
  }
}
