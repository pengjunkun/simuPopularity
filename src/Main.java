import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class Main
{
	public static void main(String[] args)
	{

		Client client=new Client("/Users/pengjunkun/work/project/packetProject/data/iqiyi_processed.csv");
		client.run();

	}
}
