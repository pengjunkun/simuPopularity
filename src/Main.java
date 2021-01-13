import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by JackPeng(pengjunkun@gmail.com) on 2021/1/12.
 */
public class Main
{
	public static void main(String[] args){
		LRUCache ln=new LRUCache(21);
		ln.put(1,0,1,1);
		ln.put(2,0,2,1);
		ln.put(3,0,3,1);
		ln.put(4,0,4,1);
		ln.get(2);


	}
}
