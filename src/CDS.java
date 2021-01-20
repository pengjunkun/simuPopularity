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
	private int requested;

	public CDS()
	{
		agent = new CSPAgent();
		random = new Random();
	}

	/**
	 * this is called by client
	 *
	 * @param id
	 * @return the latency of this request
	 */
	public float requestContent(String id, long timestamp)
	{
		requested++;
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

	public void report()
	{
		MyLog.jack("==============report============");
		MyLog.jack("sent: ");
		MyLog.jack("" + requested);
		MyLog.jack("cache capacity(entry number): "
				+ MyConf.BSL_SIZE / MyConf.FILE_SIZE);
		if (requested != 0)
			MyLog.jack("hited: " + hit_num + " ;hit ratio: ");
		float ratio = (float) (hit_num * 1.0 / requested);
		MyLog.jack("" + ratio);
		MyLog.writeMyHit(ratio + "");
		//		MyLog.writeByIndicator(ratio + "");
		//		MyLog.jack("total latency: " + totalLatency + "ms");
		//		MyLog.jack( "throughput: " + (count * MyConf.FILE_SIZE / 3600) + "KB/s");
		MyLog.jack("==============report============");
		requested = 0;
		hit_num = 0;

		//----LRU Report-----
		agent.lruReport();

		agent.VReport();
	}

	public void updateBSL_LRU_size()
	{
		agent.updateBSL_LRU_size();
	}
}
