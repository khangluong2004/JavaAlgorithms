package algorithms.data_structures;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class BinaryTreeNode {
    // Could make private for encapsulation
    Long value;
    BinaryTreeNode left;
    BinaryTreeNode right;

    public BinaryTreeNode(Long value){
        this.value = value;    
    }

    public BinaryTreeNode(Long value, BinaryTreeNode left, BinaryTreeNode right){
        this.value = value;
        this.left = left;
        this.right = right;
    }
}

// ------------------------------
// Binary Search Tree
class BSTNode extends BinaryTreeNode {
    // Add in tracking parents for 
    // finding In-order predecessor
    BSTNode left;
    BSTNode right;
    BSTNode parent;

    public BSTNode(Long value){
        super(value);   
    }

    public BSTNode(Long value, BSTNode parent){
        super(value);
        this.parent = parent;   
    }

    public BSTNode(Long value, BSTNode left, BSTNode right, BSTNode parent){
        super(value, left, right);
        this.parent = parent;
    }
}

class BST {
    public BSTNode root;

    public BST(List<Long> input){
        // <= : Left node; > : Right node
        for (Long elem: input){
            this.addElem(elem);
        }
    }


    public void inorderTraverse(ArrayList<Long> valueList, BSTNode curNode){
        // Add all traversed nodes to valueList: Should be empty
        // curNode = rootNode to traverse the entire tree
        // Inorder traversal: All elements are automatically sorted
        // Inorder = the position of add command. 
        // Similarly for post-order and pre-order traversal

        if (curNode == null){
            return;
        }
        
        inorderTraverse(valueList, curNode.left);
        valueList.add(curNode.value);
        inorderTraverse(valueList, curNode.right);
    }

    public void inorderTraverse(BSTNode curNode){
        if (curNode == null){
            return;
        }
        
        inorderTraverse(curNode.left);
        System.out.println(curNode.value);
        inorderTraverse(curNode.right);
    }

    public BSTNode searchNode(Long value){
        // Search value
        BSTNode curNode = this.root;
        while (curNode != null){
            if (curNode.value == value){
                return curNode;
            }

            if (curNode.value > value){
                curNode = curNode.left;
            } else {
                curNode = curNode.right;
            }
        }
        return null;
    }

    public int getHeight(BSTNode curNode){
        // Find the height from curNode
        // Place root to create 

        if (curNode == null){
            return 0;
        }

        return 1 + Math.max(getHeight(curNode.left), getHeight(curNode.right));
    }

    public BSTNode findMin(BSTNode curNode){
        // Go straight to the left most leaf
        // For minimum of the entire tree, set curNode = root
        // cur_Node must not be null
        BSTNode preNode = curNode;
        while (curNode != null){
            preNode = curNode;
            curNode = curNode.left;
        }
        return preNode;
    }

    public BSTNode findMax(BSTNode curNode){
        // Go straight to the right most leaf
        // For maximum of the entire tree, set curNode = root
        // cur_Node must not be null
        BSTNode preNode = curNode;
        while (curNode != null){
            preNode = curNode;
            curNode = curNode.right;
        }
        return preNode;
    }



    public void deleteElem(Long value){
        // Delete the first node containing `value`
        BSTNode deleteNode = searchNode(value);
        BSTNode parentNode = deleteNode.parent;

        // Check if left or right branch of parent
        boolean left = false;
        if (parentNode != null && 
            parentNode.left != null && 
            deleteNode.value == parentNode.left.value){
            left = true;
        }

        // 1. Leaf node
        if (deleteNode.left == null && deleteNode.right == null){
            helper_assign_child(parentNode, null, left);
            return;
        }

        // 2. One-child node
        if (deleteNode.left == null){
            helper_assign_child(parentNode, deleteNode.right, left);
            return;
        } else {
            if (deleteNode.right == null){
                helper_assign_child(parentNode, deleteNode.left, left);
                return;
            }
        }

        // 3. Two-children node
        BSTNode successor = findMin(deleteNode.right);


        // Smallest node larger than deleteNode
        // Transfer all links/edges of delete node to successor
        successor.parent = deleteNode.parent;
        successor.right = deleteNode.right;
        successor.left = deleteNode.left;
        deleteNode.right.parent = successor;
        deleteNode.left.parent = successor;

        // Remove the deleteNode and all its links
        helper_assign_child(parentNode, successor, left);
        deleteNode.parent = null;
        deleteNode.right = null;
        deleteNode.left = null;

        return;
    }

