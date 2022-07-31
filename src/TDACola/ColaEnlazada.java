package TDACola;
import Excepciones.EmptyQueueException;
import TDAPila.Nodo;

public class ColaEnlazada<E> implements Queue<E>{

	protected Nodo<E> head, tail;
	protected int size;
	
	public ColaEnlazada() {
		head=null;
		tail=null;
		size=0;
	}
	
	public void enqueue(E elem) {
		Nodo<E> nodo = new Nodo<E>();
		nodo.setElem(elem);
		nodo.setSig(null);
		if(size==0)
			head=nodo;
		else
			tail.setSig(nodo);
		tail=nodo;
		size++;
	}

	
	public E dequeue() throws EmptyQueueException {
		if(size==0)
			throw new EmptyQueueException("Cola vacia");
		
		E aux= head.getElem();
		head= head.getSig();
		size--;
		if(size==0)
			tail=null;
	
		return aux;
	}

	public E front() throws EmptyQueueException {
		E aux;
		if(size==0)
		throw new EmptyQueueException("Cola vacia");
		aux=head.getElem();
		return aux;
	}

	
	public boolean isEmpty() {
		
		return size==0;
	}

	@Override
	public int size() {
		return size;
	}

}
