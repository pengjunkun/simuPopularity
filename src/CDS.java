/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class CDS
{
	//for now, we assume that we have only one CSPAgent
	private CSPAgent agent;

	public CDS()
	{
		agent=new CSPAgent();
	}

	/**
	 * this is called by client
	 *
	 * @param id
	 * @return the latency of this request
	 */
	public int requestContent(int id,long timestamp)
	{
		int latency = 0;
		//firstly, try to get from bsl
		if (agent.get(id,timestamp))
		{
			latency += SimuValue.CLIENT2CDS;
			latency += SimuValue.CDS2CSP_ID;
			//when hits, we assume this content will be return through PKT_NUM_ONCE packets
			latency += SimuValue.CSP2CLIENT * SimuValue.PKT_NUM_ONCE;
			return latency;
		}
		//else send a full packet to CSP which will handover to client
		else
		{
			latency += SimuValue.CLIENT2CDS;
			latency += (SimuValue.CDS2CSP_FULL + SimuValue.CSP2CLIENT)
					* SimuValue.PKT_NUM_ONCE;
			//when miss, we assume this content will be return through PKT_NUM_ONCE packets
			return latency;
		}
	}
}
