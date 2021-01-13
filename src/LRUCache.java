import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
class LRUCache
{
	private long capacity;

	private long usedSize = 0;

	class Node
	{
		int key;
		CacheFile file;
		Node before;
		Node after;
	}

	private HashMap<Integer, Node> hashMap = new HashMap<Integer, Node>();
	private Node head, tail;

	public LRUCache(long capacity)
	{
		this.capacity = capacity;

		head = new Node();
		head.before = null;

		tail = new Node();
		tail.after = null;

		head.after = tail;
		tail.before = head;
	}

	public Node getHead()
	{
		return head;
	}

	public Node getTail()
	{
		return tail;
	}

	/**
	 * in our logic, head is the eldest node
	 *
	 * @return
	 */
	private Node popHead()
	{
		Node res = head.after;
		removeNode(res);
		return res;
	}

	private void addToTail(Node node)
	{
		node.after = tail;
		node.before = tail.before;
		tail.before.after = node;
		tail.before = node;
	}

	private void moveToTail(Node node)
	{
		removeNode(node);
		addToTail(node);
	}

	public CacheFile get(int key)
	{
		Node node = hashMap.get(key);
		if (node == null)
		{
			return null;
		}
		moveToTail(node);
		return node.file;
	}
	public void put(int key, int size, long timestamp, float popualrity) {
		Node node = hashMap.get(key);
		if (node == null) {
			//check the size firstly
			if (usedSize+size>capacity){
				Node head=this.popHead();
				hashMap.remove(head.key);
			}
			Node newNode = new Node();
			newNode.key = key;
			newNode.file=new CacheFile(key,size,timestamp,popualrity);
			hashMap.put(key, newNode);
			addToTail(newNode);
			usedSize+=size;
		} else {
			//if already exists
			get(key);
		}
	}

	private void removeNode(Node node)
	{
		Node before = node.before;
		Node after = node.after;
		before.after = after;
		after.before = before;
	}


}
