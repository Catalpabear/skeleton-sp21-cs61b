# Object
java中的所有类都继承自Object类,继承了以下可重写的方法:
```java
String toString()
boolean equals(Object obj)
Class <?> getClass()
int hashCode()
protected Objectclone()
protected void finalize()
void notify()
void notifyAll()
void wait()
void wait(long timeout)
void wait(long timeout, int nanos)
```

## toString()
该类隐式提供了**输出运算符重载**(cpp) , 在调用sout时 , 实际上按以下步骤执行:
```java
String s = dog.toString();
System.out.println(s);
```
不重写会默认打印对象的十六进制地址,重写该方法相当于对输出进行重载  

P.S 构建输出字符串建议使用 **`StringBuilder`** 类 , 使用其 `append` 方法  
不建议使用 `returnString += keys[i];` 因为实质上是 `returnString = returnString + keys[i];`    
每次都会创建一个新的字符串对象，效率低下.

## equals()  
  
**`==`** 在Java中 , 针对基本类型按值判断 , 针对指针类型(对象)按址判断  
对于两个对象 , **`==`** 只考虑两者指向的内存空间是否一致  
如果两者内存里存放的值一致 , **`==`** 仍会返回 **`false`**  
默认的equals()如下:
```java
public boolean equals(Object obj) {
    return (this == obj);
}
```
重写该方法相当于对 **`==`** 进行重载  
  
P.S 重写原则([来自61B-book](https://joshhug.gitbooks.io/hug61b/content/chap6/chap64.html))  
- equals must be an equivalence relation
  - reflexive: x.equals(x) is true
  - symmetric: x.equals(y) if and only if y.equals(x)
  - transitive: x.equals(y) and y.equals(z) implies x.equals(z)
- It must take an Object argument, in order to override the original .equals() method

- It must be consistent if x.equals(y), then as long as x and y remain unchanged: x must continue to equal y

- It is never true for null x.equals(null) must be false

## 神秘语法()
编写ArraySet时,可选的使用一个静态方法,使构建更简单:
```java
public static <K> ArraySet<K> of(K... stuf){

}
```
**`K... stuf`** 表示可变参数 , 在函数体中可通过 `for(k i:stuf)` 循环获取其中的元素  
  
关于< K > , 查看[静态泛型方法](./Generics%20and%20Autoboxing.md)