package TDADiccionario;

import java.util.Iterator;

import Excepciones.InvalidEntryException;
import Excepciones.InvalidKeyException;
import Excepciones.InvalidPositionException;
import TDALista.*;

public class DiccionarioConLista<K, V> implements Dictionary<K, V> {
	private PositionList<Entrada<K, V>> list;

	public DiccionarioConLista() {
		list = new DoubleLinkedList<Entrada<K, V>>();
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		Entry<K, V> salida = null;
		Iterator<Entrada<K, V>> it = list.iterator();
		while (it.hasNext() && salida == null) {
			Entry<K, V> pos = it.next();
			if (pos.getKey().equals(key))
				salida = pos;
		}
		return salida;
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K, V>> salida = new DoubleLinkedList<Entry<K, V>>();
		for (Entry<K, V> i : list) {
			if (i.getKey().equals(key)) {
				salida.addLast(i);
			}
		}
		return salida;
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entrada<K, V> entrada = new Entrada<K, V>(key, value);
		list.addLast(entrada);
		return entrada;
	}

	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if (e == null)
			throw new InvalidEntryException("Entrada nula");

		Iterator<Position<Entrada<K, V>>> it = list.positions().iterator();
		boolean encontre = false;
		Entry<K, V> salida = null;
		while (it.hasNext() && !encontre) {
			Position<Entrada<K, V>> pos= it.next();
			if (pos.element().equals(e)) 
				try {
					list.remove(pos);
					encontre = true;
					salida = pos.element();

				} catch (InvalidPositionException ex) {
					ex.getMessage();
				}
			
			
		}
		if (!encontre)
			throw new InvalidEntryException("Entrada invalida");
		return salida;

	}

	public Iterable<Entry<K, V>> entries() {
		return (Iterable) list;
	}

	private void checkKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("error: clave nula");
	}

}