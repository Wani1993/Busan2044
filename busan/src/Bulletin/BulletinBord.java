package Bulletin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.greenart.dbutil.BusanUtil;

public class BulletinBord {
	public String BulletInputId(int userNum) {
		String a = null;
		try (Connection conn = BusanUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Busan.login_info");
				ResultSet rs = pstmt.executeQuery();) {

			while (rs.next()) {
				if(userNum == rs.getInt("userNum")) {
					a = rs.getString("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return a;
	}
	
	public String BulletInputName(int userNum) {
		String a = null;
		try (Connection conn = BusanUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Busan.login_info");
				ResultSet rs = pstmt.executeQuery();) {

			while (rs.next()) {
				if(userNum == rs.getInt("userNum")) {
					a = rs.getString("name");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return a;
	}

	public int BulletCreate(String userId, String username, String text, int star) {
		try (Connection conn = BusanUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO Busan.bulletinBord (userId, username, text, star) values (?, ?, ?, ?)");) {

			pstmt.setString(1, userId);
			pstmt.setString(2, username);
			pstmt.setString(3, text);
			pstmt.setInt(4, star);

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) {
		BulletinBord bb = new BulletinBord();

		int result = bb.BulletCreate(bb.BulletInputId(14), bb.BulletInputName(14), "뒤집어놓으셨다~~!!!!!", 5);
		System.out.println(result);
	}
}