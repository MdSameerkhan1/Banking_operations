package my;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Balance {
    public static void main(String[] args) {
        Scanner din = new Scanner(System.in);

        System.out.println("******** Balance Enquiry Process *********");
        System.out.println("Enter Customer Account number");
        String cnumber = din.next();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "1234");
            String sql = "select * from customer_accounts where cus_accountnumber='" + cnumber + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                System.out.println("Enter Transaction Password");
                int ntxnpass = din.nextInt();
                String sql1 = "select * from customer_accounts where cpass='" + ntxnpass + "' and cid='" + rs.getInt(1) + "'";
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery(sql1);

                if (rs1.next()) {
                    System.out.println("Your Balance Amount is: " + rs1.getDouble(8));
                } else {
                    System.out.println("Transaction Password is Incorrect");
                }
            } else {
                System.out.println("Sorry !!! Customer Account number is Incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
