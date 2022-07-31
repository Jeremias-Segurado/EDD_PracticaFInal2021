package TDAMapeo;

public class NodoABB<K, V> {

	protected NodoABB<K, V> padre, left, right;
	protected K key;
	protected V value;

	public NodoABB() {
		padre = null;
		left = null;
		right = null;
		key = null;
		value = null;
	}

	public NodoABB(K key, V value) {
		this();
		this.key = key;
		this.value = value;
	}

	public NodoABB<K, V> getPadre() {
		return padre;
	}

	public void setPadre(NodoABB<K, V> padre) {
		this.padre = padre;
	}

	public NodoABB<K, V> getLeft() {
		return left;
	}

	public void setLeft(NodoABB<K, V> left) {
		this.left = left;
	}

	public NodoABB<K, V> getRight() {
		return right;
	}

	public void setRight(NodoABB<K, V> right) {
		this.right = right;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
