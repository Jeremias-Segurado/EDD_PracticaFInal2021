package TDADiccionario;

import Excepciones.*;
import TDALista.*;

public class DiccionarioABB<K extends Comparable<K>, V> implements Dictionary<K, V> {
	protected NodoABB<K, V> root;
	protected int cant;
	protected ComparatorABB<K> comp;

	public DiccionarioABB(ComparatorABB<K> c) {
		comp = c;
		root = new NodoABB<K, V>();
	}

	public int size() {
		return cant;
	}

	public boolean isEmpty() {
		return cant == 0;
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Clave nula");
		return findAux(key, root);
	}

	private Entry<K, V> findAux(K key, NodoABB<K, V> cod) {
		try {
			if (cod.getKey() == null)
				return null;
			int c = comp.compare(key, cod.getKey());
			if (c == 0)
				return cod.getKeys().first().element();
			if (c < 0)
				return findAux(key, cod.getLeft());
			else
				return findAux(key, cod.getRight());
		}

		catch (EmptyListException e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Clave nula");
		return findAllAux(key, root);
	}

	private PositionList<Entry<K, V>> findAllAux(K key, NodoABB<K, V> nodo) {
		if (nodo.getKey() == null)
			return new DoubleLinkedList<Entry<K, V>>();
		int c = comp.compare(key, nodo.getKey());
		if (c == 0)
			return nodo.getKeys();
		if (c < 0)
			return findAllAux(key, nodo.getLeft());
		else
			return findAllAux(key, nodo.getRight());

	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Clave nula");
		Entrada<K, V> en = new Entrada<K, V>(key, value);
		insertAux(en, root);
		return en;
	}

	public void insertAux(Entrada<K, V> en, NodoABB<K, V> nodo) {

		if (nodo.getKey() == null) {
			System.out.println(nodo.getKey() + "    nulo del insert xd");
			nodo.setKey(en.getKey());
			nodo.getKeys().addFirst(en);
			nodo.setLeft(new NodoABB<K, V>());
			nodo.setRight(new NodoABB<K, V>());
			nodo.getLeft().setPadre(nodo);
			nodo.getRight().setPadre(nodo);
			cant++;
		} else {
			int c = comp.compare(en.getKey(), nodo.getKey());
			if (c == 0) {
				nodo.getKeys().addLast(en);
				cant++;
			} else if (c < 0)
				insertAux(en, nodo.getLeft());
			else if (nodo.getRight() == null)
				insertAux(en, nodo.getRight());
		}

	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if (e == null || e.getKey() == null)
			throw new InvalidEntryException("Entrada nula");
		NodoABB<K, V> bus = buscar(e.getKey(), root);
		if (bus != null) {
			cant--;
			return removeAux(bus, e);
		} else
			throw new InvalidEntryException("No se encuentra la entrada en el diccionario");

	}

	private NodoABB<K, V> buscar(K key, NodoABB<K, V> nodo) {
		if (nodo.getKey() == null)
			return null;
		int c = comp.compare(key, nodo.getKey());
		if (c == 0)
			return nodo;
		else if (c < 0)
			return buscar(key, nodo.getLeft());
		else
			return buscar(key, nodo.getRight());

	}

	private Entry<K, V> removeAux(NodoABB<K, V> nodo, Entry<K, V> e) throws InvalidEntryException {
		PositionList<Entry<K, V>> lista = nodo.getKeys();
		if (lista.size() > 1) {
			for (Position<Entry<K, V>> en : nodo.getKeys().positions())
				if (en.element().getKey().equals(e.getKey()) && en.element().getValue().equals(e.getValue())) {
					Entry<K, V> remove = null;
					try {
						remove = lista.remove(en);
					} catch (InvalidPositionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return remove;
				}
		} else {

			if (nodo.getLeft().getKey() == null && nodo.getRight().getKey() == null) {
				nodo.setLeft(null);
				nodo.setRight(null);
				nodo.setKey(null);
				return e;
			} else {
				if (nodo == root)
					return casoRaiz(e, nodo);
				else {
					if (nodo.getLeft().getKey() != null && nodo.getRight().getKey() == null) {
						if (nodo.getParent().getLeft() == nodo)
							nodo.getParent().setLeft(nodo.getLeft());
						else
							nodo.getParent().setRight(nodo.getLeft());
						nodo.getLeft().setPadre(nodo.getParent());
						return e;
					} else if (nodo.getLeft().getKey() == null && nodo.getRight().getKey() != null) {
						System.out.println(nodo.getParent().getKey() == null);
						if (nodo.getParent().getLeft() == nodo)
							nodo.getParent().setLeft(nodo.getRight());
						else
							nodo.getParent().setRight(nodo.getRight());
						nodo.getRight().setPadre(nodo.getParent());
					} else
						return eliminarMinimo(e, nodo.getRight());
				}
			}
		}
		return null;
	}

	private Entry<K, V> casoRaiz(Entry<K, V> e, NodoABB<K, V> nodo) {
		if (nodo.getLeft().getKey() != null && nodo.getRight().getKey() == null) {
			root = nodo.getLeft();
			nodo.getLeft().setPadre(null);
			return e;
		} else if (nodo.getLeft().getKey() == null && nodo.getRight().getKey() != null) {
			root = nodo.getRight();
			nodo.getRight().setPadre(null);
		} else
			return eliminarMinimo(e, nodo.getRight());
		return null;
	}

	private Entry<K, V> eliminarMinimo(Entry<K, V> ret, NodoABB<K, V> nodo) {
		if (nodo.getLeft().getKey() == null)
			if (nodo.getRight().getKey() == null) {
				nodo.setKey(null);
				nodo.setKey(null);
				nodo.setRight(null);
				return ret;
			} else {
				nodo.getParent().setRight(nodo.getRight());
				nodo.getRight().setPadre(nodo.getParent());
				return ret;
			}
		else
			return eliminarMinimo(ret, nodo.getLeft());
	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> lista = new DoubleLinkedList<Entry<K, V>>();
		// System.out.println(root.getLeft() == null+ "okeeeee");
		entriesAux(lista, root);
		return lista;
	}

	private void entriesAux(PositionList<Entry<K, V>> lista, NodoABB<K, V> nodo) {
		for (Position<Entry<K, V>> entr : nodo.getKeys().positions())
			lista.addLast(entr.element());
		if (nodo.getLeft() != null)
			entriesAux(lista, nodo.getLeft());
		if (nodo.getRight() != null)
			entriesAux(lista, nodo.getRight());
	}

}
