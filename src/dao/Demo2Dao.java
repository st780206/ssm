package dao;

import java.util.List;

import bean.Demo2Bean;

public interface Demo2Dao {

	List<Demo2Bean> getAllDemo2();
	
	// @Param("user_cd")
	Demo2Bean getDemo2ByName(String username);
	
	Demo2Bean getDemo2ByDemo2Bean(Demo2Bean demo2Bean);
	
	int addDemo2(Demo2Bean demo2Bean);
	
	int updateDemo2(Demo2Bean demo2Bean);
	
	int deleteDemo2(Demo2Bean demo2Bean);

}
