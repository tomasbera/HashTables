import java.util.HashMap;
import java.util.Random;

public class HashTableInt {
    private final int size;
    private final int[] hashList;
    private double collisions;

    public HashTableInt(int size){
        hashList = new int[isPrime((int) (size*1.25))];
        this.size = isPrime(hashList.length);
        this.collisions = 0;
    }

    public void put(int k){
        int h = hash1(k, size);

        if(hashList[h] == 0) hashList[h] = k;

        else{
            int h2 = hash2(k, size);
            int p = probe(h,h2,1, size);

            for(;;){
                p = (p+h2)%hashList.length;
                if(hashList[p]==0){
                    hashList[p] = k;
                    break;
                }
                collisions++;
            }
        }
    }

    public static int hash1(int k, int m){
        if (k%m<0) return (k%m)+m;
        return k % m;
    }

    public static int hash2(int k, int m){
        if (k%(m-1) < 0) return (k%(m-1))+m;
        return (k % (m - 1)) - 1;
    }

    public static int probe(int h1, int h2, int i, int m){
        if (((h1+i*h2)%m) < 0) return ((h1 + i*h2) % m) + m;
        return (h1 + i*h2) % m;
    }

    public int isPrime(int size){
        while (!GetNextPrime(size))size++;
        return size;
    }

    public boolean GetNextPrime(int size) {
        int i = 2;
        boolean isPrime = true;
        while (i <= Math.sqrt(size)) {
            if (size % i == 0) {
                isPrime = false;
                break;
            }
            ++i;
        }
        return isPrime;
    }

    public static void main(String[] args) {
        double length = 10000000;
        double start;
        double end;
        double totTime;
        HashTableInt ht = new HashTableInt((int) length);
        HashMap<Integer, Integer> hm = new HashMap();
        int[] anotherList = new int[(int) ht.size];

        for (int i = 0; i < ht.size; i++) {
            Random random = new Random();
            anotherList[i] = random.nextInt();
        }


        start = System.nanoTime();
        for (int i = 0; i < ht.hashList.length; i++) ht.put(anotherList[i]);
        end = System.nanoTime();
        totTime = end-start;
        System.out.println("Min algoritme bruker: "+totTime/1000000.0 +"ms");


        start = System.nanoTime();
        for (int i = 0; i < ht.size; i++) hm.put(anotherList[i], anotherList[i]);
        end = System.nanoTime();
        totTime = end-start;
        System.out.println("Java sin algoritme bruker: " + totTime/1000000 +"ms");
        System.out.println("Antall kolisjoner: "+ht.collisions);
        System.out.println("Laste-faktoren til programmet er : " + length/ht.hashList.length);

    }
}
