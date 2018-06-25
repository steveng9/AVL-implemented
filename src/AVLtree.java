/*
 * 
 */


import java.util.Stack;




/**
 * A self-balancing storage unit that performs insertions and deletions in logarithmic time.
 * Stores objects of type Comparable.
 * 
 * @author Steven Golob 
 * @version June 24, 2018
 */
public class AVLtree<AnyType extends Comparable<AnyType>> {
    
    private AVLnode<AnyType> myRoot;
    
    private int mySize;
    
    public AVLtree() {
        myRoot = null;
        mySize = 0;
    }
    
    //________________________________________________________________________________________
    
    public int size() {
        return mySize;
    }
    
    public boolean isEmpty() {
        return mySize == 0;
    }
    
    public void insert(final AnyType theData) {
        AVLnode<AnyType> insertedNode = new AVLnode<>(theData);
        AVLnode<AnyType> checkSpot = myRoot;
        boolean checkSpotIsRightChild = false;
        Stack<AVLnode<AnyType>> ancestors = new Stack<>();
        
        while (checkSpot != null) {
            ancestors.push(checkSpot);
            if (insertedNode.compareTo(checkSpot) <= 0) {
                checkSpot = checkSpot.myLeftChild;
                checkSpotIsRightChild = false;
            } else {
                checkSpot = checkSpot.myRightChild;
                checkSpotIsRightChild = true;
            }
        }
        
        if (ancestors.isEmpty()) {
            myRoot = insertedNode;
        } else {
            if (checkSpotIsRightChild) {
                ancestors.peek().myRightChild = insertedNode;
            } else {
                ancestors.peek().myLeftChild = insertedNode;
            }
        }
        ancestors.push(insertedNode);
        updateHeightsAfterInsert(ancestors);
        mySize++;
    }
    
    private void updateHeightsAfterInsert(final Stack<AVLnode<AnyType>> thePath) {
        AVLnode<AnyType> thisNode = thePath.pop();
        int minimumCorrectHeight = 0;
        while (!thePath.isEmpty()) {
            thisNode = thePath.pop();
            minimumCorrectHeight++;
            if (!thisNode.isBalanced()) {
                performRotation(thisNode, thePath);
                break; // all above nodes need not changing
            }
            if (thisNode.myHeight < minimumCorrectHeight) thisNode.updateHeight();
            else break; // all above heights need not changing
        }
    }

    private void performRotation(final AVLnode<AnyType> theImbalancedNode, final Stack<AVLnode<AnyType>> thePath) {
        AVLnode<AnyType> parent = thePath.isEmpty() ? null : thePath.peek();
        if (theImbalancedNode.isLeftChildTaller()) {
            if (theImbalancedNode.myLeftChild.isLeftChildTaller()) {
                rotateWithLeftChild(theImbalancedNode, parent);
            } else { 
                rotateLeftRightImbalance(theImbalancedNode, parent);
            }
        } else {
            if (theImbalancedNode.myRightChild.isLeftChildTaller()) {
                rotateRightLeftImbalance(theImbalancedNode, parent);
            } else {
                rotateWithRightChild(theImbalancedNode, parent);
            }
        }
    }
    
    private void rotateWithRightChild(final AVLnode<AnyType> theNode, final AVLnode<AnyType> theParent) {
        AVLnode<AnyType> rightChild = theNode.myRightChild;
        theNode.myRightChild = rightChild.myLeftChild;
        rightChild.myLeftChild = theNode;
        rightChild.myLeftChild.myHeight--;
        attachRotatedUpNode(theNode, rightChild, theParent);
    }
    
    private void rotateLeftRightImbalance(final AVLnode<AnyType> theNode, final AVLnode<AnyType> theParent) {
        rotateWithRightChild(theNode.myLeftChild, theNode);
        rotateWithLeftChild(theNode, theParent);
        theNode.myLeftChild.myHeight++;
    }
    
    private void rotateWithLeftChild(final AVLnode<AnyType> theNode, final AVLnode<AnyType> theParent) {
        AVLnode<AnyType> leftChild = theNode.myLeftChild;
        theNode.myLeftChild = leftChild.myRightChild;
        leftChild.myRightChild = theNode;
        leftChild.myRightChild.myHeight--;
        attachRotatedUpNode(theNode, leftChild, theParent);
    }
    
    private void rotateRightLeftImbalance(final AVLnode<AnyType> theNode, final AVLnode<AnyType> theParent) {
        rotateWithLeftChild(theNode.myRightChild, theNode);
        rotateWithRightChild(theNode, theParent);
        theNode.myRightChild.myHeight++;
    }
    
    private void attachRotatedUpNode(final AVLnode<AnyType> theOriginalImbalancedNode, 
            final AVLnode<AnyType> theRotatedUp, final AVLnode<AnyType> theParent) {
        if (theParent == null) {
            myRoot = theRotatedUp;
        } else {
            if (theParent.myLeftChild == theOriginalImbalancedNode) {
                theParent.myLeftChild = theRotatedUp;
            } else {
                theParent.myRightChild = theRotatedUp;
            }
        }
    }
    
    public boolean remove(final AnyType theData) {
        return remove(new AVLnode<>(theData));
    }

    public boolean remove(final AVLnode<AnyType> theRemovedNode) {
        AVLnode<AnyType> checkSpot = myRoot, parentOfCheckSpot = null;
        ReplaceableStack<AVLnode<AnyType>> pathFromRootToParentOfReplacement = new ReplaceableStack<>();
        boolean nodeExists = false;
        
        while (checkSpot != null) {
            if (checkSpot.compareTo(theRemovedNode) == 0) {
                replaceNode(checkSpot, pathFromRootToParentOfReplacement);
                nodeExists = true;
                break;
            } else {
                parentOfCheckSpot = checkSpot;
                if (parentOfCheckSpot.compareTo(theRemovedNode) < 0)
                    checkSpot = parentOfCheckSpot.myRightChild;
                else
                    checkSpot = parentOfCheckSpot.myLeftChild;
            }
            pathFromRootToParentOfReplacement.push(parentOfCheckSpot);
        }
        if (nodeExists) mySize--;
        return nodeExists;
    }

