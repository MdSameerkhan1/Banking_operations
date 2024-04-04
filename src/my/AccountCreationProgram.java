package my;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AccountCreationProgram {
    public static void main(String[] args) {
        Scanner din = new Scanner(System.in);

        System.out.println("******** Account Creation Process *********");
        System.out.println("Enter Customer Name");
        String cname = din.next();
        System.out.println("Enter Customer UserName");
        String cuser = din.next();
        System.out.println("Enter Customer Password");
        String cpass = din.next();
        System.out.println("Enter Customer Email");
        String cemail = din.next();
        System.out.println("Enter Customer Mobile");
        String cmobile = din.next();
        System.out.println("Enter Customer account no");
        String cacc= din.next();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "1234");
            String sql = "insert into customer_accounts(cname, cuser, cpass, cemail, cmobile,cus_accountnumber) values (?, ?, ?, ?, ?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cname);
            ps.setString(2, cuser);
            ps.setString(3, cpass);
            ps.setString(4, cemail);
            ps.setString(5, cmobile);
            ps.setString(6, cacc);
            int i = ps.executeUpdate();
            if (i > 0) {
                System.out.println("Account Successfully Created");
            } else {
                System.out.println("Account Creation Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

