public class A_program {
    public static void main(String[] args) {
        method1(); // 栈底：main
    }
    static void method1() {
        method2(); // 栈顶：method2
    }
    static void method2() {
        throw new RuntimeException("boom");
    }
}