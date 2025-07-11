package deque;

public class ArrayDeque<T> {
    T[] array;
    int size;
    int first_ins, last_ins;
    int capacity;

    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        capacity = 8;
        first_ins = 0;
        last_ins = 1;
    }

    public void addFirst(T element) {
        if (size == capacity) {
            resize(2 * capacity);
        }
        array[first_ins] = element;
        first_ins=secure_sub(first_ins);
        size++;
    }
    public void addLast(T element){
        if (size == capacity) {
            resize(2 * capacity);
        }
        array[last_ins] = element;
        last_ins=secure_add(last_ins);
        size++;
    }

    public T removeFirst(){
        if(isEmpty()){
            return null;
        }
        int change=secure_add(first_ins);
        T element = array[change];
        array[change] = null;
        first_ins=change;
        if (is_More()){
            resize(capacity/2);
        }
        size--;
        return element;
    }
    public T removeLast(){
        if(isEmpty()){
            return null;
        }
        int change=secure_sub(last_ins);
        T element = array[change];
        array[change] = null;
        last_ins=change;

        if (is_More()){
            resize(capacity/2);
        }
        size--;
        return element;
    }

    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        int mark=first_ins,iter=secure_add(first_ins),j=1;
        while(iter!=mark){
            if(array[iter]==null){
                break;
            }
            temp[j] = array[iter];
            iter=secure_add(iter);
            j++;
        }
        if (array[iter]!=null) {
            temp[j] = array[iter];
        }
        this.capacity = capacity;
        last_ins=first_ins=0;
        int len=size+1;
        while(len>0){
            last_ins=secure_add(last_ins);
            len--;
        }
        array = temp;

    }
    private int secure_add(int index){
        if(index == capacity-1){
            return 0;
        }
        return index+1;
    }
    private int secure_sub(int index){
        if(index == 0){
            return capacity-1;
        }
        return index-1;
    }
    private boolean is_More(){
        return capacity>=16 && ((double)size / capacity < 0.25);
    }
    public int size(){
        return size;
    }
    public T get(int index){
        if (index < 0 || index >= size) {
            return null;
        }
        int realIndex = (first_ins + 1 + index) % capacity;
        return array[realIndex];
    }

    public boolean isEmpty(){
        return size==0;
    }
}
