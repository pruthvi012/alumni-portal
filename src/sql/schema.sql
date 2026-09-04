-- ============================================================
--  Alumni Networking Portal — Database Schema
--  Run this ONCE in MySQL before compiling the Java project
-- ============================================================

CREATE DATABASE IF NOT EXISTS alumni_portal;
USE alumni_portal;

-- Table 1: Alumni Profiles
CREATE TABLE IF NOT EXISTS alumni_profiles (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100)  NOT NULL,
    email            VARCHAR(100)  UNIQUE NOT NULL,
    graduation_year  INT,
    industry         VARCHAR(100),
    skills           TEXT
);

-- Table 2: Job Opportunities (posted by alumni)
CREATE TABLE IF NOT EXISTS job_opportunities (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(150) NOT NULL,
    company      VARCHAR(100) NOT NULL,
    industry     VARCHAR(100),
    location     VARCHAR(100),
    description  TEXT,
    posted_by    INT,
    FOREIGN KEY (posted_by) REFERENCES alumni_profiles(id)
);

-- Table 3: Mentors (subset of alumni who opted in)
CREATE TABLE IF NOT EXISTS mentors (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    alumni_id   INT NOT NULL,
    industry    VARCHAR(100),
    expertise   TEXT,
    available   BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (alumni_id) REFERENCES alumni_profiles(id)
);

-- ============================================================
--  Optional: seed data for demo / testing
-- ============================================================

INSERT INTO alumni_profiles (name, email, graduation_year, industry, skills) VALUES
('Priya Sharma',   'priya@example.com',   2020, 'Information Technology', 'Java, Spring Boot, MySQL'),
('Arjun Mehta',    'arjun@example.com',   2019, 'Finance',                'Python, Risk Analysis, Excel'),
('Sneha Rao',      'sneha@example.com',   2021, 'Healthcare',             'Data Analysis, R, SPSS'),
('Karthik Nair',   'karthik@example.com', 2018, 'Information Technology', 'Cloud, AWS, DevOps'),
('Divya Pillai',   'divya@example.com',   2022, 'Marketing',              'SEO, Google Ads, Content');

INSERT INTO job_opportunities (title, company, industry, location, description, posted_by) VALUES
('Java Backend Developer', 'TechCorp Pvt Ltd',   'Information Technology', 'Bangalore', 'Build REST APIs using Spring Boot and MySQL.',           1),
('Financial Analyst',      'HDFC Securities',    'Finance',                'Mumbai',    'Analyse equity markets and prepare investment reports.', 2),
('Cloud Engineer',         'Infosys',            'Information Technology', 'Hyderabad', 'Manage AWS infrastructure and CI/CD pipelines.',          4);

INSERT INTO mentors (alumni_id, industry, expertise, available) VALUES
(1, 'Information Technology', 'Java, JDBC, Spring Boot, Microservices', TRUE),
(4, 'Information Technology', 'Cloud Computing, AWS, DevOps, Docker',   TRUE),
(2, 'Finance',                'Investment Banking, Python, Risk Mgmt',  TRUE),
(3, 'Healthcare',             'Data Analysis, Medical Informatics',     TRUE);
