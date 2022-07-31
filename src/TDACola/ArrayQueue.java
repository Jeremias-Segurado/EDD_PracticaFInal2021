package TDACola;

import Excepciones.EmptyQueueException;

public class ArrayQueue<E> implements Queue<E> {

	private E[] q;
	private int f,r;
	private static final int longitud=10;
	
	public ArrayQueue() {
		q= (E[]) new Object[longitud];
		f=0;
		r=0;
	}
	
	public void enqueue(E elem) {
		if(size()==q.length-1) {
			E[] aux= copiar(f);
			r=size();
			f=0;
			q=aux;
		}
		q[r]=elem;
		r=(r+1)%q.length;
	}

	
	public E dequeue() throws EmptyQueueException {
		E aux;
		if(f==r)
			throw new EmptyQueueException("Cola vacia");
		else {
			aux= q[f];
			q[f]=null;
			f=(f+1)%q.length;
		}
		return aux;
	}

	
	public E front() throws EmptyQueueException {
		if (f==r)
			throw new EmptyQueueException("Cola vacia");
		
		return q[f];
	}

	public boolean isEmpty() {
		
		return f==r;
	}

	
	public int size() {
		return (q.length-f+r)%q.length;
	}
	
	private E[] copiar(int n) {
		E[] aux=(E[]) new Object[2*q.length];
		  for(int j=0;j<size();j++) {
			  aux[j]=q[n];
			  n=(n+1)%q.length;  
		  }
		 return aux;
	}

}
