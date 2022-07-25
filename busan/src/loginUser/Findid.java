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
import javax.swing.JTextField;

import kr.co.greenart.dbutil.BusanUtil;

public class Findid extends JFrame {

	private boolean b = false;

	public Findid() {
		super("아이디찾기");

		JPanel pnl = new JPanel();
	
		getContentPane().add(pnl);
		pnl.setLayout(null);
		

		JButton btn = new JButton("아이디찾기");
		btn.setBounds(80, 205, 126, 31);
		btn.setBackground(Color.white);
		pnl.add(btn);

		setSize(300, 300);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		pnl.setBackground(Color.LIGHT_GRAY);

		JLabel username = new JLabel("이름");
		username.setBounds(17, 61, 57, 15);
		pnl.add(username);

		JLabel phoneNum = new JLabel("전화번호");
		phoneNum.setBounds(17, 142, 57, 15);
		pnl.add(phoneNum);

		JTextField usernameF = new JTextField(10);
		usernameF.setBounds(110, 55, 152, 28);
		pnl.add(usernameF);

		JTextField phoneNumF = new JTextField(10);
		phoneNumF.setBounds(110, 136, 152, 28);
		pnl.add(phoneNumF);

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

					while (rs.next()) {
						String userid = rs.getString("id");
						String name = rs.getString("name");
						String phoneNum = rs.getString("phoneNum");
						if (usernameF.getText().equals(name) && phoneNumF.getText().equals(phoneNum)) {
							JOptionPane.showMessageDialog(Findid.this, "아이디는 " + userid);
							b = true;
							setVisible(false);
						} else {

							if (usernameF.getText().equals("") || phoneNumF.getText().equals("")
									|| !usernameF.getText().equals(name) || !phoneNumF.getText().equals(phoneNum)) {
								a = "이름 또는 전화번호를 확인하세요.";

							}
						}
					}
					if (!b) {
						JOptionPane.showMessageDialog(Findid.this, a);

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
		setResizable(false);

	}

	public static void main(String[] args) {
		new Findid().setVisible(true);
	}

}
