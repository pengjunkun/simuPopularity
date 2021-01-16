import java.util.Random;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class CDS
{
	//for now, we assume that we have only one CSPAgent
	private CSPAgent agent;
	private Random random;
	private int hit_num;

	public CDS()
	{
		agent = new CSPAgent();
		random = new Random();
	}

	public int getHit_num()
	{
		return hit_num;
	}

	public void setHit_num(int hit_num)
	{
		this.hit_num = hit_num;
	}

	/**
	 * this is called by client
	 *
	 * @param id
	 * @return the latency of this request
	 */
	public float requestContent(int id, long timestamp)
	{
		int latency = 0;
		//hit
		if (agent.get(id, timestamp))
		{
			hit_num++;
			//fixed TCP connection:
			latency += (2 * MyConf.CLIENT2CDS + 3 * MyConf.SEND64);
			latency += (2 * MyConf.CLIENT2CDS + 2 * MyConf.SEND64) * 10;
			latency += (MyConf.SEND1024 + MyConf.RESTORE_LATENCY) * 1024;
			//when hits, we assume this content will be return through PKT_NUM_ONCE packets
			return latency;
		}
		//miss
		else
		{
			//fixed TCP connection:
			latency += (2 * MyConf.CLIENT2CDS + 3 * MyConf.SEND64);
			latency += (2 * MyConf.CLIENT2CDS + MyConf.SEND64) * 10 * (1
					+ MyConf.MISS_INCREASE * random.nextFloat());
			latency +=
					MyConf.SEND1024 * 1024 * (1 + MyConf.MISS_INCREASE * random
							.nextFloat());
			//when miss, we assume this content will be return through PKT_NUM_ONCE packets
			return latency;
		}
	}
}
