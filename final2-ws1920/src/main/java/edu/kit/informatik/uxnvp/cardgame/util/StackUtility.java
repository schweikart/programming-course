package edu.kit.informatik.uxnvp.cardgame.util;

import java.util.Stack;

/**
 * Utility class for working with Java-stacks.
 * @author Max Schweikart
 * @version 1.0
 */
public final class StackUtility {
    /**
     * Do not instantiate utility classes.
     * @throws AssertionError utility classes should not be instantiated.
     */
    private StackUtility() {
        throw new AssertionError("Utility classes should not be instantiated.");
    }

    /**
     * Creates a (shallow) copy of a stack.
     * @param stack the stack to copy.
     * @param <E> the content type of the stack.
     * @return a copy of the stack.
     */
    public static <E> Stack<E> clone(Stack<E> stack) {
        Stack<E> temp = new Stack<>();
        moveAll(stack, temp);

        Stack<E> clone = new Stack<>();
        while (!temp.isEmpty()) {
            E item = temp.pop();
            stack.push(item);
            clone.push(item);
        }

        return clone;
    }

    /**
     * Moves all elements from one stack to another. The elements of the source stack will be in reversed order in the
     * destination stack.
     * @param src the stack to move elements from.
     * @param dest the stack to move elements to.
     * @param <T> the content type of the stacks.
     */
    public static <T> void moveAll(Stack<T> src, Stack<T> dest) {
        while (!src.isEmpty()) {
            dest.push(src.pop());
        }
    }

    /**
     * Count elements in a stack that are equal to a given element
     * @param stack the stack to count elements in.
     * @param element the element to count, may not be null.
     * @param <T> the content type of the stack.
     * @return the amount of elements in the stack that are equal to the given element.
     */
    public static <T> int count(Stack<T> stack, T element) {
        int amount = 0;
        for (T stackElement : stack) {
            if (element.equals(stackElement)) {
                amount++;
            }
        }
        return amount;
    }
}
