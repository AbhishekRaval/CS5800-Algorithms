package RedBlackTrees;
import java.util.ArrayList;

public class RbTree {
	
	//Initializing Root to Null
	//nil is an empty RedBlackNode used throughout the program.
    public RedBlackNode nil = new RedBlackNode();
    public RedBlackNode root = nil;

    // Instantiates a new Red black tree.
    
    public RbTree() {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }


    public void insert(int key) {
        RedBlackNode rbNode = new RedBlackNode(key);
        insert(rbNode);
    }
    
    // Source. CLRS page 315
	//    RB-INSERT.T; z/
	//    1 y = T.nil
	//    2 x = T.root
	//    3 while x not equals T.nil
	//    4 y = x
	//    5 if z.key < x.key
	//    6 x = x.left
	//    7 else x = x.right
	//    8 z.p = y
	//    9 if y == T.nil
	//    10 T.root = z
	//    11 else if z.key < y.key
	//    12 y.left = z
	//    13 else y.right = z
	//    14 z.left = T.nil
	//    15 z.right = T.nil
	//    16 z.color = RED
	//    17 RB-INSERT-FIXUP.T; z/
    private void insert(RedBlackNode z) {
    	RedBlackNode y = nil;
    	RedBlackNode x = root;
    	while (x != nil && x != null) {
    		y = x;
    		if (z.key < x.key) {
    			//Go Left
    			x = x.left;
    		} else {
    			//Go Right
    			x = x.right;
    		}
    	}
    	z.parent = y;
    	if (y == nil || y == null) {
    		root = z;
    	} else if (z.key < y.key) {
    		y.left = z;
    	} else {
    		y.right = z;
    	}
    	z.left = nil;
    	z.right = nil;
    	z.color = RedBlackNode.Color.RED;
    	insertFixup(z);
    }
    
    // Source. CLRS page 316
	//    RB-INSERT-FIXUP.T; z/
	//    1 while z.p.color == RED
	//    2 if z.p = = z.p.p.left
	//    3 y = z.p.p.right
	//    4 if y.color == RED
	//    5 z.p.color = BLACK // case 1
	//    6 y.color = BLACK // case 1
	//    7 z.p.p.color = RED // case 1
	//    8 z = z.p.p // case 1
	//    9 else if z == z.p.right
	//    10 z z z.p // case 2
	//    11 LEFT-ROTATE.T; z/ // case 2
	//    12 z.p.color = BLACK // case 3
	//    13 z.p.p.color = RED // case 3
	//    14 RIGHT-ROTATE.T; z.p.p/ // case 3
	//    15 else (same as then clause
	//    with “right” and “left” exchanged)
	//    16 T.root.color = BLACK
    private void insertFixup(RedBlackNode z) {
        RedBlackNode y = nil;
        while (z.parent.color == RedBlackNode.Color.RED) {
            if (z.parent == z.parent.parent.left) {
                y = z.parent.parent.right;
                if (y.color == RedBlackNode.Color.RED) {
                    z.parent.color = RedBlackNode.Color.BLACK;
                    y.color = RedBlackNode.Color.BLACK;
                    z.parent.parent.color = RedBlackNode.Color.RED;
                    z = z.parent.parent;
                } else {
                    //Case 2.
                    if (z == z.parent.right) {
                        z = z.parent;
                        rotateLeft(z);
                    }

                    //Case 3.
                    z.parent.color = RedBlackNode.Color.BLACK;
                    z.parent.parent.color = RedBlackNode.Color.RED;
                    rotateRight(z.parent.parent);
                }
            }
            else {
                y = z.parent.parent.left;
                //case1
                if (y.color == RedBlackNode.Color.RED) {
                    z.parent.color = RedBlackNode.Color.BLACK;
                    y.color = RedBlackNode.Color.BLACK;
                    z.parent.parent.color = RedBlackNode.Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = RedBlackNode.Color.BLACK;
                    z.parent.parent.color = RedBlackNode.Color.RED;
                    rotateLeft(z.parent.parent);
                }

            }
        }
        root.color = RedBlackNode.Color.BLACK;
    }

   
    // Source. CLRS page 313
	//	    LEFT-ROTATE.T; x/
	//	    1 y = x.right // set y
	//	    2 x.right = y.left // turn y’s left subtree into x’s right subtree
	//	    3 if y.left not T.nil
	//	    4 y.left.p = x
	//	    5 y.p = x.p // link x’s parent to y
	//	    6 if x.p == T.nil
	//	    7 T.root = y
	//	    8 else if x == x.p.left
	//	    9 x.p.left = y
	//	    10 else x.p.right = y
	//	    11 y.left = x // put x on y’s left
	//	    12 x.p = y

