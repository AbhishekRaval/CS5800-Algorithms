package RedBlackTrees;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class redBlackDataStructures {
	//Code for Testing Data Structures
}
class RedBlackNode{
    public enum Color {
        RED,BLACK
    }

    public int key;
    public Color color;
    public RedBlackNode left;
    public RedBlackNode right;
    public RedBlackNode parent;


    public RedBlackNode(){
        color = Color.BLACK;
        parent = null;
        left = null;
        right = null;
    }

    public RedBlackNode(int key) {
        this();
        this.key = key;
    }

}

//Code to print tree: https://stackoverflow.com/a/4973083
//The following source code is taken from stack overflow
class printTree{
    
    public static void printRBTree(RedBlackNode root) {
        int maxLevel = maxLevel(root);
        printRedBlackNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    

    private static void printRedBlackNodeInternal(List<RedBlackNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 2;

        printWhitespaces(firstSpaces-floor);

        List<RedBlackNode> newRedBlackNodes = new ArrayList<RedBlackNode>();
        for (RedBlackNode node : nodes) {
            if (node != null && node.key!=0) {
                System.out.print((node.color == RedBlackNode.Color.BLACK ? "B" : "R") + node.key);
                newRedBlackNodes.add(node.left);
                newRedBlackNodes.add(node.right);
            } else {
                newRedBlackNodes.add(null);
                newRedBlackNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null && nodes.get(j).left.key!=0)
                    System.out.print("/");
                else
                    printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null && nodes.get(j).right.key!=0 )
                    System.out.print("\\");
                else
                    printWhitespaces(1);

                printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printRedBlackNodeInternal(newRedBlackNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static int maxLevel(RedBlackNode node) {
        if (node == null)
            return 0;

            return Math.max(node.left.key==0?0:maxLevel(node.left), node.right.key==0?0:maxLevel(node.right)) + 1;
    }

    private static boolean isAllElementsNull(List list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
}
// Till here the code is obtained from stack over flow, Author : Michal Kruzman