/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class CacheFile
{
	private String id;

	private long lastaccess;
	private int size=0;
	private float popularity=0;

	public CacheFile(String id, int size,long lastaccess,float popularity )
	{
		this.id = id;
		this.lastaccess = lastaccess;
		this.size = size;
		this.popularity=popularity;
	}

	public int getSize()
	{
		return size;
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
