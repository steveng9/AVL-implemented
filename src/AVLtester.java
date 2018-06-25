

import java.util.Random;

public class AVLtester {

    public static void main(String[] args) {
        AVLtree<Integer> avl = new AVLtree<>();
        
        ReplaceableStack<NotComparableObject> s = new ReplaceableStack<>();

        NotComparableObject replaced = new NotComparableObject(4);

        s.push(replaced);
        
        s.push(new NotComparableObject(5));
        s.push(new NotComparableObject(6));
        s.push(new NotComparableObject(7));
        s.push(new NotComparableObject(8));
        s.push(new NotComparableObject(9));
        s.push(new NotComparableObject(10));
        
        s.replaceHighestInstance(new NotComparableObject(12), new NotComparableObject(15));
        
        while (!s.isEmpty()) {
            System.out.print(s.pop().toString());
        }
        
        
        
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
        avl.remove(17);
        avl.remove(16);
        avl.remove(5);
        Random rand = new Random();
        
        for (int i = 0; i <= 100; i++) {
            avl.insert(rand.nextInt(5000));
        }

        for (int i = 0; i <= 50000; i++) {
            avl.remove(rand.nextInt(5000));
        }

        System.out.println(avl.toString());

        System.out.println(avl.size());
    }

}
