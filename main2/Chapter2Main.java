package main2;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chapter2Main {

	// 얘는 챕터2를 통채로 가져오는 애

	// dao.read() 로 사용하면 된다.

	// 콘솔창에 출력을 목적으로

	// 일단 확률을 만든다.
	// 랜덤 숫자를 뽑는 메소드
	// 계속 쓸거라서 만들었다.
	public int Random() {

		Random rnd = new Random();
		int random = rnd.nextInt(100) + 1;
		return random;
	}

	// 낮인 애들만 가져오는 배열을 만들자
	// 낮인 챕터 2 스토리들이 통채로 배열로 받은 메소드
	public List<Chapter2Story> storyTimeIsDay() {

		Chapter2StoryDao dao = new Chapter2StoryDaoImpl();
		List<Chapter2Story> dayArr = new ArrayList<>();

		try {
			List<Chapter2Story> daoArr = dao.read();

			for (int i = 0; i < daoArr.size(); i++) {

				if (daoArr.get(i).getStoryTime().equals("12")) {

					dayArr.add(daoArr.get(i));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dayArr;
	}

	// 낮인 챕터 2 스토리들이 통채로 들어온 메소드의 아이디만 모아놓은 배열
	public List<Integer> storyTimeIsDayOfId() {

		List<Chapter2Story> list = new ArrayList<>(storyTimeIsDay());
		List<Integer> storyTimeisDayOfId = new ArrayList<>();

		for (Chapter2Story c2 : list) {
			storyTimeisDayOfId.add(c2.getStoryId());
		}
		return storyTimeisDayOfId;

	}

	// 챕터2 중 밤인 애들을 통채로 모아놓은 배열
	public List<Chapter2Story> storyTimeIsNight() {

		Chapter2StoryDao dao = new Chapter2StoryDaoImpl();
		List<Chapter2Story> nightArr = new ArrayList<>();

		try {
			List<Chapter2Story> daoArr = dao.read();

			for (int i = 0; i < daoArr.size(); i++) {

				if (daoArr.get(i).getStoryTime().equals("9")) {

					nightArr.add(daoArr.get(i));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nightArr;
	}
	
	// 챕터 2중 밤인 애들의 아이디만 모아놓은 배열
	public List<Integer> storyTimeIsNightOfId() {

		List<Chapter2Story> list = new ArrayList<>(storyTimeIsNight());
		List<Integer> storyTimeisNightOfId = new ArrayList<>();

		for (Chapter2Story c2 : list) {
			storyTimeisNightOfId.add(c2.getStoryId());
		}
		return storyTimeisNightOfId;
	}
	
	
	// 챕터 2중 storyTime(문자열)만  받는 애들
	public List<String> storyTime() {
		Chapter2StoryDao dao = new Chapter2StoryDaoImpl();
		List<Chapter2Story> list;
		
		try {
			list = new ArrayList<>(dao.read());
			List<String> time = new ArrayList<>();
			
			for (Chapter2Story c2 : list) {
				time.add(c2.getStoryTime());
			}
			
			return time;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		}
		
	// 진행이 되도록 하는 메소드-> 낮
	public void dayBattle() {
		
//		if (dao.read())
//		if(Random() < 41 && storyTime() ) {
//			
//			// 전투가 들어간다.
//		}
	}

	// 아무래도 한 줄씩 뽑는게 나을 듯...?? 아닌
	public static void main(String[] args) {
		Chapter2Main abc = new Chapter2Main();

//		System.out.println(abc.storyTimeIsDay());
		List<Chapter2Story> storyDay = abc.storyTimeIsDay();
		System.out.println(abc.storyTimeIsDayOfId());
		System.out.println(abc.storyTimeIsNightOfId());

//		abc.storyTimeIsDay().getStoryId();
	}

}
