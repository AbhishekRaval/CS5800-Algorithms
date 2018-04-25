package EuclideanGCD;

public class HW13_PB06_Extended_Euclid_GCD {

    public static int gcd(int p, int q) {
        if (q == 0) {
            return p;
        }
        return gcd(q, p % q);
    }

    public static void main(String[] args){
        System.out.println(gcd(4, 16) );
        System.out.println(gcd(25, 125) );
    }
}
