## Java have its poiters
这里我记录一些*java*里发现的和C不同的地方

### 没有new赋值的对象是指针
看看这段代码（节选自61b书本[2.1](https://joshhug.gitbooks.io/hug61b/content/chap2/chap21.html))
```java
public class PollQuestions {
   public static void main(String[] args) {
      Walrus a = new Walrus(1000, 8.3);
      Walrus b;
      b = a;
      b.weight = 5;
      System.out.println(a);
      System.out.println(b);       
   }
   
   public static class Walrus {
      public int weight;
      public double tuskSize;
      
      public Walrus(int w, double ts) {
         weight = w;
         tuskSize = ts;
      }
   }
}
```
在程序11行,将b赋给a.但从运行结果看,后续对b的修改影响到了a,于是可以认为b是一个指向a的指针
主函数程序可以等价于:
```cpp
int main()
{
    Walrus a(1000,8.3);
    Walrus*b = &a;
    b->weight=5;
    cout<<a<<endl<<b<<endl;
}

