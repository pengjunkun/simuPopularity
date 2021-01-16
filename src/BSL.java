/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class BSL {
	//the memory stack
	private MyCache memory;
	private long BSLSize;
	//the M head records
	private int M;
	//new inserting number
	private int newPutNum=0;

	public BSL(long bslSize)
	{
		BSLSize=bslSize;
		memory=new MyCache(BSLSize);
		M=100;
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
	 */
	public void updateBSL()
	{
			//1.
			M=2*newPutNum;
//			System.out.println("new M= "+M);
			newPutNum=0;
			//2~3. sort the first M nodes
			memory.sortM_ByPop(M);
	}

	/**
	 * put a new file into cache.
	 * this may occur a replacement, right or not?
	 * @param id
	 * @param size
	 * @param timestamp
	 * @param popularity
	 * @return return the popularity of file that is replaced(0 for not occur replacement)
	 */
	public float put(int id,int size,long timestamp,float popularity){
		newPutNum++;
		return memory.put(id,size,timestamp,popularity);
	}

}
