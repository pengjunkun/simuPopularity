import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/15.
 */
public class LRUCache extends LinkedHashMap<Integer, CacheFile>
{
	private long capacity;

	private long usedSize = 0;

	private int requested;
	private int hited;

	public LRUCache(long initialCapacity)
	{
		super(100, 0.75F, true);
		capacity = initialCapacity;
	}

	@Override public CacheFile get(Object key)
	{
		requested += 1;
		if (super.get(key) != null)
		{
			hited += 1;
			//			MyLog.jack("hited key:" + key+",have "+size());
		}
		return null;
	}

	@Override public CacheFile put(Integer key, CacheFile value)
	{

		usedSize += value.getSize();
		return super.put(key, value);
	}

	@Override protected boolean removeEldestEntry(
			Map.Entry<Integer, CacheFile> eldest)
	{
		boolean res = usedSize > capacity;
		if (res)
		{
			usedSize -= eldest.getValue().getSize();
		}
		return res;
	}

	public void report()
	{
		if (requested != 0)
		{
			MyLog.jack("-------------LRU report-------------");
			System.out.println("sent: " + requested);
			System.out.println("cache capacity(entry number): "
					+ MyConf.BSL_SIZE / MyConf.FILE_SIZE);
			if (requested != 0)
				System.out.println(
						"hited: " + hited + " ;hit ratio: " + (hited * 1.0
								/ requested));
			MyLog.jack("-------------LRU report-------------");
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

}
