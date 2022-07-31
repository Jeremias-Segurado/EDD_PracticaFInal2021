package TDAMapeo;

import java.util.Iterator;

import Excepciones.BoundaryViolationException;
import Excepciones.EmptyListException;
import Excepciones.InvalidKeyException;
import Excepciones.InvalidPositionException;
import TDALista.*;

public class MapeoConLista<K, V> implements Map<K, V> {

	protected PositionList<Entrada<K, V>> S;

	public MapeoConLista() {
		S = new DoubleLinkedList<Entrada<K, V>>();
	}

	@Override
	public int size() {
		return S.size();
	}

	@Override
	public boolean isEmpty() {

		return size() == 0;
	}

	@Override
	public V get(K key) throws InvalidKeyException {

		if (key == null)
			throw new InvalidKeyException("Clave invalida");

		V value = null;
		Iterator<Entrada<K, V>> it = S.iterator();
		Entrada<K, V> p = null;
		boolean encontre = false;
		while (it.hasNext() && !encontre) {
			p = it.next();
			if (p.getKey().equals(key)) {
				encontre = true;
				value = p.getValue();
			}

		}
		return value;

	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {

		if (key == null)
			throw new InvalidKeyException("Clave invalida");
		// Iterable<Position<Entrada<K, V>>> pos = S.positions();
		Iterator<Entrada<K, V>> it = S.iterator();
		boolean encontre = false;

		Entrada<K, V> p = null;
		while (it.hasNext() && !encontre) {
			p = it.next();
			if (p.equals(key))
				encontre = true;
		}

		V aux = null;

		if (encontre) {
			aux = p.getValue();
			p.setValue(value);
		} else {
			S.addLast(new Entrada<K, V>(key, value));
		}

		return aux;

	}

	@Override
	public V remove(K key) throws InvalidKeyException {

		if (key == null)
			throw new InvalidKeyException("Clave inválida");
		V removed=null;
		try {
		Position<Entrada<K,V>> p= (S.isEmpty())?null:S.first();
		while(p!=null) {
			if(p.element().getKey().equals(key)) {
				removed=p.element().getValue();
				S.remove(p);
				p=null;
			}else {
				p=(S.last()==p)?null:S.next(p);
			}
			
		}
		}catch(InvalidPositionException|BoundaryViolationException|EmptyListException e) {
			
		}
			
		
		return removed;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> lista = new DoubleLinkedList<K>();

		Iterator<Entrada<K, V>> it = S.iterator();
		while(it.hasNext()) {
			lista.addLast(it.next().getKey());
		}
		return lista;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> lista = new DoubleLinkedList<V>();
		Iterator<Entrada<K, V>> it = S.iterator();
		while(it.hasNext()) {
			lista.addLast(it.next().getValue());
		}

		return lista;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {

		PositionList<Entry<K, V>> lista = new DoubleLinkedList<Entry<K, V>>();
		Iterator<Entrada<K, V>> it = S.iterator();
		while(it.hasNext()) {
			lista.addLast(it.next());
		}

		return lista;
	}

}
