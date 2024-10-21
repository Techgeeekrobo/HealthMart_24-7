# Healthcare Application

## Table of Contents
1. [Introduction](#introduction)
2. [Features](#features)
3. [Installation](#installation)
4. [Usage](#usage)
5. [Screens](#screens)
6. [Technologies Used](#technologies-used)
7. [Contributing](#contributing)
8. [License](#license)

## Introduction
The **Healthcare Application** provides a comprehensive solution for managing various health-related services such as booking lab tests, ordering medicines, finding doctors, and accessing health articles. It integrates with Firebase for seamless data storage and authentication.

## Features
1. **Login**: Secure login for users using Firebase Authentication.
2. **Registration**: User registration with validation for fields like email, password, and phone number.
3. **Lab Test**: 
   - Browse multiple lab test packages.
   - Add packages to the cart.
   - Place an order for lab tests.
4. **Medicine**: 
   - View a list of available medicines.
   - Add medicines to the cart.
   - Place an order for medicines.
5. **Find Doctor**: 
   - Search for specialist doctors.
   - Book an appointment.
6. **Health Articles**: Access various health-related articles for users to read.
7. **Order Details**: View order details, including both medicine and lab test orders.
8. **Logout**: Securely log out of the application.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/healthcare-app.git
   cd healthcare-app
2 Open the project in Android Studio.
3 Sync project with Gradle files.
4 Configure Firebase by adding the google-services.json file to the app/ directory.
5 Build and run the application on an emulator or physical device.
  ## Usage
Login/Registration: Users can register and log in using their email and password.
Lab Test & Medicine: After logging in, users can browse and order lab test packages or medicines, adding them to their cart.
Find Doctor: Search for doctors by specialization and book an appointment.
Order History: Users can view their past orders and appointments from the Order Details section.
Health Articles: Access health-related articles from the main menu.
Logout: Securely log out from the application.
## Screens
Login Screen
Registration Screen
Lab Test Packages List
Medicine List
Doctor Search and Appointment Booking
Health Articles
Order Details
Cart and Checkout
## Technologies Used
Android SDK: For native Android development.
Firebase Authentication: For user login and registration.
Firebase Firestore: For real-time data storage.
RecyclerView: For displaying lists of lab tests, medicines, and orders.
DatePicker & TimePicker: For booking appointments with doctors.
Material Design: For modern UI elements.
## Contributing
Feel free to fork the repository and submit pull requests. Contributions are welcome to enhance the functionality and UI/UX of the application.

License
This project is licensed under the MIT License. See the LICENSE file for details.
