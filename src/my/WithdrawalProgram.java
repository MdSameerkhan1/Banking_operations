package my;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class WithdrawalProgram {
    public static void main(String[] args) {
        Scanner din = new Scanner(System.in);

        System.out.println("******** Withdrawal Amount Process *********");
        System.out.println("Enter Customer Account number");
        String cnumber = din.next();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "1234");
            String sql = "select * from customer_accounts where cus_accountnumber='" + cnumber + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                System.out.println("Enter Transaction Password");
                int cpass = din.nextInt();
                String sql1 = "select * from customer_accounts where cpass='" + cpass + "' and cid='" + rs.getInt(1) + "'";
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery(sql1);

                if (rs1.next()) {
                    System.out.println("Enter Withdrawal Amount");
                    double wamount = din.nextDouble();

                    String sql2 = "select * from customer_accounts where cid='" + rs.getInt(1) + "'";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql2);

                    if (rs2.next()) {
                        double gamount = rs2.getDouble(4);

                        if (wamount > gamount) {
                            System.out.println("Insufficient Balance Amount");
                        } else {
                            double uamount = gamount - wamount;
                            String sql3 = "update customer_accounts set cus_balance='" + uamount + "' where cid='" + rs.getInt(1) + "'";
                            PreparedStatement ps = con.prepareStatement(sql3);
                            int i = ps.executeUpdate();

                            if (i > 0) {
                                System.out.println("Please Wait Your Transaction is Processing ..");
                                System.out.println("Please Take Your Cash");
                            } else {
                                System.out.println("Withdrawal Failed.. Due to Some issues");
                            }
                        }
                    }
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

