package Lesson22HW20HashMap;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashMap<K, V> implements MyMap<K, V>, Iterable<MyHashMap.Entry<K, V>> {

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

    public V merge(K kay, V newVal, MyBiFunction<? super V, ? super V, ? extends V> mergeFunk) {
        if (newVal == null || mergeFunk == null)
            throw new NullPointerException();
        if (containsKey(kay)) {
            V apply = mergeFunk.apply(get(kay), newVal);
            put(kay, apply);
            return apply;
        }
        return null;
    }

    public V getOrDefault(K key, V defaultVal) {
        V val = get(key);
        if (val == null) {
            return defaultVal;
        } else return val;
    }

    public V remove(K kay, V value) {
        if (containsKey(kay)) {
            if (get(kay).equals(value)) {
                return remove(kay);
            }
        }
        return null;
    }

    public V putIfAbsent(K k, V v) {
        if (!containsKey(k)) {
            return put(k, v);
        }
        return null;
    }

    public V computeIfPresent(K key, MyBiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (remappingFunction == null) {
            throw new NullPointerException();
        }
        V computeVal = null;
        if (containsKey(key)) {
            computeVal = remappingFunction.apply(key, get(key));
            put(key, computeVal);
        }
        return computeVal;
    }

    public V computeIfAbsent(K key, MyFunction<? super K, ? extends V> mappingFunction) {
        if (mappingFunction == null) {
            throw new NullPointerException();
        }
        V computeVal = null;
        if (!containsKey(key)) {
            computeVal = mappingFunction.apply(key);
            put(key, computeVal);
        }
        return computeVal;
    }

    public int getSize() {
        return size;
    }

    public int getEntryCounter() {
        return entryCounter;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
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

            private Entry<K, V> findNextEntryInArray(int indexOfCurrentEntry) {
                if (indexOfCurrentEntry + 1 >= size) {
                    nextEntry = null;
                    return currentEntry;
                } else {
                    for (int j = indexOfCurrentEntry + 1; j < size; j++) {
                        if (hashTable[j] != null) {
                            nextEntry = hashTable[j];
                            return currentEntry;
                        } else {
                            nextEntry = null;
                        }
                    }
                }
                return currentEntry;
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
                                return findNextEntryInArray(i);
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
                            return findNextEntryInArray(getHash(currentEntry.key));
                        } else {
                            nextEntry = currentEntry.next;
                            return currentEntry;
                        }
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

            public Entry<K, V> getCurrent() {
                return currentEntry;
            }
        };
    }

    public static class Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;
        private Entry<K, V> previous;

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

    @Override
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

    @Override
    public boolean isEmpty() {
        return entryCounter == 0;
    }

    @Override
    public V put(K k, V v) {
        int hash = getHash(k);
        Entry<K, V> entry = hashTable[hash];

        if (entry != null) {
            if (entry.key.equals(k)) {
                entry.value = v;
                return entry.value;
            } else {
                while (entry.next != null) {
                    entry = entry.next;
                    if (entry.key.equals(k)) {
                        entry.value = v;
                        return entry.value;
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
        return v;
    }

    @Override
    public V remove(K k) {
        int hash = getHash(k);
        Entry<K, V> entry = hashTable[hash];

        while (entry != null) {
            if (entry.key.equals(k)) {
                unlink(entry);
                entryCounter--;
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    @Override
    public V get(K k) {
        Entry<K, V> entry = hashTable[getHash(k)];

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
        MyHashMap<Integer, String> myHashMap = new MyHashMap<>();
        myHashMap.put(1, "A");
        myHashMap.put(4, "A1");
        myHashMap.put(5, "A2");
        System.out.println("****************** putIfAbsent test **************************");
        System.out.println(myHashMap);
        for (int i = 4; i < 8; i++) {
            myHashMap.putIfAbsent(i, " putIfAbsent ");
        }
        System.out.println(myHashMap);
        System.out.println("****************** computeIfPresent &  computeIfAbsent test **************************");
        System.out.println(myHashMap);
        for (int i = 0; i < 10; i++) {
            myHashMap.computeIfPresent(i, (k, val) -> val + " ++++++ " + k);
            myHashMap.computeIfAbsent(i, (k) -> k + " ------ ");
        }
        System.out.println(myHashMap);
        System.out.println("****************** remove(K kay, V val) test**************************");
        myHashMap.put(20001, "A2");
        myHashMap.put(30001, "C1");
        System.out.println(myHashMap);
        System.out.println(myHashMap.remove(20001, "A2"));
        System.out.println(myHashMap.remove(30001, "A2"));
        System.out.println(myHashMap);
        System.out.println("****************** getOrDefault test************************************");
        System.out.println(myHashMap);
        System.out.println(myHashMap.getOrDefault(3, "default"));
        System.out.println(myHashMap.getOrDefault(19, "default"));
        System.out.println(myHashMap);
        System.out.println("****************** merge test******************************************");
        System.out.println(myHashMap);
        System.out.println(myHashMap.merge(1, " New Val", (oldVal, newVal) -> oldVal + " " + newVal));
        System.out.println(myHashMap);
    }
}
