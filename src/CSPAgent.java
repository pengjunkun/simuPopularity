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
	//after some replacement, the p will increase
	private float p = 1;
	//value of correct replacement
	private int v = 0;

	public CSPAgent()
	{
		bsl = new BSL(MyConf.BSL_SIZE);
		candidates = new CandiLRU();
	}

	public boolean get(int id, long timestamp)
	{
		boolean result = bsl.get(id, timestamp);
		//if not in BSL
		if (!result)
		{
			float candidate_pop = candidates.getPopulairty(id, timestamp);
			if (candidate_pop > p)
			{
				//remove candidate from candidates, because it has became a formal cache -_-
				float oldPop = bsl.put(id, MyConf.FILE_SIZE, timestamp, candidate_pop);
				candidates.remove(id);

				//				System.out.println( "current p,file pop, replaced file pop: " + p + ", " + candidate_pop + ", " + oldPop);
				//only calculate the replacement cases
				if (oldPop != 0)
				{
					if (oldPop < candidate_pop)
						v++;
					else
						v--;
				}
			}
		}
		//every request brings a check chance
		checkUpdate(timestamp);
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
			//4.
			//v is too high, which means the threshold is too high.
			System.out.println(
					"in this " + MyConf.UPDATE_PERIOD + " seconds, v= " + v);
			if (v > MyConf.Vh)
			{
				p *= MyConf.POP_DECREASE;
//				if (p < 1)
//					p = 1.0F;
			}
			//v is too low, which means the threshold is too low
			else if (v < -MyConf.Vl)
			{
				p *= MyConf.POP_INCREASE;
			}
			System.out.println("set p= " + p);
			//5.
			v = 0;

			//update lastUpdate time
			lastUpdate = timestamp;
		}

	}
}
