import java.util.NoSuchElementException;

public class PseudoQueue<T> {
	
	private PseudoArray<T> items;
	private int size = 0;
	
	public PseudoQueue() {
		
		items = new PseudoArray<>(size);
	}
	
	public void enqueue(T element) {
		
		PseudoArray<T> buffer = new PseudoArray<>(size+1);
		buffer.add(0, element);
		for(int i = 0; i < size; i++) {
			buffer.add(i+1, items.get(i));
		}
		items = null;
		items = new PseudoArray<>(size+1);
		for(int i = 0; i < size+1; i++) {
			items.add(i, buffer.get(i));
		}
		buffer = null;
		size++;
	}
	
	public T dequeue() {
		
		if(size == 0) {
			throw new NoSuchElementException();
		}
		T ret = items.get(size-1);
		PseudoArray<T> buffer = new PseudoArray<>(size-1);
		for(int i = 0; i < size-1; i++) {
			buffer.add(i, items.get(i));
		}
		items = null;
		items = new PseudoArray<>(size-1);
		for(int i = 0; i < size-1; i++) {
			items.add(i, buffer.get(i));
		}
		size--;
		buffer = null;
		return ret;
	}
	
	public T peek() throws NoSuchElementException {
		
		return items.get(size-1);
	}
}