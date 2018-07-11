

import java.util.Random;

public class AVLtester {

    public static void main(String[] args) {
        AVLtree<Integer> avl = new AVLtree<>();
//        
//        avl.insert(25);
//        avl.insert(20);
//        avl.insert(30);
//        avl.insert(10);
//        avl.insert(23);
//        avl.insert(26);
//        avl.insert(35);
//        avl.insert(5);
//        avl.insert(15);
//        avl.insert(22);
//        avl.insert(24);
//        avl.insert(38);
//        avl.insert(16);
//        avl.insert(21);
//
//        avl.myRotations.clear();
//        avl.remove(21);
//        avl.remove(24);
//        avl.remove(22);
//        System.out.println(avl.toString());
//        System.out.println(avl.isBalanced());
//        System.out.println("Rotations used in deletions: " + avl.myRotations);
        
//        System.out.println(avl.isBalanced());
//        System.out.println(avl.toString());
//        System.out.println(avl.size());
        
        
        Random rand = new Random();
        
        
        for (int i = 0; i <= 100; i++) {
            int inserted = rand.nextInt(5000);
            avl.insert(inserted);
        }
        System.out.println(avl.isBalanced());
        
        
        for (int i = 0; i <= 1000; i++) {
            int removed = rand.nextInt(5000);
            avl.remove(removed);
        }
        System.out.println(avl.size());
        System.out.println(avl.isBalanced());
        
        
//
//        System.out.println(avl.toString());
//        System.out.println(avl.size());
    }

}
