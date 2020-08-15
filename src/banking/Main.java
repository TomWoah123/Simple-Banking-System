package banking;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean enter = true;
        TreeMap<String, String> users = new TreeMap<>();
        do {
            displayMenu();
            String str = scan.nextLine();
            if (str.equals("1")) {
                createAccount(users);
            } else if (str.equals("2")) {
                loginAccount(users, scan);
            } else if (str.equals("0")) {
                System.out.println("Bye!");
                enter = false;
            }
            System.out.println();
        } while(enter);
    }

    public static void displayMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public static void createAccount(TreeMap<String, String> map) {
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        String number = "400000";
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            number += r.nextInt(10);
        }
        System.out.println(number);
        System.out.println("Your card PIN");
        int p = r.nextInt(10000);
        while (map.containsKey(createPIN(p))) {
            p = r.nextInt(10000);
        }
        String pin = createPIN(p);
        System.out.println(pin);
        map.put(pin, number);
    }

    private static String createPIN(int p) {
        String pin = "";
        if (p < 10) {
            pin += "000" + p;
        } else if (p < 100) {
            pin += "00" + p;
        } else if (p < 1000) {
            pin += "0" + p;
        } else {
            pin += p;
        }
        return pin;
    }

    public static void loginAccount(TreeMap<String, String> map, Scanner scanner) {
        System.out.println("Enter your card number");
        String number = scanner.nextLine();
        System.out.println("Enter your PIN number");
        String pin = scanner.nextLine();
        if (map.containsKey(pin) && map.get(pin).equals(number)) {
            System.out.println("You have successfully logged in!");
            loginActivity(scanner);
        } else {
            System.out.println("Wrong card number or PIN!");
        }
    }

    public static void loginActivity(Scanner scan) {
        boolean enter = true;
        do {
            System.out.println("1. Balance");
            System.out.println("2. Log out");
            System.out.println("0. Exit");
            String num = scan.nextLine();
            if (num.equals("1")) {
                System.out.println("Balance: 0");
            } else if (num.equals("2")) {
                System.out.println("Logged out successfully");
                enter = false;
            } else if (num.equals("0")) {
                System.out.println("Bye!");
                System.exit(0);
            } else {
                System.out.println("Invalid input");
            }
        } while (enter);
    }
}
