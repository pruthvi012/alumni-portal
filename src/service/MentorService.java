package service;

import db.DBConnection;
import model.Mentor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorService {

    // INSERT a new mentor record (linked to an existing alumni)
    public boolean addMentor(Mentor mentor) {
        String sql = "INSERT INTO mentors (alumni_id, industry, expertise, available) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,     mentor.getAlumniId());
            ps.setString(2,  mentor.getIndustry());
            ps.setString(3,  mentor.getExpertise());
            ps.setBoolean(4, mentor.isAvailable());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding mentor: " + e.getMessage());
            return false;
        }
    }

    // SELECT mentors by keyword — JOIN query across mentors + alumni_profiles
    public List<Mentor> findMentorsByKeyword(String keyword) {
        List<Mentor> list = new ArrayList<>();

        String sql = "SELECT m.id, m.alumni_id, a.name AS alumni_name, " +
                     "       m.industry, m.expertise, m.available " +
                     "FROM mentors m " +
                     "JOIN alumni_profiles a ON m.alumni_id = a.id " +
                     "WHERE (m.industry LIKE ? OR m.expertise LIKE ?) " +
                     "AND m.available = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mentor m = new Mentor();
                m.setId(rs.getInt("id"));
                m.setAlumniId(rs.getInt("alumni_id"));
                m.setAlumniName(rs.getString("alumni_name")); // from JOIN
                m.setIndustry(rs.getString("industry"));
                m.setExpertise(rs.getString("expertise"));
                m.setAvailable(rs.getBoolean("available"));
                list.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error searching mentors: " + e.getMessage());
        }
        return list;
    }

    // SELECT all available mentors — JOIN query
    public List<Mentor> getAllAvailableMentors() {
        List<Mentor> list = new ArrayList<>();
        String sql = "SELECT m.id, m.alumni_id, a.name AS alumni_name, " +
                     "       m.industry, m.expertise, m.available " +
                     "FROM mentors m " +
                     "JOIN alumni_profiles a ON m.alumni_id = a.id " +
                     "WHERE m.available = TRUE";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Mentor m = new Mentor();
                m.setId(rs.getInt("id"));
                m.setAlumniId(rs.getInt("alumni_id"));
                m.setAlumniName(rs.getString("alumni_name"));
                m.setIndustry(rs.getString("industry"));
                m.setExpertise(rs.getString("expertise"));
                m.setAvailable(rs.getBoolean("available"));
                list.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching mentors: " + e.getMessage());
        }
        return list;
    }
}
