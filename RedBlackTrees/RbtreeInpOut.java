package RedBlackTrees;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RbtreeInpOut {

	public static void main(String[] args) {
		String inputFile = "D:\\Projects\\Java2\\CS5800_Algorithms\\src\\RedBlackTrees\\input\\rbtree.txt";
		interactiveTest(inputFile);
	}  

	public static List<String> getInput(String inputLocation) {
		List<String> intNumList = new ArrayList<String>();        
		// Code to read the input from file using BufferedReader		
		try (BufferedReader bufferedInputReader = new BufferedReader(new FileReader(inputLocation))) 
		{String textLine = null;
		while ((textLine = bufferedInputReader.readLine()) != null) {
			intNumList.addAll(Arrays.asList(textLine.split(",")));
		}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return intNumList;
	}

	/**
	 * Interactive test.
	 */
	public static void interactiveTest(String inputFile) {
		List<String> input = getInput(inputFile);

		RbTree redBlackTree = null;

		Scanner reader = new Scanner(System.in);  // Reading from System.in

		int userInput = -1;
		System.out.println("Entered Input String from file is " + input);
		System.out.println("Do you want to Build, the tree ? Enter 1 to go ahead, any other number to start building your own tree");
		int inpyes = reader.nextInt();
		
		if(inpyes == 1)
		{
			if (input != null && !input.isEmpty()) {
				redBlackTree = new RbTree();
				for (String s : input) {
					redBlackTree.insert(Integer.parseInt(s));
					printTree.printRBTree(redBlackTree.root);
				}
			} else {
				System.out.println("Please provide input file.");
			}
		}
		
		else {
			System.out.println("Enter Numbers to build the tree, stop to exit entering number");
			redBlackTree = new RbTree();
			List<String> inputUser = new ArrayList<String>();
			String inUser = "a";
			reader.nextLine();
			while(!inUser.equalsIgnoreCase("exit"))
			{
				inUser = reader.nextLine();
				if(inUser.isEmpty())
				{
					System.out.println("Enter Number, can't insert blank");
				}
				else if(!inUser.equalsIgnoreCase("exit")) {
				redBlackTree.insert(Integer.parseInt(inUser));
				printTree.printRBTree(redBlackTree.root);}
			}
		}
		
		
		RedBlackNode tempNode = null;
		while (!(userInput == 11)) {
			System.out.println("Welcome to RTB Operations. Available commands are :");
			System.out.println("1.  Insert a New element ");
			System.out.println("2.  Print ");
			System.out.println("3.  Rotate Right");
			System.out.println("4.  Rotate Left ");
			System.out.println("5.  Search | Key");
			System.out.println("6.  Min | Key");
			System.out.println("7.  Max | Key");
			System.out.println("8.  Successor | Key");
			System.out.println("9.  Predecessor | Key");
			System.out.println("10. Sort");
			System.out.println("11. Exit ");
			System.out.println("Enter the Number, to do the operation, Ex: 1 to Insert");
			userInput = reader.nextInt(); // Scans the next token of the input as an int.
			int key=0;
			switch (userInput) {
			//Insert into tree
			case 1:
				System.out.println("Performing Insertion in Tree.");
					System.out.println("Enter Number, you wish to insert in the tree.");
					 key = reader.nextInt();
					redBlackTree.insert(key);
					printTree.printRBTree(redBlackTree.root);			
				break;
				//Print	
			case 2:
				System.out.println("Executing PRINT OPERATION");
					printTree.printRBTree(redBlackTree.root);
				break;
				//Rotate Right
			case 3:
				System.out.println("Performing Rotate Right operation.");
					redBlackTree.rotateRight(redBlackTree.root);
					printTree.printRBTree(redBlackTree.root);
				break;
				//Rotate Left	
			case 4:
				System.out.println("Performing Rotate Left	operation.");
					redBlackTree.rotateLeft(redBlackTree.root);
					printTree.printRBTree(redBlackTree.root);
				break;
				//Search	
			case 5:
				System.out.println("Performing Search operation.");
					System.out.println("Enter Number, you wish to search");
					key = reader.nextInt();
					RedBlackNode search = (redBlackTree.search(key));
					if (search == null) {
						System.out.println("tempNode not found");							
					}
					else
					{
						printTree.printRBTree(search);
					}
				break;
				//Min	
			case 6:
				System.out.println("Performing Min operation.");
					System.out.println("Enter Number");
					key = reader.nextInt();
					tempNode = redBlackTree.search(key);
					System.out.println("Minimum number from current node is: " + redBlackTree.min(tempNode).key);
					printTree.printRBTree(redBlackTree.min(tempNode));
				break;
				//Max
			case 7:
				System.out.println("Performing Max operation.");
					System.out.println("Enter Node to find Maximum from.");
					key = reader.nextInt();
					tempNode = redBlackTree.search(key);
					System.out.println("Maximum number from current node is: " + redBlackTree.max(tempNode).key);
					printTree.printRBTree(redBlackTree.max(tempNode));
				break;
				//successor	
			case 8:
				System.out.println("Performing successor operation.");
					System.out.println("Enter the number, whose successor, you wish to find.");
					 key = reader.nextInt();
					 tempNode = redBlackTree.search(key);
					 System.out.println("Successor to " + key + " is: " + redBlackTree.successor(tempNode).key);
					printTree.printRBTree(redBlackTree.successor(tempNode));
				break;
				//predecessor	
			case 9:
				System.out.println("Performing predecessor operation.");
					System.out.println("Enter the number, whose predecessor, you wish to find.");
					 key = reader.nextInt();
					 tempNode = redBlackTree.search(key);
					 System.out.println("Predecessor to " + key + " is: " + redBlackTree.predecessor(tempNode).key);
					printTree.printRBTree(redBlackTree.predecessor(tempNode));
			
				break;
				//sort	
			case 10:
				System.out.println("Performing sort operation.");
					for (int key1 : redBlackTree.sort(redBlackTree.root)) {
						System.out.print(key1 + " , ");
					}
					System.out.println("Done Sorting");
				break;			    
			case 11:
				System.out.println("Quitting Interactive shell.");
				break;
			default:
				System.out.println("Insert Between 1-11, to perform operation, 11 to exit.");
				break;
			}

		}
	}
}