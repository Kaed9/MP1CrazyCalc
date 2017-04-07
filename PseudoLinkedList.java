public class PseudoLinkedList <T> {
	
	PseudoLink<T> first;
	
	public PseudoLinkedList() {
		
		first = null;
	}
	
	public void addLink(T value) {
		
		if(first == null) {
			this.first = new PseudoLink<T>(value);
		} else {
			PseudoLink<T> l = first;
			while(l.next != null) {
				l = l.next;
			}
			l.next = new PseudoLink<T>(value);
		}
	}
	
	public void addFirst(T value) {
		
		PseudoLink<T> link = new PseudoLink<T>(value);	
		link.next = first;
		first = link;
	}
	
	public void addLink(int index, T value) {
		
		PseudoLink<T> l = first;
		int ctr = 0;
		if(first == null) {
			this.first = new PseudoLink<T>(value);
		} else {
			while(l.next != null || ctr+1 != index) {
				l = l.next;
				ctr++;
			}
			if(l.next == null) {
				l.next = new PseudoLink<T>(value);
			} else {
				PseudoLink<T> link = new PseudoLink<>(value);
				link.next = l.next.next;
				l.next = link;
			}
		}
	}
}