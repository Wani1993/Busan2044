package main2;
public class Chapter2Story {
	
	private int storyId;
	private String storyNum;
	private String storyMain;
	private String storyTime;
	
	public Chapter2Story(int storyId, String storyNum, String storyMain, String storyTime) {
		super();
		this.storyId = storyId;
		this.storyNum = storyNum;
		this.storyMain = storyMain;
		this.storyTime = storyTime;
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

	public String getStoryTime() {
		return storyTime;
	}

	public void setStoryTime(String storyTime) {
		this.storyTime = storyTime;
	}

	@Override
	public String toString() {
		return "Chapter2Story [storyId=" + storyId + ", storyNum=" + storyNum + ", storyMain=" + storyMain + ", storyTime="
				+ storyTime + "]";
	}


	public int getStoryId() {
		return storyId;
	}


	public void setStoryId(int storyId) {
		this.storyId = storyId;
	}

	
	
	
}
