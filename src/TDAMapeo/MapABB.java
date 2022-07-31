package TDAMapeo;

import Excepciones.InvalidKeyException;
import TDADiccionario.ComparatorABB;
import TDALista.DoubleLinkedList;
import TDALista.PositionList;

public class MapABB<K extends Comparable<K>, V> implements Map<K, V> {

	protected NodoABB<K, V> root;
	protected int size;
	protected ComparatorABB<K> comp;

	public MapABB(ComparatorABB<K> c) {
		size = 0;
		comp = c;
		root = new NodoABB<K, V>();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Clave invalida");
		return getAux(key, root);
	}

	private V getAux(K key, NodoABB<K, V> nodo) {

		if (nodo.getKey() == null)
			return null;
		int c = comp.compare(key, nodo.getKey());
		if (c == 0)
			return nodo.getValue();
		else if (c < 0)
			return getAux(key, nodo.getLeft());
		else
			return getAux(key, nodo.getRight());

	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Clave invalida");
		NodoABB<K, V> nuevo = new NodoABB<K, V>(key, value);
		putAux(nuevo, root);
		return value;
	}

	private void putAux(NodoABB<K, V> nuevo, NodoABB<K, V> nodo) {

		if (nodo.getKey() == null) {
			nodo.setKey(nuevo.getKey());
			nodo.setValue(nuevo.getValue());
			nodo.setLeft(new NodoABB<K, V>());
			nodo.setRight(new NodoABB<K, V>());
			nodo.getLeft().setPadre(nodo);
			nodo.getRight().setPadre(nodo);
			size++;
		} else {
			int c = comp.compare(nodo.getKey(), nuevo.getKey());
			if (c == 0) {
				nodo.setValue(nuevo.getValue());
			} else {
				if (c < 0) {
					putAux(nuevo, nodo.getLeft());
				} else
					putAux(nuevo, nodo.getRight());
			}

		}

	}

	@Override
	public V remove(K key) throws InvalidKeyException {

		if (key == null)
			throw new InvalidKeyException("Clave invalida");
		NodoABB<K, V> nodo = buscar(key, root);
		V toRet = null;
		if (nodo.getKey() != null) {
			toRet = nodo.getValue();
			remove_aux(nodo);
		}

		return nodo.getValue();
	}

	/**
	 * Método auxiliar para la eliminación de un nodo del ABB.
	 * 
	 * @param nodo
	 */
	private void remove_aux(NodoABB<K, V> nodo) {
		if (isExternal(nodo)) {
			nodo.setKey(null);
			nodo.setLeft(null);
			nodo.setRight(null);
			nodo.setPadre(null);
			size--;
		} else {
			if (nodo == root) {//si es raiz
				if (soloTieneHijoIzquierdo(nodo)) {
					nodo.getLeft().setPadre(null);
					root = nodo.getLeft();
				} else {
					if (soloTieneHijoDerecho(nodo)) {
						nodo.getRight().setPadre(null);
						root = nodo.getRight();
					} else {
						nodo.setKey(eliminarMin(nodo.getRight()));
					}
				}
			} else {
				if (soloTieneHijoIzquierdo(nodo)) {
					if (nodo.getPadre().getLeft() == nodo) {
						nodo.getPadre().setLeft(nodo.getLeft());
					} else {
						nodo.getPadre().setRight(nodo.getLeft());
					}
					nodo.getLeft().setPadre(nodo.getPadre());
				} else {
					if (soloTieneHijoDerecho(nodo)) {
						if (nodo.getPadre().getLeft() == nodo)
							nodo.getPadre().setLeft(nodo.getRight());
						else
							nodo.getPadre().setRight(nodo.getRight());
						nodo.getRight().setPadre(nodo.getPadre());
					} else {
						// Se modifica el rótulo por el mínimo valor del ABB en inorden a partir de
						// nodo.
						nodo.setKey(eliminarMin(nodo.getRight()));
					}
				}
			}
		}
	}

	/**
	 * Establece si un nodo es hoja en el ABB.
	 * 
	 * @param nodo A considerar si es hoja en el ABB.
	 * @return Verdadero si el nodo es hoja, falso en caso contrario.
	 */
	private boolean isExternal(NodoABB<K, V> nodo) {
		return nodo.getLeft().getKey() == null && nodo.getRight().getKey() == null;
	}

	/**
	 * Establece si un nodo solamente tiene hijo izquierdo.
	 * 
	 * @param nodo A considerar si tiene solamente hijo izquierdo.
	 * @return Verdadero si nodo tiene solamente hijo izquierdo, falso en caso
	 *         contrario.
	 */
	private boolean soloTieneHijoIzquierdo(NodoABB<K, V> nodo) {
		return nodo.getLeft().getKey() != null && nodo.getRight().getKey() == null;
	}

	/**
	 * Establece si un nodo solamente tiene hijo derecho.
	 * 
	 * @param nodo A considerar si tiene solamente hijo derecho.
	 * @return Verdadero si nodo tiene solamente hijo derecho, falso en caso
	 *         contrario.
	 */
	private boolean soloTieneHijoDerecho(NodoABB<K, V> nodo) {
		return nodo.getRight().getKey() != null && nodo.getLeft().getKey() == null;
	}

	/**
	 * Obtiene el nodo cuyo valor es mínimo, a partir del subárbol izquierdo del
	 * nodo parametrizado.
	 * 
	 * @param nodo A partir del cual se busca el elemento mínimo en su subárbol
	 *             izquierdo.
	 * @return Elemento mínimo hallado.
	 */
	private K eliminarMin(NodoABB<K, V> nodo) {
		K toRet;
		if (nodo.getLeft().getKey() == null) {
			toRet = nodo.getKey();
			if (nodo.getRight().getKey() == null) {
				nodo.setKey(null);
				nodo.setLeft(null);
				nodo.setRight(null);
			} else {
				nodo.getPadre().setRight(nodo.getRight());
				nodo.getRight().setPadre(nodo.getPadre());

			}
		} else {
			toRet = eliminarMin(nodo.getLeft());
		}

		return toRet;
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

	@Override
	public Iterable<K> keys() {
		PositionList<K> lista = new DoubleLinkedList<K>();
		keys_aux(lista, root);
		return lista;
	}

	private void keys_aux(PositionList<K> lista, NodoABB<K, V> nodo) {

		if (nodo != null) {
			lista.addLast(nodo.getKey());
			if (nodo.getLeft() != null)
				keys_aux(lista, nodo.getLeft());
			if (nodo.getRight() != null)
				keys_aux(lista, nodo.getRight());

		}
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> lista = new DoubleLinkedList<V>();
		values_aux(lista, root);

		return lista;
	}

	private void values_aux(PositionList<V> lista, NodoABB<K, V> nodo) {
		if (nodo != null) {
			lista.addLast(nodo.getValue());
			if (nodo.getLeft() != null)
				values_aux(lista, nodo.getLeft());
			if (nodo.getRight() != null)
				values_aux(lista, nodo.getRight());

		}

	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> lista = new DoubleLinkedList<Entry<K, V>>();
		entries_aux(lista, root);
		return lista;
	}

	private void entries_aux(PositionList<Entry<K, V>> lista, NodoABB<K, V> nodo) {
		if (nodo != null) {
			lista.addLast(new Entrada<K, V>(nodo.getKey(), nodo.getValue()));
			if (nodo.getLeft() != null)
				entries_aux(lista, nodo.getLeft());
			if (nodo.getRight() != null)
				entries_aux(lista, nodo.getRight());
		}

	}

}
