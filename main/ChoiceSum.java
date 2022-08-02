package main;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoiceSum {
	private int choiceId, storyId;
	private String storyNum, choiceMain, storyMain;
	private String storyCheck, choiceCheck;

	private String choiceNum; // chapter2에서 사용

	public ChoiceSum(int choiceId, String storyNum, String choiceMain, String storyMain, String storyCheck,
			String choiceCheck) {
		super();
		this.choiceId = choiceId;
		this.storyNum = storyNum;
		this.choiceMain = choiceMain;
		this.storyMain = storyMain;
		this.storyCheck = storyCheck;
		this.choiceCheck = choiceCheck;
	}

	public ChoiceSum(int storyId, String storyNum, String choiceMain, String storyMain) {
		super();
		this.storyId = storyId;
		this.storyNum = storyNum;
		this.choiceMain = choiceMain;
		this.storyMain = storyMain;
	}

	public ChoiceSum(int choiceId, String choiceNum, String choiceMain) {
		this.choiceId = choiceId;
		this.choiceNum = choiceNum;
		this.choiceMain = choiceMain;
	}

	public int getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(int choiceId) {
		this.choiceId = choiceId;
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

	public String getChoiceMain() {
		return choiceMain;
	}

	public void setChoiceMain(String choiceMain) {
		this.choiceMain = choiceMain;
	}

	public String getStoryMain() {
		return storyMain;
	}

	public void setStoryMain(String storyMain) {
		this.storyMain = storyMain;
	}

	public String getStoryCheck() {
		return storyCheck;
	}

	public void setStoryCheck(String storyCheck) {
		this.storyCheck = storyCheck;
	}

	public String getChoiceCheck() {
		return choiceCheck;
	}

	public void setChoiceCheck(String choiceCheck) {
		this.choiceCheck = choiceCheck;
	}

	public String getChoiceNum() {
		return choiceNum;
	}

	public void setChoiceNum(String choiceNum) {
		this.choiceNum = choiceNum;
	}

	@Override
	public String toString() {
		return "ChoiceSum [choiceId=" + choiceId + ", storyId=" + storyId + ", storyNum=" + storyNum + ", choiceMain="
				+ choiceMain + ", storyMain=" + storyMain + ", storyCheck=" + storyCheck + ", choiceCheck="
				+ choiceCheck + ", choiceNum=" + choiceNum + "]";
	}

	public static void main(String[] args) {
		Random rd = new Random();
		List<String> list = new ArrayList<>();
	}
}
