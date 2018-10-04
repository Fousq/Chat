import javax.swing.*;

public class LoginWindow extends JFrame{
	private JTextField nickNameTF;
	private JTextField portTF;
	private JTextField IPTF;
	private JButton btnJoin;
	
	public LoginWindow() {
		createWelcomePanel();
		setSize(280, 403);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lblNickName = new JLabel("Nick name:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNickName, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNickName, 100, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNickName);
		
		nickNameTF = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, nickNameTF, 6, SpringLayout.SOUTH, lblNickName);
		springLayout.putConstraint(SpringLayout.WEST, nickNameTF, 38, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, nickNameTF, -41, SpringLayout.EAST, getContentPane());
		getContentPane().add(nickNameTF);
		nickNameTF.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		springLayout.putConstraint(SpringLayout.NORTH, lblPort, 47, SpringLayout.SOUTH, nickNameTF);
		springLayout.putConstraint(SpringLayout.WEST, lblPort, 117, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblPort);
		
		portTF = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, portTF, 6, SpringLayout.SOUTH, lblPort);
		springLayout.putConstraint(SpringLayout.WEST, portTF, 0, SpringLayout.WEST, nickNameTF);
		springLayout.putConstraint(SpringLayout.EAST, portTF, 0, SpringLayout.EAST, nickNameTF);
		getContentPane().add(portTF);
		portTF.setColumns(10);
		
		JLabel lblIp = new JLabel("IP: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblIp, 63, SpringLayout.SOUTH, portTF);
		springLayout.putConstraint(SpringLayout.EAST, lblIp, 0, SpringLayout.EAST, lblPort);
		getContentPane().add(lblIp);
		
		IPTF = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, IPTF, 7, SpringLayout.SOUTH, lblIp);
		springLayout.putConstraint(SpringLayout.WEST, IPTF, 0, SpringLayout.WEST, nickNameTF);
		springLayout.putConstraint(SpringLayout.EAST, IPTF, 0, SpringLayout.EAST, nickNameTF);
		getContentPane().add(IPTF);
		IPTF.setColumns(10);
		
		btnJoin = new JButton("Join");
		springLayout.putConstraint(SpringLayout.NORTH, btnJoin, 63, SpringLayout.SOUTH, IPTF);
		springLayout.putConstraint(SpringLayout.EAST, btnJoin, 0, SpringLayout.EAST, lblNickName);
		getContentPane().add(btnJoin);
		
		setVisible(true);
	}
	
	private void createWelcomePanel() {
		JOptionPane.showMessageDialog(this, "This is a simple project to learn how to code.", "Welcome", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public JButton getBtnJoin() {
		return btnJoin;
	}
	
	public boolean areTFsEmpty() {
		return nickNameTF.getText().isEmpty() && portTF.getText().isEmpty() && IPTF.getText().isEmpty();
	}
	
	public int getPort() {
		return Integer.parseInt(portTF.getText());
	}
	
	public String getNickName() {
		return nickNameTF.getText();
	}
	
	public String getIP() {
		return IPTF.getText();
	}
	
}
