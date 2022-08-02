package main;


public class Story {

	private int storyId;
	private String storyNum, storyMain;
	private int storyTime;
	private String check, check2, check3;

//	public Story(int storyId, String storyNum, String storyMain, String storyTime, String check) {
//		super();
//		this.storyId = storyId;
//		this.storyNum = storyNum;
//		this.storyMain = storyMain;
//		this.storyTime = storyTime;
//		this.check = check;
//	}

	public Story(int storyId, String storyNum, String storyMain, int storyTime, String check) {
		super();
		this.storyId = storyId;
		this.storyNum = storyNum;
		this.storyMain = storyMain;
		this.storyTime = storyTime;
		this.check = check;
	}

	public int getStoryId() {
		return storyId;
	}

	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}

	public String getStoryNum() {
		return storyNum;
	}

	public void setStoryNum(String storyNum) {
		this.storyNum = storyNum;
	}

	public String getStoryMain() {
		return storyMain;
	}

	public void setStoryMain(String storyMain) {
		this.storyMain = storyMain;
	}

	public int getStoryTime() {
		return storyTime;
	}

	public void setStoryTime(int storyTime) {
		this.storyTime = storyTime;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getCheck2() {
		return check2;
	}

	public void setCheck2(String check2) {
		this.check2 = check2;
	}

	public String getCheck3() {
		return check3;
	}

	public void setCheck3(String check3) {
		this.check3 = check3;
	}

	@Override
	public String toString() {
		return "Story [storyId=" + storyId + ", storyNum=" + storyNum + ", storyMain=" + storyMain + ", storyTime="
				+ storyTime + ", check=" + check + ", check2=" + check2 + ", check3=" + check3 + "]";
	}

}
