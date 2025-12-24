package input;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
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
 * <p>Основные возможности:</p>
 * <ul>
 *   <li>Динамическое расширение при добавлении элементов</li>
 *   <li>Поддержка операций добавления, удаления, получения и замены элементов</li>
 *   <li>Итерация через for-each циклы и итераторы</li>
 *   <li>Поддержка Stream API</li>
 *   <li>Добавление всех элементов из другой коллекции</li>
 * </ul>
 *
 * @param <T> тип элементов, хранимых в коллекции
 */
public class CustomCollection<T> implements CollectionInterface<T> {
    /** Коэффициент увеличения емкости при расширении массива. */
    private static final float GROWTH_FACTOR = 1.5f;

    /** Начальная емкость по умолчанию при создании коллекции без указания размера. */
    private static final int DEFAULT_CAPACITY = 10;

    /** Внутренний массив для хранения элементов коллекции. */
    private Object[] elements;

    /** Количество фактически хранящихся элементов в коллекции. */
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

    @Override
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

    @Override
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

    @Override
    public boolean remove(T element) {
        for (int i = 0; i < size; i++) {
            if(Objects.equals(element, elements[i])) {
                removeByIndex(i);
                return true;
            }
         }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс не может быть меньше 0 или больше " + size);
        }
        return (T) elements[index];
    }


    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс не может быть меньше 0 или больше " + size);
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
    @Override
    public Stream<T> stream() {
        return (Stream<T>) Arrays.stream(elements, 0, size);
    }

    /**
     * Добавляет все элементы из указанной коллекции в конец этой коллекции.
     *
     * <p>Порядок элементов в целевой коллекции соответствует порядку,
     * в котором они возвращаются итератором указанной коллекции.
     *
     * <p>Поведение этого метода не определено, если указанная коллекция изменяется
     * в процессе выполнения операции.
     *
     * @param collection коллекция, содержащая элементы для добавления
     */
    public void addAll(CustomCollection<? extends T> collection) {
        if (collection == null || collection.isEmpty()) {
            return;
        }

        for (T element : collection) {
            add(element);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator iterator() {
        return new CustomArrayIterator();
    }

    /**
     * Внутренний класс итератора для CustomCollection.
     * Реализует fail-fast поведение: выбрасывает {@link IllegalStateException}
     * при попытке удаления без предварительного вызова {@link #next()}.
     */
    class CustomArrayIterator implements Iterator<T> {
        /** Текущая позиция итератора в массиве элементов. */
        private int cursor = 0;

        /** Индекс последнего возвращенного элемента; -1 если элемент не был возвращен или был удален. */
        private int lastRet = -1;

        public CustomArrayIterator() {
        }

        /**
         * Проверяет, существует ли следующий элемент для итерации.
         *
         * @return {@code true} если итерация содержит больше элементов,
         *         {@code false} в противном случае
         */
        public boolean hasNext() {
            return cursor < size;
        }

        /**
         * Возвращает следующий элемент в итерации.
         *
         * @return следующий элемент в итерации
         * @throws NoSuchElementException если итерация не содержит больше элементов
         */
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Элемент отсутствует");
            }
            lastRet = cursor;
            T element = (T) elements[cursor];
            cursor++;

            return element;
        }

        /**
         * Удаляет из коллекции последний элемент, возвращенный этим итератором.
         *
         * @throws IllegalStateException если метод {@link #next} еще не был вызван,
         *         или метод {@link #remove} уже был вызван после последнего вызова {@link #next}
         */
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
