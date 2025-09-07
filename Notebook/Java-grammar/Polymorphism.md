# Polymorphism

### 接口多态实现“运算符重载”

Java中不存在运算符重载,我们用接口引入相关方法实现这一点.  
例如类的对象之间的**大小比较**:
```java
public interface OurComparable {
    public int compareTo(Object o);
}
```
后续类在声明时使用**`implements OurComparable`** , 然后重写该方法,如:
```java
@Override
public int compareTo(Object o) {
    Dog uddaDog = (Dog) o;//使用时将通用的Object转化为Dog
    return this.size - uddaDog.size;
}
```
还可以构建静态 *OurComparable* 方法,不止是Dog对象能使用:
```java
public static OurComparable method(OurComparable items) {
}//传入 OurComparable的子类,因为运行时多态,每个子类都能使用这个方法
```

### Comparator

这是一个官方的 *OurComparable* 接口,与上文不同,Object处使用了**泛型**  

接口内容如下:
```java
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```
引入时,可使用语句 **`import java.util.Comparator;`**  
设计时,在类中可引入 **静态类和静态方法**,例如:  
```java
import java.util.Comparator;

public class Dog implements Comparable<Dog> {
    ...
    public int compareTo(Dog uddaDog) {
        return this.size - uddaDog.size;
    }

    private static class NameComparator implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.name.compareTo(b.name);
        }
    }

    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }
}
```
使用时可以这样调用:
```java
Comparator<Dog> cp=Dog.getNameComparator();
int result = cp.compare(dog1,dog2);
```
和scanner对象差不多()

### 强大的接口()

- All methods must be public.
  - 所有方法必须是 public 的。
- All variables must be public static final.
  - 所有变量必须是 public static final 的。
- Cannot be instantiated  
  - 不能被实例化
- All methods are by default abstract unless specified to be default
  - 所有方法默认都是抽象的，除非特别指定为 default
- Can implement more than one interface per class
  - 一个类可以实现多个接口  

## Interface Iterable
继续介绍迭代器接口,内容如下:
```java
public interface Iterable<T> {
    Iterator<T> iterator();
}

public interface Iterator<T> {
    boolean hasNext();
    T next();
    //还有两个default方法,remove(抛出异常)和forEachRemaining(foreach循环使用)
}
```
使用时,添加私有内部类 `classIterator` , 重写 hasNext next 方法  
在原本类中也重写 `Iterator<T> iterator()` 方法 , 只返回 `new classIterator();` 即可

增强型for循环
```java
for(String s : am){
  sout(am);
}
```
相当于
```java
ArrayMap.KeyIterator ami = am.new KeyIterator();

while (ami.hasNext()) {
    System.out.println(ami.next());
}
```
----


