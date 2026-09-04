// State Management
const initialState = {
    alumni: [
        { id: 1, name: "Priya Sharma", email: "priya@example.com", year: 2020, industry: "Information Technology", skills: "Java, Spring Boot, MySQL", bio: "Senior Developer at TechCorp" },
        { id: 2, name: "Arjun Mehta", email: "arjun@example.com", year: 2019, industry: "Finance", skills: "Python, Risk Analysis, Excel", bio: "Financial Analyst with 5 years exp" },
        { id: 3, name: "Sneha Rao", email: "sneha@example.com", year: 2021, industry: "Healthcare", skills: "Data Analysis, R", bio: "Passionate about health-tech" }
    ],
    jobs: [
        { id: 1, title: "Java Developer", company: "TechCorp", industry: "IT", location: "Bangalore", desc: "Building scalable microservices.", posted: "2 days ago" },
        { id: 2, title: "Financial Analyst", company: "HDFC", industry: "Finance", location: "Mumbai", desc: "Equity analysis and reporting.", posted: "1 week ago" }
    ],
    mentors: [
        { id: 1, name: "Karthik Nair", industry: "IT", expertise: "Cloud, AWS, DevOps", students: 12 },
        { id: 2, name: "Divya Pillai", industry: "Marketing", expertise: "SEO, Google Ads", students: 8 }
    ]
};

let appData = {
    alumni: JSON.parse(localStorage.getItem('ap_alumni')) || initialState.alumni,
    jobs: JSON.parse(localStorage.getItem('ap_jobs')) || initialState.jobs,
    mentors: JSON.parse(localStorage.getItem('ap_mentors')) || initialState.mentors
};

let currentSection = 'dashboard';

// Navigation
function showSection(section) {
    currentSection = section;
    const grid = document.getElementById('content-grid');
    const title = document.getElementById('section-title');
    const subtitle = document.getElementById('section-subtitle');
    const statsContainer = document.getElementById('stats-container');
    
    // Update Nav UI
    document.querySelectorAll('nav li').forEach(li => li.classList.remove('active'));
    const navItem = document.getElementById(`nav-${section}`);
    if (navItem) navItem.classList.add('active');

    // Clear grid
    grid.innerHTML = '';
    grid.classList.remove('fade-in');
    void grid.offsetWidth; 
    grid.classList.add('fade-in');

    updateStats();

    if (statsContainer) statsContainer.style.display = (section === 'dashboard' ? 'grid' : 'none');

    if (section === 'dashboard') {
        title.innerText = 'Network Dashboard';
        subtitle.innerText = "Welcome back! Here's what's happening in your network.";
        renderDashboard(grid);
    } else if (section === 'alumni') {
        title.innerText = 'Alumni Profiles';
        subtitle.innerText = 'Search and connect with fellow graduates.';
        renderAlumni(grid);
    } else if (section === 'jobs') {
        title.innerText = 'Job Board';
        subtitle.innerText = 'Explore exclusive opportunities for our alumni.';
        renderJobs(grid);
    } else if (section === 'mentors') {
        title.innerText = 'Mentors Hub';
        subtitle.innerText = 'Find a mentor or register to help others.';
        renderMentors(grid);
    }
    
    lucide.createIcons();
}

function updateStats() {
    document.getElementById('stat-alumni-count').innerText = appData.alumni.length;
    document.getElementById('stat-jobs-count').innerText = appData.jobs.length;
    document.getElementById('stat-mentors-count').innerText = appData.mentors.length;
}

function renderDashboard(grid) {
    const recentAlumni = appData.alumni.slice(-3).reverse();
    grid.innerHTML = `<h3 style="grid-column: 1/-1; margin-bottom: -1rem;">Recently Joined</h3>`;
    recentAlumni.forEach(person => {
        grid.innerHTML += createCard({
            icon: 'user',
            title: person.name,
            subtitle: `Class of ${person.year} • ${person.industry}`,
            tags: person.skills.split(','),
            subtext: person.bio || 'New member'
        });
    });
}

