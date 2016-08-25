/**
 * PersonService只包含一个方法getPersonInfo，返回3条含有数据的Person实例。
 */
package service;

import java.util.ArrayList;
import java.util.List;

import bean.Demo1Bean;

public class Demo1ServiceImpl implements Demo1Service {
	
	public List<Demo1Bean> getPersonInfo() {

		List<Demo1Bean> personData = new ArrayList<Demo1Bean>();

		// 填充数据

		Demo1Bean p = null;

		p = new Demo1Bean(1, "小明", "25", "中共党员");

		personData.add(p);

		p = new Demo1Bean(1, "小华", "21", "共青团员");

		personData.add(p);

		p = new Demo1Bean(1, "小丽", "13", "少先队员");

		personData.add(p);

		return personData;

	}

}