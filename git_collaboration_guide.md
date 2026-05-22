# 👥 Group 6-39-A: GitHub Collaboration & Push Guide

This guide explains how to push the completed MVC + DAO Car Rental System components individually to GitHub so that **every group member gets proper commit and push credit** for their specific assignments.

---

## 📂 Division of Components

Since the codebase is structured under a clean **MVC + DAO** architecture, files are divided by architectural layers. Assign one layer to each group member:

| Member | Assigned Architectural Layer | Specific Files to Add / Modify |
|:---|:---|:---|
| **Member 1 (Dipes)** | Base Project Skeleton & Forms | Main, Login, Signup & ForgotPassword forms |
| **Member 2** | **Model Layer** | `src/model/User.java` |
| **Member 3** | **DAO Layer** | `src/dao/UserDao.java` & `src/dao/UserDaoImpl.java` |
| **Member 4** | **Controller Layer** | `src/controller/UserController.java` |
| **Member 5** | **Dashboard Views** | `src/view/AdminDashboard.java` / `.form` & `src/view/UserDashboard.java` / `.form` |

---

## 🛠️ Step-by-Step Pushing Instructions (For Members 2, 3, 4, and 5)

Each member should perform these steps from **their own computer** using their own GitHub account:

### 1. Add Members as Collaborators (Done by Dipes)
1. Go to the GitHub repository: `https://github.com/Dipes-git/Group-6-39-A-Car-Rental-System.git`
2. Go to **Settings** ➡️ **Collaborators** ➡️ **Add people**.
3. Invite all group members by username or email.
4. **All members must check their email or GitHub notifications to accept the invite.**

### 2. Clone the Repository
On your local PC, open terminal/command prompt and run:
```bash
git clone https://github.com/Dipes-git/Group-6-39-A-Car-Rental-System.git
cd Group-6-39-A-Car-Rental-System
```

### 3. Create an Isolated Feature Branch
Create and switch to a branch named after your assigned component:
```bash
# Member 2 (Model):
git checkout -b feature-user-model

# Member 3 (DAO):
git checkout -b feature-user-dao

# Member 4 (Controller):
git checkout -b feature-user-controller

# Member 5 (Dashboards):
git checkout -b feature-user-dashboards
```

### 4. Overwrite Your Assigned Files
1. Copy the final, completed version of your assigned files (shared by Dipes) and paste them into your local cloned directory, replacing any existing placeholders.
2. Verify they are in the correct directory (e.g. `src/model/User.java`).

### 5. Stage, Commit, and Push Your Branch
Run these commands to submit your branch to GitHub:
```bash
# 1. Stage the files
git add .

# 2. Commit with a meaningful, professional message
git commit -m "Implement User Model layer with role-based access attributes"
# (or "Implement database UserDao operations", "Implement UserController routing", etc.)

# 3. Push to GitHub
git push origin <your-branch-name>
# (e.g., git push origin feature-user-model)
```

### 6. Create & Merge a Pull Request (PR) on GitHub
1. Open the GitHub repository in your web browser.
2. You will see a yellow banner saying: `Your branch had recent pushes... Compare & pull request`. Click it!
3. Write a brief description of what you did and click **Create pull request**.
4. Once created, click the green **Merge pull request** button to merge it into the main/master branch.

---

## 🚀 Running & Verification Checklist

Once all Pull Requests are merged, anyone can pull the complete project and run it:
1. Pull the master branch:
   ```bash
   git pull origin master
   ```
2. Open the project in **NetBeans** or compile it from the terminal:
   ```bash
   javac -d build/classes -cp "path/to/mysql-connector.jar" src/**/*.java
   ```
3. Run the database migration script in MySQL:
   ```sql
   ALTER TABLE users ADD COLUMN role VARCHAR(10) NOT NULL DEFAULT 'user';
   ```
