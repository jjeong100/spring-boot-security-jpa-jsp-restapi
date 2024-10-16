package org.rb.sbsec.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.rb.sbsec.model.FileInfo;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileInfoBatchRepository {
	
	private final JdbcTemplate jdbcTemplate;

    public FileInfoBatchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
	public void batchInsert(List<FileInfo> list) {
		jdbcTemplate.batchUpdate(
				"INSERT INTO FILE_INFO (FILE_SIZE,UPDATE_DT,ACTION_YN,DEL_YN,DIRECTORY,FILE_EXT,FILE_NAME,FILE_TYPE) "
				+ "VALUES(?,?,?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {
					@Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setLong(1, list.get(i).getFileSize());
						ps.setTimestamp(2, null);
						ps.setString(3, list.get(i).getActionYn());
						ps.setString(4, list.get(i).getDelYn());
						ps.setString(5, list.get(i).getDirectory());
						ps.setString(6, list.get(i).getFileExt());
						ps.setString(7, list.get(i).getFileName());
						ps.setString(8, list.get(i).getFileType());
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
		});
	}

}