function renderAlumni(grid) {
    appData.alumni.forEach(person => {
        grid.innerHTML += createCard({
            icon: 'user',
            title: person.name,
            subtitle: `Class of ${person.year} • ${person.industry}`,
            tags: person.skills.split(','),
            subtext: person.email
        });
    });
}

function renderJobs(grid) {
    appData.jobs.forEach(job => {
        grid.innerHTML += createCard({
            icon: 'briefcase',
            title: job.title,
            subtitle: `${job.company} • ${job.location}`,
            tags: [job.industry, job.posted],
            subtext: job.desc
        });
    });
}

function renderMentors(grid) {
    appData.mentors.forEach(m => {
        grid.innerHTML += createCard({
            icon: 'message-square',
            title: m.name,
            subtitle: `${m.industry} Specialist`,
            tags: m.expertise.split(','),
            subtext: `Mentored ${m.students || 0} students`
        });
    });
}

function createCard({ icon, title, subtitle, tags, subtext }) {
    // Ensure tags is always an array
    const safeTags = Array.isArray(tags) ? tags : (tags ? tags.split(',') : []);
    return `
        <div class="card">
            <div class="card-icon"><i data-lucide="${icon || 'user'}"></i></div>
            <h3 class="card-title">${title || 'No Title'}</h3>
            <div class="card-subtitle">${subtitle || ''}</div>
            <p style="font-size: 0.9rem; color: var(--text-secondary); min-height: 3em; overflow: hidden; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical;">${subtext || ''}</p>
            <div class="card-tags">
                ${safeTags.map(t => t ? `<span class="tag ${t === safeTags[0] ? 'tag-primary' : ''}">${t.trim()}</span>` : '').join('')}
            </div>
        </div>
    `;
}

// Modal Logic
function openModal(specificType = null) {
    const section = specificType || (currentSection === 'dashboard' ? 'alumni' : currentSection);
    const title = document.getElementById('modal-title');
    const fields = document.getElementById('form-fields');
    
    title.innerHTML = `
        <div style="display: flex; flex-direction: column; gap: 0.5rem;">
            <span>Add New Entry</span>
            <select id="modal-type-selector" onchange="openModal(this.value)" style="background: var(--bg-surface); color: white; border: 1px solid var(--primary); padding: 0.4rem; border-radius: 8px; font-size: 0.9rem;">
                <option value="alumni" ${section === 'alumni' ? 'selected' : ''}>Alumni Profile</option>
                <option value="jobs" ${section === 'jobs' ? 'selected' : ''}>Job Opportunity</option>
                <option value="mentors" ${section === 'mentors' ? 'selected' : ''}>Mentor Register</option>
            </select>
        </div>
    `;
    
    let html = '';
    if (section === 'alumni') {
        html = `
            <div class="form-group">
                <label>Full Name</label>
                <input type="text" id="f-name" class="form-input" placeholder="e.g. John Doe" required>
            </div>
            <div class="form-group">
                <label>Email Address</label>
                <input type="email" id="f-email" class="form-input" required>
            </div>
            <div class="form-group">
                <label>Graduation Year</label>
                <input type="number" id="f-year" class="form-input" value="2024">
            </div>
            <div class="form-group">
                <label>Industry</label>
                <input type="text" id="f-industry" class="form-input" placeholder="e.g. IT, Finance">
            </div>
            <div class="form-group">
                <label>Skills (comma separated)</label>
                <input type="text" id="f-skills" class="form-input" placeholder="Java, SQL, React">
            </div>
        `;
    } else if (section === 'jobs') {
        html = `
            <div class="form-group">
                <label>Job Title / Position</label>
                <input type="text" id="f-title" class="form-input" placeholder="e.g. Java Developer" required>
            </div>
            <div class="form-group">
                <label>Hiring Company</label>
                <input type="text" id="f-company" class="form-input" placeholder="e.g. TechCorp" required>
            </div>
            <div class="form-group">
                <label>Location</label>
                <input type="text" id="f-location" class="form-input" placeholder="e.g. Remote, Bangalore">
            </div>
            <div class="form-group">
                <label>Industry</label>
                <input type="text" id="f-industry" class="form-input" placeholder="e.g. Software">
            </div>
            <div class="form-group">
                <label>Job Description</label>
                <textarea id="f-desc" class="form-input" style="height: 100px; resize: none;" placeholder="Details about the role..."></textarea>
            </div>
        `;
    } else if (section === 'mentors') {
        html = `
            <div class="form-group">
                <label>Mentor Name</label>
                <input type="text" id="f-name" class="form-input" required>
            </div>
            <div class="form-group">
                <label>Industry</label>
                <input type="text" id="f-industry" class="form-input">
            </div>
            <div class="form-group">
                <label>Expertise</label>
                <input type="text" id="f-expertise" class="form-input" placeholder="e.g. Cloud Architecture">
            </div>
        `;
    }

    fields.innerHTML = html;
    document.getElementById('modal').classList.add('active');
    lucide.createIcons();
}

