/**
 * 
 */



import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Steven Golob 
 * @version
 */
class AVL_Test_Case {
//
//    /**
//     * @throws java.lang.Exception
//     */
//    @Before
//    void setUp() throws Exception {
//    }
//
//    @Test
//    void testConstructor() {
//    }
//    
//    @Test
//    void testAddPlacement() {
//        
//    }
//    
//    @Test
//    void testAddRightRightImbalance() {
//        
//    }
//    
//    @Test
//    void testAddRightLeftImbalance() {
//        
//    }
//    
//    @Test
//    void testAddLeftRightImbalance() {
//        
//    }
//    
//    @Test
//    void testAddLeftLeftImbalance() {
//        
//    }
//
//    @Test
//    void testDeleteRightRightImbalance() {
//        
//    }
//    
//    @Test
//    void testDeleteRightLeftImbalance() {
//        
//    }
//    
//    @Test
//    void testDeleteLeftRightImbalance() {
//        
//    }
//    
//    @Test
//    void testDeleteLeftLeftImbalance() {
//        
//    }
//
//    
    @Test
    void testLogarithmicHeight() {
        AVLtree<Double> tree = new AVLtree<>();
        tree.insert(.5);
        tree.insert(1.7);
        assertTrue(tree.isBalanced());
    }
//    
//    @Test
//    void testGenerics() {
//        
//    }

}
