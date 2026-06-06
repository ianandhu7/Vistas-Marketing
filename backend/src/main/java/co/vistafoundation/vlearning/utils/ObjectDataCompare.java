/**
 * 
 */
package co.vistafoundation.vlearning.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

/**
 * @author vk
 *
 */
@Component
public class ObjectDataCompare {
	
	public static List<String> getDifference(Object s1, Object s2) throws IllegalAccessException {
	    List<String> values = new ArrayList<>();
	    for (Field field : s1.getClass().getDeclaredFields()) {
	        field.setAccessible(true);
	        Object value1 = field.get(s1);
	        Object value2 = field.get(s2);

	        if (value1 != null && value2 != null) {
	            if (!Objects.equals(value1, value2)) {
	                values.add(String.valueOf(field.getName()));
	            }
	        }
	    }
	    return values;
	}

}
