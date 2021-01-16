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
	//value of correct replacement
	private int v = 0;
	private int requested = 0;
	private int replaced = 0;

	private LRUCache memory2;
	private long lastReport;

	public CSPAgent()
	{
		bsl = new BSL(MyConf.BSL_SIZE);
		candidates = new CandiLRU();
		memory2 = new LRUCache(MyConf.BSL_SIZE);
	}

	public boolean get(int id, long timestamp)
	{
		requested++;
		boolean result = bsl.get(id, timestamp);

		CacheFile tmp2 = memory2.get(id);
		if (tmp2 == null)
		{
			memory2.put(id, new CacheFile(id, MyConf.FILE_SIZE, timestamp, 0));
		}

		//if not in BSL
		if (!result)
		{
			float candidate_pop = candidates.getPopulairty(id, timestamp);
			if (candidate_pop > p)
			{
				//remove candidate from candidates, because it has became a formal cache -_-
				float oldPop = bsl
						.put(id, MyConf.FILE_SIZE, timestamp, candidate_pop);
				candidates.remove(id);

				//				System.out.println( "current p,file pop, replaced file pop: " + p + ", " + candidate_pop + ", " + oldPop);
				//only calculate the replacement cases
				if (oldPop != -1)
				{
					replaced++;
					if (oldPop < candidate_pop)
						v++;
					else
						v--;
				}
			}
		}
		//every request brings a check chance
		checkUpdate(timestamp);
		checkReport(timestamp);
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
	private void checkUpdate(long timestamp)
	{
		if ((timestamp - lastUpdate) > MyConf.UPDATE_PERIOD)
		{
			bsl.updateBSL();
			float diff = (float) (v * 1.0 / replaced);
			//4.
			//v is too high, which means the threshold is too high.
			//to conservative
			System.out.println("in this " + MyConf.UPDATE_PERIOD + " seconds");
			MyLog.jack(
					"request= " + requested + ",replaced=" + replaced + ", v= "
							+ v + ", diff=" + diff);
			if (diff > MyConf.Vh)
			{
				p *= MyConf.POP_DECREASE;
				if (p < 1)
					p = 1.0F;
			}
			//v is too low, which means the threshold is too low
			//too aggressive
			else if (diff < -MyConf.Vl)
			{
				p *= MyConf.POP_INCREASE;
			}
			System.out.println("set p= " + p);
			//5.
			v = 0;
			lastUpdate = timestamp;
			requested = 0;
			replaced = 0;
		}
	}

	private void checkReport(long timestamp)
	{
		if ((timestamp - lastReport) > MyConf.REPORT_PERIOD)
		{
			memory2.report();
			lastReport=timestamp;
		}
	}
}
