package com.yufa.xz.nio.ss;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author admin
 * @data 2020/8/28
 */
public class IterTest {

    public static void main(String[] args) {
        Set<Integer> integerSet = new HashSet<>();
        integerSet.add(1);
        integerSet.add(2);
        integerSet.add(3);
        integerSet.add(4);
        integerSet.add(5);

        Iterator<Integer> iterator = integerSet.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
            iterator.remove();
        }

        Iterator<Integer> iterator2 = integerSet.iterator();


    }
}
