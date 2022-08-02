package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;

import bulletin.Bulletin;
import kr.co.green.BusanUtil;
import loginUser.BusanUser;
import loginUser.Login;
import main2.StoryPnl2;
import statistics.AchvPnl;

public class StoryPnl extends JFrame {
	private BusanUser loguser;
//	private StoryPnl2 storyPnl2;
	private Bulletin bul;
	private JPanel pnlBBG;
	private NpcDao npcdao = new NpcDao();
	private UserInfo user = new UserInfo();
	private List<Story> list;
	private List<ChoiceSum> choiceList;
	private int sn = 0;
	private int snChoice = 0;
	private int num13 = 0;
	private JTextArea storyArea;
	private JButton[] btnChoice = new JButton[5]; // 버튼 처리를 위해 옮김
	private JPanel choicePnl; // 버튼 패널 추가
	private Random rd = new Random();
	private List<Character> path = new ArrayList<>();
	private List<Integer> path_c = new ArrayList<>();
	private List<JLabel> lblNpcImg;
	private ItemConsole itemconsole = new ItemConsole(new ItemDao(), user);

	private URL hp = Login.class.getClassLoader().getResource("700.png");
	private ImageIcon fullhp = new ImageIcon(hp);
	private URL hpzero = Login.class.getClassLoader().getResource("701.png");
	private ImageIcon emptyhp = new ImageIcon(hpzero);
	private URL mp = Login.class.getClassLoader().getResource("702.png");
	private ImageIcon fullmp = new ImageIcon(mp);
	private URL bg = Login.class.getClassLoader().getResource("first.jpg");
	private ImageIcon back = new ImageIcon(bg);
	private JLabel[] hplbl = { new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	private JLabel[] mplbl = { new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel() };
//	private int heart = user.getHp();
//	private int mental = user.getMental();

	public static Font mainFont = new Font("한컴산뜻돋움", Font.BOLD, 20);
	private boolean req/* requirement */ = false, bandageEv = false, stop = false;

	private List<JLabel> userInven = new ArrayList<>();
	private List<JLabel> userInvenCount = new ArrayList<>();

	// 선택지 중복 가능하게 하기 위한 생성자
	int repeatNum;

	public void hpmp() {
		int heart = user.getHp();
		int mental = user.getMental();

		for (int i = 0; i < 5; i++) {
			if (i < (heart)) {
				hplbl[i].setIcon(fullhp);
			} else {
				hplbl[i].setIcon(emptyhp);
			}
		}

		for (int i = 0; i < 5; i++) {
			if (i < (mental)) {
				mplbl[i].setIcon(fullmp);
			} else {
				mplbl[i].setIcon(emptyhp);
			}
		}
	}

	public void userInven() {
		for (JLabel lbll : userInven) {
			lbll.setText("");
			lbll.setIcon(null);
		}
		for (JLabel lbll : userInvenCount) {
			lbll.setText("");
		}
		List<Item> items = user.getInventory();
		itemconsole.lblImg(userInven, items);
		itemconsole.lblCount(userInven, userInvenCount);
		for (int i = 0; i < userInven.size(); i++) {
			itemconsole.imgHover(userInven.get(i));
		}
	}

//	중복 선택지 활성화 메소드
	private void repeatChoice(int btn) {
		choicePnl.setVisible(true);
		if (repeatNum < 2) {
			btnChoice[btn].setEnabled(false);
			switch (btn) {
			case 0: { // 장주먹
				itemconsole.getItem(8); // 살골
				user.getParty().add(4);
				npcImg(lblNpcImg);
				itemconsole.lblImg(userInven, user.getInventory());
				itemconsole.lblCount(userInven, userInvenCount);
				break;
			}
			case 1: { // 전판례
				itemconsole.getItem(7); // 법전
				user.getParty().add(5);
				npcImg(lblNpcImg);
				itemconsole.lblImg(userInven, user.getInventory());
				itemconsole.lblCount(userInven, userInvenCount);
				break;
			}
			case 2: { // 도독놈
//				itemconsole.getItem(8); // 없음
				user.getParty().add(6);
				npcImg(lblNpcImg);
				break;
			}

			case 3: { // 고모리
				itemconsole.getItem(2); // 천본앵?
				user.getParty().add(7);
				npcImg(lblNpcImg);
				itemconsole.lblImg(userInven, user.getInventory());
				itemconsole.lblCount(userInven, userInvenCount);
				break;
			}

			case 4: { // 한은둔
//				itemconsole.getItem(8); // 없음
				user.getParty().add(8);
				npcImg(lblNpcImg);
				break;
			}

			}
			btn += 6;
			repeatNum++;
			storyArea.setText(getAchoice(btn).getStoryMain());

			if (repeatNum == 2) {
				sn++;
				choicePnl.setVisible(false);
				snChoice++;
			}
		} else {
			sn++;
			storyArea.setText(getAstory(sn).getStoryMain());
		}
	}

	// 선택지 기록
	private void insertPath(String path) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		list = new ArrayList<>();

		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO savehere(storyPath) VALUES (?)");
			pstmt.setString(1, path);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}

	}

	// btnChoice 클릭 액션 처리 메소드
	private void storyUpdate(int btnNum) {
		// btnNum = 몇 번째 버튼인지 입력
		int btn = btnNum - 1;
		sn++;

		choicePnl.setVisible(false);

		switch (snChoice) {
		case 0: {
			storyArea.setText(getAchoice(btn).getStoryMain());
			break;
		}
		case 1: {
			btn += 4;
			storyArea.setText(getAchoice(btn).getStoryMain());
			break;
		}
		case 2: {
			btn += 6;
			storyArea.setText(getAchoice(btn).getStoryMain());
			break;
		}
		case 3: {
			btn += 11;
			storyArea.setText("(전투발생 이벤트)");
			break;
		}
		case 4: {
			btn += 13;
			storyArea.setText(getAchoice(btn).getStoryMain());
			break;
		}
		case 5: {
			btn += 16;
			storyArea.setText(getAchoice(btn).getStoryMain());
			break;
		}
		case 6: {
			// 랜덤 발생
			break;
		}
		case 7: {
			btn += 22;
			storyArea.setText(getAchoice(btn).getStoryMain());
			break;
		}
		case 8: {
			btn += 25;
			storyArea.setText(getAchoice(btn).getStoryMain());
			break;
		}
		}
		path_c.add(getAchoice(btn).getChoiceId());
		storyArea.setCaretPosition(0); // 클릭마다 커서를 젤 위로 올려 스크롤이 내려가있는 걸 방지
	}

	// choiceText에서 사용할 for문 메소드 제작
	private void choiceFor(int btnNum, int listIndex) {
		// btnNum = 선택지 개수(버튼 개수)
		// listIndex = choiceList의 몇 번째 index에서 시작할 지

		for (int i = 0; i < btnChoice.length; i++) {
			btnChoice[i].setVisible(false);
		}

		for (int i = 0; i < btnNum; i++, listIndex++) {
			btnChoice[i].setText(getAchoice(listIndex).getChoiceMain());
			btnChoice[i].setVisible(true);
			btnChoice[i].setEnabled(true);
			if (snChoice == 7 && i == 1) {
				btnChoice[i].setText(getAchoice(listIndex + 1).getChoiceMain());
				break;
			} else if (snChoice == 8 && i == 0) {
				btnChoice[i].setText(getAchoice(listIndex + 1).getChoiceMain());
			} else if (snChoice == 8 && i == 1) {
				btnChoice[i].setText(getAchoice(listIndex + 1).getChoiceMain());
				break;
			}
		}
	}

	// 선택지 변경 메소드
	private void choiceText(int choiceNum) {
		// choiceNum = 몇 번째 선택지 인지(아마 snChoice 입력하면 될듯)
		sn--;
		switch (choiceNum) {
		case 0: {
			choiceFor(4, 0);
			break;
		}
		case 1: {
			choiceFor(2, 4);
			break;
		}
		case 2: {
			choiceFor(5, 6);
			break;
		}
		case 3: {
			// 전투 발생
			break;
		}
		case 4: {
			choiceFor(3, 13);
			break;
		}
		case 5: {
			choiceFor(4, 16);
			break;
		}
		case 6: {
			// 랜덤 발생
			break;
		}
		case 7: {
			choiceFor(2, 22);
			break;
		}
		case 8: {
			choiceFor(2, 24);
			if (req == true) {
				btnChoice[0].setVisible(true);
			}
			break;
		}

		}
		sn++;
		choicePnl.setVisible(false);
	}

	// DB에서 chapter1_choice 테이블 리스트에 담기
	private ChoiceSum getAchoice(int a) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		choiceList = new ArrayList<>();

		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM `busan`.chapter1_choiceSum");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ChoiceSum cic = new ChoiceSum(rs.getInt("choiceId"), rs.getString("storyNum"),
						rs.getString("choiceMain"), rs.getString("StoryMain"), rs.getString("storyCheck"),
						rs.getString("choiceCheck"));
				choiceList.add(cic);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
		return choiceList.get(a);
	}

	// DB에서 chapter1 테이블 리스트에 담기
	private Story getAstory(int selecRow) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		list = new ArrayList<>();

		try {

			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM busan.chapter1_storyonly");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Story str = new Story(rs.getInt("storyId"), rs.getString("storyNum"), rs.getString("storyMain"),
						rs.getInt("storyTime"), rs.getString("check"));

				list.add(str);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
		return list.get(selecRow);
	}

	public void npcImg(List<JLabel> lblNpcImg) {
		List<Integer> npcParty = user.getParty();
		String path = null;
		try (Connection conn = BusanUtil.getConnection()) {
			for (int i = 0; i < npcParty.size(); i++) {
				path = npcdao.getNpcPath(conn, npcParty.get(i));
				Toolkit kit = Toolkit.getDefaultToolkit();
				URL url = StoryPnl.class.getClassLoader().getResource(path);
				Image img = kit.getImage(url);
				lblNpcImg.get(i).setIcon(new ImageIcon(img));

				lblNpcImg.get(i).setText(String.valueOf(npcParty.get(i)));
				lblNpcImg.get(i).setFont(new Font("휴먼엑스포", Font.PLAIN, 0));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String IdToNpcInfo(int npc_id, String select) {
		Connection conn = null;
		String info = "";
		try {
			conn = BusanUtil.getConnection();
			if (select.equals("name")) {
				info = npcdao.getNpcName(conn, npc_id);
			}
			if (select.equals("memo")) {
				info = npcdao.getNpcMemo(conn, npc_id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BusanUtil.closeConn(conn);
		}
		return info;
	}

	public void npcImgHover(JLabel lbl) {
		int npc_id = itemconsole.lbl_searchID(lbl); // 패널에서 id값 가져오기
		String color = "";
		if (npc_id != -1) { // npcId가 빈 값이 아닐때

			if (npc_id == 2) {
				color = "이름 : <font face=\"sansserif\" color=\"purple\">";
			} else {
				color = "이름 : <font face=\"sansserif\" color=\"black\">";
			}
			String info_Npc = "<html>" + color + IdToNpcInfo(npc_id, "name") + "</font><br>" + "설명 : "
					+ IdToNpcInfo(npc_id, "memo") + "</html>";
			lbl.setToolTipText(info_Npc);
		}
	}

	public StoryPnl(BusanUser loguser) {
		super("부산2044");
		this.loguser = loguser;
		
		// 인벤토리 예시

		JPanel pnl_userInven = new JPanel();
		pnl_userInven.setBounds(865, 140, 304, 450);
		getContentPane().add(pnl_userInven);
		pnl_userInven.setLayout(null);

//				JLabel lblNewLabel = new JLabel("0");
//				lblNewLabel.setBounds(50, 64, 26, 26);
//				pnl_userInven.add(lblNewLabel);

		List<Integer> party = new ArrayList<>(Arrays.asList(2));
		user.setParty(party);

		// 유저 인벤토리 count용
		userInvenCount = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			JLabel lbl = new JLabel("");
//					lbl.setOpaque(true);
			lbl.setFont(new Font("휴먼굴림체", Font.BOLD, 14));
			lbl.setForeground(Color.red);
			userInvenCount.add(lbl);
			pnl_userInven.add(lbl);
		}

		userInvenCount.get(0).setBounds(50, 64, 26, 26);
		userInvenCount.get(1).setBounds(126, 64, 26, 26);
		userInvenCount.get(2).setBounds(202, 64, 26, 26);
		userInvenCount.get(3).setBounds(278, 64, 26, 26);
		userInvenCount.get(4).setBounds(50, 154, 26, 26);
		userInvenCount.get(5).setBounds(126, 154, 26, 26);
		userInvenCount.get(6).setBounds(202, 154, 26, 26);
		userInvenCount.get(7).setBounds(278, 154, 26, 26);
		userInvenCount.get(8).setBounds(50, 244, 26, 26);
		userInvenCount.get(9).setBounds(126, 244, 26, 26);
		userInvenCount.get(10).setBounds(202, 244, 26, 26);
		userInvenCount.get(11).setBounds(278, 244, 26, 26);
		userInvenCount.get(12).setBounds(50, 334, 26, 26);
		userInvenCount.get(13).setBounds(126, 334, 26, 26);
		userInvenCount.get(14).setBounds(202, 334, 26, 26);
		userInvenCount.get(15).setBounds(278, 334, 26, 26);
		userInvenCount.get(16).setBounds(50, 424, 26, 26);
		userInvenCount.get(17).setBounds(126, 424, 26, 26);
		userInvenCount.get(18).setBounds(202, 424, 26, 26);
		userInvenCount.get(19).setBounds(278, 424, 26, 26);

		// 유저 인벤토리 img용
		userInven = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			JLabel lbl = new JLabel("");
//					lbl.setOpaque(true);
			userInven.add(lbl);
			pnl_userInven.add(lbl);

		}
		userInven.get(0).setBounds(0, 0, 76, 90);
		userInven.get(1).setBounds(76, 0, 76, 90);
		userInven.get(2).setBounds(152, 0, 76, 90);
		userInven.get(3).setBounds(228, 0, 76, 90);
		userInven.get(4).setBounds(0, 90, 76, 90);
		userInven.get(5).setBounds(76, 90, 76, 90);
		userInven.get(6).setBounds(152, 90, 76, 90);
		userInven.get(7).setBounds(228, 90, 76, 90);
		userInven.get(8).setBounds(0, 180, 76, 90);
		userInven.get(9).setBounds(76, 180, 76, 90);
		userInven.get(10).setBounds(152, 180, 76, 90);
		userInven.get(11).setBounds(228, 180, 76, 90);
		userInven.get(12).setBounds(0, 270, 76, 90);
		userInven.get(13).setBounds(76, 270, 76, 90);
		userInven.get(14).setBounds(152, 270, 76, 90);
		userInven.get(15).setBounds(228, 270, 76, 90);
		userInven.get(16).setBounds(0, 360, 76, 90);
		userInven.get(17).setBounds(76, 360, 76, 90);
		userInven.get(18).setBounds(152, 360, 76, 90);
		userInven.get(19).setBounds(228, 360, 76, 90);

		// 기본지급 아이템들~~~~~!!!!!!
		user.setInventory(
				new ArrayList<Item>(Arrays.asList(new Item(3, 9999, 1), new Item(19, 0, 2), new Item(10, 0, 3), new Item(12, 0, 1))));
		userInven(); // 이 메소드는 항상 써줘야 inventory List<Item>이 이미ㅈ화 된다

		ToolTipManager m = ToolTipManager.sharedInstance();
		m.setInitialDelay(0);

		// 제일 큰 패널
		pnlBBG = new JPanel();

		JPanel npcPnl = new JPanel();
		npcPnl.setBounds(858, 637, 305, 100);
//        npcPnl.setBackground(Color.gray);
		npcPnl.setOpaque(false);
		pnlBBG.add(npcPnl);
		npcPnl.setLayout(null);

		lblNpcImg = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			JLabel lbl = new JLabel("");
//            lbl.setBackground(new Color(112, 128, 144));
			lbl.setOpaque(false);
			lblNpcImg.add(lbl);
			npcPnl.add(lbl);
		}

		lblNpcImg.get(0).setBounds(0, 0, 76, 100);
		lblNpcImg.get(1).setBounds(76, 0, 76, 100);
		lblNpcImg.get(2).setBounds(152, 0, 76, 100);
		lblNpcImg.get(3).setBounds(228, 0, 76, 100);

		npcImg(lblNpcImg);
		for (int i = 0; i < lblNpcImg.size(); i++) {
			npcImgHover(lblNpcImg.get(i));
		}
		ToolTipManager m2 = ToolTipManager.sharedInstance();
		m2.setInitialDelay(0);

		int bound = 920;
		int bound2 = 920;
		for (int i = 0; i < 5; i++) {
			hplbl[i].setBounds(bound, 36, 60, 60);
			pnlBBG.add(hplbl[i]);
			mplbl[i].setBounds(bound2, 86, 60, 60);
			pnlBBG.add(mplbl[i]);
			bound += 50;
			bound2 += 50;
		}
		hpmp();
// --------------------------------------------

		// 텍스트 나오는 부분
		JPanel pnlTxt = new JPanel();
		pnlTxt.setBounds(12, 10, 824, 841);
		pnlTxt.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlTxt.setPreferredSize(new Dimension(800, 700));
		pnlTxt.setLayout(null);
//		대수 추가
		// 스크롤 추가
		JScrollPane jsp = new JScrollPane(); // JScrollPane(스크롤 생성)은 생성자에 참조변수(JTextArea)가 있어야 한다.
		jsp.setBounds(12, 10, 800, 615);
		pnlTxt.add(jsp);
		jsp.setBackground(Color.BLACK);

		// 스토리 나오는 TextArea
		storyArea = new JTextArea("(PRESS ANY KEY)");
		storyArea.setFont(mainFont);
		jsp.setViewportView(storyArea);
		storyArea.setBackground(Color.GRAY);
//		storyArea.setForeground(Color.white);
		storyArea.setEditable(false);

//		 클릭 시 다음 스토리 노출
		storyArea.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
//				System.out.println(user.getInventory());
//				System.out.println(user.getHp() + "  " + user.getMental());
				try {
					storyArea.setText(getAstory(sn).getStoryMain());
					if (sn == 23 && num13 == 0) {
//						itemconsole.getItem(13);
						for (int i = 0; i < 2; i++) {
							itemconsole.getItem(10);
							itemconsole.getItem(13);
							itemconsole.getItem(19);
						}
						hpmp();
						num13++;
					}
					if (snChoice == 3 || snChoice == 6) {
						snChoice++;
					}

					if (getAstory(sn).getCheck() != null && getAstory(sn).getCheck().equals("선택지")) {
						choiceText(snChoice);
						choicePnl.setVisible(true);
						sn--;
					}
					if (getAstory(sn).getCheck() != null && getAstory(sn).getCheck().equals("확률")) {
						int aHalf = 20 + rd.nextInt(2);
						storyArea.setText(getAchoice(aHalf).getStoryMain());
					}

					if (getAstory(sn).getStoryNum().equals("8-3")) {
						user.mentalRefresh("up", 1);
						hpmp();
					} // 챕터 2 넘어가기전에 멘탈 회복시켜주는거 넣어야됨

					sn++;

					if (snChoice == 8 && stop == true) {
						storyArea.setText(getAchoice(23).getStoryMain());
						stop = false;
						sn--;
					}

//					System.out.println(sn + "\t" + snChoice);
				} catch (IndexOutOfBoundsException ex) {
					storyArea.setText(
							"챕터 2로 이동~!!");
					System.out.println("선택지 " + path.toString());
					System.out.println("choiceId " + path_c.toString());
//					insertPath(path_c.toString());
				} finally {
					storyArea.setCaretPosition(0); // 클릭마다 커서를 젤 위로 올려 스크롤이 내려가있는 걸 방지
					userInven();
				}
			}
		});

		pnlBBG.setLayout(null);
		pnlBBG.add(pnlTxt);

		choicePnl = new JPanel();
		choicePnl.setBounds(12, 607, 800, 224);
		pnlTxt.add(choicePnl);
		choicePnl.setLayout(null);

		// 선택지 고르는 버튼 (최대 다섯개)
		btnChoice[0] = new JButton("선택지1");
		btnChoice[0].setBounds(10, 28, 782, 35);
		choicePnl.add(btnChoice[0]);

		btnChoice[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getAstory(sn).getStoryNum().equals("3-2")) {
					repeatChoice(0);
				} else {
					choiceText(snChoice);
					storyUpdate(1);
					if (snChoice == 0) {
						user.mentalRefresh("down", 1);
					} else if (snChoice == 1) {
						itemconsole.itemRemove(19);
						System.out.println(user.getInventory());
//					} else if (snChoice == 2) {
//						itemconsole.getItem(8); // 살골
//						user.getParty().add(4);
//						npcImg(lblNpcImg);
//						System.out.println(user.getInventory());
					} else if (snChoice == 5) {
						System.out.println("출혈에 걸렸다!" + user.getBleed());
						itemconsole.itemRemove(19);
						JOptionPane.showMessageDialog(StoryPnl.this, "출혈에 걸려서 붕대를 사용했다!!");
					} else if (snChoice == 8) {
						user.mentalRefresh("down", 1);
						System.out.println("강아지");
					}

					if (snChoice == 7) {
						stop = true;
					}
					snChoice++;
					path.add('1');
					userInven();
					hpmp();
				}
			}
		});

		btnChoice[1] = new JButton("선택지2");
		btnChoice[1].setBounds(10, 68, 782, 35);
		choicePnl.add(btnChoice[1]);

		btnChoice[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getAstory(sn).getStoryNum().equals("3-2")) {
					repeatChoice(1);

				} else {
					choiceText(snChoice);
					storyUpdate(2);
					if (snChoice == 2) {
//						itemconsole.getItem(7); // 법전 얻기
//						user.getParty().add(5);
//						npcImg(lblNpcImg);
//						System.out.println(user.getInventory());
					} else if (snChoice == 4) {
						user.HPRefresh("down", 10);
						System.out.println("배드엔딩 넣기");
					} else if (snChoice == 8) {
						user.mentalRefresh("up", 1); // 쿠키 얻기
						itemconsole.itemRemove(10);
						user.getParty().add(9);
						npcImg(lblNpcImg);
					}
					if (snChoice == 7) {
						storyArea.setText(getAchoice(24).getStoryMain());

					}
					snChoice++;
					path.add('2');
					storyArea.setCaretPosition(0); // (아니야! 뭔가 이상해...) 선택지 선택
					userInven();
					hpmp();

				}
			}
		});

		btnChoice[2] = new JButton("선택지3");
		btnChoice[2].setBounds(10, 108, 782, 35);
		choicePnl.add(btnChoice[2]);

		btnChoice[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getAstory(sn).getStoryNum().equals("3-2")) {
					repeatChoice(2);
				} else {
					choiceText(snChoice);
					storyUpdate(3);
					if (snChoice == 0) {
						user.mentalRefresh("down", 1);
					} else if (snChoice == 4) {
						itemconsole.getItem(9);
					} else if (snChoice == 5) {
						user.HPRefresh("down", 1);
					}
					snChoice++;
					path.add('3');
					userInven();
					hpmp();
				}
			}
		});

		btnChoice[3] = new JButton("선택지4");
		btnChoice[3].setBounds(10, 148, 782, 35);
		choicePnl.add(btnChoice[3]);

		btnChoice[3].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getAstory(sn).getStoryNum().equals("3-2")) {
					repeatChoice(3);
				} else {

					choiceText(snChoice);
					storyUpdate(4);
					if (snChoice == 0) {
						user.mentalRefresh("up", 1);
						itemconsole.getItem(16);
//					} else if (snChoice == 2) {
//						itemconsole.getItem(2); // 고모리 얻기
//						user.getParty().add(7);
//						npcImg(lblNpcImg);
//						System.out.println(user.getInventory());
					} else if (snChoice == 5) {
						user.mentalRefresh("down", 10);
						System.out.println("배드엔딩 넣기");
					}

					snChoice++;
					path.add('4');
					userInven();
					hpmp();
				}

			}
		});

		btnChoice[4] = new JButton("선택지5");
		btnChoice[4].setBounds(10, 188, 782, 35);
		choicePnl.add(btnChoice[4]);

		btnChoice[4].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getAstory(sn).getStoryNum().equals("3-2")) {
					repeatChoice(4);
				} else {
					choiceText(snChoice);
					storyUpdate(5);
					snChoice++;
					path.add('5');
					userInven();
					hpmp();
				}
			}
		});

		choicePnl.setVisible(false);
