

import java.util.Random;

public class AVLtester {

    public static void main(String[] args) {
        AVLtree<Integer> avl = new AVLtree<>();
        
        avl.insert(25);
        avl.insert(20);
        avl.insert(30);
        avl.insert(10);
        avl.insert(23);
        avl.insert(26);
        avl.insert(35);
        avl.insert(5);
        avl.insert(15);
        avl.insert(22);
        avl.insert(24);
        avl.insert(38);
        avl.insert(16);
        avl.insert(21);
        
        avl.remove(25);
        avl.remove(26);
        
        System.out.println(avl.toString());
        System.out.println(avl.size());
        
        
//        Random rand = new Random();
//        
//        for (int i = 0; i <= 100; i++) {
//            avl.insert(rand.nextInt(5000));
//        }
//        for (int i = 0; i <= 50000; i++) {
//            avl.remove(rand.nextInt(5000));
//        }
//
//        System.out.println(avl.toString());
//        System.out.println(avl.size());
    }

}
