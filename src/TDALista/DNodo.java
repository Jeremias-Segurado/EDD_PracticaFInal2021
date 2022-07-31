package TDALista;

public class DNodo<E> implements Position<E> {

	private E elem;
	private DNodo<E> next, prev;

	public DNodo(DNodo<E> p, DNodo<E> n, E e) {
		prev = p;
		next = n;
		elem = e;
	}

	public DNodo(E e) {
		this(null, null, e);
	}

	@Override
	public E element() {
		return elem;
	}

	public DNodo<E> getPrev() {
		return prev;
	}

	public DNodo<E> getNext() {
		return next;
	}

	public void setElemento(E e) {
		elem = e;
	}

	public void setNext(DNodo<E> n) {
		next = n;
	}

	public void setPrev(DNodo<E> p) {
		prev = p;
	}

}
