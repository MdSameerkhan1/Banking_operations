package my;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DepositProgram {
    public static void main(String[] args) {
        Scanner din = new Scanner(System.in);

        System.out.println("******** Deposit Amount Process *********");
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
                    double pamount = rs1.getDouble(4);
                    System.out.println("Enter Your Deposit Amount");
                    double damount = din.nextDouble();
                    double tamount = pamount + damount;

                    String sql2 = "update customer_accounts set cus_balance='" + tamount + "' where cid='" + rs.getInt(1) + "'";
                    PreparedStatement ps = con.prepareStatement(sql2);
                    int i = ps.executeUpdate();

                    if (i > 0) {
                        System.out.println("Your Amount Deposited in Your Account Successfully");
                        // Code Here - Display updated amount //
                    } else {
                        System.out.println("Amount is not Deposited");
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
