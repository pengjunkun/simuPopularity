/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class MyUtil
{
	static float lambda= 1.0F;
	public static float calPop(float old_pop, long old_time, long timestamp)
	{
		return (float) (1+Math.exp(-lambda*(timestamp-old_time))*old_pop);
	}
}
