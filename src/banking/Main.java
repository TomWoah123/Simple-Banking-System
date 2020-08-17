package banking;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
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

    public static ResultSet insert(int id, String number, String pin, int balance) {
        String url = "jdbc:sqlite:C:\\sqlite\\testdb.db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        String sql = "INSERT INTO CARD VALUES(?,?,?,?)";
        ResultSet rs = null;
        try (Connection c = dataSource.getConnection()) {
            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setInt(1, id);
                s.setString(2, number);
                s.setString( 3, pin);
                s.setInt(4, balance);
                s.executeUpdate();
                rs = s.executeQuery("SELECT * FROM CARD");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void displayMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public static void createAccount(TreeMap<String, String> map) {
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        String number = createNumber();
        while (!luhnAlgorithm(number)) {
            number = createNumber();
        }
        System.out.println(number);
        System.out.println("Your card PIN");
        String pin = createPIN();
        while (map.containsKey(pin)) {
            pin = createPIN();
        }
        System.out.println(pin);
        map.put(pin, number);
        int id = new Random().nextInt(10000);
        int balance = new Random().nextInt(100000000);
        insert(id, number, pin, balance);
    }

    private static String createNumber() {
        String number = "400000";
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            number += r.nextInt(10);
        }
        return number;
    }

    private static String createPIN() {
        Random r = new Random();
        int p = r.nextInt(10000);
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

    public static boolean luhnAlgorithm(String number) {
        int[] digits = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            digits[i] = Integer.parseInt("" + number.charAt(i));
        }
        for (int k = 0; k < digits.length - 1; k++) {
            if (k % 2 == 0) {
                digits[k] *= 2;
            }
        }
        for (int j = 0; j < digits.length - 1; j++) {
            if (digits[j] > 9) {
                digits[j] -= 9;
            }
        }
        int sum = 0;
        for (int digit : digits) {
            sum += digit;
        }
        return sum % 10 == 0;
    }
}