function closeModal() {
    document.getElementById('modal').classList.remove('active');
}

function saveData() {
    // Correctly identify what we are saving based on the dropdown selection
    const section = document.getElementById('modal-type-selector').value;
    
    if (section === 'alumni') {
        const entry = {
            id: Date.now(),
            name: document.getElementById('f-name').value,
            email: document.getElementById('f-email').value,
            year: document.getElementById('f-year').value,
            industry: document.getElementById('f-industry').value,
            skills: document.getElementById('f-skills').value,
            bio: "New Alumni Member"
        };
        appData.alumni.push(entry);
        localStorage.setItem('ap_alumni', JSON.stringify(appData.alumni));
    } else if (section === 'jobs') {
        const entry = {
            id: Date.now(),
            title: document.getElementById('f-title').value,
            company: document.getElementById('f-company').value,
            location: document.getElementById('f-location').value,
            industry: document.getElementById('f-industry').value,
            desc: document.getElementById('f-desc').value,
            posted: "Just now"
        };
        appData.jobs.push(entry);
        localStorage.setItem('ap_jobs', JSON.stringify(appData.jobs));
    } else if (section === 'mentors') {
        const entry = {
            id: Date.now(),
            name: document.getElementById('f-name').value,
            industry: document.getElementById('f-industry').value,
            expertise: document.getElementById('f-expertise').value,
            students: 0
        };
        appData.mentors.push(entry);
        localStorage.setItem('ap_mentors', JSON.stringify(appData.mentors));
    }

    closeModal();
    // Switch to the section we just added data to so the user SEES IT immediately
    showSection(section);
    
    // Add a simple success notification
    const notify = document.createElement('div');
    notify.style.cssText = `
        position: fixed; bottom: 2rem; right: 2rem; 
        background: var(--accent); color: white; 
        padding: 1rem 2rem; border-radius: 12px; 
        box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        z-index: 2000; animation: slideUp 0.3s ease-out;
    `;
    notify.innerText = '✔ Entry Saved Successfully!';
    document.body.appendChild(notify);
    setTimeout(() => notify.remove(), 3000);
}

// Search
function handleSearch() {
    const query = document.getElementById('search-input').value.toLowerCase();
    document.querySelectorAll('.card').forEach(card => {
        const text = card.innerText.toLowerCase();
        if (text.includes(query)) {
            card.style.display = 'block';
            card.style.animation = 'none'; // Prevent re-animation on every keyup
        } else {
            card.style.display = 'none';
        }
    });
}

// Init
window.onload = () => {
    console.log("App initialized - Loading Alumni Section");
    showSection('alumni');
};