    private void replaceNode(final AVLnode<AnyType> theRemovedNode, final ReplaceableStack<AVLnode<AnyType>> thePath) {
        AVLnode<AnyType> replacement = null;
        AVLnode<AnyType> parentOfRemoved = thePath.isEmpty() ? null : thePath.peek();
        thePath.push(theRemovedNode);
        if (theRemovedNode.myLeftChild != null)
            replacement = removeLargestDescendent(theRemovedNode.myLeftChild, thePath);
        else if (theRemovedNode.myRightChild != null)
            replacement = removeSmallestDescendent(theRemovedNode.myRightChild, thePath);
        
        if (replacement != null) {
            replacement.myLeftChild = theRemovedNode.myLeftChild;
            replacement.myRightChild = theRemovedNode.myRightChild;
        }
        if (parentOfRemoved == null) myRoot = replacement;
        else if (parentOfRemoved.myLeftChild == theRemovedNode) 
            parentOfRemoved.myLeftChild = replacement;
        else 
            parentOfRemoved.myRightChild = replacement;
        
        thePath.replaceHighestInstance(theRemovedNode, replacement);
        updateHeightsAfterDeletion(thePath);
    }

    private AVLnode<AnyType> removeSmallestDescendent(final AVLnode<AnyType> theSubRoot, final Stack<AVLnode<AnyType>> thePath) {
        AVLnode<AnyType> smallestDescendent = theSubRoot, parentOfSmallest = thePath.peek(), originalParent = thePath.peek();
        while (smallestDescendent.myLeftChild != null) {
            parentOfSmallest = smallestDescendent;
            smallestDescendent = smallestDescendent.myLeftChild;
            thePath.push(parentOfSmallest);
        }
        if (parentOfSmallest == originalParent) {
            parentOfSmallest.myRightChild = smallestDescendent.myRightChild;
        } else {
            parentOfSmallest.myLeftChild = smallestDescendent.myRightChild;
        }
        return smallestDescendent;
    }

    private AVLnode<AnyType> removeLargestDescendent(final AVLnode<AnyType> theSubRoot, final Stack<AVLnode<AnyType>> thePath) {
        AVLnode<AnyType> largestDescendent = theSubRoot, parentOfLargest = thePath.peek(), originalParent = thePath.peek();
        while (largestDescendent.myRightChild != null) {
            parentOfLargest = largestDescendent;
            largestDescendent = largestDescendent.myRightChild;
            thePath.push(parentOfLargest);
        }
        if (parentOfLargest == originalParent) {
            originalParent.myLeftChild = largestDescendent.myLeftChild;
        } else {
            parentOfLargest.myRightChild = largestDescendent.myLeftChild;
        }
        return largestDescendent;
    }

    private void updateHeightsAfterDeletion(final Stack<AVLnode<AnyType>> thePath) {
        AVLnode<AnyType> nodeOnPath;
        while (!thePath.isEmpty()) {
            nodeOnPath = thePath.pop();
            if (nodeOnPath != null) {
                nodeOnPath.updateHeight();
                if (!nodeOnPath.isBalanced()) {
                    nodeOnPath.myHeight--;
                    performRotation(nodeOnPath, thePath);
                }
            }
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder string = new StringBuilder();
        string.append("Data / Height\n");
        string.append("-------------\n");
        buildString(myRoot, string);
        return string.toString();
    }
    
    private void buildString(final AVLnode<AnyType> theNode, final StringBuilder theString) {
        if (theNode != null) {
            theString.append(theNode.toString());
            buildString(theNode.myLeftChild, theString);
            buildString(theNode.myRightChild, theString);
        }
    }
    
    
    /**
     * A storage-capsule for one data element that is connected to the tree.
     * 
     * @author Steven Golob 
     * @version June 24, 2018
     */
    private class AVLnode<Type> implements Comparable<AVLnode<Type>>{
        
        private int myHeight;
        
        private Type myData;
        
        private AVLnode<Type> myLeftChild;
        
        private AVLnode<Type> myRightChild;
        
        AVLnode (final Type theData) {
            this(theData, null, null, 0);
        }
        
        AVLnode (final Type theData, final AVLnode<Type> theLeft, final AVLnode<Type> theRight, final int theHeight) {
            myData = theData;
            myLeftChild = theLeft;
            myRightChild = theRight;
            myHeight = theHeight;
        }
        
        private boolean isBalanced() {
            int balance = Math.abs(getLeftChildHeight() - getRightChildHeight());
            return balance <= 1;
        }
        
        private int getLeftChildHeight() {
            if (myLeftChild == null) return -1;
            return myLeftChild.myHeight;
        }

        private int getRightChildHeight() {
            if (myRightChild == null) return -1;
            return myRightChild.myHeight;
        }
        
        private boolean isLeftChildTaller() {
            return getLeftChildHeight() > getRightChildHeight();
        }
        
        private void updateHeight() {
            myHeight = Math.max(getLeftChildHeight(), getRightChildHeight()) + 1;
        }

        @Override
        @SuppressWarnings("unchecked")
        public int compareTo(final AVLnode<Type> theOther) {
            return ((Comparable<Type>) myData).compareTo((Type) theOther.myData);
        }
        
        @Override
        public String toString() {
            return myData + " " + myHeight + "\n";
        }
    }
}