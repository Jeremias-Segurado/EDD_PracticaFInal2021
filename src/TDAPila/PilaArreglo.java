package TDAPila;

import Excepciones.EmptyStackException;

public class PilaArreglo<E> implements Stack<E> {

	protected int cant;
	protected E[] arre;

	/**
	 * Se inicializan la pila vacia y sus atributos correspondientes.
	 */
	public PilaArreglo() {
		arre = (E[]) new Object[25];
		cant = 0;
	}

	public int size() {
		return cant;
	}

	public boolean isEmpty() {
		return cant == 0;
	}

	public E top() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException("Pila Vacia");
		}
		return arre[cant - 1];
	}

	/**
	 * Copia los elementos actuales de la estructura en una nueva coleccion con el
	 * doble del tamaño actual.
	 * 
	 * @return Nueva coleccion con el doble de la longitud de la estructura actual.
	 */
	private E[] Copiar() {
		E[] aux = (E[]) new Object[2 * arre.length];
		for (int i = 0; i < size(); i++) {
			aux[i] = arre[i];
		}
		return aux;
	}

	public void push(E element) {
		if (arre.length == size()) {
			E[] ArreAux = Copiar();
			arre = ArreAux;
		}
		arre[cant] = element;
		cant++;
	}

	public E pop() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException("Pila Vacia");
		}
		E ret = arre[cant - 1];
		arre[cant - 1] = null;
		cant--;
		return ret;
	}
}
