import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class CandiLRU extends LinkedHashMap<String,CacheFile>
{
	public CandiLRU()
	{
		super(MyConf.CANDIDATES_NUM,0.75f,true);
	}

	/**
	 * if not in candidates, this record will be saved
	 * otherwise, will return the new popularity value
	 * @param id
	 * @param timestamp
	 * @return
	 */
	public float getPopulairty(String id,long timestamp){
		CacheFile file= super.get(id);
		if (file==null){
			put(id,new CacheFile(id,0,timestamp,1));
			return 1;
		}
		//if found, update the popularity
		long old_time=file.getLastaccess();
		file.setLastaccess(timestamp);
		float old_pop=file.getPopularity();
		float newPop=MyUtil.candiCalPop(old_pop,old_time,timestamp);
		file.setPopularity(newPop);
		return newPop;

	}


	@Override protected boolean removeEldestEntry(
			Map.Entry<String, CacheFile> eldest)
	{
		return size()>MyConf.CANDIDATES_NUM;
	}
}
