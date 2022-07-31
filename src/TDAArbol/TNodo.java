package TDAArbol;

import TDALista.DoubleLinkedList;
import TDALista.Position;
import TDALista.PositionList;

public class TNodo<E> implements Position<E> {

	private E elemento;
	private TNodo<E> padre;
	private PositionList<TNodo<E>> hijos;

	public TNodo(E e, TNodo<E> padre) {
		elemento = e;
		this.padre = padre;
		hijos = new DoubleLinkedList<TNodo<E>>();
	}

	public TNodo(E e) {
		this(e, null);
	}

	public E element() {
		return elemento;
	}

	public PositionList<TNodo<E>> getHijos() {
		return hijos;
	}

	public void setElemento(E e) {
		elemento = e;
	}

	public TNodo<E> getPadre() {
		return padre;
	}

	public void setPadre(TNodo<E> padre) {
		this.padre = padre;
	}
}
