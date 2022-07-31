package TDAArbolBinario;

public class BTNodo<E> implements BTPosition<E> {

	protected E element;
	protected BTPosition<E> left, right, parent;

	public BTNodo(E elem, BTPosition<E> left, BTPosition<E> right, BTPosition<E> parent) {
		element = elem;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	public BTNodo(E elem,BTPosition<E> padre) {
		element=elem;
		parent=padre;
	}
	public BTNodo(E elem) {
		element=elem;
	}

	public E element() {
		return element;
	}

	public void setParent(BTPosition<E> p) {

		parent = p;
	}

	public void setLeft(BTPosition<E> p) {
		left = p;
	}

	public void setRight(BTPosition<E> p) {
		right = p;
	}

	public BTPosition<E> getParent() {
		return parent;
	}

	public BTPosition<E> getLeft() {
		return left;
	}

	public BTPosition<E> getRight() {
		return right;
	}

	@Override
	public void setElement(E elem) {

		element=elem;
	}

}
