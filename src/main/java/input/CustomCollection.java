package input;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * CustomCollection - кастомная реализация динамического массива с автоматическим расширением.
 * Поддерживает хранение элементов любого типа, итерацию и базовые операции коллекции.
 *
 * <p>Коллекция использует внутренний массив для хранения элементов и автоматически
 * расширяется при заполнении. Коэффициент расширения: {@value #GROWTH_FACTOR}.
 *
 * <p>Реализует интерфейс {@link Iterable} для поддержки for-each циклов и стримов.
 *
 * @param <T> тип элементов, хранимых в коллекции
 */
public class CustomCollection<T> implements Iterable<T> {
    private static final float GROWTH_FACTOR = 1.5f;
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    public CustomCollection() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    public CustomCollection(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Capacity cannot be less then 0");
        }
        this.elements = new Object[initialCapacity];
    }

    // TODO Конструктор из существующей коллекции, копирует все элементы с ёмкостью равной ёмкости коллекции

    public boolean add(T element) {
        // Проверяем достаточно ли места для добавления
        if (size == elements.length) {
            // Если недостаточно, то увеличиваем размер списка
            increaseCapacity(size + 1);
        }
        // Добавляем новый элемент на последнюю позицию
        elements[size] = element;
        // Увеличиваем число элементов в массиве
        size++;
        return true;
    }

    public void removeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be less then 0 or more then " + size);
        }
        // Вычисляем количество элементов, которые нужно сместить влево после удаления
        int numToMove = size - index - 1;

        if (numToMove > 0) {
            // Используем System.arraycopy для эффективного копирования части массива:
            // 1. elements - исходный массив
            // 2. index+1 - начальная позиция в исходном массиве (элемент после удаляемого)
            // 3. elements - целевой массив (тот же массив, копируем в себя)
            // 4. index - начальная позиция в целевом массиве (на место удаляемого элемента)
            // 5. numToMove - количество элементов для копирования
            System.arraycopy(elements, index + 1, elements, index, numToMove);
        }
        // Уменьшаем size на 1 после копирования в конце массива остался "лишний" элемент и удаляем его
        elements[--size] = null;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be less then 0 or more then " + size);
        }
        return (T) elements[index];
    }

    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index cannot be less then 0 or more then " + size);
        }

        T old = (T) elements[index];
        elements[index] = element;
        return old;
    }

    /**
     * Возвращает последовательный {@code Stream} с элементами этой коллекции в качестве источника.
     *
     * <p>Этот метод позволяет использовать функциональные операции над элементами коллекции.
     *
     * @return последовательный {@code Stream} элементов этой коллекции
     */
    public Stream<T> stream() {
        return (Stream<T>) Arrays.stream(elements, 0, size);
    }

    /**
     * Добавляет все элементы из указанной коллекции в конец этой коллекции.
     *
     * <p>Порядок элементов в целевой коллекции соответствует порядку,
     * в котором они возвращаются итератором указанной коллекции.
     *
     * @param collection коллекция, содержащая элементы для добавления
     * @return {@code true} если эта коллекция изменилась в результате вызова
     */
    public boolean addAll(Collection<? extends T> collection) {
        // Проверяем, что переданная коллекция не null и не пустая
        if (collection == null || collection.isEmpty()) {
            // Возвращаем false, так как нечего добавлять
            return false;
        }

        int requiredCapacity = size + collection.size();
        if (requiredCapacity > elements.length) {
            increaseCapacity(requiredCapacity);
        }
        for (T element : collection) {
            // Добавляем элемент в конец массива
            // elements[size] = element - добавляет элемент на текущую позицию
            // size++ - увеличивает счетчик элементов
            elements[size++] = element;
        }
        return true;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator iterator() {
        return new CustomArrayIterator();
    }

    // Внутренний клас для итератора
    class CustomArrayIterator implements Iterator<T> {
        private int cursor = 0; // текущая позиция
        private int lastRet = -1; // индекс последнего возвращенного элемента

        public CustomArrayIterator() {
        }

        public boolean hasNext() {
            return cursor < size;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Элемент отсутствует");
            }
            lastRet = cursor;
            T element = (T) elements[cursor];
            cursor++;

            return element;
        }

        public void remove() {
            if (lastRet == -1) {
                throw new IllegalStateException();
            }
            removeByIndex(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }

    /**
     * Увеличивает емкость внутреннего массива, чтобы обеспечить минимальную указанную емкость.
     * @param minCapacity минимальная требуемая емкость
     */
    private void increaseCapacity(int minCapacity) {
        int newCapacity = (int) Math.max(elements.length * GROWTH_FACTOR + 1, minCapacity);
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }
}
