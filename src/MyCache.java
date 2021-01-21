import java.util.HashMap;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
class MyCache
{
	private long capacity;

	private long usedSize = 0;

	class Node
	{
		String key;
		CacheFile file;
		Node before;
		Node after;
	}

	private HashMap<String, Node> hashMap = new HashMap<String, Node>();
	private Node head, tail;

	public MyCache(long capacity)
	{
		this.capacity = capacity;

		head = new Node();
		head.before = null;

		tail = new Node();
		tail.after = null;

		head.after = tail;
		tail.before = head;
	}

	public void updateSize(long newCapacity)
	{
		while (newCapacity < usedSize)
		{
			removeOldest();
		}
		capacity = newCapacity;
	}

	public long getCapacity()
	{
		return capacity;
	}

	/**
	 * in our logic, head is the eldest node
	 *
	 * @return
	 */
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

	private void moveToTail(Node node)
	{
		unlink(node);
		addToTail(node);
	}

	public CacheFile get(String key)
	{
		Node node = hashMap.get(key);
		if (node == null)
		{
			return null;
		}
		//		printAll();
		//		MyLog.jack("hited key:" + key+",have "+hashMap.size());
		//		printAll();
		moveToTail(node);
		return node.file;
	}

	public float getPopByIndex(int index)
	{
		Node node = head;
		for (int i = 0; i < index; i++)
		{
			node = head.after;
		}
		if (node.file == null)
			MyLog.jack("not change pop");

		return node.file == null ? -1F : node.file.getPopularity();

	}

	/**
	 * @param key
	 * @param size
	 * @param timestamp
	 * @param popualrity
	 * @return return the popularity of file that is replaced(0 for not occur replacement)
	 */
	public float put(String key, int size, long timestamp, float popualrity)
	{
		//		if (usedSize==capacity)
		//			MyLog.jack("full now");
		//			return -1;
		float result = -1;
		Node node = hashMap.get(key);
		if (node == null)
		{
			//check the size firstly
			if (usedSize + size > capacity)
			{
				Node removedNode = removeOldest();
				result = removedNode.file.getPopularity();
				result = MyUtil
						.calNowPop(result, removedNode.file.getLastaccess(),
								timestamp);
			}
			Node newNode = new Node();
			newNode.key = key;
			newNode.file = new CacheFile(key, size, timestamp, popualrity);
			hashMap.put(key, newNode);
			addToTail(newNode);
			usedSize += size;
		}
		return result;
	}

	private Node removeOldest()
	{
		Node popedNode = this.popHead();
		usedSize -= popedNode.file.getSize();
		Node removedNode = hashMap.remove(popedNode.key);
		return removedNode;
	}

	private void unlink(Node node)
	{
		Node before = node.before;
		Node after = node.after;
		before.after = after;
		after.before = before;
	}

	/**
	 * @param flagNode   the node which you want follow
	 * @param insertNode the node which you want insert into list
	 */
	private void inserAfter(Node flagNode, Node insertNode)
	{
		insertNode.before = flagNode;
		insertNode.after = flagNode.after;
		flagNode.after.before = insertNode;
		flagNode.after = insertNode;
	}

	public void updateHeadM(int M, long timestamp)
	{
		Node node = head;
		for (int i = 0; i < M; i++)
		{
			node = node.after;
			if (node == tail)
				break;
			node.file.setPopularity(MyUtil.calNowPop(node.file.getPopularity(),
					node.file.getLastaccess(), timestamp));
		}
	}

	/**
	 * write a Select-Sort algorithm by hand
	 * tail_____not need to sort____|M|____unsorted_____|uNode|aNode|____already sorted____|head
	 */
	public void sortM_ByPop(int M)
	{
		//A stands for the number of already sorted nodes
		int A = 0;
		//aNode stands for the last sorted node(see in pic above)
		//uNode stands for the first unsorted node(see in above pic)
		Node aNode = head;
		Node uNode = head.after;
		while (A < M)
		{
			if (uNode == tail)
				break;
			Node minNode = findMin(M - A, uNode);
			unlink(minNode);
			inserAfter(aNode, minNode);
			aNode = minNode;
			A++;
			if (aNode == uNode)
				uNode = aNode.after;
		}

	}

	/**
	 * find the min popularity Node in the next 'count' nodes, which need (count-1) comparation.
	 *
	 * @param count
	 * @param startNode
	 * @return
	 */
	private Node findMin(int count, Node startNode)
	{
		Node result = startNode;
		Node nextNode = startNode.after;
		for (int i = 1; i < count; i++)
		{
			if (nextNode == tail)
				break;
			if (nextNode.file.getPopularity() < result.file.getPopularity())
				result = nextNode;

			nextNode = nextNode.after;
		}
		return result;

	}

	/**
	 * for debug use
	 */
	public void printAll()
	{
		Node node = head.after;
		while (node != tail)
		{
			//			MyLog.jack( "id=" + node.key + ";pop=" + node.file.getPopularity());
			MyLog.jackJoint(node.key + ",");
			node = node.after;
		}
		MyLog.jack("------------");
	}

	public static void unitTest()
	{
		MyCache ln = new MyCache(21);
//		ln.put(20, 0, 1, 20);
//		ln.put(18, 0, 1, 18);
//		ln.put(19, 0, 1, 19);
//		ln.put(1, 0, 1, 1);
//		ln.put(2, 0, 1, 2);
//		ln.put(3, 0, 1, 3);
//		ln.put(4, 0, 1, 4);
//		ln.put(5, 0, 1, 5);
//		ln.put(6, 0, 1, 6);
//		ln.put(7, 0, 1, 7);
//		ln.put(8, 0, 1, 8);
//		ln.put(9, 0, 1, 9);
//		ln.put(10, 0, 1, 10);
//		ln.put(11, 0, 1, 11);
//		ln.put(12, 0, 1, 12);
//		ln.put(13, 0, 1, 13);
//		ln.put(14, 0, 1, 14);
//		ln.put(15, 0, 1, 15);
//		ln.put(16, 0, 1, 16);
//		ln.put(17, 0, 1, 17);

		ln.printAll();
		ln.sortM_ByPop(10);

		ln.printAll();
	}
}
