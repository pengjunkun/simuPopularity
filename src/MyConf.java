/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class MyConf
{
	public static final long UPDATE_PERIOD = 120;
	public static final long REPORT_PERIOD = 1*60*60;
	//	p*=MyConf.POP_DECREASE;
	public static final float POP_DECREASE = 0.95F;
	//	p*=MyConf.POP_INCREASE;
	public static final float POP_INCREASE = 1.05F;
	public static final double EXPON_LAMBDA = 1.0F;
	//temp work: the BSL size, unit:KB
	public static final long BSL_SIZE =  10 * 1024 * 1024;
	public static final int FILE_SIZE = 1*1024;
	public static final int CANDIDATES_NUM = 1000000;
	public static int Vh = 15;
	public static int Vl = 10;
}
