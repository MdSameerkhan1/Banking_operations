package my;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class transfer {
    public static void main(String[] args) {
        Scanner din = new Scanner(System.in);

        System.out.println("******** Transfer Amount Process *********");
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
                    System.out.println("Enter Transfer Account Number");
                    String tnumber = din.next(); // SBI101
                    String sql2 = "select * from customer_accounts where cus_accountnumber='" + tnumber + "'";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql2);

                    if (rs2.next()) {
                        double vamount = rs2.getDouble(4);
                        System.out.println("Transfer Account is Verified");
                        System.out.println("Enter Transfer Amount");
                        double tamount = din.nextDouble();

                        String sql3 = "select * from customer_accounts where cid='" + rs.getInt(1) + "'";
                        Statement st3 = con.createStatement();
                        ResultSet rs3 = st3.executeQuery(sql3);

                        
                        if (rs3.next()) {
                            double gamount = rs3.getDouble(8);

                            if (tamount > gamount) {
                                System.out.println("Insufficient Balance Amount to Transfer");
                            } else {
                                double person1 = gamount - tamount;
                                double person2 = vamount + tamount;

                                String sql4 = "update customer_accounts set cus_balance='" + person1 + "' where cid='" + rs.getInt(1) + "'";
                                PreparedStatement ps4 = con.prepareStatement(sql4);
                                int i = ps4.executeUpdate();

                                String sql5 = "update customer_accounts set cus_balance='" + person2 + "' where cus_accountnumber='" + tnumber + "'";
                                PreparedStatement ps5 = con.prepareStatement(sql5);
                                int j = ps5.executeUpdate();

                                if (i > 0 && j > 0) {
                                    System.out.println("Transfer Account Successfully Sent");
                                } else {
                                    System.out.println("Transaction failed");
                                }
                            }
                        }
                    } else {
                        System.out.println("Transfer Account is not Verified");
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

