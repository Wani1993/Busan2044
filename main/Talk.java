package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import kr.co.green.BusanUtil;
import main2.ShopPnl;

public class Talk extends JPanel {
	int count = 0;
	UserInfo user = new UserInfo();
	private Map<String, String> map = new HashMap<>();// <talkNum, talk> 를 담은 맵
	private Map<String, String> map2 = new HashMap<>();// <btn, story> 를 담은 맵
	private Map<String, Integer> map3 = new HashMap<>(); // <talkNun><like>담은 맵
	private String talkNum = "0";
	private String btn1 = "1";
	private String btn2 = "2";
	private JPanel container;

	// 호감도
	private void plusminus(String talkNum) {
		// 톡넘버에 맞는 like값을 like에 더해야함
		user.setNpc_likability(user.getNpc_likability() + map3.get(talkNum));
		// like값을 얻어야함

		// 개인의 호감도를 가져와서 다시 set해주는거
//		user.setNpc_likability(user.getNpc_likability() + (double)like);

	}

	// 한주탈 멘트
	private void getTalk(int day) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM busan.talk_table WHERE diaNum = ?");
			pstmt.setInt(1, day);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String TalkNum = rs.getString("talkNum");
				String Talk = rs.getString("talk");
				int like = rs.getInt("likability");
				map.put(TalkNum, Talk);
				map3.put(TalkNum, like);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
	}

	// 한주탈 멘트에 대한 선택지
	private void getChoice(int day) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM busan.talkChoice_table WHERE diaNum = ? ");
			pstmt.setInt(1, day);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String btn = rs.getString("btn");
				String story = rs.getString("story");

				map2.put(btn, story);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}

	}

	public Talk(int day, UserInfo user, ShopPnl container) {
		this.user = user;
		this.container = container; //이 컨테이너가 ShopPnl

		container.getBtnPnl().setVisible(false);
		container.add(Talk.this);
		
		getTalk(day);
		getChoice(day);
		setSize(800, 600);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(112, 128, 144));
		panel.setBounds(0, 0, 784, 561);
		add(panel);
		panel.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(112, 128, 144));
		panel_2.setBounds(12, 29, 216, 350);
		panel.add(panel_2);
		panel_2.setLayout(null);

		Toolkit kit = Toolkit.getDefaultToolkit();
		URL url = Talk.class.getClassLoader().getResource("talk상점캐.png");
		ImageIcon img = new ImageIcon(kit.getImage(url));
		JLabel storeChar = new JLabel(img);
		storeChar.setBackground(new Color(112, 128, 144));
		storeChar.setBounds(0, 0, 216, 350);
		panel_2.add(storeChar);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(12, 389, 760, 162);
		panel.add(panel_3);
		panel_3.setLayout(null);

		JTextArea text = new JTextArea(map.get("0"));
		text.setFont(new Font("Monospaced", Font.PLAIN, 17));
		text.setBounds(0, 0, 760, 162);
		panel_3.add(text);
		text.setEditable(false);

		JButton Choice1 = new JButton("");
		Choice1.setBounds(240, 235, 532, 39);
		Choice1.setVisible(false);
		panel.add(Choice1);

		JButton Choice2 = new JButton("");
		Choice2.setBounds(240, 297, 532, 39);
		Choice2.setVisible(false);
		panel.add(Choice2);

		JPanel heartImg = new JPanel();
		heartImg.setBounds(305, 94, 400, 40);
		panel.add(heartImg);
		heartImg.setLayout(null);

		List<JLabel> lblHeartImg = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			JLabel lbl = new JLabel("");
			lbl.setBackground(new Color(112, 128, 144));
			lbl.setOpaque(true);
			lblHeartImg.add(lbl);
			heartImg.add(lbl);
		}

		lblHeartImg.get(0).setBounds(0, 0, 40, 40);
		lblHeartImg.get(1).setBounds(40, 0, 40, 40);
		lblHeartImg.get(2).setBounds(80, 0, 40, 40);
		lblHeartImg.get(3).setBounds(120, 0, 40, 40);
		lblHeartImg.get(4).setBounds(160, 0, 40, 40);
		lblHeartImg.get(5).setBounds(200, 0, 40, 40);
		lblHeartImg.get(6).setBounds(240, 0, 40, 40);
		lblHeartImg.get(7).setBounds(280, 0, 40, 40);
		lblHeartImg.get(8).setBounds(320, 0, 40, 40);
		lblHeartImg.get(9).setBounds(360, 0, 40, 40);

		JButton start = new JButton("대답하기");
		start.setFont(new Font("굴림", Font.PLAIN, 15));
		start.setBounds(442, 346, 97, 23);
		panel.add(start);
		JButton end = new JButton("대답종료");
		end.setFont(new Font("굴림", Font.PLAIN, 15));
		end.setBounds(442, 346, 97, 23);
		panel.add(end);

		start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Choice1.setText(map2.get(btn1));
				Choice1.setVisible(true);
				Choice2.setText(map2.get(btn2));
				Choice2.setVisible(true);
				start.setVisible(false);
			}
		});
		end.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//대화 종료면은 어떻게 될지 로직 짜야함
				container.getBtnPnl().setVisible(true);
	            container.remove(Talk.this);
			}
		});

		Choice1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				talkNum = talkNum + "-1";
				btn1 = btn1 + "-1";
				btn2 = btn2 + "-1";
				System.out.println(btn1);
				text.setText(map.get(talkNum));
				String stop = map2.get(btn1);
				if (stop == null) {
					Choice1.setVisible(false);
					Choice2.setVisible(false);
					end.setVisible(true);
				} else {
					Choice1.setText(stop);
					Choice2.setText(map2.get(btn2));
				}
				plusminus(talkNum);
				likabilityImg(lblHeartImg);
			}
		});

		Choice2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				talkNum = talkNum + "-2";
				btn1 = btn1 + "-2";
				btn2 = btn2 + "-2";

				text.setText(map.get(talkNum));
				String stop = map2.get(btn1);
				if (stop == null) {
					Choice1.setVisible(false);
					Choice2.setVisible(false);
					end.setVisible(true);
				} else {
					Choice1.setText(stop);
					Choice2.setText(map2.get(btn2));
				}
				plusminus(talkNum);
				likabilityImg(lblHeartImg);
			}
		});

		// 지연이언니가만든 호감도
		likabilityImg(lblHeartImg);

	}

	// 호감도 하트 차는거
	public void likabilityImg(List<JLabel> lblHeartImg) {
		double heart = user.getNpc_likability();
//		double heart = 4.9;
		Toolkit kit = Toolkit.getDefaultToolkit();

		for (int i = 0; i < 10; i++) {
			if (i < ((int) heart)) {
				URL url = Trade.class.getClassLoader().getResource("700.png");
				Image img = kit.getImage(url);
				lblHeartImg.get(i).setIcon(new ImageIcon(img));
			} else {
				URL url = Trade.class.getClassLoader().getResource("701.png");
				Image img = kit.getImage(url);
				lblHeartImg.get(i).setIcon(new ImageIcon(img));
			}
		}
	}

//	public static void main(String[] args) {
//		UserInfo user = new UserInfo();
//		user.setNpc_likability(8);
//		Talk a = new Talk(3, user);
//		a.setVisible(true);
//
//	}
}
