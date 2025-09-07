java.lang.reflect包内提供了一系列类型, 它们和java.lang.Class一起提供了反射的使用:
- Class \<?> 
- Constructor\<?>
- Field
- Method
反射可以在运行中动态获取文件中类的成员信息,储存到程序中的变量; 还可以对类的相关成员进行操作. 关于操作, 变量成员可以重新赋值, 方法成员可以被调用.
以下引用[菜鸟教程](https://www.runoob.com/java/java-reflection.html)的使用总结:
1. **获取 `Class` 对象**：首先获取目标类的 `Class` 对象。
2. **获取成员信息**：通过 `Class` 对象，可以获取类的字段、方法、构造函数等信息。
3. **操作成员**：通过反射 API 可以读取和修改字段的值、调用方法以及创建对象  
#### 获取class对象
```java
Class<?> clazz = MyClass.class;
//假设类的定义是这样
public class MyClass {
	private int a;
	private String b;
	public void printInt(int i){
		sout(i);
	}
	public MyClass(int a,String b){
		this.a = a;
		this.b = b;
	}
}
```
以后对反射的应用基于对clazz方法的调用  

#### **获取成员信息**

对类的操作可以基于 当场新建 , 也可以基于 已经初始化的成员, 下面两个代码块分别对应:
```java
Class<?> clazz = MyClass.class;
Constructor<?> constr = clazz.getConstruct();
MyClass clasl = constr.newInstance(0721,"ciallo");
```

```java
MyClass clasl = new MyClass(0721,"ciallo");
Class<?> clazz = clasl.getclass();
```
我们通过 Field 类进行类的数据成员信息访问和修改:
```java
Field aField = clazz.getDeclaredField("a");
// getDeclaredField(String fieldName) 参数表示要访问的成员变量名 
```
Field类提供了几个方法: `get(Object o), set(Object o,Object setValue), getModifiers(), setAccessible()` 


#### **操作成员**

除去上文的访问构造函数,成员变量, 反射还能调用类的方法:
```java
Method print = clazz.getMethod("printInt");
//函数参数 填入方法名字符串
```
Method类提供了几个方法: `invoke(), getReturnType(), getParameterTypes()` 可以调用方法或者获取方法的相关信息.

父类和类实现的接口也作为类的信息可以被访问, 文档里会有对应的方法, 在此不表.