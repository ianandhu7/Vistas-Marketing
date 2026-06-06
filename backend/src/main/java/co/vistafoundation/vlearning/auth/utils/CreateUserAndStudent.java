package co.vistafoundation.vlearning.auth.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.vistafoundation.vlearning.auth.dto.BulkSignupDTO;
import co.vistafoundation.vlearning.common.response.Document;
import co.vistafoundation.vlearning.user.util.SecondaryLanguageMapper;

@Repository
public class CreateUserAndStudent {
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private SecondaryLanguageMapper languageMapper;

    @Transactional
    public Document<List<Long>> createUsersAndStudents(List<BulkSignupDTO> dtos, String createdBy) {
        Document<List<Long>> result = new Document<>();
        List<Long> userSurIds = new ArrayList<>();

        String insertUserSql = "INSERT INTO vl_user (created_at, updated_at, class_standard, email, first_name, last_name, mobile_number, password, registered_as, secondary_language, user_profile_pic, username, active_flag, max_attempts, registered_source) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, 0,?)";

        try {
            jdbcTemplate.batchUpdate(insertUserSql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    BulkSignupDTO dto = dtos.get(i);
                    ps.setTimestamp(1, java.sql.Timestamp.from(Instant.now()));
                    ps.setTimestamp(2, java.sql.Timestamp.from(Instant.now()));
                    ps.setString(3, dto.getClassStandard());
                    ps.setString(4, dto.getEmail());
                    ps.setString(5, dto.getName());
                    ps.setString(6, ".");
                    ps.setString(7, dto.getMobileNumber());
                    ps.setString(8, passwordEncoder.encode(dto.getPassword()));
                    ps.setString(9, "Student");
                    ps.setString(10, languageMapper.getCorrespondingLanguages(dto.getSecondaryLanguage()));
                    ps.setString(11, null);
                    ps.setString(12, dto.getMobileNumber());
                    ps.setString(13, "Bulk Signup");

                }

                @Override
                public int getBatchSize() {
                    return dtos.size();
                }
            });

            for (BulkSignupDTO dto : dtos) {
                Long userId = jdbcTemplate.queryForObject("SELECT user_sur_id FROM vl_user WHERE mobile_number = ?", 
                                                            Long.class, dto.getMobileNumber());
                userSurIds.add(userId);
            }
            String insertStudentSql = "INSERT INTO student (created_at, updated_at, created_by, updated_by, gender, idclass_standard, idlanguage, idstate, idstudent_medium, idsyllabus, idparent, idvl_user, is_profile_edited, school_name, remarks) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, null, ?, 0, ?, ?)";
            String userRoleMapSql = "INSERT INTO user_role_map (vl_user_sur_id, vl_role_sur_id) VALUES (?, ?)";
            for (int i = 0; i < userSurIds.size(); i++) {
                Long userId = userSurIds.get(i);
                BulkSignupDTO dto = dtos.get(i);

                // Update the insertStudentSql query to use the retrieved user ID
                jdbcTemplate.update(insertStudentSql, ps -> {
                    ps.setTimestamp(1, java.sql.Timestamp.from(Instant.now()));
                    ps.setTimestamp(2, java.sql.Timestamp.from(Instant.now()));
                    ps.setString(3, createdBy);
                    ps.setString(4, createdBy);
                    ps.setString(5, null);
                    ps.setString(6, dto.getClassStandard());
                    ps.setString(7, dto.getSecondaryLanguage());
                    ps.setString(8, dto.getState());
                    ps.setString(9, dto.getMedium());
                    ps.setString(10, dto.getSyllabus());
                    ps.setLong(11, userId); // Use the retrieved user ID
                    ps.setString(12, dto.getSchoolName());
                    ps.setString(13, dto.getRemarks());
                });
                jdbcTemplate.update(userRoleMapSql, ps -> {
                    ps.setLong(1, userId);
                    ps.setLong(2, 1);
                    });
            }

            System.out.println(userSurIds);

            result.setData(userSurIds);
            result.setMessage("Users and students created successfully.");
            result.setStatusCode(200);
        } catch (Exception e) {
            e.printStackTrace();
            result.setData(null);
            result.setMessage("Failed to create users and students: " + e.getMessage());
            result.setStatusCode(500);
        }

        return result;
    }

}
