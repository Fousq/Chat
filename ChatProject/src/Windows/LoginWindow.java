package Windows;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import DataBase.DataBase;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame{
	
	private JTextField nickNameTF;
	private JButton btnLogIn;
	private JPasswordField passwordField;
	private JButton btnSighUp;
	private DataBase dataBase = null;
	
	public LoginWindow() {
		setSize(300, 260);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblNickName = new JLabel("Nick name:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNickName, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNickName, 100, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNickName);
		
		nickNameTF = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, nickNameTF, 6, SpringLayout.SOUTH, lblNickName);
		springLayout.putConstraint(SpringLayout.WEST, nickNameTF, 38, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, nickNameTF, 223, SpringLayout.WEST, getContentPane());
		getContentPane().add(nickNameTF);
		nickNameTF.setColumns(10);
		
		btnLogIn = new JButton("Log in");
		springLayout.putConstraint(SpringLayout.SOUTH, btnLogIn, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnLogIn, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnLogIn);
		
		JLabel lblPassword = new JLabel("Password: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 29, SpringLayout.SOUTH, nickNameTF);
		springLayout.putConstraint(SpringLayout.WEST, lblPassword, 0, SpringLayout.WEST, lblNickName);
		getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 38, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, passwordField, -61, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, 6, SpringLayout.SOUTH, lblPassword);
		getContentPane().add(passwordField);
		
		btnSighUp = new JButton("Sigh up");
		springLayout.putConstraint(SpringLayout.NORTH, btnSighUp, 0, SpringLayout.NORTH, btnLogIn);
		springLayout.putConstraint(SpringLayout.WEST, btnSighUp, 10, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnSighUp);
		
		setVisible(true);
	}
	
	public JButton getBtnLogIn() {
		return btnLogIn;
	}
	
	public JButton getBtnSighUp() {
		return btnSighUp;
	}
	
	public String getNickName() {
		return nickNameTF.getText();
	}
	
	public boolean isUser(String name, String password) {
		return dataBase.isUserExist(name, password);
	}
}
