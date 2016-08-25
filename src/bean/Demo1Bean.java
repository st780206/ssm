/**
 * Person类为一个POJO，用来存储人员信息。
 */
package bean;

public class Demo1Bean {

	private Integer id;

	private String name;

	private String age;

	private String description;

	public Demo1Bean(Integer id, String name, String age, String description) {

		this.id = id;

		this.name = name;

		this.age = age;

		this.description = description;

	}

	public Integer getId() {

		return id;

	}

	public void setId(Integer id) {

		this.id = id;

	}

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getAge() {

		return age;

	}

	public void setAge(String age) {

		this.age = age;

	}

	public String getDescription() {

		return description;

	}

	public void setDescription(String description) {

		this.description = description;

	}

}