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
	private double totalLatency;
	private long lastReport;

	public Client(String filePath)
	{
		this.filePath = filePath;
		totalLatency = 0;
		cds = new CDS();
		lastReport=0;
	}

	private void sendRequest(String id, long timestamp)
	{
		float res = cds.requestContent(id, timestamp);
		//		if (timestamp % 21187 == 0)
		//			MyLog.jack("latency: " + res);
		totalLatency += res;
		actionPeriod(timestamp);
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
				String id = oneLine.split(",")[1];
				String timestamp = oneLine.split(",")[0];
				sendRequest(id, Long.parseLong(timestamp));
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
		MyLog.logger.info("total time: " + totalLatency);
		MyLog.logger.info("==========final report========");
	}

	public void run()
	{
		readFileAndSent(filePath);
	}

	private void actionPeriod(long timestamp)
	{

		if ((timestamp - lastReport) >= MyConf.REPORT_PERIOD)
		{
			cds.report();

//			cds.updateBSL_LRU_size();

			lastReport = timestamp;
		}
	}

}
