import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class MyLog
{
	public static Logger logger;
	public static BufferedWriter tagWriter;

	private static BufferedWriter writerSize;
	private static BufferedWriter writerResult;
	private static BufferedWriter writerMyHit;
	private static BufferedWriter writerLRUHit;

	public static ArrayList<BufferedWriter> writers = new ArrayList<>();

	//	public static String[] lambdas = { "1.01", "1.03", "1.07", "1.15", "1.31" };
	public static String[] lambdas = { "1.15" };
	//	public static Float[] testSizes = { 0.25F, 0.5F, 1F, 2F, 4F, };
	public static Float[] testSizes = { 0.25F };

	static
	{
		try
		{
			writerSize = new BufferedWriter(new FileWriter("./size.csv"));
			writerResult = new BufferedWriter(new FileWriter("./result.csv"));
			writerMyHit = new BufferedWriter(new FileWriter("./myHit.csv"));
			writerLRUHit = new BufferedWriter(new FileWriter("./LRUHit.csv"));

			for (int i = 0; i < lambdas.length; i++)
			{
				writers.add(new BufferedWriter(
						new FileWriter("./Lam" + lambdas[i] + ".csv")));
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		logger = Logger.getLogger("clientLog");
		FileHandler fh = null;
		try
		{
			fh = new FileHandler("client.log");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		fh.setLevel(Level.INFO);
		fh.setFormatter(new SimpleFormatter());
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(Level.ALL);
		ch.setFormatter(new SimpleFormatter());
		logger.addHandler(fh);
		logger.addHandler(ch);
	}

	public static void initTagWriter()
	{
		try
		{
			tagWriter = new BufferedWriter(new FileWriter(MyConf.TAG + ".csv"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static void tagWriter(String cont)
	{
		try
		{
			tagWriter.write(cont);
			tagWriter.newLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void closeTagWriter()
	{
		try
		{
			tagWriter.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void jack(String s)
	{
		//		System.out.println(s);
	}

	public static void jackJoint(String s)
	{
		System.out.print(s);
	}

	public static void writeByIndicator(String cont)
	{
		try
		{
			writers.get(MyConf.LAMBDAINDICATOR).write(cont + " ");
			//			writers.get(MyConf.LAMBDAINDICATOR).newLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeMyHit(String cont)
	{
		try
		{
			writerMyHit.write(cont);
			writerMyHit.newLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeLRUHit(String cont)
	{
		try
		{
			writerLRUHit.write(cont);
			writerLRUHit.newLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeSize(String cont)
	{
		try
		{
			writerSize.write(cont);
			writerSize.newLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeResult(String cont)
	{
		try
		{
			writerResult.write(cont);
			writerResult.newLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void closeWrite()
	{
		try
		{
			writerSize.close();
			writerMyHit.close();
			writerLRUHit.close();
			writerResult.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
