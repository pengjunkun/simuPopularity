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

	private static BufferedWriter writerSize;
	private static BufferedWriter writerV;
	private static BufferedWriter writerMyHit;
	private static BufferedWriter writerLRUHit;

	public static ArrayList<BufferedWriter> writers = new ArrayList<>();

	public static String[] lambdas = { "0", "5", "10", "15", "20", "25", "30",
			"35", "40", "45" };

	static
	{
		try
		{
			writerSize = new BufferedWriter(new FileWriter("./size.csv"));
			writerV = new BufferedWriter(new FileWriter("./v.csv"));
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

	public static void jack(String s)
	{
				System.out.println(s);
	}

	public static void jackJoint(String s)
	{
		System.out.print(s);
	}

	public static void writeByIndicator(String cont)
	{
		try
		{
			writers.get(MyConf.LAMBDAINDICATOR).write(cont+" ");
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

	public static void writeV(String cont)
	{
		try
		{
			writerV.write(cont);
			writerV.newLine();
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
			writerV.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
