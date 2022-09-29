import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import static javax.swing.JOptionPane.*;

public class HashTableString {
    public int size;
    public DoubleLinkedList[] lists;
    public double collisions;

    public HashTableString(int size){
        this.size = isPrime(size);
        this.lists = new DoubleLinkedList[this.size];
        this.collisions = 0;
    }

    private void put(String word, String value){
        int hashedKey = wordToInt(word, size);

        if (lists[hashedKey] == null){
            lists[hashedKey] = new DoubleLinkedList();
        } else {
            collisions++;
        }

        lists[hashedKey].addFirst(hashedKey, value);
    }

    public boolean getValueToKey(String word){
        int hashedKey = wordToInt(word, size);
        boolean value = false;
        DoubleLinkedList listVal = lists[hashedKey];

        if (listVal != null){
            Node arrayVal = listVal.head;
            while(arrayVal != null){
                if (arrayVal.getKey() == hashedKey){
                    value = true;
                    break;
                }
                arrayVal = arrayVal.next;
            }
            return value;
        }
        return false;
    }

    public int wordToInt(String word, int m){
        int key = 0;

        for (char n : word.toCharArray()) {
            key = (key+n)*11;
        }
        if (key % m < 0) return (key % m) + m;
        return (key % m);
    }

    public int isPrime(int n){
        while (!GetNextPrime(n))n++;
        return n;
    }

    public boolean GetNextPrime(int n) {
        int i = 2;
        boolean isPrime = true;
        while (i <= Math.sqrt(n)) {
            if (n % i == 0) {
                isPrime = false;
                break;
            }
            ++i;
        }
        return isPrime;
    }

    static class Node{

        private final int key;
        private final String value;
        private final Node next;
        private Node prev;

        public Node(int key, String value,Node next, Node prev) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public static class DoubleLinkedList {
        private Node head = null;
        private Node tail = null;

        private void addFirst(int key, String value) {
            head = new Node(key, value, head, null);

            if (tail == null) tail = head;
            else {
                assert head.next != null;
                head.next.prev = head;
            }
        }
    }

    private void printHastTable(){
        Node temp;
        double numOfStud = 0;
        double numbOfEmpty = 0;

        for (int i = 0; i < size; i++) {
            if (lists[i] != null){
                temp = lists[i].head;
                System.out.println(temp.getKey() +" "+ temp.getValue());
                numOfStud++;

                while (temp.next != null){
                    temp = temp.next;
                    numOfStud++;
                    System.out.println(temp.getKey() +" "+ temp.getValue());
                }
            }
        }

        for (DoubleLinkedList list : lists) {
            if (list != null) ++numbOfEmpty;
        }

        System.out.println("\n" + size);
        System.out.println(numbOfEmpty);
        System.out.println("Number of collisions: " + collisions);
        System.out.println("Average number of collision: " + collisions/numOfStud);
        System.out.println("Load Factor: " + numbOfEmpty/lists.length);
    }


    public static void main(String[] args) {
        String searchName = showInputDialog("Who do you want to search for?");
        HashTableString ht = new HashTableString(115);

        try {
            File myObj = new File("C:\\Users\\Administrator\\Arbkrav\\Øving5\\src\\Names");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                ht.put(data, data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        ht.printHastTable();
        System.out.println("");
        System.out.println("The search for the student name was: " + ht.getValueToKey(searchName));
    }
}
