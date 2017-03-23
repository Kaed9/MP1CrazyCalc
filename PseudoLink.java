public class PseudoLink <T> {
	private T key;
	PseudoLink<T> next = null;
	
	public PseudoLink(T key){
		this.key = key;
	}
	
	public PseudoLink(){
		key = null;
	}
	
	public T getKey(){
		return key;
	}
	
	public void setKey(T key){
		this.key = key;
	}
	
	/*public Link getNext(){
		return next;
	}*/
}