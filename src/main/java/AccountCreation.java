import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AccountCreation extends JFrame {

    private JTextField textEntry;       //username input
    private JButton submitButton;       //create account button
    private JLabel resp;              //success/failure of account creation


    public AccountCreation(){

        super();
        this.setLayout(new FlowLayout());
        this.textEntry = new JTextField(15);
        this.submitButton = new JButton("Create account");
        this.resp= new JLabel("");
        this.add(this.textEntry);
        this.add(this.submitButton);
        this.add(this.resp);
        this.setMinimumSize(new Dimension(340, 400));
        this.pack();
        this.setVisible(true);

        //create account
        this.submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                String text = textEntry.getText();
                
                try{

                    URL url = new URL("http://localhost:8080/AccountCreation/createAccount?userName=" + text);
                    HttpURLConnection api = (HttpURLConnection) url.openConnection();
                    
                    api.setRequestMethod("POST");
                    api.setRequestProperty( "Content-type", "application/json");
                    api.connect();
                    
                    BufferedReader in = new BufferedReader(new InputStreamReader(api.getInputStream()));
                    in.close();
                    
                    //displays success/failure message
                    
                    if (api.getResponseCode() == 200) {
                        resp.setText("User: " + text + " success");
                    } 
                    else {
                        resp.setText("User: " + text + " already exists");
                    }

                }

                catch (MalformedURLException f) {
                    // url error 
                    System.out.println("Bad URL...");
                    resp.setText("User: " + text + " failed");
                }

                catch (IOException f) {
                    // 'Not found' or 'unauthorized' errors
                    System.out.println("HTTP Error");
                    resp.setText("User: " + text + " failed");
                }
            }
        });

    }


}
