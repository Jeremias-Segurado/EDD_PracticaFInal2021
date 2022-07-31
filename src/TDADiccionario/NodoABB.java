package TDADiccionario;

import TDALista.*;

public class NodoABB<K, V> {
	protected NodoABB<K, V> padre, left, right;
	protected K key;
	protected PositionList<Entry<K, V>> claves;

	public NodoABB() {
		key = null;
		left = null;
		right = null;
		claves = new DoubleLinkedList<Entry<K, V>>();
	}

	public NodoABB(K l, NodoABB<K, V> p) {
		padre = p;
		key = l;
		claves = new DoubleLinkedList<Entry<K, V>>();
	}

	public NodoABB<K, V> getParent() {
		return padre;
	}

	public void setKey(K k) {
		key = k;
	}

	public PositionList<Entry<K, V>> getKeys() {
		return claves;
	}

	public void setLeft(NodoABB<K, V> l) {
		left = l;
	}

	public void setRight(NodoABB<K, V> r) {
		right = r;
	}

	public K getKey() {
		return key;
	}

	public NodoABB<K, V> getLeft() {
		return left;
	}

	public NodoABB<K, V> getRight() {
		return right;
	}

	public void setPadre(NodoABB<K, V> p) {
		padre = p;
	}

	public void setValues(PositionList<Entry<K, V>> l) {
		claves = l;

	}
}