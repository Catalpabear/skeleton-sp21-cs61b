package deque;

public class LinkedListDeque<T> implements Deque<T> {

    private class Node<T> {
        private T data;
        private Node<T>prev;
        private Node<T> next;
        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size;
    private Node<T>sentinel;
    private Node<T>curr_head,curr_tail;

    public LinkedListDeque() {
        sentinel = new Node<T>(null, null, null);
        curr_head = sentinel;
        curr_tail = sentinel;
        size=0;
    }
    private void zero_deque(){
        curr_head = sentinel;
        curr_tail = sentinel;
        sentinel.next = null;
        sentinel.prev = null;
    }

    public LinkedListDeque(T head_data) {
        sentinel=new Node<>(null, null, null);
        curr_head= new Node<>(head_data, null, sentinel);
        sentinel.prev=curr_head;
        curr_tail=sentinel;
        size=1;
    }

    public int size() {
        return size;
    }
    @Override
    public void addFirst(T data) {
        curr_head.prev=new Node<>(data, null, curr_head);
        curr_head=curr_head.prev;
        size++;
    }
    @Override
    public void addLast(T data) {
        curr_tail.next=new Node<>(data, curr_tail, null);
        curr_tail=curr_tail.next;
        size++;
    }
    @Override
    public T removeFirst(){
        if(curr_head==sentinel){
            if(curr_head.next==null){
                return null;
            }else{
                T data=curr_head.next.data;
                curr_head=curr_head.next.next;
                size--;
                if(isEmpty()){
                    zero_deque();
                }
                return data;
            }
        }else{
            if(curr_head==null){
                return null;
            }else {
                T data=curr_head.data;
                curr_head=curr_head.next;
                size--;
                return data;
            }
        }
    }
    @Override
    public T removeLast(){
        if(curr_tail==sentinel){
            if(curr_tail.prev==null){
                return null;
            }else{
                T data=curr_tail.prev.data;
                curr_tail=curr_tail.prev.prev;
                size--;
                if(isEmpty()){
                    zero_deque();
                }
                return data;
            }
        }else{
            if(curr_tail==null){
                return null;
            }else{
                T data=curr_tail.data;
                curr_tail=curr_tail.prev;
                size--;
                return data;
            }
        }
    }


    @Override
    public void printDeque(){
        Node<T> move=curr_head;
        while(move!=null){
            if(move==sentinel){
                move=move.next;
                continue;
            }
            System.out.print(move.data+" ");
            move=move.next;
        }
        System.out.println();
    }
    @Override
    public T get(int x){
        Node<T> move=curr_head;
        while(x>0){
            move=move.next;
            x--;
        }
        if(move==sentinel){
            return sentinel.next.data;
        }
        return move.data;
    }
    public T getRecursive(int index){
       return Recursive_helper(index,curr_head);
    }
    private T Recursive_helper(int index,Node<T> move){
       if(index==0) {
           return move.data;
       }else{
           return Recursive_helper(index-1,move.next);
       }
    }

}
