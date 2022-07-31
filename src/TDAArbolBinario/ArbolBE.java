package TDAArbolBinario;

import Excepciones.*;
import java.util.Iterator;
import TDAArbol.*;
import TDALista.*;
import TDACola.*;
import TDAPila.*;

public class ArbolBE<E> implements BinaryTree<E> {

	protected BTPosition<E> raiz;
	protected int size;

	public ArbolBE() {
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

	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTPosition<E> n = checkPosition(r);
		if (isInternal(r))
			throw new InvalidPositionException("El nodo a añadir árboles no puede ser interno.");
		try {

			if (!T1.isEmpty()) {
				BTPosition<E> n1 = new BTNodo<E>(T1.root().element());
				build(T1, n1, T1.root());
				n.setLeft(n1);
				n1.setParent(n);
			}
			if (!T2.isEmpty()) {
				BTPosition<E> n2 = new BTNodo<E>(T2.root().element());
				build(T2, n2, T2.root());
				n.setRight(n2);
				n2.setParent(n);
			}
			size += T1.size() + T2.size();
		} catch (EmptyTreeException e) {
			e.printStackTrace();
		}
	}

	private void build(BinaryTree<E> T, BTPosition<E> n, Position<E> p) {
		try {
			if (T.hasLeft(p)) {
				n.setLeft(new BTNodo<E>(T.left(p).element(), null, null, n));
				build(T, n.getLeft(), T.left(p));
			}
			if (T.hasRight(p)) {
				n.setRight(new BTNodo<E>(T.right(p).element(), null, null, n));
				build(T, n.getRight(), T.right(p));
			}
		} catch (InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}

	}

	// ----------EJERCICIOS----------------

	public void ordenPrevio() {
		pre1(raiz);
	}

	private void pre1(BTPosition<E> n) {
		System.out.print(n.element());
		if (n.getLeft() != null)
			pre1(n.getLeft());
		if (n.getRight() != null)
			pre1(n.getRight());
	}

	// ---------------------

	public void ordenPosterior() {
		pos1(raiz);
	}

	private void pos1(BTPosition<E> n) {
		if (n.getLeft() != null)
			pos1(n.getLeft());
		if (n.getRight() != null)
			pos1(n.getRight());

		System.out.println(n.element());
	}

	// -----------------------

	public void ordenSimetrico() {
		in(raiz);
	}

	private void in(BTPosition<E> n) {
		if (n.getLeft() != null)
			in(n.getLeft());
		System.out.print(n.element());
		if (n.getRight() != null)
			in(n.getRight());
	}

	// --------------------------

	public void porNiveles() {
		try {
			Queue<BTPosition<E>> q = new ArrayQueue<BTPosition<E>>();
			q.enqueue(raiz);
			q.enqueue(null);
			while (!q.isEmpty()) {
				BTPosition<E> pos = q.dequeue();
				if (pos != null) {
					System.out.print(pos.element());
					if (pos.getLeft() != null)
						q.enqueue(pos.getLeft());
					if (pos.getRight() != null)
						q.enqueue(pos.getRight());
				} else if (!q.isEmpty()) {
					System.out.println();
					q.enqueue(null);
				}
			}
		} catch (EmptyQueueException e) {
			e.printStackTrace();
		}
	}

	// ----------------------------

	public BinaryTree<E> clone() {
		BinaryTree<E> BT = new ArbolBE<E>();
		try {
			if (size > 0) {
				BT.createRoot(raiz.element());
				preclon(BT, raiz, BT.root());
			}
		} catch (InvalidOperationException | EmptyTreeException e) {
			e.printStackTrace();
		}
		return BT;
	}

