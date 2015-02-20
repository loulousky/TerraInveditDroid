package MarcusD.TerraInvedit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class MapSorter<K, V>
{
	public Map<K, V> map;
	
	public MapSorter(Map<K, V> map)
	{
		this.map = map;
	}
	
	public Map<K, V> sort()
	{
		List<Map.Entry<K, V>> ret = new ArrayList<Map.Entry<K,V>>(this.map.entrySet());
		
		Collections.sort(ret,
			new Comparator<Map.Entry<K, V>>()
			{
				@Override
				public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2)
				{
					return MapSorter.this.compare(e1.getKey(), e1.getValue(), e2.getKey(), e2.getValue());
				}
			}
		);
		
		Map<K, V> retmap = new LinkedHashMap<K, V>();
		for(Iterator<Map.Entry<K, V>> it = ret.iterator(); it.hasNext();)
		{
			Map.Entry<K, V> en = it.next();
			retmap.put(en.getKey(), en.getValue());
		}
		
		return retmap;
	}
	
	public abstract int compare(K key1, V val1, K key2, V val2);
}
