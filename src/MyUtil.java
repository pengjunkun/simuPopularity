/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class MyUtil
{
	public static float calPop(float old_pop, long old_time, long timestamp)
	{
		return (float) (1+Math.exp(-MyConf.EXPON_LAMBDA*(timestamp-old_time))*old_pop);
	}
}
