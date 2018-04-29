package dataStructure;

class Node{
    int data;
    Node next = null;
    @Override
    public String toString() {
        return "{ "+data+" }";
    }
}
class Implementation {
    Node first;
    public void insertFirst(int data){
        Node newNode = new Node();
        newNode.data = data;
        newNode.next = first;
        first = newNode;
    }
    public void deleteFirst(){
        first = first.next;
    }
    public void insertLast(int data){
        Node newNode = new Node();
        newNode.data= data;
        Node curr = first;
        while (curr.next != null){
            curr = curr.next;
        }
        curr.next = newNode;
    }

    public void printList(){
        Node current = first;
        while (current != null){
            System.out.println(current.toString());
            current = current.next;
        }
    }
}


public class LinkedList {
    public static void main(String[] args) {
        Implementation ll = new Implementation();
        ll.insertFirst(10);
        ll.insertFirst(20);
        ll.insertFirst(30);
        ll.insertFirst(40);

        ll.insertLast(50);
        ll.printList();

        System.out.println("After Deleting first element");
        ll.deleteFirst();
        ll.deleteFirst();
        ll.printList();
    }
}
