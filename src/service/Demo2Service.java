package service;

import java.util.List;

import bean.Demo2Bean;

public interface Demo2Service {

	List<Demo2Bean> getAllDemo2();
	
	Demo2Bean getDemo2ByName(String username);
	
	Demo2Bean getDemo2ByDemo2Bean(Demo2Bean demo2Bean);
	
	int addDemo2(Demo2Bean demo2Bean);
	
	int updateDemo2(Demo2Bean demo2Bean);
	
	int deleteDemo2(Demo2Bean demo2Bean);

}