//			선택지 끝

		getContentPane().add(pnlBBG);

		JLabel hpwhat = new JLabel("체력");
		hpwhat.setBounds(870, 36, 60, 60);
		pnlBBG.add(hpwhat);

		JLabel mpwhat = new JLabel("멘탈");
		mpwhat.setBounds(870, 86, 60, 60);
		pnlBBG.add(mpwhat);

		JButton bulletinBtn = new JButton("후기게시판");
		bulletinBtn.setBounds(853, 10, 105, 30);
		bulletinBtn.setBackground(Color.gray);
		pnlBBG.add(bulletinBtn);
		
		bulletinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Bulletin(loguser).setVisible(true);
			}
		});
		
		JButton upjuck = new JButton("업적보기");
		upjuck.setBounds(963, 10, 105, 30);
		upjuck.setBackground(Color.gray);
		pnlBBG.add(upjuck);
		
		upjuck.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AchvPnl(loguser).setVisible(true);
			}
		});

		JButton logout = new JButton("로그아웃");
		logout.setBounds(1073, 10, 105, 30);
		logout.setBackground(Color.gray);
		pnlBBG.add(logout);
		
		
		JButton next = new JButton("다음챕터");
		next.setBounds(963, 780, 120, 40);
		next.setFont(new Font("맑은고딕", Font.BOLD, 20));
		pnlBBG.add(next);
		
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new StoryPnl2(user, loguser).setVisible(true); // 다음 프레임에 생성자에 유저를 받을수있게 만든후 실행
				StoryPnl.this.setVisible(false);
				
			}
		});

		JLabel backlbl = new JLabel();
		backlbl.setBounds(0, 0, 1200, 870);
		pnlBBG.add(backlbl);
		backlbl.setIcon(back);

		pnlBBG.setOpaque(false);
		choicePnl.setOpaque(false);
		pnl_userInven.setOpaque(false);
		pnlTxt.setOpaque(false);

		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new Login().setVisible(true);
				;
			}
		});

//        System.out.println(user.getParty().toString());
//        Connection conn = null;
//        try {
//        	conn = BusanUtil.getConnection();
//			npcdao.getNpcPath(conn, 5);
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} finally {
//			BusanUtil.closeConn(conn);
//		}

		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

	}

//	public static void main(String[] args) {
//		new StoryPnl(new BusanUser("magic22x", "1111")).setVisible(true);
//		;
//	}
}
