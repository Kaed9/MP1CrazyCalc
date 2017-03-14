import java.util.EmptyStackException;

public class PseudoStack<T>{
	private PseudoQueue<T> items;
	private int size = 0;
	
	public PseudoStack(){
		items = new PseudoQueue<>();
	}
	
	public void push(T element){
		PseudoQueue<T> buffer = new PseudoQueue<>();
		for(int i = 0; i < size; i++){
			buffer.enqueue(items.dequeue());
		}
		items.enqueue(element);
		for(int i = 0; i < size; i++){
			items.enqueue(buffer.dequeue());
		}
		size++;
		buffer = null;
	}
	
	public T pop() throws EmptyStackException{
		if(size == 0){
			throw new EmptyStackException(); 
		}
		T ret = items.dequeue();
		size--;
		return ret;
	}
	
	public T peek() throws EmptyStackException{
		if(size == 0){
			throw new EmptyStackException();
		}
		return items.peek();
	}
}