计算机网络之间的通信需要依赖三个数据：

- 机器IP  -> IPV4 四个字节 192.128.0.1 (每个数字0-255)
- 端口号 -> 应用程序的标识    两个字节, 0-65535
- 传输协议  

网络的TCP/IP 协议分为四层:
- 应用层  -> http ftp telnet 等
- 传输层  -> tcp udp 等
- 网络层  -> IP
- 物理-数据链路层 -> 最底层, 转换为二进制使用物理设备传输信息  


机器IP + 端口号 是我们的方向, 传输协议是我们的方式, 下面讲讲方式:

### UDP 传输

User Datagram Protocol 具有传输快, 丢包多[^1]的特点, 常用于传输视频画面  

[^1]: UDP 启动时不会检查双方机器是否连通, 故速度快  

在Java中, 我们使用
`java.net.InetAddress` 类表示对 IP地址 的抽象, 这个类不提供构造函数, 我们使用静态方法getByName(String host[^2])   获取其子类的实例变量(子类 Inet4Address, Inet6Address)

[^2]: host参数表示IP地址, 可以是IPV4, 也可以是IPV6

`java.net.DatagramSocket` 包进行功能抽象, 这个类抽象了接收和发送数据包的socket , 构造函数一般填入端口号, 空参表示使用随机可用端口号.  调用send方法即可发送对应的数据包  ,  接收数据包时需要往构造函数填入端口号(开放端口),  PS.   发送的端口号可以和发送到的端口号不同 

`java.net.DatagramPackage` 表示前面那个类的数据包, 我们可以往其中写入数据, 再由DatagramSocket类发送到对应主机, 这个类的构造函数完整的参数有:
```java
public DatagramPacket(byte[] buf, int offset, int length, //buf[offset, ?] 数据长度length 
                      InetAddress address, int port){}   // IP port(端口) 发到这个IP的哪个端口
```
#### 发送数据
一般分为几个步骤: 
- 初始化 IP socket 信息, 将数据变成字节数组  
- 构建数据包类 -> DatagramPackage
- 调用 socket 的 `send()` 函数发送数据包  
- 释放资源  

#### 接收数据
一般分为几个步骤: 
- 初始化 socket 填入 发送方指向的 port
- 初始化 数据包, 内部字节数组可以为空(不是null)
- 调用 socket 的 `receive()` 方法 接收数据包(未接收到该线程将被阻塞[^3])
- 使用 DatagramPackage 的各种 get 方法 获取数据包内的相关数据
- 释放资源

#### UDP 组播 广播
使用MulticastSocket 类实现 组播, 接收端调用此类的 joinGroup() 方法 加入一个组  
发送端只需要指定组名即可, 组名有规范, 自行了解.    

广播对比组播 , 将组名改成 "255.255.255.255" 即可, 局域网所有开放端口的机器都可以接收到数据  

### TCP 传输

Transmission Control Protocol  具有传输慢, 但是数据完整性好的特点, TCP在传输前会进行连接检查(三次握手) , 传输断开后会进行 四次挥手.   
TCP通过 IO 流 传输数据, 发送端视为输出, 接收端视为输入  

Java 对于TCP 提供了 Socket 类, 该类封装了整个三次握手细节, 我们可以不需要深究, 仅仅初始化就行了:
```java
Socket socket = new Socket("127.0.0.1", 8888); // IP地址及端口号
```
初始化这个类的时候, Java 会直接与目标地址进行连接, 连接不上会抛出异常.  

Socket 类包含一个输出流成员, 我们可以直接使用这个成员的 `write()` 方法, 向目标写入数据并发送:
```java
socket.getOutputStream().write("Hello World".getBytes());
// 这种写法不好, get的Output流未被显式释放, 正确的如下:
// OutputStream os = socket.getOutputStream();
// os.write();
// os.close()
```
socket 类实例 同样需要关闭.

Java 为接收端 提供了另一个类  ServerSocket (由端口构造), 调用其 `acccept()` 方法 启动一个监听(等待逻辑和 receive 一致) , 与发送端建立连接成功后, 方法会返回一个 Socket 对象, 从这个对象的输入流成员中, 读取对应的数据.

Input 流不需要显式释放, 返回的 socket 和 创建的 ServerSocket 对象都需要释放.  
释放之前, 底层会进行 四次挥手, 细节也被封装的很好  

## 什么是Socket?

socket 的中文翻译是"套接字" , 不知所云, 所以笔记中尽量不写它的中文, 全程使用 `socket` 代指 . 通过上面的例子, socket 实际上是一个连接应用层和传输层的中间件 (主要做的是传输的工作)

[^3]: 默认无限等待, 可以通过`setSoTimeOut()` 方法设置最长等待时间
