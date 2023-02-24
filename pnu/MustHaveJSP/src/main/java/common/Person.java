package common;

public class Person {
	private String name;		//모든 속성 데이터가 private //private 멤버 변수
	private int age;			//private멤버 변수	
	
public Person() {				//필드를 포함한  constructor추가
	// TODO Auto-generated constructor stub
}

public Person(String name, int age) {			//자동으로 소스코드를 제너레이터 해줌
	super();
	this.name = name;
	this.age = age;
}



public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}

@Override
public String toString() {
	return "Person [name=" + name + ", age=" + age + "]";
}



}
