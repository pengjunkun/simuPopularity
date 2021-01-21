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

		//for now, each generated file has about 1520000 different content in 3560000 requests
		//		Client client = new Client( "/Users/pengjunkun/Downloads/pythonProject/result.csv");
		//				"/Users/pengjunkun/work/project/packetProject/data/5-4.csv");
		//		client.run();

		for (int s = 0; s < MyLog.testSizes.length; s++)
		{
			Float sizeP = MyLog.testSizes[s];
			System.out.println("fixe size: " + sizeP);

			MyConf.BSL_SIZE = (long) (sizeP * 100 * MyConf.FILE_SIZE);
			for (int l = 0; l < MyLog.lambdas.length; l++)
			{
				String ll = MyLog.lambdas[l];
				MyConf.TAG = sizeP + "-" + ll;
				MyLog.initTagWriter();
				System.out.println("fixe lambda: " + ll);
				Client client = new Client(
						"/Users/pengjunkun/Downloads/pythonProject/" + ll
								+ ".csv");
				client.run();
				MyLog.closeTagWriter();
			}

		}
		MyLog.closeWrite();
	}
}