package loginUser;
import java.awt.Color;
import java.awt.Font;
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
		// TODO 폰트
		id.setFont(new Font("한컴산뜻돋움", Font.BOLD, 14));
		id.setBounds(94, 24, 253, 28);
		JPasswordField ps = new JPasswordField(10);
		ps.setBounds(94, 78, 253, 30);
		
		Join join = new Join();
		Findpw fpw = new Findpw();
		Findid fid = new Findid();
		
		
		
		
		
		// TODO 폰트
		JButton btn = new JButton("로그인");
		btn.setFont(new Font("한컴산뜻돋움", Font.BOLD, 14));
		btn.setBounds(44, 125, 126, 31);
		btn.setBackground(Color.white);
		btn.setForeground(Color.pink);
		
		
		// TODO 폰트
		JButton btn2 = new JButton("회원가입");
		btn2.setFont(new Font("한컴산뜻돋움", Font.BOLD, 14));
		btn2.setBounds(219, 125, 126, 31);
		btn2.setBackground(Color.white);
		btn2.setForeground(Color.pink);
		
		// TODO 폰트
		JLabel idlbl = new JLabel("아이디");
		idlbl.setFont(new Font("한컴산뜻돋움", Font.BOLD, 14));
		idlbl.setBounds(25, 30, 57, 15);
		idlbl.setForeground(Color.white);
		
		// TODO 폰트
		JLabel pwlbl = new JLabel("비밀번호");
		pwlbl.setFont(new Font("한컴산뜻돋움", Font.BOLD, 14));
		pwlbl.setBounds(25, 85, 70, 15);
		pwlbl.setForeground(Color.white);

		// TODO 폰트
		JButton btn3 = new JButton("아이디 찾기");
		btn3.setFont(new Font("한컴산뜻돋움", Font.BOLD, 14));
		btn3.setBounds(44, 165, 126, 31);
		btn3.setBackground(Color.white);
		btn3.setForeground(Color.pink);
		
		
	
		
		JButton btn4 = new JButton("비밀번호 찾기");
		btn4.setFont(new Font("한컴산뜻돋움", Font.BOLD, 13));
		btn4.setBounds(219, 165, 126, 31);
		btn4.setBackground(Color.white);
		btn4.setForeground(Color.pink);


			
		
		getContentPane().add(pnl);
		pnl.setLayout(null);
		pnl.add(idlbl);
		pnl.add(id);
		pnl.add(pwlbl);
		pnl.add(ps);
		pnl.add(btn);
		pnl.add(btn2);
		pnl.add(btn3);
		pnl.add(btn4);
		
		
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
		
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				join.setVisible(true);
			
			}
		});
		
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fid.setVisible(true);
			
			}
		});
		
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fpw.setVisible(true);
			
			}
		});
		

		
		
		setSize(400, 250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pnl.setBackground(Color.pink);
		id.setToolTipText("아이디를 입력하세요!");
		ps.setToolTipText("비밀번호를 입력하세요!");
		
	    setLocationRelativeTo(null); // 창이 가운데에서 출력된다
	    setResizable(false); // 사이즈 조정 불가
		
	}
	
	
	
	public static void main(String[] args) {
		Login l = new Login();
		l.setVisible(true);
		
	}
}