	private void preclon(BinaryTree<E> BT, BTPosition<E> r, Position<E> n) {
		try {
			if (r.getLeft() != null) {
				BT.addLeft(n, r.getLeft().element());
				preclon(BT, r.getLeft(), BT.left(n));
			}
			if (r.getRight() != null) {
				BT.addRight(n, r.getRight().element());
				preclon(BT, r.getRight(), BT.right(n));
			}
		} catch (BoundaryViolationException | InvalidPositionException | InvalidOperationException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------

	public BinaryTree<E> mirror() {
		BinaryTree<E> T = new ArbolBE<E>();
		try {
			if (!isEmpty()) {
				T.createRoot(raiz.element());
				premirror(T, raiz, T.root());
			}
		} catch (EmptyTreeException | InvalidOperationException e) {
			e.printStackTrace();
		}
		return T;
	}

	private void premirror(BinaryTree<E> T, BTPosition<E> r, Position<E> n) {
		try {
			if (r.getLeft() != null) {
				T.addRight(n, r.getLeft().element());
				premirror(T, r.getLeft(), T.right(n));
			}
			if (r.getRight() != null) {
				T.addLeft(n, r.getRight().element());
				premirror(T, r.getRight(), T.left(n));
			}
		} catch (InvalidPositionException | InvalidOperationException | BoundaryViolationException e) {
			e.printStackTrace();
		}
	}

	// --------------------------------------

	public PositionList<Position<E>> camino(Position<E> ps1, Position<E> ps2) {

		PositionList<Position<E>> L = new DoubleLinkedList<Position<E>>();
		Stack<Position<E>> p1 = new LinkedStack<Position<E>>();
		Stack<Position<E>> p2 = new LinkedStack<Position<E>>();
		// Stack<Position<E>> p3= new PilaConEnlaces<Position<E>>();
		try {
			BTPosition<E> n1 = checkPosition(ps1);
			BTPosition<E> n2 = checkPosition(ps2);

			while (n1 != null) {
				p1.push(n1);
				n1 = n1.getParent();
			}

			while (n2 != null) {
				p2.push(n2);
				n2 = n2.getParent();
			}

			Position<E> aux = null;
			while ((!p1.isEmpty() && !p2.isEmpty()) && (p1.top() == p2.top())) {
				aux = p1.pop();
				p2.pop();
			}

			while (!p1.isEmpty())
				L.addFirst(p1.pop());
			L.addLast(aux);
			while (!p2.isEmpty())
				L.addLast(p2.pop());
		} catch (EmptyStackException | InvalidPositionException e) {
			e.printStackTrace();
		}
		return L;
	}

	// -------------------------------------------------

	public int depth(Position<E> p) {
		int i = 0;
		try {
			BTPosition<E> n = checkPosition(p);
			System.out.println(p.element());
			if (n.getParent() != null)
				i = 1 + depth(n.getParent());
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
		return i;
	}

	// ----------------------------------------------------

	public int altura(Position<E> p1) {
		PositionList<BTPosition<E>> L = new DoubleLinkedList<BTPosition<E>>();
		try {
			BTNodo<E> n = checkPosition(p1);
			L = height(n);
			for (BTPosition<E> bn : L)
				System.out.println(bn.element());
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
		return L.size() - 1;
	}

	private PositionList<BTPosition<E>> height(BTPosition<E> n) {
		PositionList<BTPosition<E>> L = new DoubleLinkedList<BTPosition<E>>();
		if (n.getLeft() == null && n.getRight() == null)
			L.addLast(n);
		else {
			int max = 0;
			PositionList<BTPosition<E>> camino = new DoubleLinkedList<BTPosition<E>>();
			if (n.getLeft() != null) {
				camino = height(n.getLeft());
				if (camino.size() > max) {
					max = camino.size();
					L = camino;
				}
			}
			if (n.getRight() != null) {
				camino = height(n.getRight());
				if (camino.size() > max) {
					max = camino.size();
					L = camino;
				}
			}
			L.addLast(n);
		}
		return L;

	}

	// -------------------------------------------

	public boolean esPropio() {
		if (!isEmpty())
			return prePropio(raiz);
		return true;
	}

	private boolean prePropio(BTPosition<E> n) {
		boolean propio;
		if (n.getLeft() == null && n.getRight() == null)
			return true;
		else if (n.getLeft() == null || n.getRight() == null)
			return false;
		propio = prePropio(n.getLeft());
		if (propio)
			propio = prePropio(n.getRight());
		return propio;
	}

	// ---------------------------------------------

	public void padres() {
		if (!isEmpty())
			recpa(raiz, 1);
	}

	private void recpa(BTPosition<E> n, int i) {
		try {
			if (isInternal(n))
				System.out.println(n.element() + " es padre y esta en el nivel " + i);
			if (hasLeft(n))
				recpa(n.getLeft(), i + 1);
			if (hasRight(n))
				recpa(n.getRight(), i + 1);
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
	}

	// -----------------------------------------------

	public boolean subArbol(BinaryTree<E> T1) {
		try {
			if (!isEmpty() && !T1.isEmpty())
				return recsub(T1, raiz, T1.root());
		} catch (EmptyTreeException e) {
			e.printStackTrace();
		}
		return T1.isEmpty();
	}

	private boolean recsub(BinaryTree<E> T1, BTPosition<E> r, Position<E> n) {
		boolean a = true;
		try {
			if (r.element() != n.element())
				a = false;
			else {
				if (T1.hasLeft(n))
					if (!hasLeft(r))
						a = false;
					else
						a = recsub(T1, r.getLeft(), T1.left(n));
				if (a)
					if (T1.hasRight(n))
						if (!hasRight(r))
							a = false;
						else
							a = recsub(T1, r.getRight(), T1.right(n));
			}
			if (!a) {
				if (hasLeft(r))
					a = recsub(T1, r.getLeft(), T1.root());
				if (!a)
					if (hasRight(r))
						a = recsub(T1, r.getRight(), T1.root());
			}
		} catch (InvalidPositionException | BoundaryViolationException | EmptyTreeException e) {
			e.printStackTrace();
		}
		return a;
	}

	// -------------------------------------------------

	public boolean equals(BinaryTree<E> T1) {
		try {
			if (!isEmpty() && !T1.isEmpty())
				return prequals(T1, T1.root(), raiz);
		} catch (EmptyTreeException e) {
			e.printStackTrace();
		}
		return (isEmpty() && T1.isEmpty());
	}

	private boolean prequals(BinaryTree<E> T1, Position<E> n, BTPosition<E> r) {
		boolean a = true;
		try {
			if (n.element() != r.element())
				a = false;
			else {
				if (hasLeft(r) && T1.hasLeft(n))
					a = prequals(T1, T1.left(n), r.getLeft());
				else if (hasLeft(r) || T1.hasLeft(n))
					a = false;
				if (a)
					if (hasRight(r) && T1.hasRight(n))
						a = prequals(T1, T1.right(n), r.getRight());
					else if (hasRight(r) || T1.hasRight(n))
						a = false;

			}
		} catch (InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		return a;
	}

}