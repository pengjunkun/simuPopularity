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
	//	private double totalLatency;
	private long lastReport;
	private int totalRequest;
	private int totalHit;

	public Client(String filePath)
	{
		this.filePath = filePath;
		//		totalLatency = 0;
		cds = new CDS();
		lastReport = 0;
		totalHit = 0;
		totalRequest = 0;
	}

	private void sendRequest(String id, long timestamp)
	{
		totalRequest += 1;
		boolean res = cds.requestContent(id, timestamp);
		if (res)
			totalHit += 1;
		//		if (timestamp % 21187 == 0)
		//			MyLog.jack("latency: " + res);
		//		totalLatency += res;
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
			bufferedReader.close();

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		finalReport();

	}

	private void finalReport()
	{
		MyLog.logger.info("==========final report========");
		if (totalRequest != 0)
		{
			MyLog.logger.info(MyConf.TAG + " our " + (1.0F * totalHit
					/ totalRequest));
			MyLog.writeResult(
					MyConf.TAG + " our " + (1.0F * totalHit / totalRequest));
		}
		MyLog.logger.info("==========final report========");
		cds.fianlLruReport();
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
