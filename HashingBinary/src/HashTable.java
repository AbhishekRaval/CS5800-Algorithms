//Constructing a Hash for Text
// Keys -> words | counts -> values
// This file contains implementation of Hash Table, where given a string it.
// Here we are storing all the indexes of the head of the linkedlists in buckets.
// We call this as array of buckets since, we can access bucket's head using hashCode as Index.
public class HashTable {	   
	   NodeChainList[] hashBucketArray;
	    public int hashBucketArraySize;
	    final int sizeMultiplier = 31;
	    final int asciiMultiplier = 11;
	    //Constructor to use hashBucket, with empty size
	    
	    // hashBucketArray is initialized of 31*size of input string to accomodate with the hashing function, as we're not using
	    // direct addressing to solve the  problem.
	    public HashTable(int size){
	        this.hashBucketArraySize =(size<< 5)-size;
	        this.hashBucketArray = new NodeChainList[this.hashBucketArraySize];
	        //Initializing chains for each buckets.
	        for(int i=0; i<this.hashBucketArraySize;i++){
	            this.hashBucketArray[i] = new NodeChainList();
	        }
	    }
	    
	    //basic skeleton of hashCode inspired from : http://bit.ly/2ppcUKP
	    //Core Logic -> take a hash, multiply it by 31, and then recursively add ascii value of each character
	    // of string multiplied by 11 to the hash. 
	    // Concluded from empirical analysis using large datasets, and comparing time and memory factors on input.
	    public int hashCode(String word) {
	        int hash = 0;
	        int i =0;
	        for (i = 0; i < word.length(); i++) {
	        	char c = word.charAt(i); 
	        	int ascii = (int)c;
	        	//Multiplying is a bad idea thus using bit shift operator and additions to replace multiplication
	        	//and save time computing hash function of value, as discussed in Hashing 2B video. 
	          hash = (hash<< 5) - hash + (ascii<<3) + ascii + ascii + ascii; 
	        }
	        hash = Math.abs(hash%hashBucketArraySize);
	        return hash; 
	    }
	    
	    // All the basic 4 functions required as deliverables. 
	    // Which follows the ideology, find the key and call the same function on bucket.
	    public void insert(String key, int value){
	        Node node = new Node(key,value);
	        int hashCode = hashCode(node.textContent);
	        this.hashBucketArray[hashCode].insertOrUpdate(node);
	    }

	    public int find(String key) throws Exception {
	        int hashCode = hashCode(key);
	        return this.hashBucketArray[hashCode].find(key);
	    }

	    public void delete(String key) throws Exception {
	        int hashCode = hashCode(key);
	        this.hashBucketArray[hashCode].delete(key);
	    }

	    public void increase(String key){
	        int hashCode = hashCode(key);
	        this.hashBucketArray[hashCode].increase(key);
	    }

	    public String listAllKeys(){
	        StringBuilder sb = new StringBuilder();
	        for(int i=0;i<this.hashBucketArraySize;i++){
	            sb.append(hashBucketArray[i].getAvailableKeyValues());
	        }
	        return sb.toString();
	    }
}
