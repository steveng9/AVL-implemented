

import java.util.Stack;

public class ReplaceableStack<E> extends Stack<E> {
    
    private static final long serialVersionUID = -1805630063151108403L;

    public boolean replaceHighestInstance(final E theOld, final E theNew) {
        boolean elementExistsInStack = false;
        Stack<E> temporaryStack = new Stack<>();
        while (!this.isEmpty()) {
            E topOfStack = this.pop();
            if (topOfStack == theOld) {
                elementExistsInStack = true;
                this.push(theNew);
                break;
            } else {
                temporaryStack.push(topOfStack);
            }
        }
        while (!temporaryStack.isEmpty()) {
            this.push(temporaryStack.pop());
        }
        return elementExistsInStack;
    }
}
