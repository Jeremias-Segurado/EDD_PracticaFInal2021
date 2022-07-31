package TDADiccionario;

import java.util.Iterator;

import Excepciones.InvalidEntryException;
import Excepciones.InvalidKeyException;
import Excepciones.InvalidPositionException;
import TDALista.*;

public class DiccionarioHashAbierto<K, V> implements Dictionary<K, V> {

	protected PositionList<Entrada<K, V>> bucket[];
	protected int n;
	protected int N = 13;
	protected final double fc = 0.7;

	public DiccionarioHashAbierto() {
		bucket = (DoubleLinkedList<Entrada<K, V>>[]) new DoubleLinkedList[N];
		n = 0;
		for (int i = 0; i < bucket.length; i++) {
			bucket[i] = new DoubleLinkedList<Entrada<K, V>>();
		}
	}

	
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if (key==null) throw new InvalidKeyException("ERROR clave nula.");
		if (n / N >= fc)
			rehash();
		int clave = H(key);
		PositionList<Entrada<K, V>> l = bucket[clave];
		Entrada<K, V> nueva = new Entrada<K, V>(key, value);
		bucket[clave].addLast(nueva);
		n++;
		return nueva;
	}
	
	
	
	
	//-------------------------------------------
	
	
	private int H(K key) {
		return key.hashCode() % N;
	}

	
	
	
	/**
	 * Consulta el número de entradas del diccionario.
	 * 
	 * @return Número de entradas del diccionario.
	 */
	public int size() {
		return n;
	}

	/**
	 * Consulta si el diccionario está vacío.
	 * 
	 * @return Verdadero si el diccionario está vacío, falso en caso contrario.
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * Busca una entrada con clave igual a una clave dada y la devuelve, si no
	 * existe retorna nulo.
	 * 
	 * @param key Clave a buscar.
	 * @return Entrada encontrada.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key);
		Entry<K, V> ret = null;
		int clave = H(key);
		Iterator<Entrada<K, V>> it = bucket[clave].iterator();
		boolean esta = false;

		Entrada<K, V> act = it.hasNext() ? it.next() : null;
		while (!esta && act != null) {
			if (key.equals(act.getKey())) {
				esta = true;
				ret = act;
			} else {
				act = it.hasNext() ? it.next() : null;
			}
		}
		return ret;
	}

	/**
	 * Retorna una colección iterable que contiene todas las entradas con clave
	 * igual a una clave dada.
	 * 
	 * @param key Clave de las entradas a buscar.
	 * @return Colección iterable de las entradas encontradas.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K, V>> lista = new DoubleLinkedList<Entry<K, V>>();
		int clave = H(key);

		for (Entry<K, V> elem : bucket[clave]) {
			if (key.equals(elem.getKey())) {
				lista.addLast(elem);
			}
		}
		return lista;
	}

	/**
	 * Inserta una entrada con una clave y un valor dado en el diccionario y retorna
	 * la entrada creada.
	 * 
	 * @param key Clave de la entrada a crear.
	 * @return value Valor de la entrada a crear.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	/*
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);

		if (n / N >= fc)
			rehash();
		int clave = H(key);
		PositionList<Entrada<K, V>> l = bucket[clave];
		Entrada<K, V> nueva = new Entrada<K, V>(key, value);
		bucket[clave].addLast(nueva);
		n++;
		return nueva;
	}
	 */
	/**
	 * Remueve una entrada dada en el diccionario y devuelve la entrada removida.
	 * 
	 * @param e Entrada a remover.
	 * @return Entrada removida.
	 * @throws InvalidEntryException si la entrada no está en el diccionario o es
	 *                               inválida.
	 */
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {
		if (e == null)
			throw new InvalidEntryException("Entrada Invalida");
		int clave = H(e.getKey());
		PositionList<Entrada<K, V>> l = bucket[clave];
		Position<Entrada<K, V>> cursor = null;
		Iterator<Position<Entrada<K, V>>> it = l.positions().iterator();
		Entry<K, V> salida = null;
		while (it.hasNext() && salida == null) {
			cursor = it.next();
			if (cursor.element() == e) {
				salida = cursor.element();
				try {
					l.remove(cursor);
				} catch (InvalidPositionException ex) {
					ex.getMessage();
				}
				n--;
			}
		}
		if (salida == null)
			throw new InvalidEntryException("La entrada no se encuentra en el diccionario");

		return salida;
	}

	/**
	 * Retorna una colección iterable con todas las entradas en el diccionario.
	 * 
	 * @return Colección iterable de todas las entradas.
	 */
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K, V>> lista = new DoubleLinkedList<Entry<K, V>>();
		for (int i = 0; i < N; i++) {
			for (Entry<K, V> en : bucket[i]) {
				lista.addLast(en);
			}
		}
		return lista;
	}

	private void checkKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Clave invalida");
	}

	private void rehash() {
		Iterable<Entry<K, V>> entradas = entries();
		N = proximo_primo(N * 2);
		bucket = (PositionList<Entrada<K, V>>[]) new DoubleLinkedList[N];
		n = 0;
		for (int i = 0; i < N; i++)
			bucket[i] = new DoubleLinkedList<Entrada<K, V>>();
		for (Entry<K, V> e : entradas)
			try {
				insert(e.getKey(), e.getValue());
			} catch (InvalidKeyException ex) {
				ex.getMessage();
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

	public Iterable<V> eliminarTodas(K key) throws InvalidKeyException {

		if (key == null)
			throw new InvalidKeyException("Clave invalida");
		PositionList<V> salida = new DoubleLinkedList<V>();
		try {
			int clave = H(key);

			Iterable<Position<Entrada<K, V>>> entradas = bucket[clave].positions();
			for (Position<Entrada<K, V>> e : entradas) {
				if (e.element().getKey().equals(key)) {
					salida.addLast(e.element().getValue());
					bucket[clave].remove(e);
				}
			}
		} catch (InvalidPositionException e) {
			System.out.println(e.getMessage());
		}

		return salida;
	}

}