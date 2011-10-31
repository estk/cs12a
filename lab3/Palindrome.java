import java.util.*;
class Palindrome {
    public static void main(String[] args) {
        // interaction
        String phrase, cleanPhrase;
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your test phrase:");
        phrase = scan.nextLine();
        cleanPhrase = phrase.replaceAll("\\s+", "");
        // response
        if ( isPalindrome(cleanPhrase) )
            System.out.print("That's a palindrome!  ");
        else
            System.out.print("That isn't a palindrome!  ");
        System.out.println("Bye.");
    }   

    static Boolean isPalindrome(String str) {
        str = str.toUpperCase();
        String rts = reverse(str);
        return str.equalsIgnoreCase(rts);
    }

    static String reverse(String str) {
        String tmp = "";
        for ( int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            tmp = ch + tmp;
        }
        return tmp;
    }


}
