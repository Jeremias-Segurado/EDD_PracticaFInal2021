package TDAPila;

import Excepciones.EmptyStackException;

public class LinkedStack<E> implements Stack<E>{
	
	protected Nodo<E> head;
	protected int size;
	
	public LinkedStack() {
		head=null;
		size=0;
	}
	
	public void push(E elem) {
		Nodo<E> aux= new Nodo<E>();
		aux.setElem(elem);
		aux.setSig(head);
		head=aux;
		size++;
		
	}

	
	public E pop() throws EmptyStackException {
		E aux;
		if(head==null) 
			throw new EmptyStackException("Pila vacia");
			else {
				aux=head.getElem();
				head= head.getSig();
				size--;
			}
		
		return aux;
	}

	
	public E top() throws EmptyStackException {
		E aux;
		if(head==null) 
			throw new EmptyStackException("Pila vacia");
			else 
				aux=head.getElem();
		
		return aux;
	}

	
	public boolean isEmpty() {
		
		return head==null;
	}

	
	public int size() {
		return size;
	}

}
