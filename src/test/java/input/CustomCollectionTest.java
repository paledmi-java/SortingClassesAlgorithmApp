package input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CustomCollectionTest {

    private CustomCollection<String> collection;

    @BeforeEach
    void setUp() {
        collection = new CustomCollection<>();
    }

    @Test
    void testDefaultConstructorCapacity() {
        assertEquals(10, collection.getElements().length);
    }

    @Test
    void testInitialCapacityConstructor() {
        CustomCollection<String> coll = new CustomCollection<>(5);
        assertEquals(5, coll.getElements().length);
    }

    @Test
    void testInitialCapacityConstructorWithZero() {
        assertThrows(IllegalArgumentException.class, () -> new CustomCollection<>(0));
    }

    @Test
    void testInitialCapacityConstructorWithNegative() {
        assertThrows(IllegalArgumentException.class, () -> new CustomCollection<>(-1));
    }

    @Test
    void testAddElement() {
        collection.add("test");
        assertEquals(1, collection.size());
        assertEquals("test", collection.get(0));
    }

    @Test
    void testAddMultipleElements() {
        collection.add("one");
        collection.add("two");
        collection.add("three");

        assertEquals(3, collection.size());
        assertEquals("one", collection.get(0));
        assertEquals("two", collection.get(1));
        assertEquals("three", collection.get(2));
    }

    @Test
    void testAutoExpansion() {
        // Заполняем до предела начальной ёмкости (10 элементов)
        for (int i = 0; i < 10; i++) {
            collection.add("item" + i);
        }

        // Добавляем ещё один — должно произойти расширение
        collection.add("expanded");

        assertEquals(11, collection.size());
        assertEquals("expanded", collection.get(10));
        assertTrue(collection.getElements().length > 10);
    }

    @Test
    void testGetWithValidIndex() {
        collection.add("first");
        collection.add("second");

        assertEquals("first", collection.get(0));
        assertEquals("second", collection.get(1));
    }

    @Test
    void testGetWithNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
    }

    @Test
    void testGetWithIndexEqualToSize() {
        collection.add("item");
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(1));
    }

    @Test
    void testGetWithIndexGreaterThanSize() {
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(5));
    }

    @Test
    void testSetWithValidIndex() {
        collection.add("old");
        String result = collection.set(0, "new");

        assertEquals("old", result);
        assertEquals("new", collection.get(0));
    }

    @Test
    void testSetWithNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> collection.set(-1, "value"));
    }

    @Test
    void testSetWithIndexEqualToSize() {
        collection.add("item");
        assertThrows(IndexOutOfBoundsException.class, () -> collection.set(1, "value"));
    }

    @Test
    void testRemoveByIndexWithValidIndex() {
        collection.add("one");
        collection.add("two");
        collection.add("three");

        collection.removeByIndex(1);

        assertEquals(2, collection.size());
        assertEquals("one", collection.get(0));
        assertEquals("three", collection.get(1));
    }

    @Test
    void testRemoveByIndexFirstElement() {
        collection.add("first");
        collection.add("second");

        collection.removeByIndex(0);

        assertEquals(1, collection.size());
        assertEquals("second", collection.get(0));
    }

    @Test
    void testRemoveByIndexLastElement() {
        collection.add("first");
        collection.add("last");

        collection.removeByIndex(1);

        assertEquals(1, collection.size());
        assertEquals("first", collection.get(0));
    }

    @Test
    void testRemoveByIndexWithNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> collection.removeByIndex(-1));
    }

    @Test
    void testRemoveByIndexWithIndexEqualToSize() {
        collection.add("item");
        assertThrows(IndexOutOfBoundsException.class, () -> collection.removeByIndex(1));
    }

    @Test
    void testRemoveByIndexWithIndexGreaterThanSize() {
        assertThrows(IndexOutOfBoundsException.class, () -> collection.removeByIndex(5));
    }

    @Test
    void testSize() {
        assertEquals(0, collection.size());

        collection.add("item");
        assertEquals(1, collection.size());

        collection.add("another");
        assertEquals(2, collection.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(collection.isEmpty());

        collection.add("item");
        assertFalse(collection.isEmpty());

        collection.removeByIndex(0);
        assertTrue(collection.isEmpty());
    }

    @Test
    void testClear() {
        collection.add("one");
        collection.add("two");

        collection.clear();

        assertEquals(0, collection.size());
        assertTrue(collection.isEmpty());
    }

    @Test
    void testIteratorHasNext() {
        assertFalse(collection.iterator().hasNext());

        collection.add("item");
        assertTrue(collection.iterator().hasNext());
    }

    @Test
    void testIteratorNext() {
        collection.add("first");
        collection.add("second");

        Iterator<String> iterator = collection.iterator();
        assertEquals("first", iterator.next());
        assertEquals("second", iterator.next());
    }

    @Test
    void testIteratorNextNoSuchElement() {
        Iterator<String> iterator = collection.iterator();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testIteratorRemove() {
        collection.add("one");
        collection.add("two");
        collection.add("three");

        Iterator<String> iterator = collection.iterator();
        iterator.next(); // переходим к первому элементу
        iterator.remove();

        assertEquals(2, collection.size());
        assertEquals("two", collection.get(0));
        assertEquals("three", collection.get(1));
    }

    @Test
    void testIteratorRemoveWithoutNext() {
        Iterator<String> iterator = collection.iterator();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void testIteratorRemoveTwice() {
        collection.add("item");

        Iterator<String> iterator = collection.iterator();
        iterator.next();
        iterator.remove();

        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void testStream() {
        collection.add("one");
        collection.add("two");
        collection.add("three");

        long count = collection.stream().count();
        assertEquals(3, count);

        String joined = collection.stream()
                .map(String::toUpperCase)
                .reduce("", String::concat);
        assertEquals("ONETWOTHREE", joined);
    }

    @Test
    void testAddAllWithNullCollection() {
        collection.addAll(null);
        assertEquals(0, collection.size());
    }

    @Test
    void testAddAllWithEmptyCollection() {
        CustomCollection<String> empty = new CustomCollection<>();
        collection.addAll(empty);
        assertEquals(0, collection.size());
    }

    @Test
    void testAddAllWithNonEmptyCollection() {
        CustomCollection<String> source = new CustomCollection<>();
        source.add("a");
        source.add("b");

        collection.addAll(source);

        assertEquals(2, collection.size());
        assertEquals("a", collection.get(0));
        assertEquals("b", collection.get(1));
    }
}