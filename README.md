# 📚 TODO List Application
<div align="center">

![GitHub stars](https://img.shields.io/github/stars/yasith-1/javaFX-library-management-system?style=for-the-badge&logo=github&color=yellow)
![GitHub forks](https://img.shields.io/github/forks/yasith-1/javaFX-library-management-system?style=for-the-badge&logo=github&color=blue)
![GitHub issues](https://img.shields.io/github/issues/yasith-1/javaFX-library-management-system?style=for-the-badge&logo=github&color=red)
![GitHub license](https://img.shields.io/github/license/yasith-1/javaFX-library-management-system?style=for-the-badge&logo=github&color=green)

<img src="https://user-images.githubusercontent.com/73097560/115834477-dbab4500-a447-11eb-908a-139a6edaec5c.gif" width="100%">

</div>

## 📋 Overview

A comprehensive **Library Management System** built with JavaFX that provides complete CRUD operations for managing library resources. This system streamlines library operations with an intuitive interface and robust functionality.

### ✨ Key Features

- 🔐 User Authentication: Secure login and signup functionality
- 📋 Task Management: Create, read, update, and delete tasks
- 🎛️ User Dashboard: Intuitive interface for managing your todos
- 💾 Database Integration: Persistent data storage using database connectivity
- 🎨 Modern UI: Clean and responsive JavaFX interface with custom styling
- 📊 Task Status Tracking: Monitor task completion status
- 🚨 Alert System: User-friendly notifications and alerts

</br>

---

## 🚀 Quick Start

### Prerequisites

- ☕ **Java 17+** 
- 🛠️ **Maven 3.6+**
- 💾 **MySQL Database**

### 📥 Installation

1. **Clone the repository:**
   ```bash
   https://github.com/yasith-1/todo-list-application.git
   cd javaFX-library-management-system
   ```

2. **Configure Database:**

![todoApp](https://github.com/user-attachments/assets/67053e65-d27c-4aac-952a-d76cdcf63520)


3. **Build and Run:**
   ```bash
   mvn clean install
   ```

</br>

---

## 🔄️ Dependencies

- javafx-controls
- javafx-fxml
- jfoenix
- mysql-connector-j
- controlsfx
- lombok
- modelmapper

</br>
  
---

## ♻️ Build Plugin
```bash
   <plugins>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>Main</mainClass>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.2.4</version> <!-- Use the latest version -->
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <shadedArtifactAttached>true</shadedArtifactAttached>
                            <shadedClassifierName>project-classifier</shadedClassifierName>
                            <outputFile>shade\${project.artifactId}.jar</outputFile>
                            <transformers>
                                <transformer
                                        implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>Main</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
   ```

</br>

---

## 🛠️ Technology Stack

<div align="center">

| Technology | Purpose | Version |
|------------|---------|---------|
| ![JavaFX](https://img.shields.io/badge/JavaFX-007396?style=for-the-badge&logo=java&logoColor=white) | Frontend Framework | Latest |
| ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white) | Backend Language | 22+ |
| ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white) | Database | 8.0+ |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white) | Build Tool | 3.6+ |

</div>


</br>

---

## 📱 Application Screenshots

<div align="left">
<details>
<summary>🖼️ Login</summary>
  
![login](https://github.com/user-attachments/assets/6748a5ee-ba46-4d9b-b2dd-fe357efb9dc1)

</details>

<details>

<summary>🖼️ Register </summary>

![register](https://github.com/user-attachments/assets/0601dffe-999b-48a8-92f6-be969ab3e961)

</details>

<details>

<summary>🖼️ Dashboard </summary>

![dashboard](https://github.com/user-attachments/assets/059de3ff-624d-40d7-b303-cee7db05164c)

</details>


*Clean and intuitive interface for easy navigation*

</div>

</br>

---

## 🏗️ Project Structure

```
todo-list-application/
├── src/main/java/
│   ├── alert/                    # 🚨 Alert system components
│   │   ├── Alert.java           # Custom alert implementation
│   │   └── AlertType.java       # Alert type definitions
│   ├── controller/              # 🎛️ MVC Controllers
│   │   ├── dashboard/           # Dashboard controllers
│   │   ├── login/              # Login controllers
│   │   └── signup/             # Signup controllers
│   ├── database/               # 🗄️ Database connectivity
│   │   └── DBconnection.java   # Database connection management
│   ├── model/                  # 📊 Data models
│   │   ├── Task.java           # Task entity
│   │   └── User.java           # User entity
│   ├── util/                   # 🔧 Utility classes
│   │   ├── CrudUtil.java       # Database CRUD operations
│   │   └── TaskStatus.java     # Task status enumeration
│   ├── Main.java               # 🚀 Application entry point
│   └── Starter.java            # Application starter
└── src/main/resources/
    ├── images/                 # 🖼️ Application images and icons
    └── view/                   # 🎨 FXML view files
        ├── todo-dashboard.fxml
        ├── user-login.fxml
        └── user-signup.fxml
```

</br>

---

## 🎯 Usage

🎯 First Time Setup:

- Launch the application
- Create a new account using the signup form
- Login with your credentials


📝 Managing Tasks:

- Add new tasks using the dashboard
- Mark tasks as complete/incomplete
- Edit existing tasks
- Delete tasks you no longer need

🧭 Navigation:

- Use the intuitive GUI to navigate between login, signup, and dashboard
- Visual feedback through the alert system

</br>

---

## 🛡️ Security Features

- 🔒 Password encryption for user accounts
- ✅ Input validation and sanitization
- 🛡️ SQL injection prevention through prepared statements


</br>

---

## 📞 Contact & Support

<div align="center">

### 👨‍💻 Developer : Yashith Prabhashwara

[![Gmail](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:yasithprabaswara1@gmail.com)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/yashith-prabhashwara-a1aa471a6/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/yasith-1)

</div>

---

</br>

## 🙏 Acknowledgments

- Thanks to all contributors who helped improve this project
- Special thanks to the JavaFX community for excellent documentation
- Inspired by modern Todo App

---

<div align="center">

### 🌟 If you found this project helpful, please give it a star! 🌟

<img src="https://capsule-render.vercel.app/api?type=waving&color=gradient&height=80&section=footer"/>

**Made with ❤️ by [Yasith Prabaswara](https://github.com/yasith-1)**

</div>
