import java.util.LinkedList;

public class Linked_List_Intersection {
	
	static class Linkedlist {
		 int nodeVal ;
		 Linkedlist next;
		 Linkedlist(int val)
		 {
			 nodeVal = val;
			 next = null;
		 }
		}
	
	//returns count of LinkedList
	public 	static int getCount(Linkedlist list)
	{
		int cnt = 0;
		while(list!=null)
		{
			cnt++;
			list = list.next;
		}
		return cnt;
	}

	//Finds Insertion Point and prints it alongwith address
	public static void listIntersectionNodes(Linkedlist listA, Linkedlist listB)
	{
		int flag = 0;
		Linkedlist result = null;
		Linkedlist currentA  =listA;
		Linkedlist currentB = listB;

		int sizeA = getCount(currentA);
		int sizeB = getCount(currentB);
		
		int diff = sizeA - sizeB;
		//reassigning temp variables, because linkedList would be traversed while counting
		// total number of elements.
		 currentA  =listA;
		 currentB = listB;
		 
		 //setting offset according to size of lists.		
		if(diff>0)
		{
			while(diff> 0)
			{
				currentA = currentA.next;
				diff--;
			}
		}
		else
		{
		diff = Math.abs(diff);	
			while(diff >0)
			{
				currentB = currentB.next;
				diff--;
			}
		}
		
		while(currentA!=null && currentB!=null)
		{
			if(currentA == currentB) 
			{
				flag=1;
				result = currentA;	
				break;
			}
			currentA = currentA.next;
			currentB = currentB.next;
		}
		
		if(flag==1)
		{
			System.out.println("result found at : " + result.nodeVal + " || address: " + result.next);
		}
		else
		{
			System.out.println("Not found");
		}
		
	}
	

	public static void main(String[] args) {		
		Linkedlist head1 = new Linkedlist(10) ;
		head1.next = new Linkedlist(20);
		head1.next.next = new Linkedlist(30);
		head1.next.next.next = new Linkedlist(40);
		head1.next.next.next.next = new Linkedlist(50);
		
		Linkedlist head2 = new Linkedlist(59);
		head2.next = head1;
		
		//Finding Intersection 
	listIntersectionNodes(head1,head2);
	}

}