/*
 final size to implement the limitations of usual array (static size)
 
 add(T element) : adds element at the last
 add(int index, T element) : adds(actually assigns) "element" at the index'th element of the list //like array[index] = element;
 get(int index) : maaram na kamo hito hahahaha
 size() : return size of array, not the number of elements at the moment
*/
public class PseudoArray <T>{
	private PseudoLinkedList<T> keys;
	private final int size;
	private int ctr;
	
	public PseudoArray(int size){
		keys = new PseudoLinkedList<>();
		keys.first = new PseudoLink<>(null);
		PseudoLink<T> link = keys.first;
		for(int i = 0; i < size-1; i++){
			link.next = new PseudoLink<>(null);
			link = link.next;
		}
		this.size = size;
		ctr = 0;
	}
	
	public boolean add(T element){// adds element to the last added part
		if(ctr >= size){
			throw new ArrayIndexOutOfBoundsException();
		}else{
			PseudoLink<T>link = keys.first;
			for(int i = 0; i < ctr; i++){
				link = link.next;
			}
			link.setKey(element);
			ctr++;
		}
		return true;
	}
	
	public boolean add(int index, T element){
		if(index >= size){
			throw new ArrayIndexOutOfBoundsException();
		}else{
			PseudoLink<T> link = keys.first;
			for(int i = 0; i < index; i++){
				link = link.next;
			}
			link.setKey(element);
		}
		return true;
	}
	
	public T get(int index){
		PseudoLink<T> link = keys.first;
		for(int i = 0; i < index; i++){
			link = link.next;
		}
		return link.getKey();
	}
	
	
	public int size(){
		return size;
	}
	
	public int ctr(){
		return ctr;
	}
}