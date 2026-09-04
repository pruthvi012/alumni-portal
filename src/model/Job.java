package model;

public class Job {
    private int    id;
    private String title;
    private String company;
    private String industry;
    private String location;
    private String description;
    private int    postedBy;

    public Job() {}

    public Job(String title, String company, String industry,
               String location, String description, int postedBy) {
        this.title       = title;
        this.company     = company;
        this.industry    = industry;
        this.location    = location;
        this.description = description;
        this.postedBy    = postedBy;
    }

    // Getters and Setters
    public int    getId()                    { return id; }
    public void   setId(int id)              { this.id = id; }
    public String getTitle()                 { return title; }
    public void   setTitle(String t)         { this.title = t; }
    public String getCompany()               { return company; }
    public void   setCompany(String c)       { this.company = c; }
    public String getIndustry()              { return industry; }
    public void   setIndustry(String i)      { this.industry = i; }
    public String getLocation()              { return location; }
    public void   setLocation(String l)      { this.location = l; }
    public String getDescription()           { return description; }
    public void   setDescription(String d)   { this.description = d; }
    public int    getPostedBy()              { return postedBy; }
    public void   setPostedBy(int pb)        { this.postedBy = pb; }

    @Override
    public String toString() {
        return String.format("[%d] %s @ %s | %s | %s\n    %s",
            id, title, company, industry, location, description);
    }
}
