package input;

public interface CollectionInterface<T> extends Iterable<String>{
    boolean add(T car); // important
    boolean remove(T car); // important
    boolean removeAt(int index);
    void clear(); // important
    T get(int index);
    boolean add(T car, int index);
    int size(); // important
    boolean contains(T car);
}
