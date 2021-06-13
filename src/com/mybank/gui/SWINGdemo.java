package com.mybank.gui;

import com.mybank.data.DataSource;
import com.mybank.domain.Account;
import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Alexander 'Taurus' Babich
 */
public class SWINGdemo {
    
    private final JEditorPane log;
    private final JButton show;
    private final JButton report;
    private final JComboBox clients;
    
    public SWINGdemo() {
        log = new JEditorPane("text/html", "");
        log.setPreferredSize(new Dimension(350, 400));
        show = new JButton("Show");
        report = new JButton("Report");
        clients = new JComboBox();
        for (int i=0; i<Bank.getNumberOfCustomers();i++)
        {
            clients.addItem(Bank.getCustomer(i).getLastName()+", "+Bank.getCustomer(i).getFirstName());
        }
        
    }
    
    private void launchFrame() {
        JFrame frame = new JFrame("MyBank clients");
        frame.setLayout(new BorderLayout());
        JPanel cpane = new JPanel();
        cpane.setLayout(new GridLayout(1, 2));
        
        cpane.add(clients);
        cpane.add(show);
        cpane.add(report);
        frame.add(cpane, BorderLayout.NORTH);
        frame.add(log, BorderLayout.CENTER);
        
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer current = Bank.getCustomer(clients.getSelectedIndex());
                String accType = current.getAccount(0)instanceof CheckingAccount?"Checking":"Savings";                
                String custInfo="<br>&nbsp;<b><span style=\"font-size:2em;\">"+current.getLastName()+", "+
                        current.getFirstName()+"</span><br><hr>"+
                        "&nbsp;<b>Acc Type: </b>"+accType+
                        "<br>&nbsp;<b>Balance: <span style=\"color:red;\">$"+current.getAccount(0).getBalance()+"</span></b>";
                log.setText(custInfo);                
            }
        });
        
        report.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               StringBuilder custInfo = new StringBuilder();
                for(int i=0; i<Bank.getNumberOfCustomers();i++ ){
                    Customer current = Bank.getCustomer(i);            
                    custInfo.append("<br>&nbsp;<b><span style=\"font-size:2em;\">")
                            .append(current.getLastName())
                            .append(", ")
                            .append(current.getFirstName())
                            .append("</span><br><hr>");                                                  
                          
                    for (int j = 0; j < current.getNumberOfAccounts(); j++) {
                        custInfo.append("&nbsp;<b>Account Type:  </b>");
                        Account account = current.getAccount(j);
                        
                        if (account instanceof SavingsAccount){ 
                            custInfo.append("'Savings'");
                        }
                        else if (account instanceof CheckingAccount){
                            custInfo.append("'Checking'");
                        }
                        custInfo.append("<br>&nbsp;<b>Balance: <span style=\"color:red;\">$")
                                .append(account.getBalance())
                                .append("</span></b></br>").append("<br></br>");
                    }
                           
                   
                }
                log.setText(custInfo.toString());
           }
        });
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setResizable(true);
        frame.setVisible(true);        
    }
    
    public static void main(String[] args) {
        DataSource dataSource = new DataSource("test.dat");
        try {
            dataSource.loadData();
        } catch (IOException ex) {
            Logger.getLogger(SWINGdemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        Bank.addCustomer("John", "Doe");
//        Bank.addCustomer("Fox", "Mulder");
//        Bank.addCustomer("Dana", "Scully");
//        Bank.getCustomer(0).addAccount(new CheckingAccount(2000));
//        Bank.getCustomer(1).addAccount(new SavingsAccount(1000, 3));
//        Bank.getCustomer(2).addAccount(new CheckingAccount(1000, 500));
        
        SWINGdemo demo = new SWINGdemo();        
        demo.launchFrame();
    }
    
}