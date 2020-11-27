package com.cxr.other.java8;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
public class Person implements  Comparable<Person>{
	private String name;  // 姓名
	private int salary; // 薪资
	private Integer age; // 年龄
	private String sex; //性别
	private String area;  // 地区

	// 构造方法
	public Person(String name, int salary, int age,String sex,String area) {
		this.name = name;
		this.salary = salary;
		this.age = age;
		this.sex = sex;
		this.area = area;
	}

	public Person(String name, int salary, String sex, String area) {
		this.name = name;
		this.salary = salary;
		this.sex = sex;
		this.area = area;
	}

	@Override
	public int compareTo(Person o) {
		return 0;
	}
	// 省略了get和set，请自行添加

}
