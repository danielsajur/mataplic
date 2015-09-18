package unifacs.grafos.dao;

import java.io.InputStream;
import java.util.PropertyResourceBundle;

public class GrafoDao {
	
	private static PropertyResourceBundle resourceBundle;
	
	static {
		try {
			if(resourceBundle == null) {
				
				InputStream stream = GrafoDao.class.getResourceAsStream("grafo.properties");
				resourceBundle = new PropertyResourceBundle(stream);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String getValue(String key) {
		return resourceBundle.getString(key);
	}
}
