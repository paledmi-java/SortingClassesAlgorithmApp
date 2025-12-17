package input;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CustomCollection<T> implements Iterable<String> {
    private static final float GROWTH_FACTOR = 1.5f;
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    // Стандартный конструктор
    public CustomCollection() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    // Конструктор с заданной величиной
    public CustomCollection(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be less then 0");
        }
        this.elements = new Object[initialCapacity];
    }

    // TODO Конструктор из существующей коллекции, копирует все элементы с ёмкостью равной ёмкости коллекции

    public boolean add(Object element) {
        // Проверяем достаточно ли места для добавления
        if (size == elements.length) {
            // Если места не осталось применяем формулу увеличения длинны массива
            int newCapacity = (int) (elements.length * GROWTH_FACTOR + 1);
            // Создаем новый массив с увеличенной длинной
            Object[] newElements = new Object[newCapacity];
            // Копируем в него элементы из старого массива
            System.arraycopy(elements,0, newElements, 0, elements.length);
            this.elements = newElements;
        }
        // Добавляем новый элемент на последнюю позицию
        elements[size] = element;
        // Увеличиваем число элементов в массиве
        size++;
        return true;
    }

    public T get(int index) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index cannot be less then 0 or more then " + size);
        }
        return (T) elements[index];
    }

    // Проверить почему надо вернуть старый элемент
    public T set(int index, T element) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index cannot be less then 0 or more then " + size);
        }

        T old = (T) elements[index];
        elements[index] = element;
        return old;
    }

    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator spliterator() {
        return Iterable.super.spliterator();
    }
}
