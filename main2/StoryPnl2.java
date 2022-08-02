package main2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;

import bulletin.Bulletin;
import kr.co.green.BusanUtil;
import loginUser.BusanUser;
import loginUser.Login;
import main.ChoiceSum;
import main.Item;
import main.ItemConsole;
import main.ItemDao;
import main.NpcDao;
import main.Story;
import main.StoryPnl;
import main.UserInfo;
import statistics.AchvPnl;

public class StoryPnl2 extends JFrame {
	private BusanUser loguser;
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
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~민트초코 개싫어
	private JButton dateBtn;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~민트초코 개싫어
	
	private JLabel[] hplbl = { new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel() };
	private JLabel[] mplbl = { new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel() };
//	private int heart = user.getHp();
//	private int mental = user.getMental();

	public static Font mainFont = new Font("한컴산뜻돋움", Font.BOLD, 20);

	private List<JLabel> userInven = new ArrayList<>();
	private List<JLabel> userInvenCount = new ArrayList<>();
	
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~민트초코 개싫어
	public JButton getDateBtn() {
		return dateBtn;
	}
	public List<JLabel> getUserInven() {
		return userInven;
	}
	public List<JLabel> getUserInvenCount() {
		return userInvenCount;
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~민트초코 개싫어
	public ItemConsole getItemConsole() {
		return this.itemconsole;
	}
	
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



	public void npcImg(List<JLabel> lblNpcImg) {
		List<Integer> npcParty = user.getParty();
		String path = null;
		try (Connection conn = BusanUtil.getConnection()) {
			for (int i = 0; i< npcParty.size(); i++) {
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
		int npc_id = itemconsole.lbl_searchID(lbl);	// 패널에서 id값 가져오기 
		String color = "";
		if (npc_id != -1) {	// npcId가 빈 값이 아닐때
			
			if (npc_id == 2) {
				color = "이름 : <font face=\"sansserif\" color=\"purple\">";
			} else {
				color = "이름 : <font face=\"sansserif\" color=\"black\">";
			}
			String info_Npc = "<html>" + color + IdToNpcInfo(npc_id, "name") + "</font><br>" 
							+ "설명 : " + IdToNpcInfo(npc_id, "memo") + "</html>";	
			lbl.setToolTipText(info_Npc);
		}
	}
	

	public StoryPnl2(UserInfo user , BusanUser loguser) {
		super("부산2044");
		this.user = user;
		this.loguser = loguser;
		
		

		System.out.println(user.getInventory().toString());
		// 인벤토리 예시
		System.out.println(user.getParty());
	
		
		// 제일 큰 패널
		pnlBBG = new JPanel();

		// 텍스트 나오는 부분
		JPanel pnlTxt = new Chapter2(user, StoryPnl2.this);
		pnlTxt.setBounds(12, 10, 824, 851);
		pnlTxt.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlTxt.setPreferredSize(new Dimension(800, 700));
		pnlTxt.setLayout(null);
		
//		JPanel pnlTxt2 = new Chapter2_ScreenDESU(user, StoryPnl2.this);
//		pnlTxt.setBounds(12, 10, 824, 851);
//		pnlTxt.setBorder(new LineBorder(new Color(0, 0, 0)));
//		pnlTxt.setPreferredSize(new Dimension(800, 700));
//		pnlTxt.setLayout(null);
				
		
		JPanel pnl_userInven = new JPanel();
		pnl_userInven.setBounds(865, 140, 304, 450);
		getContentPane().add(pnl_userInven);
		pnl_userInven.setLayout(null);
		
		
//				JLabel lblNewLabel = new JLabel("0");
//				lblNewLabel.setBounds(50, 64, 26, 26);
//				pnl_userInven.add(lblNewLabel);

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
//		user.setInventory(
//				new ArrayList<Item>(Arrays.asList(new Item(3, 9999, 1), new Item(19, 0, 2), new Item(10, 0, 3))));
//		userInven(); // 이 메소드는 항상 써줘야 inventory List<Item>이 이미ㅈ화 된다

//		ToolTipManager m = ToolTipManager.sharedInstance();
//		m.setInitialDelay(0);


		
		
		
		
//		List<Integer> party = new ArrayList<>(Arrays.asList(2));
//	    user.setParty(party);
		
		
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
		ToolTipManager m = ToolTipManager.sharedInstance();
		m.setInitialDelay(0);
		
		int bound = 920;
		int bound2 = 920;
		for(int i = 0; i < 5; i++) {
			hplbl[i].setBounds(bound, 36, 60, 60);
			pnlBBG.add(hplbl[i]);
			mplbl[i].setBounds(bound2, 86, 60, 60);
			pnlBBG.add(mplbl[i]);
			bound += 50;
			bound2 += 50;
		}
		hpmp();
//		for (int i = 0; i < user.getInventory().size(); i++) {
//			user.setInventory(new ArrayList<Item>(Arrays.asList(user.getInventory().get(i))));			
//		}
	
// --------------------------------------------



		pnlBBG.setLayout(null);
		pnlBBG.add(pnlTxt);
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
		
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~민트초코 개싫어
		dateBtn = new JButton("1일차");
		dateBtn.setBounds(960, 780, 105, 27);
		pnlBBG.add(dateBtn);
		dateBtn.setEnabled(false);
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~민트초코 개싫어

		JLabel backlbl = new JLabel();
		backlbl.setBounds(0, 0, 1200, 870);
		pnlBBG.add(backlbl);
		backlbl.setIcon(back);
		
		pnlBBG.setOpaque(false);
		pnl_userInven.setOpaque(false);
		pnlTxt.setOpaque(false);
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new Login().setVisible(true);;
			}
		});
		
		userInven();
		npcImg(lblNpcImg);
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

	}
	
//	public static void main(String[] args) {
//		new StoryPnl2(new BusanUser("magic22x", "1111")).setVisible(true);;
//	}
}
