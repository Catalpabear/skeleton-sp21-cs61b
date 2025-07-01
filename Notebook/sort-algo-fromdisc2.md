## 一个排序算法，有点意思

来自disc2

### 整体思路

- 辅助函数，用于获取数组某一项后面的最小项的下标
- **sort函数**，对数组进行排序.首先对数组遍历，每次遍历执行以下操作:

  - 获取当前项后面的最小项的下标（调用辅助函数）
  - 将当前项与获取的最小项交换位置

### 代码

```java
public static int help_func(int[] inputArray, int k) {
        int x = inputArray[k];
        int answer = k;
        int index = k + 1;
        while (index < inputArray.length) {
            if (inputArray[index] < x) {
                x = inputArray[index];
                answer = index;
            }
            index = index + 1;
        }
        return answer;
    }

    public static void sort(int[] inputArray) {
        int index = 0;
        while (index < inputArray.length) {
            int targetIndex = help_func(inputArray, index);
            int temp = inputArray[targetIndex];
            inputArray[targetIndex] = inputArray[index];
            inputArray[index] = temp;
            index = index + 1;
        }
    }
```
