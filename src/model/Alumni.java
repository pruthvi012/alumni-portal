package model;

public class Alumni {
    private int    id;
    private String name;
    private String email;
    private int    graduationYear;
    private String industry;
    private String skills;

    public Alumni() {}

    public Alumni(String name, String email, int graduationYear, String industry, String skills) {
        this.name           = name;
        this.email          = email;
        this.graduationYear = graduationYear;
        this.industry       = industry;
        this.skills         = skills;
    }

    // Getters and Setters
    public int    getId()                       { return id; }
    public void   setId(int id)                 { this.id = id; }
    public String getName()                     { return name; }
    public void   setName(String n)             { this.name = n; }
    public String getEmail()                    { return email; }
    public void   setEmail(String e)            { this.email = e; }
    public int    getGraduationYear()           { return graduationYear; }
    public void   setGraduationYear(int y)      { this.graduationYear = y; }
    public String getIndustry()                 { return industry; }
    public void   setIndustry(String i)         { this.industry = i; }
    public String getSkills()                   { return skills; }
    public void   setSkills(String s)           { this.skills = s; }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | %s | Batch: %d | Skills: %s",
            id, name, email, industry, graduationYear, skills);
    }
}
