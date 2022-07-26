package savehere;

import java.sql.SQLException;
import java.util.List;

public interface SaveHereDao {
	
	   int create(String userId, String storyPath) throws SQLException;
	   List<SaveHere> read() throws SQLException;
	   SaveHere read(int num) throws SQLException;
	   int update(int num, String userId, String stroyPath) throws SQLException;
	   int delete(int num) throws SQLException;

}
