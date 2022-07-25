package loginUser;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.greenart.dbutil.BusanUtil;

public class Findpw extends JFrame {

	private boolean b = false;

	public Findpw() {
		super("비번찾기");

		JPanel pnl = new JPanel();
		JTextField id = new JTextField(10);
		id.setBounds(110, 35, 152, 28);

		getContentPane().add(pnl);
		pnl.setLayout(null);
		pnl.add(id);

		JButton btn = new JButton("비밀번호찾기");
		btn.setBounds(80, 205, 126, 31);
		btn.setBackground(Color.white);
		pnl.add(btn);

		JLabel idlbl = new JLabel("아이디");
		idlbl.setBounds(17, 41, 57, 15);
		pnl.add(idlbl);

		setSize(300, 300);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		pnl.setBackground(Color.LIGHT_GRAY);

		JLabel username = new JLabel("이름");
		username.setBounds(17, 96, 57, 15);
		pnl.add(username);

		JLabel userPhone = new JLabel("전화번호");
		userPhone.setBounds(17, 149, 57, 15);
		pnl.add(userPhone);

		JTextField usernameF = new JTextField(10);
		usernameF.setBounds(110, 93, 152, 28);
		pnl.add(usernameF);

		JTextField userPhoneF = new JTextField(10);
		userPhoneF.setBounds(110, 146, 152, 28);
		pnl.add(userPhoneF);

		btn.addActionListener(new ActionListener() {

			@Override
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
					String a1 = "";
					String a2 = "";
					int count = 0;

					while (rs.next()) {
						String userid = rs.getString("id");
						String userpassword = rs.getString("password");
						String name = rs.getString("name");
						String phoneNum = rs.getString("phoneNum");

						if (id.getText().equals(userid) && usernameF.getText().equals(name)
								&& userPhoneF.getText().equals(phoneNum)) {
							JOptionPane.showMessageDialog(Findpw.this, "비밀번호는 " + userpassword);
							b = true;
							setVisible(false);
						} else {

//							if (!(id.getText().equals(userid))) {
//								a = "아이디를 확인하세요\n";
//							}
//							if (!(usernameF.getText().equals(name))) {
//								a1 = "이름을 확인하세요\n";
//							}
//							if (!(userPhoneF.getText().equals(phoneNum))) {
//								a2 = "생년월일을 확인하세요\n";
//							}

							if (!id.getText().equals(userid) || !usernameF.getText().equals(name)
									|| !userPhoneF.getText().equals(phoneNum)) {
								if (id.getText().equals("") || usernameF.getText().equals("") || (userPhoneF.getText().equals(""))) {
									a = "빈칸을 채워주세요.";
									a1 = "";
									a2 = "";
								} 
										
								if(!id.getText().equals(userid)) {
									count++;
								}
								if(!usernameF.getText().equals(name)) {
									count++;
								}
								if(!userPhoneF.getText().equals(phoneNum)) {
									count++;
								}
									

								if ( (!id.getText().equals(userid)
										&& (usernameF.getText().equals(name) && userPhoneF.getText().equals(phoneNum)))
										|| (!id.getText().equals(userid) && userPhoneF.getText().equals(phoneNum))
										|| (!id.getText().equals(userid) && usernameF.getText().equals(name)) ) {
									a = "아이디를 확인하세요\n";
									
								}
								if ( (!usernameF.getText().equals(name)
										&& (id.getText().equals(userid) && userPhoneF.getText().equals(phoneNum)))
										|| (!usernameF.getText().equals(name) && userPhoneF.getText().equals(phoneNum))
										|| (!usernameF.getText().equals(name) && id.getText().equals(userid)) ) {
									a1 = "이름을 확인하세요\n";
									
								} 
								if ( (!userPhoneF.getText().equals(phoneNum)
										&& (id.getText().equals(userid) && usernameF.getText().equals(name)))
										|| (!userPhoneF.getText().equals(phoneNum) && id.getText().equals(userid))
										|| (!userPhoneF.getText().equals(phoneNum) && usernameF.getText().equals(name)) ) {
									a2 = "전화번호를 확인하세요\n";
									
								} 
								
								if(count == 3) {
									a = "개인정보를 확인해주세요";
									a1 = "";
									a2 = "";
								}
								

							}

						}

					}
					if (!b) {
						JOptionPane.showMessageDialog(Findpw.this, a + a1 + a2);
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

		setLocationRelativeTo(null); // 창이 가운데에서 출력된다
		setResizable(false); // 사이즈 조정불가

	}

	public static void main(String[] args) {
		new Findpw().setVisible(true);
	}

}
