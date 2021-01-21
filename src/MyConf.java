/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/13.
 */
public class MyConf
{
	public static String TAG = "";
	public static final long UPDATE_PERIOD = 120;
	public static final long REPORT_PERIOD = 20 * 60;
	//	p*=MyConf.POP_DECREASE;
	public static final float POP_DECREASE = 0.95F;
	//	p*=MyConf.POP_INCREASE;
	public static final float POP_INCREASE = 1.05F;
	public static final int V_SIZE = 20;

	//the average life time is 1/lambda
	public static float CANDI_EXPON_LAMBDA = 1F;
	public static float BSL_EXPON_LAMBDA = .01F;
	public static int LAMBDAINDICATOR = 0;
	//temp work: the BSL size, unit:KB
	//the range of content from [0,99999]
	//so, below 4 stands for 4% of whole files
	public static long BSL_SIZE = 1 * 100 * 1024;
	//todo: random of filesize
	//may replace several cached files
	//test several times to show
	public static final int FILE_SIZE = 1 * 1024;
	public static final int TRANS_ONE_FILE_NORM = 128 + FILE_SIZE;
	public static final int TRANS_ONE_FILE_OUR = 128;
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
