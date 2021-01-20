/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class BSL
{
	//the memory stack
	private MyCache memory;
	//the M head records
	private int M;
	//new inserting number
	private int newPutNum = 0;
	private int requestedNum = 0;
	private int lastRequestedNum = 0;

	public BSL(long bslSize)
	{
		memory = new MyCache(bslSize);
		M = 100;
	}

	public boolean get(String id, long timestamp)
	{
		requestedNum++;
		boolean result = false;
		CacheFile tmp = memory.get(id);
		//find,update last access time and popularity
		if (tmp != null)
		{
			result = true;
			tmp.setPopularity(
					MyUtil.calPop(tmp.getPopularity(), tmp.getLastaccess(),
							timestamp));
			tmp.setLastaccess(timestamp);
		}
		//not found
		return result;
	}

	/**
	 * in one period, we have some tasks to do:
	 * 1. reset M
	 * 2. sort the first M nodes
	 * 3. recombine the link
	 * 4. update p(in CSPAgent)
	 * 5. reset v(in CSPAgent)
	 */
	public void updateBSL(long timestamp)
	{
		//1.
		M = 2 * newPutNum;
		MyLog.jack("new M= " + M);
		newPutNum = 0;

		memory.updateHeadM(M, timestamp);

		//2~3. sort the first M nodes
		memory.sortM_ByPop(M);
	}

	public float getMiddleThreshold()
	{
		int index = M / 2;
		return memory.getPopByIndex(index);

	}

	public long updateSize()
	{
		if (lastRequestedNum == 0)
		{
			lastRequestedNum = requestedNum;
			return memory.getCapacity();
		}
		long newBslSize = MyUtil.calBslSize(lastRequestedNum, requestedNum);
		lastRequestedNum = requestedNum;
		requestedNum = 0;
		memory.updateSize(newBslSize);
		return newBslSize;

	}

	/**
	 * put a new file into cache.
	 * this may occur a replacement, right or not?
	 *
	 * @param id
	 * @param size
	 * @param timestamp
	 * @param popularity
	 * @return return the popularity of file that is replaced(0 for not occur replacement)
	 */
	public float put(String id, int size, long timestamp, float popularity)
	{
		newPutNum++;
		return memory.put(id, size, timestamp, popularity);
	}

}
