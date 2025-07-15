package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> ,Iterable<T>{
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
    @Override
    public void addFirst(T element) {
        if (size == capacity) {
            resize(2 * capacity);
        }
        array[first_ins] = element;
        first_ins=secure_sub(first_ins);
        size++;
    }
    @Override
    public void addLast(T element){
        if (size == capacity) {
            resize(2 * capacity);
        }
        array[last_ins] = element;
        last_ins=secure_add(last_ins);
        size++;
    }
    @Override
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
    @Override
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
    @Override
    public int size(){
        return size;
    }
    @Override
    public T get(int index){
        if (index < 0 || index >= size) {
            return null;
        }
        int realIndex = (first_ins + 1 + index) % capacity;
        return array[realIndex];
    }
    @Override
    public void printDeque(){
        int iter=secure_add(first_ins);
        while(iter != last_ins){
            System.out.println(array[iter]+" ");
            iter=secure_add(iter);
        }
        System.out.println();
    }

    @Override
    public Iterator<T> iterator(){
        return new AdequeIterator();
    }

    private class AdequeIterator implements Iterator<T> {
        private int index;
        public AdequeIterator() {
            index = secure_add(first_ins);
        }
        @Override
        public T next(){
            int temp=index;
            index=secure_add(index);
            return array[temp];
        }
        @Override
        public boolean hasNext(){
            return index != last_ins;
        }
    }
    @Override
    public boolean equals(Object obj){
        if(obj==null|| !(obj instanceof ArrayDeque || ((Deque<?>) obj).size() != this.size()) ){
            return false;
        }
        if(obj==this){
            return true;
        }
        for(int i=0;i<size;i++){
            if(!(this.get(i).equals( ( (Deque<?>)obj ).get(i) ))){
                return false;
            }
        }
        return true;
    }
}
