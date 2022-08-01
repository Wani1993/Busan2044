package main2;
import java.sql.SQLException;
import java.util.List;



public interface Chapter2ChoiceDao {
		List<Chapter2Choice> read() throws SQLException;
		Chapter2Choice read(int storyId) throws SQLException;
}
