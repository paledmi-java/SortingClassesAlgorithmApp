package input;

public interface CollectionInterface<T> extends Iterable<T>{
    void add(T element); // important
    void removeByIndex(int index); // important
    void clear(); // important
    T get(int index);
    int size(); // important
}
