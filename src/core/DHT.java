package core;


public interface DHT<V> {
	
	public V get(String key);
	
	public void put(String key, V element);
	
	public void join(Node<V> other);
	
}
