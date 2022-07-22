package loginUser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.greenart.dbutil.BusanUtil;

public class Login extends JFrame {
	private boolean b = false;
	
	public Login() {
		super("로그인 프로그램");
		JPanel pnl = new JPanel();
		JTextField id = new JTextField(10);
		id.setBounds(94, 24, 253, 28);
		JPasswordField ps = new JPasswordField(10);
		ps.setBounds(94, 78, 253, 30);
		
		Join map = new Join();
		Findpw fpw = new Findpw();
		Findid fid = new Findid();
		
	
		
		getContentPane().add(pnl);
		pnl.setLayout(null);
		pnl.add(id);
		pnl.add(ps);
		
		
		JButton btn = new JButton("로그인");
		btn.setBounds(44, 125, 126, 31);
		btn.setForeground(Color.pink);
		
		pnl.add(btn);
		
		JButton btn2 = new JButton("회원가입");
		btn2.setBounds(219, 125, 126, 31);
		btn2.setForeground(Color.pink);
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.setVisible(true);
			
			}
		});
		pnl.add(btn2);
		
		JLabel lblNewLabel = new JLabel("아이디");
		lblNewLabel.setBounds(25, 30, 57, 15);
		lblNewLabel.setForeground(Color.white);
		pnl.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("비밀번호");
		lblNewLabel_1.setBounds(25, 85, 57, 15);
		lblNewLabel_1.setForeground(Color.white);
		pnl.add(lblNewLabel_1);
		
		JButton btn3 = new JButton("아이디 찾기");
		btn3.setBounds(44, 165, 126, 31);
		btn3.setForeground(Color.pink);
		pnl.add(btn3);
		
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fid.setVisible(true);
			
			}
		});
		
		JButton btn4 = new JButton("비밀번호 찾기");
		btn4.setBounds(219, 165, 126, 31);
		btn4.setForeground(Color.pink);
		pnl.add(btn4);
		
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fpw.setVisible(true);
			
			}
		});
		
		
		
		
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "SELECT * FROM login_info";
				
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				
				try {
					conn = BusanUtil.getConnection();
					stmt = conn.createStatement();
					rs = stmt.executeQuery(query);
					String a = "";
					
					while (rs.next()) {
						String userid = rs.getString("id");
						String userpassword = rs.getString("password");
						
						if (id.getText().equals(userid) && ps.getText().equals(userpassword)) {
							JOptionPane.showMessageDialog(Login.this, "로그인 되었습니다");
							b = true;
							setVisible(false);
						} else { 
							if (!id.getText().equals(userid) || !ps.getText().equals(userpassword)) {
								a = "아이디 또는 비밀번호를 확인하세요.";
								}
						}
					}
					if (!b) {
					JOptionPane.showMessageDialog(Login.this, a);
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				} finally {
					BusanUtil.closeRS(rs);
					BusanUtil.closeStmt(stmt);
					BusanUtil.closeConn(conn);
				}
								
			}
		});
		
		

		
		
		setSize(400, 250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pnl.setBackground(Color.pink);
		id.setToolTipText("아이디를 입력하세요!");
		
	    setLocationRelativeTo(null); // 창이 가운데에서 출력된다
	    setResizable(false); // 사이즈 조정 불가
		
	}
	
	
	
	public static void main(String[] args) {
		new Login().setVisible(true);
	}
}
