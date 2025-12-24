package input;

import java.util.stream.Stream;

public interface CollectionInterface<T> extends Iterable<T>{
    boolean add(T element); // important
    boolean remove(T element);// important
    void removeByIndex(int index);
    void clear(); // important
    T get(int index);
    int size(); // important
    boolean isEmpty();
    Stream<T> stream();
}
