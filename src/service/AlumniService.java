package service;

import db.DBConnection;
import model.Alumni;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumniService {

    // INSERT a new alumni profile
    public boolean saveProfile(Alumni alumni) {
        String sql = "INSERT INTO alumni_profiles (name, email, graduation_year, industry, skills) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, alumni.getName());
            ps.setString(2, alumni.getEmail());
            ps.setInt(3,    alumni.getGraduationYear());
            ps.setString(4, alumni.getIndustry());
            ps.setString(5, alumni.getSkills());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Error saving profile: " + e.getMessage());
            return false;
        }
    }

    // SELECT all profiles
    public List<Alumni> getAllProfiles() {
        List<Alumni> list = new ArrayList<>();
        String sql = "SELECT * FROM alumni_profiles";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Alumni a = new Alumni();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setEmail(rs.getString("email"));
                a.setGraduationYear(rs.getInt("graduation_year"));
                a.setIndustry(rs.getString("industry"));
                a.setSkills(rs.getString("skills"));
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching profiles: " + e.getMessage());
        }
        return list;
    }

    // SELECT by industry keyword (LIKE query)
    public List<Alumni> searchByIndustry(String keyword) {
        List<Alumni> list = new ArrayList<>();
        String sql = "SELECT * FROM alumni_profiles WHERE industry LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alumni a = new Alumni();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setEmail(rs.getString("email"));
                a.setGraduationYear(rs.getInt("graduation_year"));
                a.setIndustry(rs.getString("industry"));
                a.setSkills(rs.getString("skills"));
                list.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error searching profiles: " + e.getMessage());
        }
        return list;
    }
}