    public void rotateLeft(RedBlackNode x) {
        RedBlackNode y = x.right;
        x.right = y.left;
        if (y.left != nil && y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == nil || x.parent == null) {
            root = y;
        }
        else if (x.parent.left == x) {
            x.parent.left = y;
        }
        else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }
    
    

    public void rotateRight(RedBlackNode y) {
        RedBlackNode x = y.left;
        y.left = x.right;
        if (x.right != nil && x.right != null) {
            x.right.parent = y;
        }

        x.parent = y.parent;
        if (y.parent == nil) {
            root = x;
        }
        else if (y.parent.right == y) {
            y.parent.right = x;
        }
        else {
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;
    }
    
    
    // Source. CLRS page 290
	//    ITERATIVE-TREE-SEARCH.x; k/
	//    1 while x not NIL and k not x.key
	//    2 if k < x.key
	//    3 x = x.left
	//    4 else x = x.right
	//    5 return x
    public RedBlackNode search(int key) {
        RedBlackNode currentNode = root;
        while (currentNode != nil && currentNode != null) {
            if (currentNode.key == key) {
                return currentNode;
            } else if (key > currentNode.key) {
                currentNode = currentNode.right;
            } else {
                currentNode = currentNode.left;
            }
        }
        return null;
    }
    
    // Source. CLRS page 291
	//    TREE-MINIMUM.x/
	//    1 while x.left not NIL
	//    2 x = x.left
	//    3 return x
    public RedBlackNode min(RedBlackNode node) {
        RedBlackNode currentNode = node;
        while (currentNode.left != nil && currentNode.left != null) {
            currentNode = currentNode.left;
        }
        return currentNode;
    }
    
    // Source. CLRS page 291
	//    TREE-MAXIMUM.x/
	//    1 while x.right not NIL
	//    2 x = x.right
	//    3 return x
    public RedBlackNode max(RedBlackNode node) {
        RedBlackNode currentNode = node;
        while (currentNode.right != nil && currentNode.right != null) {
            currentNode = currentNode.right;
        }
        return currentNode;
    }

    //Source. CLRS Page 292
	//    TREE-SUCCESSOR.x/
	//    1 if x.right not NIL
	//    2 return TREE-MINIMUM.x.right/
	//    3 y = x.p
	//    4 while y not NIL and x == y.right
	//    5 x = y
	//    6 y = y.p
	//    7 return y
    public RedBlackNode successor(RedBlackNode node) {
        RedBlackNode currentNode = node;
        if (currentNode.right != nil && currentNode.right != null) {
            return this.min(currentNode.right);
        }
        RedBlackNode y = currentNode.parent;
        while (y != nil && y != null && currentNode == y.right) {
            currentNode = y;
            y = y.parent;
        }
        return y;
    }


    public RedBlackNode predecessor(RedBlackNode node) {
        RedBlackNode currentNode = node;
        if (currentNode.left != nil && currentNode.left != null) {
            return this.max(currentNode.left);
        }
        RedBlackNode x = currentNode.parent;
        while (x != nil && x != null && currentNode == x.left) {
            currentNode = x;
            x = x.parent;
        }
        return x;
    }

	//    INORDER-TREE-WALK.x/
	//    1 if not NIL
	//    2 INORDER-TREE-WALK.x.left/
	//    3 print x.key
	//    4 INORDER-TREE-WALK.x.right/
    public ArrayList<Integer> sort(RedBlackNode node) {
        ArrayList<Integer> sortedList = new ArrayList<>();
        if (node != nil && node != null) {
            sortedList.addAll(sort(node.left));
            sortedList.add(node.key);
            sortedList.addAll(sort(node.right));
        }
        return sortedList;
    }
   
}
