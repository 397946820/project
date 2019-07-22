package com.jeeplus.modules.scm.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.druid.sql.ast.expr.SQLAggregateExpr.Option;
import com.google.common.collect.Maps;

public class LambdaTest {
	static List<Person> persons = new ArrayList<>();
	static Map<Integer, String> filter_map = Maps.newConcurrentMap();
	static List<Employee> employeeList = new ArrayList<>();
	static {
		Person p1 = new Person(2, "zs", "wuhan");
		Person p2 = new Person(2, "ls", "wuhan");
		Person p3 = new Person(2, "ww", "nanjing");
		Person p4 = new Person(2, "mz", "beijing");
		Person p5 = new Person(2, "hhy", "xinyu");
		Person p6 = new Person(2, "jj", "xinyu");
		persons.add(p6);
		persons.add(p5);
		persons.add(p4);
		persons.add(p3);
		persons.add(p2);
		persons.add(p1);
		filter_map.put(1, "linode.com");
		filter_map.put(2, "heroku.com");
		filter_map.put(3, "digitalocean.com");
		filter_map.put(4, "aws.amazon.com");
		employeeList.add(new Employee("Matt", 5000, "New York"));
		employeeList.add(new Employee("Steve", 6000, "London"));
		employeeList.add(new Employee("Carrie", 20000, "New York"));
		employeeList.add(new Employee("Peter", 7000, "New York"));
		employeeList.add(new Employee("Pat", 8000, "London"));
		employeeList.add(new Employee("Tammy", 29000, "Shanghai"));
	}

	public static void main(String[] args) {
		// // 分组
		// Map<String, List<Person>> collect =
		// persons.stream().collect(Collectors.groupingBy(c -> c.getAddress()));
		// System.out.println(collect);
		// // java 8 List 转Map
		// Map<String, Person> mapList = persons.stream()
		// .collect(Collectors.toMap((key -> key.getName()), (value -> value)));
		// mapList.forEach((key, value) -> System.out.println(key + ":" + value));
		// Map<String, Person> mappedMovies =
		// persons.stream().collect(Collectors.toMap(Person::getName, value -> value));
		// mapList.forEach((key, value) -> System.out.println(key + ":" + value));
		// // FilterMap操作s
		// String result = null;
		// for (Map.Entry<Integer, String> entry : filter_map.entrySet()) {
		// if ("heroku.com".equals(entry.getValue())) {
		// result = entry.getValue();
		// }
		// }
		// System.out.println("Before Java 8 :" + result);
		// // JAVA 8
		// result = filter_map.entrySet().stream().filter(map ->
		// "heroku.com".equals(map.getValue()))
		// .map(map -> map.getValue()).collect(Collectors.joining());
		// System.out.println("JAVA 8 :" + result);
		// Map<Integer, String> collect1 = filter_map.entrySet().stream().filter(c ->
		// "heroku.com".equals(c.getValue()))
		// .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		// System.out.println(collect1);
		// Optional操作可以防止NullPointException
		// anyMatch
		boolean isMatch = employeeList.stream().anyMatch(employee -> employee.getOffice().equals("London"));
		System.out.println("是否有在London办公的员工");
		System.out.println(isMatch);
		// 判断所有salary大于4000
		boolean allMatch = employeeList.stream().allMatch(employee -> employee.getSalary() > 4000);
		System.out.println("所有员工工资是否都大于4000");
		System.out.println(allMatch);
		// 找出工资最高
		Optional<Employee> hightestSalary = employeeList.stream()
				.max((e1, e2) -> Integer.compare(e1.getSalary(), e2.getSalary()));
		System.out.println("工资最高的员工是：");
		System.out.println(hightestSalary.get().getName());
		// 返回姓名列表
		List<String> names = employeeList.stream().map(employee -> employee.getName()).collect(Collectors.toList());
		System.out.println(names);
		// List转换成map
		Map<String, Employee> employeeMap = employeeList.stream()
				.collect(Collectors.toMap(key -> key.getName(), value -> value));
		employeeMap.forEach((key, value) -> System.out.println(key + "=" + value));
		// 统计办公室是New York的个数
		long officeCount = employeeList.stream().filter(e -> e.getOffice().equals("New York")).count();
		System.out.println("统计New York办公数为：" + officeCount);
		//List转换为Set
		Set<String> officeSet = employeeList.stream().map(e->e.getName()).distinct().collect(Collectors.toSet());
		System.out.println(officeSet);
		//查找办公室地点是New York的员工
		Optional<Employee> allMatchedEmployees = employeeList.stream().filter(e->e.getOffice().equals("New York")).findAny();
		System.out.println("办公室地点是New York的员工有："+allMatchedEmployees);
		//按照工资的降序来列出员工信息
		List<Employee> sortEmployeeList = employeeList.stream().sorted((e1,e2)->Integer.compare(e2.getSalary(), e1.getSalary())).collect(Collectors.toList());
		System.out.println("按照员工工资的降序来列出员工信息：");
		sortEmployeeList.forEach(e->System.out.println(e.getName()));
		//按照名字的升序列出员工信息
		List<Employee> sortEmployeeByName = employeeList.stream().sorted((e1,e2)->e1.getName().compareTo(e2.getName())).collect(Collectors.toList());
		System.out.println("按照名字的升序列出员工信息");
		sortEmployeeByName.forEach(e->System.out.println(e.getName()));
		//获取工资最高的前2条员工信息
		List<Employee> top2EmployeeList = employeeList.stream().sorted((e1,e2)->Integer.compare(e2.getSalary(), e1.getSalary())).limit(2).collect(Collectors.toList());
		System.out.println("工资最高的前2条员工信息：");
		top2EmployeeList.forEach(e->System.out.println(e.getName()));
		
	}

}
