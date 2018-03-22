import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.List;
import java.util.Random;

//Basic File to handle the Logistics of program !
public class HashInputOutput {
	long startTime = System.nanoTime();
	//Main file this is the entry program to the file !
	public static void main(String[] args) {
		//Main Function to Test out code, use inputFile1 or inputFile2
		//inputFile2 contains 3,70,000 unique dictionary words
		//inputFile1 contains 4000 line alice in wonderland story
		HashInputOutput table = new HashInputOutput();
	        String inputFile1 = "D:\\Projects\\Java2\\HashingBinary\\src\\Alice_in_wonderland.txt";
	        String inputFile2 = "D:\\Projects\\Java2\\HashingBinary\\src\\words_alpha.txt";
	        String outputFileLocation = "D:\\Projects\\Java2\\HashingBinary\\src\\output\\";
	        //Given a input in form of file, prints words and its count to output file.
	        table.processFileOutput(inputFile2, outputFileLocation);  
	        //Given an Input String prints to the console.
	        //table.processString("THE THE THE the the th tge TFE tge tfe tke kte");
	}
	
	//Function to process String and Build Hash Table 
	//Input: String -> ListofKeyvalues
	public void processString(String input){
		//String to List using Regex
		List<String> wordInputList = new ArrayList<String>();		
		 wordInputList.addAll(Arrays.asList(input.replaceAll("[^a-zA-Z ]", "").split("\\s+")));
		 String hashingOutput = insertIntoHashTable(wordInputList);
		 System.out.println(hashingOutput);
	}
	
	//Get StringList as input and builds up hashTable
	//iNp
	public String insertIntoHashTable( List<String> wordInputList) {	
		String hashTableOutput = "";
		HashTable hashTable = new HashTable(wordInputList.size());
        for (String input : wordInputList) {
            if (!input.isEmpty()) {
            	int count = hashTable.find(input);
            		if(count == 0) {
            			hashTable.insert(input, 1);
            		}
            		else {
            			hashTable.insert(input, ++count);
            		}
            }
        }        
         hashTableOutput = hashTable.listAllKeys();	
         return hashTableOutput;
	}
	
	//Given an Input Location and output location, reads input string from file and saves output to a new file.
	public void processFileOutput(String inputLocation, String outputLocation) {		
		
		String hashTableOutput = "";
        List<String> wordInputList = new ArrayList<String>();        
		// Code to read the input from file using BufferedReader		
	    try (BufferedReader bufferedInputReader = new BufferedReader(new FileReader(inputLocation))) 
	    {String textLine = null;
            while ((textLine = bufferedInputReader.readLine()) != null) {
                wordInputList.addAll(Arrays.asList(textLine.replaceAll("[^a-zA-Z ]", "").split("\\s+")));
            }
            hashTableOutput = insertIntoHashTable(wordInputList);
            
        } catch (IOException e) {
            e.printStackTrace();
        }	    
	    
	    //Code to write file output
	    Random rand = new Random();		   
		   System.out.println("Creating OutputFile");
	        File file = new File(outputLocation + "Hashing_Output" + rand.nextInt()  + ".txt");
	        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
	            // if file doesn't exists, then create it
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            // get the content in bytes
	            byte[] contentInBytes = hashTableOutput.getBytes();
	            fileOutputStream.write(contentInBytes);
	            fileOutputStream.flush();
	            fileOutputStream.close();
	            long endTime = System.nanoTime();	            
	            System.out.println("Done");
	            System.out.println("Time Taken "+ ((endTime - startTime)/1000000000) + " seconds");
	            System.out.println("Memory Used " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1048576 + " megabytes. ");
	            //credits: https://stackoverflow.com/questions/6646467/how-to-find-time-taken-to-run-java-program
	            System.out.println();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }   		
	}
}