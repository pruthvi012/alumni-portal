import db.DBConnection;
import model.Alumni;
import model.Job;
import model.Mentor;
import service.AlumniService;
import service.JobService;
import service.MentorService;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner       sc             = new Scanner(System.in);
    static AlumniService alumniService  = new AlumniService();
    static JobService    jobService     = new JobService();
    static MentorService mentorService  = new MentorService();

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("     ALUMNI NETWORKING PORTAL (JDBC Demo)    ");
        System.out.println("==============================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1  -> addAlumniProfile();
                case 2  -> viewAllProfiles();
                case 3  -> searchAlumniByIndustry();
                case 4  -> postJob();
                case 5  -> viewAllJobs();
                case 6  -> searchJobsByKeyword();
                case 7  -> addMentor();
                case 8  -> findMentorsByKeyword();
                case 9  -> viewAllMentors();
                case 0  -> {
                    DBConnection.closeConnection();
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        sc.close();
    }

    // ── Menu ─────────────────────────────────────────────
    static void printMenu() {
        System.out.println("\n========== MENU ==========");
        System.out.println(" 1. Add Alumni Profile");
        System.out.println(" 2. View All Alumni");
        System.out.println(" 3. Search Alumni by Industry");
        System.out.println("---------------------------");
        System.out.println(" 4. Post Job Opportunity");
        System.out.println(" 5. View All Jobs");
        System.out.println(" 6. Search Jobs by Keyword");
        System.out.println("---------------------------");
        System.out.println(" 7. Register as Mentor");
        System.out.println(" 8. Find Mentors by Keyword");
        System.out.println(" 9. View All Available Mentors");
        System.out.println("---------------------------");
        System.out.println(" 0. Exit");
        System.out.println("===========================");
    }

    // ── Alumni ──────────────────────────────────────────
    static void addAlumniProfile() {
        System.out.println("\n-- Add Alumni Profile --");
        String name     = readString("Name: ");
        String email    = readString("Email: ");
        int    year     = readInt("Graduation Year: ");
        String industry = readString("Industry (e.g. IT, Finance, Healthcare): ");
        String skills   = readString("Skills (comma-separated): ");

        Alumni a = new Alumni(name, email, year, industry, skills);
        boolean ok = alumniService.saveProfile(a);
        System.out.println(ok ? "✔ Profile saved successfully!" : "✘ Failed to save profile.");
    }

    static void viewAllProfiles() {
        List<Alumni> list = alumniService.getAllProfiles();
        System.out.println("\n-- All Alumni (" + list.size() + " record(s)) --");
        if (list.isEmpty()) System.out.println("  No alumni found.");
        list.forEach(System.out::println);
    }

    static void searchAlumniByIndustry() {
        String keyword = readString("\nEnter industry keyword: ");
        List<Alumni> list = alumniService.searchByIndustry(keyword);
        System.out.println("Found " + list.size() + " alumni matching \"" + keyword + "\":");
        if (list.isEmpty()) System.out.println("  No results.");
        list.forEach(System.out::println);
    }

    // ── Jobs ─────────────────────────────────────────────
    static void postJob() {
        System.out.println("\n-- Post Job Opportunity --");
        String title    = readString("Job Title: ");
        String company  = readString("Company: ");
        String industry = readString("Industry: ");
        String location = readString("Location: ");
        String desc     = readString("Description: ");
        int    alumniId = readInt("Your Alumni ID (posted_by): ");

        Job j = new Job(title, company, industry, location, desc, alumniId);
        boolean ok = jobService.postJob(j);
        System.out.println(ok ? "✔ Job posted successfully!" : "✘ Failed to post job.");
    }

    static void viewAllJobs() {
        List<Job> list = jobService.getAllJobs();
        System.out.println("\n-- All Jobs (" + list.size() + " record(s)) --");
        if (list.isEmpty()) System.out.println("  No jobs found.");
        list.forEach(System.out::println);
    }

    static void searchJobsByKeyword() {
        String keyword = readString("\nEnter keyword (industry or title): ");
        List<Job> list = jobService.getJobsByIndustry(keyword);
        System.out.println("Found " + list.size() + " job(s) matching \"" + keyword + "\":");
        if (list.isEmpty()) System.out.println("  No results.");
        list.forEach(System.out::println);
    }

    // ── Mentors ──────────────────────────────────────────
    static void addMentor() {
        System.out.println("\n-- Register as Mentor --");
        int    alumniId  = readInt("Your Alumni ID: ");
        String industry  = readString("Industry: ");
        String expertise = readString("Expertise keywords (e.g. Machine Learning, Cloud): ");

        Mentor m = new Mentor(alumniId, industry, expertise, true);
        boolean ok = mentorService.addMentor(m);
        System.out.println(ok ? "✔ Registered as mentor!" : "✘ Registration failed.");
    }

    static void findMentorsByKeyword() {
        String keyword = readString("\nEnter keyword (industry or expertise): ");
        List<Mentor> list = mentorService.findMentorsByKeyword(keyword);
        System.out.println("Found " + list.size() + " mentor(s) matching \"" + keyword + "\":");
        if (list.isEmpty()) System.out.println("  No mentors found.");
        list.forEach(System.out::println);
    }

    static void viewAllMentors() {
        List<Mentor> list = mentorService.getAllAvailableMentors();
        System.out.println("\n-- Available Mentors (" + list.size() + ") --");
        if (list.isEmpty()) System.out.println("  No mentors registered yet.");
        list.forEach(System.out::println);
    }

    // ── Input Helpers ────────────────────────────────────
    static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Defaulting to 0.");
            return 0;
        }
    }
}
