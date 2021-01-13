import java.util.*;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class BSL {
	//the memory stack
	private LRUCache memory;
	private long BSLSize;
	private long lastUpdate;
	//the M head records
	private int M;
	//new inserting number
	private int newPutNum=0;
	public BSL(long bslSize)
	{
		BSLSize=bslSize;
		memory=new LRUCache(BSLSize);
		lastUpdate=0;
		M=1000;
	}

	public boolean get(int id,long timestamp)
	{
		boolean result=false;
		CacheFile tmp=memory.get(id);
		//find,update last access time and popularity
		if (tmp!= null){
			result=true;
			tmp.setPopularity(MyUtil.calPop(tmp.getPopularity(),tmp.getLastaccess(),timestamp));
			tmp.setLastaccess(timestamp);
		}
		//not found

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
	 *
	 * @param timestamp
	 */
	private void checkUpdate(long timestamp)
	{
		if((timestamp-lastUpdate)>MyConf.UPDATE_PERIOD){
			//1.
			M=2*newPutNum;
			newPutNum=0;
			//2. sort the first M nodes
			LinkedList mList=new LinkedList();

			LRUCache.Node node= memory.getHead();
			for (int i=0;i<M;i++){
				node=node.after;
				if (node== memory.getTail()){
					return;
				}


			}

		}
	}

	public void put(int id,int size,long timestamp,float popularity){
		memory.put(id,size,timestamp,popularity);
		newPutNum++;
	}
}
