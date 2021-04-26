package template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IQuery <K,T> {
	
	List<T> findAll();
	default T findBId(K key) {
		T model = null;
		return model;
	}
	
	default List<T> findBy(Map<String,Object> mapQuery){
		List<T> list = new ArrayList<>();
		return list;
	}

}
