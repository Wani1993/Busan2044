package main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.green.BusanUtil;

public class NpcDao {
	public int getNpcSkill(Connection conn, int npc_id) throws SQLException {
		String query = "select * from npc_table where npc_id = ?"; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, npc_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt("npc_skill");
			}
			return result;
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
		}
	}
	public String getNpcPath(Connection conn, int npc_id) throws SQLException {
		String query = "select * from npc_table where npc_id = ?"; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, npc_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("npc_img");
			}
			return result;
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
		}
	}
	public String getNpcMemo(Connection conn, int npc_id) throws SQLException {
		String query = "select * from npc_table where npc_id = ?"; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, npc_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("npc_memo");
			}
			return result;
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
		}
	}
	public String getNpcName(Connection conn, int npc_id) throws SQLException {
		String query = "select * from npc_table where npc_id = ?"; 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, npc_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("npc_name");
			}
			return result;
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
		}
	}
}
