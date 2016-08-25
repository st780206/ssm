package service;

import java.util.List;

import javax.annotation.Resource;

import bean.Demo2Bean;
import dao.Demo2Dao;

public class Demo2ServiceImpl implements Demo2Service {
	
	@Resource
	private Demo2Dao demo2Dao;

	@Override
	public List<Demo2Bean> getAllDemo2() {
		// TODO Auto-generated method stub
		return demo2Dao.getAllDemo2();
	}

	@Override
	public Demo2Bean getDemo2ByName(String username) {
		// TODO Auto-generated method stub
		return demo2Dao.getDemo2ByName(username);
	}

	@Override
	public Demo2Bean getDemo2ByDemo2Bean(Demo2Bean demo2Bean) {
		// TODO Auto-generated method stub
		return demo2Dao.getDemo2ByDemo2Bean(demo2Bean);
	}

	@Override
	public int addDemo2(Demo2Bean demo2Bean) {
		// TODO Auto-generated method stub
		return demo2Dao.addDemo2(demo2Bean);
	}

	@Override
	public int updateDemo2(Demo2Bean demo2Bean) {
		// TODO Auto-generated method stub
		return demo2Dao.updateDemo2(demo2Bean);
	}

	@Override
	public int deleteDemo2(Demo2Bean demo2Bean) {
		// TODO Auto-generated method stub
		return demo2Dao.deleteDemo2(demo2Bean);
	}


}