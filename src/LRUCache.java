import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/15.
 */
public class LRUCache
{
	private long capacity;
	private HashMap<Integer, Node> hashMap;

	private long usedSize = 0;

	private int requested;
	private int hited;

	private Node head;
	private Node tail;

	class Node
	{
		int key;
		CacheFile file;
		Node before;
		Node after;
	}

	public LRUCache(long initialCapacity)
	{
		capacity = initialCapacity;
		hashMap = new HashMap<>();

		head = new Node();
		head.before = null;

		tail = new Node();
		tail.after = null;

		head.after = tail;
		tail.before = head;
	}

	private Node popHead()
	{
		Node res = head.after;
		unlink(res);
		return res;
	}

	private void addToTail(Node node)
	{
		node.after = tail;
		node.before = tail.before;
		tail.before.after = node;
		tail.before = node;
	}

	private void unlink(Node node)
	{
		Node before = node.before;
		Node after = node.after;
		before.after = after;
		after.before = before;
	}

	private void moveToTail(Node node)
	{
		unlink(node);
		addToTail(node);
	}

	public CacheFile get(Object key)
	{
		Node node = hashMap.get(key);
		requested += 1;
		if (node != null)
		{
			moveToTail(node);
			hited += 1;
		}

		return null;
	}

	private Node removeOldest()
	{
		Node popedNode = this.popHead();
		usedSize -= popedNode.file.getSize();
		Node removedNode = hashMap.remove(popedNode.key);
		return removedNode;
	}

	public void put(Integer key, CacheFile value)
	{
		Node node = hashMap.get(key);
		if (node == null)
		{
			//check the size firstly
			while (usedSize + value.getSize() > capacity)
				removeOldest();
			Node newNode = new Node();
			newNode.key = key;
			newNode.file = value;
			hashMap.put(key, newNode);
			addToTail(newNode);
			usedSize += value.getSize();
		}
	}

	public void report()
	{
		if (requested != 0)
		{
			MyLog.jack("-------------LRU report-------------");
			MyLog.jack("sent: " + requested);
			MyLog.jack("cache capacity(entry number): "
					+ MyConf.BSL_SIZE / MyConf.FILE_SIZE);
			if (requested != 0)
				MyLog.jack("hited: " + hited + " ;hit ratio: ");
			float ratio = (float) (hited * 1.0 / requested);
			MyLog.jack("" + ratio);
			MyLog.writeLRUHit("" + ratio);
			MyLog.jack("-------------LRU report-------------");
			requested = 0;
			hited = 0;
		}
	}

	public static void unitTest()
	{
		LRUCache ln = new LRUCache(21);
		ln.put(20, 0, 1, 20);
		ln.put(18, 0, 1, 18);
		ln.put(19, 0, 1, 19);
		ln.put(1, 0, 1, 1);
		ln.put(2, 0, 1, 2);
		ln.put(3, 0, 1, 3);
		ln.put(4, 0, 1, 4);
		ln.put(5, 0, 1, 5);
		ln.put(6, 0, 1, 6);
		ln.put(7, 0, 1, 7);
		ln.put(8, 0, 1, 8);
		ln.put(9, 0, 1, 9);
		ln.put(10, 0, 1, 10);
		ln.put(11, 0, 1, 11);
		ln.put(12, 0, 1, 12);
		ln.put(13, 0, 1, 13);
		ln.put(14, 0, 1, 14);
		ln.put(15, 0, 1, 15);
		ln.put(16, 0, 1, 16);
		ln.put(17, 0, 1, 17);

		ln.get(20);
	}

	private void put(int i, int i1, int i2, int i3)
	{
		usedSize += i1;
		put(i, new CacheFile(i, i1, i2, i3));
	}

	public void updateSize(long newSize)
	{
		while (usedSize > newSize)
			removeOldest();
		capacity = newSize;
	}
}
