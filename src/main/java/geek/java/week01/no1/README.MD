源码：
```java
package geek.java.week01.no1;

/**
 * @author: liaozibo
 * @date: 2021/3/20
 */
public class Hello {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int c = a + b;
        if (c < 10) {
            c++;
        }
        for (int i = 0; i < 5; i++) {
            c--;
        }
    }
}

```
编译命令：
```
javac -g geek\java\week01\no1\Hello.java
```

反编译命令：
```
javac -c -v geek\java\week01\no1\Hello.java
```

字节码分析：
```
E:\repo\geekUniversityJava\src\main\java>javap -c -v geek\java\week01\no1\Hello.class
Classfile /E:/repo/geekUniversityJava/src/main/java/geek/java/week01/no1/Hello.class
  Last modified 2021-3-20; size 564 bytes
  MD5 checksum 1aa713a63bdc8c3b2c50cef5dd362156
  Compiled from "Hello.java"
public class geek.java.week01.no1.Hello
  minor version: 0
  major version: 52                       // 主版本号 52, 即 JDK 1.8
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:                            // 常量池
   #1 = Methodref          #3.#23         // java/lang/Object."<init>":()V
   #2 = Class              #24            // geek/java/week01/no1/Hello
   #3 = Class              #25            // java/lang/Object
   #4 = Utf8               <init>
   #5 = Utf8               ()V
   #6 = Utf8               Code
   #7 = Utf8               LineNumberTable
   #8 = Utf8               LocalVariableTable
   #9 = Utf8               this
  #10 = Utf8               Lgeek/java/week01/no1/Hello;
  #11 = Utf8               main
  #12 = Utf8               ([Ljava/lang/String;)V
  #13 = Utf8               i
  #14 = Utf8               I
  #15 = Utf8               args
  #16 = Utf8               [Ljava/lang/String;
  #17 = Utf8               a
  #18 = Utf8               b
  #19 = Utf8               c
  #20 = Utf8               StackMapTable
  #21 = Utf8               SourceFile
  #22 = Utf8               Hello.java
  #23 = NameAndType        #4:#5          // "<init>":()V
  #24 = Utf8               geek/java/week01/no1/Hello
  #25 = Utf8               java/lang/Object
{
  public geek.java.week01.no1.Hello();        // Hello 类的构造方法
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1          // 栈大小=1, 局部遍历表大小=1, 参数数量=1
         0: aload_0                           // 将局部变量表槽位0的引用类型加载到栈
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V // 调用 Object 的构造方法
         4: return                            // 返回
      LineNumberTable:
        line 7: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   Lgeek/java/week01/no1/Hello;

  public static void main(java.lang.String[]);  // main 方法
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=5, args_size=1    // 栈大小=2, 局部变量表大小=5, 参数数量=1
         0: iconst_1                    // 将整型常量1推入栈
         1: istore_1                    // 弹出栈元素存储到局部变量表的槽位1
         2: iconst_2                    // 将整型常量2推入栈
         3: istore_2                    // 弹出栈元素存储到局部变量表的槽位2
         4: iload_1                     // 将局部变量表槽位1的整型加载到栈
         5: iload_2                     // 将局部变量表槽位2的整型加载到栈
         6: iadd                        // （弹出两个栈顶元素）执行整型相加（将结果1+2=3推入栈）
         7: istore_3                    // 弹出栈顶元素存储到局部变量表槽位3
         8: iload_3                     // 将局部变量表槽位3的整型加载到栈
         9: bipush        10            // 将整型常量10推入栈
        11: if_icmpge     17            // （弹出两个栈顶元素）执行比较操作，如果10>=3，从偏移量17开始执行
        14: iinc          3, 1          // 对局部变量表槽位3执行整型自增，自增大小为1
        17: iconst_0                    // 将整型常量0推入栈
        18: istore        4             // 弹出栈元素存储到局部变量表的槽位4
        20: iload         4             // 将局部变量表槽位4的整型加载到栈
        22: iconst_5                    // 将整型常量5推入栈
        23: if_icmpge     35            // （弹出两个栈顶元素）执行比较操作，如果5>=0，从偏移量35开始执行
        26: iinc          3, -1         // 对局部变量表槽位3执行整型自增，自增大小为-1
        29: iinc          4, 1          // 对局部变量表槽位4执行整型自增，自增大小为1
        32: goto          20            // 回退到偏移量20
        35: return                      // 返回
      LineNumberTable:
        line 9: 0
        line 10: 2
        line 11: 4
        line 12: 8
        line 13: 14
        line 15: 17
        line 16: 26
        line 15: 29
        line 18: 35
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
           20      15     4     i   I
            0      36     0  args   [Ljava/lang/String;
            2      34     1     a   I
            4      32     2     b   I
            8      28     3     c   I
      StackMapTable: number_of_entries = 3
        frame_type = 254 /* append */
          offset_delta = 17
          locals = [ int, int, int ]
        frame_type = 252 /* append */
          offset_delta = 2
          locals = [ int ]
        frame_type = 250 /* chop */
          offset_delta = 14
}
SourceFile: "Hello.java"

```