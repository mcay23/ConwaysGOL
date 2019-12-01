package GameOfLife;

public interface Observable {
    void notifyObservers(String event);
    void addObserver(Observer o);
    void removeObserver(Observer o);
}
