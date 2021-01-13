import java.io.*;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class Client
{
	private CDS cds;
	private String filePath;
	private int count;
	private long totalLatency;

	public Client(String filePath)
	{
		this.filePath = filePath;
		count = 0;
		totalLatency=0;
		cds=new CDS();
	}

	private void sendRequest(int id,long timestamp)
	{
		totalLatency+=cds.requestContent(id,timestamp);
	}

	private void readFileAndSent(String fPath)
	{
		BufferedReader bufferedReader;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(fPath));
			String oneLine = bufferedReader.readLine();
			while (!oneLine.isEmpty())
			{
				String id = oneLine.split(",")[4];
				String timestamp = oneLine.split(",")[1];
				sendRequest(Integer.parseInt(id),Long.parseLong(timestamp));
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

	public void run()
	{
		readFileAndSent(filePath);
	}
}
