/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class MyUtil
{
	public static float candiCalPop(float old_pop, long old_time,
			long timestamp)
	{
		float timeGap =
				(timestamp - old_time) == 0 ? 0.1F : (timestamp - old_time);
		return (float) (1
				+ Math.exp(-MyConf.CANDI_EXPON_LAMBDA * timeGap) * old_pop);
	}

	public static float calPop(float old_pop, long old_time, long timestamp)
	{
		float timeGap =
				(timestamp - old_time) == 0 ? 0.1F : (timestamp - old_time);
		return (float) (1
				+ Math.exp(-MyConf.BSL_EXPON_LAMBDA * timeGap) * old_pop);
	}

	public static float calNowPop(float old_pop, long old_time, long timestamp)
	{
		float timeGap =
				(timestamp - old_time) == 0 ? 0.1F : (timestamp - old_time);
		return (float) (Math.exp(-MyConf.BSL_EXPON_LAMBDA * timeGap) * old_pop);
	}

	public static long calBslSize(int Rt0, int Rt1)
	{
		int nextRequests = Rt1 + (Rt1 - Rt0) / 2;
		float entryCapacity = (float) (nextRequests / MyConf.REPORT_PERIOD);
		entryCapacity = (float) ((float) 3.0F * Math.pow(entryCapacity, 0.65));
		return (long) (entryCapacity * MyConf.FILE_SIZE);
	}
}
