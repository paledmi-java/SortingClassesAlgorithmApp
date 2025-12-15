package input;

public class CustomCollection implements CollectionInterface{

    @Override
    public boolean add(Object car) {
        return false;
    }

    @Override
    public boolean remove(Object car) {
        return false;
    }

    @Override
    public boolean removeAt(int index) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public boolean add(Object car, int index) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(Object car) {
        return false;
    }
}
