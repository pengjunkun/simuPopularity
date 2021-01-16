import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class Client
{
	private CDS cds;
	private String filePath;
	private int count;
	private double totalLatency;
	private long lastReport;

	public Client(String filePath)
	{
		this.filePath = filePath;
		count = 0;
		totalLatency = 0;
		cds = new CDS();
	}

	private void sendRequest(int id, long timestamp)
	{
		float res = cds.requestContent(id, timestamp);
//		if (timestamp % 21187 == 0)
//			System.out.println("latency: " + res);
		totalLatency += res;
		checkReport(timestamp);
	}

	private void readFileAndSent(String fPath)
	{
		BufferedReader bufferedReader;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(fPath));
			String oneLine = bufferedReader.readLine();
			//check the first line
			if (oneLine.startsWith("user"))
			{
				oneLine = bufferedReader.readLine();
			}
			while (oneLine != null)
			{
				String id = oneLine.split(",")[4];
				String timestamp = oneLine.split(",")[1];
				sendRequest(Integer.parseInt(id), Long.parseLong(timestamp));
				count++;
				oneLine = bufferedReader.readLine();
			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	private void finalReport()
	{
		MyLog.logger.info("==========final report========");
		MyLog.logger.info("sent: " + count);
		MyLog.logger.info("total time: " + totalLatency);
		MyLog.logger.info("==========final report========");
	}

	private void report()
	{
		System.out.println("==============report============");
		System.out.println("sent: " + count);
		System.out.println("cache capacity(entry number): " + MyConf.BSL_SIZE/MyConf.FILE_SIZE);
		if (count != 0)
			System.out .println("hited: "+cds.getHit_num()+" ;hit ratio: " + (cds.getHit_num() * 1.0 / count));
		System.out.println("total latency: " + totalLatency + "ms");
		System.out.println(
				"throughput: " + (count * MyConf.FILE_SIZE / 3600) + "KB/s");
		System.out.println("==============report============");
		count = 0;
		totalLatency = 0;
		cds.setHit_num(0);
	}

	public void run()
	{
		readFileAndSent(filePath);
	}

	private void checkReport(long timestamp)
	{
		if ((timestamp - lastReport) > MyConf.REPORT_PERIOD)
		{
			report();
			lastReport = timestamp;
		}
	}

}
