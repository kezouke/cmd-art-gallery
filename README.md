# Picture Library Console Application

Welcome to the Picture Library Console Application! This project is a text-based picture and author library where users can browse, add, and interact with various pictures and authors. The application provides different levels of access based on user roles: non-registered users, registered users, and administrators. Users can explore a vast collection of pictures, leave comments, and manage their favorite items. Admins have additional privileges to manage users, pictures, and authors.

## Table of Contents
1. [Features](#features)
2. [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
3. [Usage](#usage)
    - [Non-registered Users](#non-registered-users)
    - [Registered Users](#registered-users)
    - [Administrators](#administrators)
4. [Registration](#registration)
5. [Search](#search)
6. [Profile](#profile)
7. [Contributing](#contributing)
8. [License](#license)

## DEMO

[![Presentation](https://img.youtube.com/vi/CpKk7BZYHZk/0.jpg)](https://www.youtube.com/watch?v=CpKk7BZYHZk)

## Features
- Browse and view pictures and authors.
- Registered users can add new pictures and authors.
- Users can leave comments on pictures and interact with other users' comments.
- Registered users have a list of favorite pictures and authors.
- Users can save their favorite items for quick access.
- Each user has a profile with a profile picture and description.
- Admins can manage users, including promotion, demotion, and deletion.
- Admins have the authority to remove pictures and authors from the database.
- Comprehensive search functionality to quickly find pictures or authors of interest.
- Secure registration process with complex password requirements and unique usernames.

## Getting Started
### Prerequisites
- Java Development Kit (JDK) installed on your system.
- Firestore database credentials (if using Firestore).

### Installation
1. Clone this repository to your local machine.
2. Open the project in your favorite Java IDE.
3. Configure the Firestore database and specify the necessary credentials in the application.
4. Save the json key under the name "cmd-art-gallery-firebase.json" in the root folder.

## Usage
### Non-registered Users
Non-registered users can:
- View the entire collection of pictures and authors.
- Explore details about each picture, including the author, year, and comments.

### Registered Users
Registered users have the following privileges:
- Add new pictures and authors to the library.
- Leave comments on pictures and interact with other users' comments.
- Maintain a personalized list of favorite pictures and authors for quick access.
- Access their profiles to manage profile pictures and descriptions.

### Administrators
Administrators enjoy additional powers:
- Manage users by promoting, demoting, or deleting user accounts.
- Remove pictures or authors from the database, if necessary.

## Registration
To register as a user:
1. Run the application.
2. Choose the "Register" option from the main menu.
3. Enter a unique username and a strong password that meets the complexity requirements.
4. Complete the registration process to access the application with your new account.

## Search
The application offers a robust search feature, allowing users to find pictures or authors based on various criteria, such as name, country, genre, etc.

## Note: Project Realization

This project was realized during the "Challenges of Object-Oriented Programming" course at Innopolis University, under the guidance of the esteemed instructor, Egor Bugaenko. The course aimed to delve into modern problems of Object-Oriented Programming (OOP) and foster a deeper understanding of object thinking.

The Picture Library Console Application was developed as a practical exercise to explore and apply various OOP principles, design patterns, and best practices. It provided an opportunity for students to tackle real-world challenges while building a comprehensive and interactive console-based picture and author library.

We extend our gratitude to Innopolis University and Egor Bugaenko for offering this enriching educational experience, enabling students to enhance their skills in OOP and object thinking.

This project is a testament to the collective efforts of the course participants, who showcased their dedication, passion, and aptitude for creating innovative solutions in the realm of Object-Oriented Programming.

## Profile
Each registered user has a profile where they can add a profile picture and write a bio/description. Users can also view profiles of other users in the comments section.

## Contributing
We welcome contributions from the community! If you find any bugs, have suggestions for improvements, or want to add new features, feel free to open an issue or submit a pull request.

## License
This project is licensed under the [MIT License](LICENSE.md). Feel free to use, modify, and distribute it as per the terms of the license.
