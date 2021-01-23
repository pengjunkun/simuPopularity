/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class CSPAgent
{
	private BSL bsl;
	//temp work: file size
	private long lastUpdate;
	private CandiLRU candidates;

	//threshold of admission
	//in start, all the files will be cached
	//after some replacement, the p will change
	private float p = 0.99F;
	private int requested = 0;
	private int replaced = 0;
	private int hited = 0;
	private int lruHited = 0;
	private int v = 0;

	private LRUCache lruCache;

	public CSPAgent()
	{
		bsl = new BSL(MyConf.BSL_SIZE);
		candidates = new CandiLRU();
		lruCache = new LRUCache(MyConf.BSL_SIZE);
		//		lruCache = new LRUCache(1 * 200 * 1024);
	}

	public boolean get(String id, long timestamp)
	{
		requested++;

		//for lru, if not hit, this id will be saved in
		CacheFile tmp2 = lruCache.get(id);
		if (tmp2 == null)
		{
			lruCache.put(id, new CacheFile(id, MyConf.FILE_SIZE, timestamp, 0));
		} else
		{
			lruHited++;
		}

		boolean result = bsl.get(id, timestamp);
		if (result)
			hited++;
		if (!result)
		{
			float candidate_pop = candidates.getPopulairty(id, timestamp);
			if (candidate_pop > p)
			{
				//remove candidate from candidates, because it has became a formal cache -_-
				float oldPop = bsl
						.put(id, MyConf.FILE_SIZE, timestamp, candidate_pop);
				MyLog.jack("oldpop: " + oldPop);
				candidates.remove(id);

				//				MyLog.jack("current p,candidate pop, replaced file pop: " + p + ", " + candidate_pop + ", " + oldPop);
				//only calculate the replacement cases
				if (oldPop != -1)
				{
					replaced++;
					if (oldPop < candidate_pop)
						//						addV(1);
						v++;
					else
						//						addV(-1);
						v--;
				}
			}
		}
		//every request brings a check chance
		checkUpdate(timestamp);
		return result;
	}

	//	private void addV(int v)
	//	{
	//		oldV.add(v);
	//		if (oldV.size() > MyConf.V_SIZE)
	//		{
	//			oldV.poll();
	//		}
	//	}
	//
	//	private int getTotalV()
	//	{
	//		int res = 0;
	//		for (int v : oldV)
	//		{
	//			res += v;
	//		}
	//		return res;
	//	}

	/**
	 * in one period, we have some tasks to do:
	 * 1. reset M
	 * 2. sort the first M nodes
	 * 3. recombine the link
	 * 4. update p(in CSPAgent)
	 * 5. reset v(in CSPAgent)
	 */
	private void checkUpdate(long timestamp)
	{
		if ((timestamp - lastUpdate) > MyConf.UPDATE_PERIOD)
		{
			bsl.updateBSL(timestamp);

			//			float slopeByFreq = (float) (getTotalV() * 1.0 / MyConf.V_SIZE);
			//4.
			//v is too high, which means the threshold is too high.
			//to conservative
			MyLog.jack("in this " + MyConf.UPDATE_PERIOD + " seconds");
			MyLog.jack(
					"request= " + requested + ",replaced=" + replaced + ", v= "
							+ v);
			//			if (slopeByFreq > MyConf.Vh)
			if (replaced > 15)
			{
				//only replaced > 15
				if (v > MyConf.Vh)
				{
					//				float tempP = bsl.getMiddleThreshold();
					//				if (Math.abs(tempP + 1) > 0.000001)
					//					p = tempP;

					p *= MyConf.POP_DECREASE;
					if (p < 1)
						p = 1.0F;
				}
				//v is too low, which means the threshold is too low
				//too aggressive
				//			else if (slopeByFreq < -MyConf.Vl)
				else if (v < -MyConf.Vl)
				{
					p *= MyConf.POP_INCREASE;
					//				float tempP = bsl.getMiddleThreshold();
					//				if (Math.abs(tempP + 1) > 0.000001)
					//					p = tempP;
					//				if (p < 1)
					//					p = 1.0F;
				}
			}
			MyLog.jack("set p= " + p);

			float normalBandwidth = (requested * MyConf.TRANS_ONE_FILE_NORM)
					/ MyConf.UPDATE_PERIOD;
			float ourBandwidth = (hited * MyConf.TRANS_ONE_FILE_OUR
					+ (requested - hited) * MyConf.TRANS_ONE_FILE_NORM)
					/ MyConf.UPDATE_PERIOD;
			float lruBandwidth = (lruHited * MyConf.TRANS_ONE_FILE_OUR
					+ (requested - lruHited) * MyConf.TRANS_ONE_FILE_NORM)
					/ MyConf.UPDATE_PERIOD;
			MyLog.jack("norm:" + normalBandwidth);
			MyLog.jack("our :" + ourBandwidth);
			MyLog.tagWriter(
					normalBandwidth + " " + ourBandwidth + " " + lruBandwidth);

			//5.
			lastUpdate = timestamp;
			requested = 0;
			replaced = 0;
			hited = 0;
			lruHited = 0;
			v = 0;
		}
	}

	public void updateBSL_LRU_size()
	{
		long newBslSize = bsl.updateSize();
		MyConf.BSL_SIZE = newBslSize;
		lruCache.updateSize(newBslSize);
		MyLog.jack(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~new BSL size: ");
		MyLog.jack("" + newBslSize);
		//		System.out.println("new size: "+newBslSize);
		MyLog.writeSize("" + newBslSize / MyConf.FILE_SIZE);
	}

	public void lruReport()
	{
		lruCache.report();
	}

	public void fianlLruReport()
	{
		lruCache.finalReport();
	}
}
