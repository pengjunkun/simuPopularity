/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class CSPAgent
{
	private BSL bsl;
	//temp work: the BSL size, unit:KB
	private long bslSize = 10 * 1024 * 1024;
	//temp work: file size
	private int fileSize=1*1024;
	private CandiLRU candidates;

	//threshold of admission
	private int p;
	//value of correct replacement
	private int v;

	public CSPAgent()
	{
		bsl = new BSL(bslSize);
		candidates=new CandiLRU();
	}

	public boolean get(int id,long timestamp)
	{
		boolean result=bsl.get(id,timestamp);
		//if not in BSL
		if (!result){
			float candidate_pop= candidates.getPopulairty(id,timestamp);
			if (candidate_pop>p){
				bsl.put(id,fileSize,timestamp,candidate_pop);
			}

		}

		return result;
	}
}
