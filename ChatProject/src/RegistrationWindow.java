
import java.sql.SQLException;

import javax.swing.*;
import javax.crypto.*;

import DataBase.DataBase;

public class RegistrationWindow extends JFrame {
	
	private JTextField nickNameTF;
	private JTextField passwordTF;
	private JButton btnRegistrate;
	private DataBase dataBase;
	
	public RegistrationWindow() {
		setSize(240, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblNickname = new JLabel("NickName: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNickname, 28, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNickname, 20, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNickname);
		
		nickNameTF = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, nickNameTF, 25, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, nickNameTF, 6, SpringLayout.EAST, lblNickname);
		springLayout.putConstraint(SpringLayout.EAST, nickNameTF, 129, SpringLayout.EAST, lblNickname);
		getContentPane().add(nickNameTF);
		nickNameTF.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblPassword, 32, SpringLayout.SOUTH, lblNickname);
		springLayout.putConstraint(SpringLayout.EAST, lblPassword, 0, SpringLayout.EAST, lblNickname);
		getContentPane().add(lblPassword);
		
		passwordTF = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, passwordTF, 23, SpringLayout.SOUTH, nickNameTF);
		springLayout.putConstraint(SpringLayout.WEST, passwordTF, 6, SpringLayout.EAST, lblPassword);
		springLayout.putConstraint(SpringLayout.EAST, passwordTF, 129, SpringLayout.EAST, lblPassword);
		getContentPane().add(passwordTF);
		passwordTF.setColumns(10);
		
		btnRegistrate = new JButton("Registrate");
		springLayout.putConstraint(SpringLayout.SOUTH, btnRegistrate, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnRegistrate, -66, SpringLayout.EAST, getContentPane());
		getContentPane().add(btnRegistrate);
		
		setVisible(true);
		
		try {
			dataBase = new DataBase();
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed on connecting to data base", "Data Base Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public JButton getBtnRegistrate() {
		return btnRegistrate;
	}
	
	public void addToDB() {
		
	}

}
