package Lesson22HW20HashMap;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyHashMap<K, V> implements MyMap<K, V> {

    private int size = 16;
    private int entryCounter = 0;
    private final int loadFactor = 75;

    private Entry<K, V>[] hashTable;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        hashTable = new Entry[size];
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initSize) {
        hashTable = new Entry[initSize];
        size = initSize;
    }

    public int getSize() {
        return size;
    }

    public int getEntryCounter() {
        return entryCounter;
    }

    private static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;
        Entry<K, V> previous;

        Entry(K k, V v) {
            key = k;
            value = v;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "[key=" + key +
                    ", value=" + value +
                    ']';
        }
    }

    public MyIterator<Entry<K, V>> myIterator() {
        return new MyIterator<>() {
            private Entry<K, V> currentEntry = null;
            private Entry<K, V> nextEntry = null;
            private int index = 0;


            @Override
            public boolean hasNext() {
                if (index == 0) {
                    for (int i = 0; i < size; i++) {
                        if (hashTable[i] != null) {
                            return true;
                        }
                    }
                }
                return nextEntry != null;
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (index == 0) {
                    for (int i = 0; i < size; i++) {
                        if (hashTable[i] != null) {
                            currentEntry = hashTable[i];
                            index++;
                            if (currentEntry.next == null) {
                                for (int j = i + 1; j < size; j++) {
                                    if (hashTable[j] != null) {
                                        nextEntry = hashTable[j];
                                        return currentEntry;
                                    }
                                }
                            } else {
                                nextEntry = currentEntry.next;
                            }
                            return currentEntry;
                        }
                    }
                } else {
                    if (nextEntry != null) {
                        currentEntry = nextEntry;
                        index++;
                        if (currentEntry.next == null) {
                            for (int j = getHash(currentEntry.key) + 1; j < size; j++) {
                                if (hashTable[j] != null) {
                                    nextEntry = hashTable[j];
                                    return currentEntry;
                                } else {
                                    nextEntry = null;
                                }
                            }
                        } else {
                            nextEntry = currentEntry.next;
                        }
                        return currentEntry;
                    }
                }
                return null;
            }

            @Override
            public void remove() {
                if (currentEntry == null) {
                    throw new IllegalStateException();
                }
                unlink(currentEntry);
                currentEntry = null;
                entryCounter--;
            }

            @Override
            public Entry<K, V> getCurrent() {
                return currentEntry;
            }
        };
    }

    private void unlink(Entry<K, V> entry) {
        Entry<K, V> next = entry.next;
        Entry<K, V> previous = entry.previous;

        if (previous == null) {
            hashTable[entry.key.hashCode() % size] = next;
        } else {
            previous.next = next;
            entry.previous = null;
        }
        if (next != null) {
            next.previous = previous;
            entry.next = null;
        }
    }

    @SuppressWarnings("unchecked")
    private void increaseCapacity() {
        int tempCounter = entryCounter;
        entryCounter = 0;
        size *= 2;
        Entry<K, V>[] tempTable = hashTable;
        hashTable = new Entry[size];
        for (int i = 0; i < size / 2; i++) {
            if (tempTable[i] != null) {
                put(tempTable[i].key, tempTable[i].value);
                tempCounter--;
                if (tempTable[i].next != null) {
                    Entry<K, V> next = tempTable[i].next;
                    while (next != null) {
                        put(next.key, next.value);
                        tempCounter--;
                        next = next.next;
                    }
                }
            }
            if (tempCounter == 0) {
                break;
            }
        }
    }

    public int getHash(K k) {
        return k.hashCode() % size;
    }

    public boolean containsKey(K k) {
        int hash = getHash(k);
        Entry<K, V> entry = hashTable[hash];

        while (entry != null) {
            if (entry.key.equals(k)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return entryCounter == 0;
    }

    public void put(K k, V v) {
        int hash = getHash(k);
        Entry<K, V> entry = hashTable[hash];

        if (entry != null) {
            if (entry.key.equals(k)) {
                entry.value = v;
                return;
            } else {
                while (entry.next != null) {
                    entry = entry.next;
                    if (entry.key.equals(k)) {
                        entry.value = v;
                        return;
                    }
                }
                entry.next = new Entry<>(k, v);
                entry.next.previous = entry;
            }
        } else {
            hashTable[hash] = new Entry<>(k, v);
        }
        entryCounter++;
        if (entryCounter > size * loadFactor / 100) {
            increaseCapacity();
        }
    }

    public boolean remove(K k) {
        int hash = getHash(k);
        Entry<K, V> entry = hashTable[hash];

        while (entry != null) {
            if (entry.key.equals(k)) {
                unlink(entry);
                entryCounter--;
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    public V get(K k) {
        int hash = getHash(k);
        Entry<K, V> entry = hashTable[hash];

        while (entry != null) {
            if (entry.key.equals(k)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (hashTable[i] != null) {
                sb.append("[").append(hashTable[i].key).append(":").append(hashTable[i].value).append("]");
                if (hashTable[i].next != null) {
                    Entry<K, V> next = hashTable[i].next;
                    while (next != null) {
                        sb.append("{").append(next.key).append(":").append(next.value).append("}");
                        next = next.next;
                    }
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("******************Put and remove tests**************************");
        MyHashMap<Integer, String> myHashMap = new MyHashMap<>();
        myHashMap.put(1, "A");
        myHashMap.put(10001, "A1");
        myHashMap.put(20001, "A2");
        myHashMap.put(30001, "A3");
        myHashMap.put(2, "B");
        myHashMap.put(3, "C");
        myHashMap.put(4, "C");
        myHashMap.put(5, "C");
        myHashMap.put(6, "C");
        myHashMap.put(7, "C");
        myHashMap.put(9, "C");
        System.out.println(myHashMap);
        System.out.println("Remove key 20001: ");
        myHashMap.remove(20001);
        System.out.println(myHashMap);
        System.out.println("Put new element(21) with key 1: ");
        myHashMap.put(1, "21");
        System.out.println(myHashMap);
        System.out.println("Put new element(A22) with key 30001: ");
        myHashMap.put(30001, "A22");
        System.out.println(myHashMap);
        System.out.println("********************************MyIteratorTest*********************************************");
        System.out.println("Elements in HashMap: " + myHashMap.getEntryCounter());
        MyIterator<Entry<Integer, String>> entryMyIterator = myHashMap.myIterator();
        System.out.println(myHashMap);
        System.out.println("Test 1");
        System.out.println(myHashMap);
        System.out.println("remove: ");
        System.out.println(entryMyIterator.next());
        entryMyIterator.remove();
        System.out.println(myHashMap);
        System.out.println("Test 2");
        System.out.println(myHashMap);
        System.out.println("remove: ");
        System.out.println(entryMyIterator.next());
        entryMyIterator.remove();
        System.out.println(myHashMap);
        System.out.println("Test 3");
        System.out.println(myHashMap);
        System.out.println("remove: ");
        System.out.println(entryMyIterator.next());
        entryMyIterator.remove();
        System.out.println(myHashMap);
        System.out.println("Elements in HashMap: " + myHashMap.getEntryCounter());
        System.out.println("********************************Test of increaseCapacity()*********************************");
        MyHashMap<Integer, String> myHashMap2 = new MyHashMap<>();
        int random;
        for (int i = 0; i < 16; i++) {
            System.out.println("Loop #" + (i + 1));
            random = (int) (Math.random() * 100);
            System.out.println("New key: " + random);
            myHashMap2.put(random, "A" + i);
            System.out.println(Arrays.toString(myHashMap2.hashTable));
            System.out.println(myHashMap2);
        }
        System.out.println("***********************************The End of the tests************************************");
    }
}
