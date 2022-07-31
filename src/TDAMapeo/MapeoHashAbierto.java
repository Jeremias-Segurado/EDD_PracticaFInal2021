package TDAMapeo;

import java.util.Iterator;

import Excepciones.InvalidKeyException;
import Excepciones.InvalidPositionException;
import TDALista.*;

public class MapeoHashAbierto<K, V> implements Map<K, V> {

	protected PositionList<Entrada<K, V>>[] A;
	protected int N;
	protected int n;
	private static double fc = 0.9;

	private int hash(K key) {
		return key.hashCode() % N;
	}

	public MapeoHashAbierto(int tam) {
		N=tam;
		n = 0;
		A = (PositionList<Entrada<K, V>>[]) new PositionList[N];
		for (int i = 0; i < N; i++)
			A[i] = new DoubleLinkedList<Entrada<K, V>>();
	}
	
	public MapeoHashAbierto() {
		this(13);
	}

	public int size() {
		return n;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public V get(K key) throws InvalidKeyException {

		checkKey(key);
		V ret = null;
		int clave = hash(key);
		Iterator<Position<Entrada<K, V>>> it = A[clave].positions().iterator();
		Position<Entrada<K, V>> act = it.hasNext() ? it.next() : null;
		boolean esta = false;

		while (!esta && act != null) {
			if (act.element().getKey().equals(key)) {
				ret = act.element().getValue();
				esta = true;
			} else {
				act = it.hasNext() ? it.next() : null;
			}
		}
		return ret;
	}

	public V put(K key, V value) throws InvalidKeyException {

		checkKey(key);

		V ret = null;
		int clave = hash(key);

		Iterator<Entrada<K, V>> it = A[clave].iterator();
		boolean esta = false;

		while (!esta && it.hasNext()) {
			Entrada<K, V> act = it.next();

			if (key.equals(act.getKey())) {
				esta = true;
				ret = act.getValue();
				act.setValue(value);

			} else {
				act = it.hasNext() ? it.next() : null;
			}
		}
		if (!esta) {
			Entrada<K, V> e = new Entrada<K, V>(key, value);
			A[clave].addLast(e);
			n++;
		}

		if (n / N > fc)
			agrandarTabla();
		return ret;

	}

	public V remove(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Clave invalida");
		V valor = null;
		Iterator<Position<Entrada<K, V>>> it = A[hash(key)].positions().iterator();
		Position<Entrada<K, V>> pos = it.hasNext() ? it.next() : null;
		boolean esta = false;

		try {
			while (!esta && pos != null) {
				if (pos.element().getKey().equals(key)) {
					valor = pos.element().getValue();
					esta = true;
					A[hash(key)].remove(pos);
					n--;
				} else {
					pos = it.hasNext() ? it.next() : null;
				}
			}
		} catch (InvalidPositionException e) {
			System.out.println(e.getMessage());
		}
		return valor;
	}

	public Iterable<K> keys() {
		PositionList<K> lista = new DoubleLinkedList<K>();
		for (int i = 0; i < N; i++) {
			for (Position<Entrada<K, V>> en : A[i].positions()) {
				lista.addLast(en.element().getKey());
			}
		}
		return lista;
	}

	public Iterable<V> values() {
		PositionList<V> lista = new DoubleLinkedList<V>();
		for (int i = 0; i < N; i++) {
			for (Position<Entrada<K, V>> en : A[i].positions()) {
				lista.addLast(en.element().getValue());
			}
		}
		return lista;

	}

	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> lista = new DoubleLinkedList<Entry<K, V>>();
		for (int i = 0; i < N; i++) {
			for (Position<Entrada<K, V>> en : A[i].positions()) {
				lista.addLast(en.element());
			}
		}
		return lista;
	}

	public void agrandarTabla() {
		N = proximo_primo(N * 2);
		PositionList<Entrada<K, V>>[] T;
		T = (PositionList<Entrada<K, V>>[]) new DoubleLinkedList[N];
		for (int i = 0; i < N; i++)
			T[i] = new DoubleLinkedList<Entrada<K, V>>();

		for (int i = 0; i < A.length; i++)
			for (Entrada<K, V> e : A[i]) {
				int p = hash(e.getKey());
				T[p].addLast(e);
			}
		A=T;
	}

	private void checkKey(K key) throws InvalidKeyException {
		if (key == null) {
			throw new InvalidKeyException("Clave invalida");
		}
	}

	private int proximo_primo(int n) {
		boolean es = false;
		n++;
		while (!es) {
			if (esPrimo(n))
				es = true;
			else
				n++;

		}
		return n;
	}

	private boolean esPrimo(int n) {
		boolean es = false;
		int divisor = 2;
		while (divisor < n && !es) {
			if (n % divisor == 0)
				es = true;
			else
				divisor++;

		}

		return es;
	}

}
