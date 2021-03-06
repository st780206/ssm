package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bean.Demo1Bean;
import bean.Demo2Bean;
import service.Demo1ServiceImpl;
import service.Demo2Service;

// 注解是spring mvc的控制器类
@Controller

public class Demo2Controller {
	
	@Resource
	private Demo2Service demo2Service;

	// 域名访问跳转到登录页
	//@RequestMapping(value="/")
	// 注解访问路径。多个路径使用{}和,
	@RequestMapping(value="/demo2.mvc")
	public String index() {
	//public ModelAndView index() {

		// 根据mvc配置文件的定义添加前后缀。
		return "demo2";
		//ModelAndView mv = new ModelAndView();
		//mv.setViewName("login");
		//return mv;
	}

	// 登录跳转到首页
	// SpringMVC的各种参数绑定方式
	// 1. 基本数据类型(以int为例，其他类似)：
	// public void test(int count) {
	// }
	// 表单中input的name值和Controller的参数变量名保持一致，就能完成数据绑定，
	// 如果不一致可以使用@RequestParam注解。
	// 需要注意的是，如果Controller方法参数中定义的是基本数据类型，但是从页面提交过来的数据为null或者”"的话，会出现数据转换的异常。
	// 也就是必须保证表单传递过来的数据不能为null或”"，所以，在开发过程中，对可能为空的数据，最好将参数数据类型定义成包装类型，具体参见下面的例子。
	// 2. 包装类型(以Integer为例，其他类似)：
	// public void test(Integer count) {
	// }
	// 和基本数据类型基本一样，不同之处在于，表单传递过来的数据可以为null或”"，
	// 以上面代码为例，如果表单中num为”"或者表单中无num这个input，那么，Controller方法参数中的num值则为null。
	@RequestMapping(value="/demo2login.mvc", method=RequestMethod.POST)
	public ModelAndView login(String username, String password) {
		
		Demo2Bean demo2Bean = demo2Service.getDemo2ByName(username);

		ModelAndView mv = new ModelAndView();

		if (demo2Bean == null || !demo2Bean.getPasswd().equals(password)) {
			mv.setViewName("demo2");//mv.setViewName("redirect:demo2");
			mv.addObject("errorMsg", "error");
		} else {
			mv.setViewName("demo2main");
			//mv.setViewName("redirect:demo2main.jsp");
			mv.addObject("username", username);
		}

		return mv;

	}

	// 获取人员信息
	@RequestMapping(value="/demo2getPersonData.mvc")
	@ResponseBody // 添加该注解后，返回值将由转换器进行转换，转换器为Jackson，所以会转换成json格式
	public Map<String, Object> getPersonData() {

		Map<String, Object> personMap = new HashMap<String, Object>();

		List<Demo2Bean> demo2Beans = demo2Service.getAllDemo2();

		personMap.put("rows", demo2Beans);

		return personMap;

	}

}