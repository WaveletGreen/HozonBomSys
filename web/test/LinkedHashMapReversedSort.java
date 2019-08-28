import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/23 15:41
 * @Modified By:
 */
public class LinkedHashMapReversedSort {
    public static void main(String[] args) {
        LinkedHashMap<String,String > linkedhashmap = new LinkedHashMap<String,String>(10, 0.75f, true);
        linkedhashmap.put("1","a");
        linkedhashmap.put("2","b");
        linkedhashmap.put("3","c");
        linkedhashmap.put("4","d");
        ListIterator<Map.Entry<String,String>> i=new ArrayList<Map.Entry<String,String>>(linkedhashmap.entrySet()).listIterator(linkedhashmap.size());
        while(i.hasPrevious()) {
            Map.Entry<String, String> entry=i.previous();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
    }
}
