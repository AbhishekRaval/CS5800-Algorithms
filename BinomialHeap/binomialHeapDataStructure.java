package BinomialHeap;

public class binomialHeapDataStructure {

    public int value;
    public binomialHeapDataStructure parent;
    //this.binomialHeapDataStructure's leftmost child
    public binomialHeapDataStructure child;
  //this.binomialHeapDataStructure's right sibling
    public binomialHeapDataStructure sibling;
    public int amountOfChilds;

    public binomialHeapDataStructure(int v) {
        value = v;
        parent = null;
        child = null;
        sibling = null;
        amountOfChilds = 0;
    }

    @Override
    public String toString() {
        return "key = " + value + ", has " + amountOfChilds + " children";
    }
    
    public String printTree(int depth) {
        String result = "";
        for (int i = 0; i < depth; i++) {
            result += "  ";
        }
        result += toString() + "\n";
        binomialHeapDataStructure x = child;
        // goes recursively through every child of a given node
        while (x != null) {
            result += x.printTree(depth + 1);
            x = x.sibling;
        }

        return result;
    }
}