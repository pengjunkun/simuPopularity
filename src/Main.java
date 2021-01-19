import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class Main
{
	public static void main(String[] args)
	{

		Client client = new Client(
				"/Users/pengjunkun/work/project/packetProject/data/5-4.csv");
		client.run();

		//		for (int i = 0; i < MyLog.lambdas.length; i++)
		//		{
		//			MyConf.EXPON_LAMBDA = Float.parseFloat(MyLog.lambdas[i]);
		//			MyConf.LAMBDAINDICATOR = i;
		//			Client client = new Client( "/Users/pengjunkun/work/project/packetProject/data/5-4.csv");
		//			client.run();
		//			try
		//			{
		//				MyLog.writers.get(i).close();
		//			} catch (IOException e)
		//			{
		//				e.printStackTrace();
		//			}
		//		}
		//
		MyLog.closeWrite();
	}
}
