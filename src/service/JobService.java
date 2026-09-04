package service;

import db.DBConnection;
import model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobService {

    // INSERT a new job opportunity
    public boolean postJob(Job job) {
        String sql = "INSERT INTO job_opportunities (title, company, industry, location, description, posted_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, job.getTitle());
            ps.setString(2, job.getCompany());
            ps.setString(3, job.getIndustry());
            ps.setString(4, job.getLocation());
            ps.setString(5, job.getDescription());
            ps.setInt(6,    job.getPostedBy());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error posting job: " + e.getMessage());
            return false;
        }
    }

    // SELECT all jobs
    public List<Job> getAllJobs() {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT * FROM job_opportunities";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Job j = new Job();
                j.setId(rs.getInt("id"));
                j.setTitle(rs.getString("title"));
                j.setCompany(rs.getString("company"));
                j.setIndustry(rs.getString("industry"));
                j.setLocation(rs.getString("location"));
                j.setDescription(rs.getString("description"));
                j.setPostedBy(rs.getInt("posted_by"));
                list.add(j);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching jobs: " + e.getMessage());
        }
        return list;
    }

    // SELECT jobs matching a keyword in industry OR title
    public List<Job> getJobsByIndustry(String keyword) {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT * FROM job_opportunities WHERE industry LIKE ? OR title LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Job j = new Job();
                j.setId(rs.getInt("id"));
                j.setTitle(rs.getString("title"));
                j.setCompany(rs.getString("company"));
                j.setIndustry(rs.getString("industry"));
                j.setLocation(rs.getString("location"));
                j.setDescription(rs.getString("description"));
                j.setPostedBy(rs.getInt("posted_by"));
                list.add(j);
            }
        } catch (SQLException e) {
            System.err.println("Error searching jobs: " + e.getMessage());
        }
        return list;
    }
}
