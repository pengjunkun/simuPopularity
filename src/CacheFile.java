/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class CacheFile
{
	private int id;

	private long lastaccess;
	private int size=0;
	private float popularity=0;

	public CacheFile(int id, int size,long lastaccess,float popularity )
	{
		this.id = id;
		this.lastaccess = lastaccess;
		this.size = size;
		this.popularity=popularity;
	}


	public long getLastaccess()
	{
		return lastaccess;
	}

	public void setLastaccess(long lastaccess)
	{
		this.lastaccess = lastaccess;
	}

	public float getPopularity()
	{
		return popularity;
	}

	public void setPopularity(float popularity)
	{
		this.popularity = popularity;
	}
}
