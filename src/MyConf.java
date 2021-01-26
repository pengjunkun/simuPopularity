/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class MyConf
{
	/**
	 * following are the logic switch
	 */
	public static final boolean IS_UPDATE_SIZE = true;
	//after change, please correct the parameter index in Client.java
//	public static final String DATA_URL = "/Users/pengjunkun/Downloads/pythonProject/1.07.csv";
	public static final String DATA_URL = "/Users/pengjunkun/work/project/packetProject/data/5-4.csv";
	public static final float CAL_SIZE_FACTOR = 1.25F;
	//temp work: the BSL size, unit:KB
		public static long BSL_SIZE = 1 * 120 * 1024;
//	public static long BSL_SIZE = 1 * 100 * 1024;

	public static String TAG = "traceBandwidth";
	public static final long UPDATE_PERIOD = 120;
	public static final long REPORT_PERIOD = 20 * 60;
	//	p*=MyConf.POP_DECREASE;
	public static final float POP_DECREASE = 0.95F;
	//	p*=MyConf.POP_INCREASE;
	public static final float POP_INCREASE = 1.05F;

	//the average life time is 1/lambda
	public static float CANDI_EXPON_LAMBDA = 1F;
	public static float BSL_EXPON_LAMBDA = .01F;
	public static int LAMBDAINDICATOR = 0;
	//	public static long BSL_SIZE = 33790;
	//unit:KiB
	public static final int FILE_SIZE = 1 * 1024;
	public static int TRANS_ONE_FILE_NORM = 0;
	public static int TRANS_ONE_FILE_OUR = 0;

	static
	{
		TRANS_ONE_FILE_NORM = (int) ((6 + 2 * FILE_SIZE) * 1.0 / 16
				+ FILE_SIZE);
		TRANS_ONE_FILE_OUR = (int) (
				(6 + Math.log(FILE_SIZE) / Math.log(2) + FILE_SIZE) / 16);

	}

	public static final int CANDIDATES_NUM = 1000000;
	public static final float MISS_INCREASE = 0.005F;

	public static int Vh = 20;
	public static int Vl = 10;

	//unit:ms
	public static final float CLIENT2CDS = 3F;
	public static final float SEND64 = 0.00032F;
	public static final float SEND1024 = 0.00089F;
	public static final float RESTORE_LATENCY = 0.0024F;
	//the number of packets that restored from one ID packet
	//todo: change this to test 16
	//	public static final int PKT_NUM_ONCE = 10;

	//pending: below not used yet
	//unit:Mb/s
	public static final int CDS_BANDWIDTH = 100;
	//unit:KB
	public static final int PACKET_ID_SIZE = 40;
	public static final int PACKET_FULL_SIZE = 1400;

}
