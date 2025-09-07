## Generics
我觉得这个和template(cpp中的**模板**)比较像  
目前要注意的点有:
- class A<G>   G是标识符,在不确定的数据类型中使用时用G代替即可
- 能自动推断的都是"引用类型"(就是类),比如string,自己定义的类A
- "<>"里要用基本类型时使用它们的类,int,double,char,boolean,long,short,byte,float
- 对应Integer,Double,Character,Boolean,Long,Short,Byte,Float
- 实例化时使用**new< >**,  < >里面没有东西
- 创建数组时不能使用泛型,如:
  - `Glorp[] items = new Glorp[8];` 不合法
  - `Glorp[] items = (Glorp []) new Object[8];` 才合法 (Object可以看成**void***,空指针)