    public void addElem(Long value){
        // Time complexity: O(h) - h: height of tree

        if (this.root == null){
            this.root = new BSTNode(value);
            return;
        }

        BSTNode cur_node = this.root;
        BSTNode pre_node = cur_node;
        boolean right = false;
        while (cur_node != null){
            pre_node = cur_node;
            if (value > cur_node.value){
                cur_node = cur_node.right;
                right = true;
            } else {
                cur_node = cur_node.left;
                right = false;
            }
        }

        if (right){
            pre_node.right = new BSTNode(value, pre_node);
        } else {
            pre_node.left = new BSTNode(value, pre_node);
        }

    }


    protected void helper_assign_child(BSTNode parentNode, BSTNode childNode, boolean left){
        childNode.parent = parentNode;
        if (parentNode == null){
            this.root = childNode;
            return;
        }

        if (left){
            parentNode.left = childNode;
        } else {
            parentNode.right = childNode;
        }
    }


}

// ------------------------------
// Red-black Tree: Balanced BST
// Due to complexity, nodeDeletion will cover later on :D

class RedBlackNode extends BSTNode{
    // Inherited value
    RedBlackNode left;
    RedBlackNode right;
    RedBlackNode parent;
    boolean isRed;

    public RedBlackNode(Long value, boolean isRed){
        super(value);
        this.isRed = isRed;
    }

    public RedBlackNode(Long value, RedBlackNode parent, boolean isRed){
        super(value, parent);
        this.isRed = isRed;
    }

    public RedBlackNode(Long value, RedBlackNode left, 
                        RedBlackNode right, RedBlackNode parent, boolean isRed){
        super(value, left, right, parent);
        this.isRed = isRed;
    }
}

class RedBlackTree extends BST {
    // Difference: Deletion and Insertion
    public RedBlackTree(List<Long> input){
        // input: List of values of nodes
        super(input);
    }

    private void rotateRight(RedBlackNode curNode){
        // curNode must be the left-child of parent
        // parent != null
        RedBlackNode parent = curNode.parent;
        RedBlackNode grandparent = parent.parent;
        RedBlackNode right_child = curNode.right;

        // Move parent to right_child
        curNode.right = parent;
        parent.parent = curNode;

        // Move right_child to parent's new left child
        parent.left = right_child;
        right_child.parent = parent;

        // Connect curNode to grandparent
        helper_assign_child(grandparent, curNode, 
            parent.value == grandparent.left.value);
    }

    private void rotateLeft(RedBlackNode curNode){
        // curNode must be the right-child of parent
        // parent != null
        RedBlackNode parent = curNode.parent;
        RedBlackNode grandparent = parent.parent;
        RedBlackNode left_child = curNode.left;

        // Move parent to left_child
        curNode.left = parent;
        parent.parent = curNode;

        // Move left_child to parent's new right child
        parent.right = left_child;
        left_child.parent = parent;

        // Connect curNode to grandparent
        helper_assign_child(grandparent, curNode, 
            parent.value == grandparent.left.value);
    }
}

//------------------------------
// Testing

public class TreePrac {
    public static void main(String[] args) {
        System.out.println("BINARY TREE:");
        List<Long> nodes = new ArrayList<Long>(Arrays.asList(1l, 2l, 176l, 19l, 99l));
        BST testBst = new BST(nodes);
        testBst.inorderTraverse(testBst.root);

        // Add
        System.out.println("\n Add: 25");
        testBst.addElem(25l);
        testBst.inorderTraverse(testBst.root);

        // Delete
        System.out.println("\n Remove: 1");
        testBst.deleteElem(1l);
        testBst.inorderTraverse(testBst.root);

        // Status
        System.out.println("\n Helper stats:");
        System.out.println("Minimum node val: " + testBst.findMin(testBst.root).value);
        System.out.println("Maximum node val: " + testBst.findMax(testBst.root).value);
        System.out.println("Height: " + testBst.getHeight(testBst.root));

    }
}