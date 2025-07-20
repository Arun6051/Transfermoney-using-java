import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class transfermoney extends JFrame implements ActionListener
    {
        Container cn = getContentPane();
        JLabel l1,l2,l3;
        JTextField t1,t2,t3;
        JButton b;

        //Connection
        Connection con;
        PreparedStatement ps;  
        ResultSet rs;
        Statement st;

        public transfermoney()
            {
                super("NET BANKING");

                l1 = new JLabel("Sender Acc.No",Label.RIGHT);
                l2 = new JLabel("Receiver Acc.No",Label.RIGHT);
                l3 = new JLabel("Amount",Label.RIGHT);
                t1 = new JTextField();
                t2 = new JTextField();
                t3 = new JTextField();
                b = new JButton("Transfer");

                l1.setBounds(60,50,100,25);
                l2.setBounds(60,100,100,25);
                l3.setBounds(60,150,100,25);
                t1.setBounds(160,50,140,25);
                t2.setBounds(160,100,140,25);
                t3.setBounds(160,150,140,25);
                b.setBounds(180,250,100,25);

                b.addActionListener(this);

                cn.add(l1);
                cn.add(l2);
                cn.add(l3);
                cn.add(t1);
                cn.add(t2);
                cn.add(t3);
                cn.add(b);

                try
                    {
                        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                        con = DriverManager.getConnection("jdbc:odbc:MY");
                        st = con.createStatement();
                    }
                catch(Exception e)
                    {
                        System.out.println("****"+e);
                    }
                setLayout(null);
                setLocation(100,100);
                setSize(400,400);
                setVisible(true);   
            }
        public void actionPerformed(ActionEvent ae)
            {
                try
                    {
                        if(ae.getSource() == b)
                            {
                                int i1 = Integer.parseInt(t1.getText());
                                int i2 = Integer.parseInt(t2.getText());
                                int i3 = Integer.parseInt(t3.getText());
                                transferMoney(i1,i2,i3);
                            }
                    }
                catch(Exception e)
                    {

                    }
            }
        public void transferMoney(int si,int ri,int am)
            {
                int u1=0,u2=0;
                try
                    {
                        rs = st.executeQuery("Select * from bankdb where accountno = " +si);
                        if(rs.next())
                            {
                                u1 = rs.getInt("balance");
                            }
                        rs = st.executeQuery("Select * from bankdb where accountno = " +ri);
                        if(rs.next())
                            {
                                u2 = rs.getInt("balance");
                            }
                        if(u1 >= am)
                            {
                                ps = con.prepareStatement("Update bankdb set balance = " + (u1-am) + " where accountno = " +si);
                                ps.executeUpdate();
                                ps = con.prepareStatement("Update bankdb set balance = " + (u2+am) + " where accountno = " +ri);
                                ps.executeUpdate();
                                JOptionPane.showMessageDialog(null,"Amount Transfered","Phone pay",JOptionPane.PLAIN_MESSAGE);
                            }
                        else
                            {
                                JOptionPane.showMessageDialog(null,"Insufficient balance to transfer","Phone pay",JOptionPane.ERROR_MESSAGE);
                            }
                    }
                catch(Exception e)
                    {
                        System.out.println(""+e);
                    }
            }
        public static void main(String s[])
            {
                new transfermoney();
            }
    }