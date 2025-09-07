##  the exception keyword
关于异常,目前学习了三个关键字 `try catch throw`  
格式如下:
```java
try{
    //需要测试的代码块
}
catch(Exception e){
    //发生异常后需要进行的操作
    //例如 sout  or  fix
}
catch{
    //catch语句块可以有多个,区分不同异常
    //如 IOException FileNotFoundException
}
```
throw 一般在if和catch的语句里面 ，`throw new (异常对象)();`  

Java 7+ 更精确的重新抛出:
```
catch (Exception e) { throw e; } // 编译器知道 e 的实际类型，无需声明 Exception
```

## checked and unchecked

当一个 异常 抛出后 , 在后续代码块中可以修复的时候 , 该异常称为 checked ,例如 IOException  
  
无法修复则称为 unchecked , 例如 RuntimeException IndexOutOfBoundsException

处理 checked 有两种办法:
- catch
- specify (向栈底方向抛出,但是最后总要catch)
  
### 什么是“栈底方向”
下面是 [某个程序](A_program.java) 栈的示意图:
| 地址高 → 低 | 元素    | 位置 |
| ------- | ----- | -- |
| 0x1003  | method2 | 栈顶top |
| 0x1002  | method1 |    |
| 0x1001  | main | 栈底bottom |  

在方法后加上 `throws IOException` 向下抛出异常
```java
public static void gulgate() throws IOException {
    ... 
    throw new IOException("hi"); 
    ...
}
public static void main(String[] args) throws IOException {
    gulgate();  // 编译通过，异常最终由 JVM 打印并退出
    //main函数已经是栈底 , 接下来抛给 JVM , JVM自动catch
}
```
