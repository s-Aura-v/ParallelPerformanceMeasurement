package com.performance_measurement.perforamancemeasurement.Classes;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This is an implementation of SkipList from baeldung.
 * https://www.baeldung.com/java-skiplist
 */
public class SkipList {
    private SkipListNode head;
    private int maxLevel;
    private int level;

    public SkipList() {
        maxLevel = 4;
        level = 0;
        head = new SkipListNode(Integer.MIN_VALUE, maxLevel);
    }

    public void insert(int value) {
        SkipListNode[] update = new SkipListNode[maxLevel + 1];
        SkipListNode current = this.head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }

        current = current.forward[0];

        if (current == null || current.value != value) {
            int lvl = ThreadLocalRandom.current().nextInt(maxLevel + 1);

            if (lvl > level) {
                for (int i = level + 1; i <= lvl; i++) {
                    update[i] = head;
                }
                level = lvl;
            }

            SkipListNode newNode = new SkipListNode(value, lvl);
            for (int i = 0; i <= lvl; i++) {
                newNode.forward[i] = update[i].forward[i];
                update[i].forward[i] = newNode;
            }
        }
    }

    public boolean search(int value) {
        SkipListNode current = this.head;
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
        }
        current = current.forward[0];
        return current != null && current.value == value;
    }

    public void delete(int value) {
        SkipListNode[] update = new SkipListNode[maxLevel + 1];
        SkipListNode current = this.head;

        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value < value) {
                current = current.forward[i];
            }
            update[i] = current;
        }
        current = current.forward[0];

        if (current != null && current.value == value) {
            for (int i = 0; i <= level; i++) {
                if (update[i].forward[i] != current) break;
                update[i].forward[i] = current.forward[i];
            }

            while (level > 0 && head.forward[level] == null) {
                level--;
            }
        }
    }
}
