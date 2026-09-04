package model;

public class Mentor {
    private int     id;
    private int     alumniId;
    private String  alumniName;   // populated via JOIN from alumni_profiles
    private String  industry;
    private String  expertise;
    private boolean available;

    public Mentor() {}

    public Mentor(int alumniId, String industry, String expertise, boolean available) {
        this.alumniId  = alumniId;
        this.industry  = industry;
        this.expertise = expertise;
        this.available = available;
    }

    // Getters and Setters
    public int    getId()                   { return id; }
    public void   setId(int id)             { this.id = id; }
    public int    getAlumniId()             { return alumniId; }
    public void   setAlumniId(int aid)      { this.alumniId = aid; }
    public String getAlumniName()           { return alumniName; }
    public void   setAlumniName(String n)   { this.alumniName = n; }
    public String getIndustry()             { return industry; }
    public void   setIndustry(String i)     { this.industry = i; }
    public String getExpertise()            { return expertise; }
    public void   setExpertise(String e)    { this.expertise = e; }
    public boolean isAvailable()            { return available; }
    public void   setAvailable(boolean a)   { this.available = a; }

    @Override
    public String toString() {
        return String.format("[%d] %s | Industry: %s | Expertise: %s | Available: %s",
            id, alumniName, industry, expertise, available ? "Yes" : "No");
    }
}
