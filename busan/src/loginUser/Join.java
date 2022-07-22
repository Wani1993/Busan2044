package loginUser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kr.co.greenart.dbutil.BusanUtil;

public class Join extends JFrame {
	private Busanlogin user = new Busanlogin();

	private Map<String, String> map;


	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
	public List<String> list;
	

	
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Join() {
		super("회원가입 프로그램");
		map = new HashMap<>();
		list = new ArrayList<String>();
		JPanel pnl = new JPanel();
		JTextField id = new JTextField(10);
		id.setBounds(110, 35, 152, 28);
		JPasswordField ps = new JPasswordField(10);
		ps.setBounds(110, 86, 152, 30);

		JPasswordField psre = new JPasswordField();
		psre.setBounds(110, 144, 152, 28);
		pnl.add(psre);
		psre.setColumns(10);

		getContentPane().add(pnl);
		pnl.setLayout(null);
		pnl.add(id);
		pnl.add(ps);

		JButton btn = new JButton("회원가입");
		btn.setBounds(79, 302, 126, 31);
		btn.setBackground(Color.white);
		pnl.add(btn);

		JLabel idlbl = new JLabel("아이디");
		idlbl.setBounds(17, 41, 57, 15);
		pnl.add(idlbl);

		JLabel pslbl = new JLabel("비밀번호");
		pslbl.setBounds(17, 93, 57, 15);
		pnl.add(pslbl);

		setSize(300, 400);
		// setDefaultCloseOperation(EXIT_ON_CLOSE);
		pnl.setBackground(Color.LIGHT_GRAY);

		JLabel psrelbl = new JLabel("비밀번호 확인");
		psrelbl.setBounds(17, 150, 84, 15);
		pnl.add(psrelbl);
		
		JLabel username = new JLabel("이름");
		username.setBounds(17, 195, 57, 15);
		pnl.add(username);
		
		JLabel userbirthDay = new JLabel("주민번호");
		userbirthDay.setBounds(17, 247, 57, 15);
		pnl.add(userbirthDay);
		
		JTextField usernameF = new JTextField();
		usernameF.setBounds(110, 189, 152, 28);
		pnl.add(usernameF);
		usernameF.setColumns(10);
		
		JTextField userbirthDayF = new JTextField();
		userbirthDayF.setBounds(110, 241, 152, 28);
		pnl.add(userbirthDayF);
		userbirthDayF.setColumns(10);

		setLocationRelativeTo(null); // 창이 가운데에서 출력된다

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (id.getText().length() >= 4 && id.getText().length() <= 12 && ps.getText().length() >= 4
						&& ps.getText().length() <= 12) {

					String query = "SELECT * FROM login_info";

					Connection conn = null;
					Statement stmt = null;
					ResultSet rs = null;

					try {
						conn = BusanUtil.getConnection();
						stmt = conn.createStatement();
						rs = stmt.executeQuery(query);

						while (rs.next()) {
							String id = rs.getString("id");
							String password = rs.getString("password");
							String birthDay = rs.getString("birthDay");
							list.add(birthDay);
							user.read();
							map.put(id, password);
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					} finally {
						BusanUtil.closeRS(rs);
						BusanUtil.closeStmt(stmt);
						BusanUtil.closeConn(conn);
					}
					
					if(map.containsKey(id.getText())) {
						JOptionPane.showMessageDialog(Join.this, "이미 가입된 아이디입니다");
					} else {
						if (ps.getText().equals(psre.getText()) && !ps.getText().equals("") && !psre.getText().equals("") && 
								!usernameF.getText().equals("") && !userbirthDayF.getText().equals("") && userbirthDayF.getText().length() == 6 && usernameF.getText().length() < 4) {
							if (!map.containsKey(id.getText())) {
								try {
									user.create(id.getText(), ps.getText(), usernameF.getText(), userbirthDayF.getText());
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
								map.put(id.getText(), ps.getText()); // 맵에 아이디 비번 저장
								JOptionPane.showMessageDialog(Join.this, "회원가입 완료");
								id.setText("");
								ps.setText("");
								psre.setText("");
								usernameF.setText("");
								userbirthDayF.setText("");
								//System.out.println("되고있니");
								setVisible(false);
							}
						} else if (usernameF.getText().equals("") || userbirthDayF.getText().equals("") || 
								usernameF.getText().length() > 4 || !(userbirthDayF.getText().length() == 6)) {
							JOptionPane.showMessageDialog(Join.this, "이름과 주민번호를 확인해주세요");
						} else {
							JOptionPane.showMessageDialog(Join.this, "비밀번호를 확인해주세요");
						}}

				} else {
					if (id.getText().length() < 4) {
						JOptionPane.showMessageDialog(Join.this, "아이디가 너무 짧습니다.");
					} else if (id.getText().length() > 12) {
						JOptionPane.showMessageDialog(Join.this, "아이디가 너무 길어요우.");
					} else if (ps.getText().length() < 4) {
						JOptionPane.showMessageDialog(Join.this, "비밀번호가 너무 짧습니다.");
					} else if (ps.getText().length() > 12) {
						JOptionPane.showMessageDialog(Join.this, "비밀번호가 너무 길어요우.");
					}
				}
			}
		});

	}

	public static void main(String[] args) {
		Join l = new Join();
		l.setVisible(true);
		try {
			System.out.println(l.user.read());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
