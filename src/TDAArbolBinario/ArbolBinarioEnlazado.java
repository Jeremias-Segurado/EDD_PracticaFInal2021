package TDAArbolBinario;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyTreeException;
import Excepciones.InvalidOperationException;
import Excepciones.InvalidPositionException;
import TDAArbol.*;
import TDALista.*;

public class ArbolBinarioEnlazado<E> implements BinaryTree<E> {

	protected BTPosition<E> raiz;
	protected int size;

	public ArbolBinarioEnlazado() {
		raiz = null;
		size = 0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		E aux = v.element();
		n.setElement(e);
		return aux;
	}

	private BTNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if (p == null || isEmpty())
				throw new InvalidPositionException("Árbol vacío o posición inválida.");
			return (BTNodo<E>) p;
		} catch (ClassCastException e) {
			throw new InvalidPositionException("El parámetro no era de tipo BTNodo.");
		}
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> list = new DoubleLinkedList<Position<E>>();
		if (!isEmpty())
			pre(list, raiz);
		return list;
	}

	private void pre(PositionList<Position<E>> L, BTPosition<E> p) {
		L.addLast(p);
		if (p.getLeft() != null)
			pre(L, p.getLeft());
		if (p.getRight() != null)
			pre(L, p.getRight());
	}

	public Iterator<E> iterator() {
		PositionList<E> list = new DoubleLinkedList<E>();
		for (Position<E> p : this.positions())
			list.addLast(p.element());
		return list.iterator();
	}

	public Position<E> root() throws EmptyTreeException {
		if (size == 0)
			throw new EmptyTreeException("Árbol vacio.");
		return raiz;
	}

	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> n = checkPosition(v);
		if (v == raiz)
			throw new BoundaryViolationException("La raíz no tiene padre.");
		return n.getParent();
	}

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = checkPosition(v);
		PositionList<Position<E>> list = new DoubleLinkedList<Position<E>>();
		if (nodo.getLeft() != null)
			list.addLast(nodo.getLeft());
		if (nodo.getRight() != null)
			list.addLast(nodo.getRight());
		return list;
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		BTNodo<E> n = checkPosition(v);
		return (n.getLeft() != null) || (n.getRight() != null);
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		return !(isInternal(v));
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		return n.getParent() == null;
	}

	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> n = checkPosition(v);
		if (n.getLeft() == null)
			throw new BoundaryViolationException("No tiene hijo izquierdo.");
		return n.getLeft();
	}

	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> n = checkPosition(v);
		if (n.getRight() == null)
			throw new BoundaryViolationException("No tiene hijo derecho.");
		return n.getRight();
	}

	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		return n.getLeft() != null;
	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		return n.getRight() != null;
	}

	public Position<E> createRoot(E r) throws InvalidOperationException {
		if (raiz != null)
			throw new InvalidOperationException("Ya existe raíz.");
		raiz = new BTNodo<E>(r);
		size++;
		return raiz;
	}

	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		if (n.getLeft() != null)
			throw new InvalidOperationException("Ya posee un hijo izquierdo.");
		n.setLeft(new BTNodo<E>(r, null, null, n));
		size++;
		return n.getLeft();
	}

	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		if (n.getRight() != null)
			throw new InvalidOperationException("Ya posee un hijo derecho.");
		n.setRight(new BTNodo<E>(r, null, null, n));
		size++;
		return n.getRight();
	}

	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTPosition<E> n = checkPosition(v);
		if (hasLeft(v) && hasRight(v))
			throw new InvalidOperationException("No se puede eliminar un nodo con 2 hijos.");
		if (hasLeft(n)) {
			n.getLeft().setParent(n.getParent());
			if (n != raiz) {
				if (n.getParent().getLeft() == n)
					n.getParent().setLeft(n.getLeft());
				else
					n.getParent().setRight(n.getLeft());
			} else
				raiz = n.getLeft();
			n.setLeft(null);
		} else if (n.getRight() != null) {
			n.getRight().setParent(n.getParent());
			if (n != raiz) {
				if (n.getParent().getRight() == n)
					n.getParent().setRight(n.getRight());
				else
					n.getParent().setLeft(n.getRight());
			} else
				raiz = n.getRight();
			n.setRight(null);
		} else if (isExternal(n))
			if (n != raiz) {
				if (n.getParent().getRight() == n)
					n.getParent().setRight(null);
				else
					n.getParent().setLeft(null);
			}
		n.setParent(null);
		E aux = n.element();
		n.setElement(null);
		size--;
		return aux;
	}

	public void attach(Position<E> p, BinaryTree<E> t1, BinaryTree<E> t2) throws InvalidPositionException {
		BTPosition<E> raiz_local = checkPosition(p);
		if (raiz_local.getLeft() != null || raiz_local.getRight() != null)
			throw new InvalidPositionException("La posicion no corresponde a un nodo hoja");
		try {
			// Clonación de T1 como subárbol izquierdo
			if (!t1.isEmpty()) {
				Position<E> raiz_t1 = t1.root();
				BTPosition<E> hi_raiz_local = new BTNodo<E>(raiz_t1.element(), raiz_local);
				raiz_local.setLeft(hi_raiz_local);
				clonar(raiz_local.getLeft(), raiz_t1, t1);
			}
			// Clonación de T2 como subárbol derecho
			if (!t2.isEmpty()) {
				Position<E> raiz_t2 = t2.root();
				BTPosition<E> hd_raiz_local = new BTNodo<E>(raiz_t2.element(), raiz_local);
				raiz_local.setRight(hd_raiz_local);
				clonar(raiz_local.getRight(), raiz_t2, t2);
			}
			size += t1.size() + t2.size();
		} catch (EmptyTreeException e) {
			raiz_local.setLeft(null);
			raiz_local.setRight(null);
		}
	}

	protected void clonar(BTPosition<E> padre_local, Position<E> padre_t, BinaryTree<E> t) {
		try {
			// Si existe hijo izquierdo en T de padre_t, se clona este y el subárbol a
			// partir del hijo izquierdo de padre_t.
			if (t.hasLeft(padre_t)) {
				Position<E> hi_padre_t = t.left(padre_t);
				BTPosition<E> hi_padre_local = new BTNodo<E>(hi_padre_t.element(), padre_local);
				padre_local.setLeft(hi_padre_local);
				clonar(hi_padre_local, hi_padre_t, t);
			}
			// Si existe hijo derecho en T de padre_t, se clona este y el subárbol a partir
			// del hijo derecho de padre_t.
			if (t.hasRight(padre_t)) {
				Position<E> hd_padre_t = t.right(padre_t);
				BTPosition<E> hd_padre_local = new BTNodo<E>(hd_padre_t.element(), padre_local);
				padre_local.setRight(hd_padre_local);
				clonar(hd_padre_local, hd_padre_t, t);
			}
		} catch (InvalidPositionException | BoundaryViolationException e) {
			padre_local.setLeft(null);
			padre_local.setRight(null);
		}
	}

}
