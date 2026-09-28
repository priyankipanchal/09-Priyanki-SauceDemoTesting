# SauceDemo E-Commerce Automated & Manual Testing Suite

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Chrome](https://img.shields.io/badge/Google_Chrome-4285F4?style=for-the-badge&logo=googlechrome&logoColor=white)

---

## 📌 Project Overview
This repository contains the end-to-end QA validation pipeline for the **[SauceDemo](https://www.saucedemo.com/)** e-commerce platform. It combines manual exploratory testing with an automated regression suite built using **Java and Selenium WebDriver** to ensure catalog reliability, session consistency, and frictionless checkout processing.

* **Candidate Name:** Priyanki Panchal
* **Track:** Software Testing Track
* **Academic Program:** Software Development Multi-Track Certification Program
* **Collaboration:** Baroda Institute of Technology (BIT) in academic association with IIT Patna - Vishlesan I-Hub & IBM

---

## 🎬 Video Recording & Demo
Due to GitHub's 100 MB file limit, the full-length automation test execution demo video is hosted on Google Drive:
* 🔗 **[Click Here to Watch the Automation Video Demo](PASTE_YOUR_GOOGLE_DRIVE_LINK_HERE)**

---

## 📊 Summary Metrics & Deliverables

| Metric / Category | Count / Status | Notes |
|---|:---:|---|
| **Total Test Cases** | 20 | Complete functional & regression coverage |
| **Manual Test Cases** | 15 | Form validation, UI layouts, boundary values |
| **Automated Test Cases** | 5 | Java + Selenium WebDriver automation suite |
| **Passed Tests** | 17 | Core checkout and catalog journeys verified |
| **Defects Identified** | 3 | Logged with steps, severity, and screenshots |
| **Functional Pass Rate** | 85% | Exceeds the mandatory 60% evaluation floor |

---

## 🛠️ Technology Stack & Architecture
* **Language:** Java (JDK 21)
* **Automation Framework:** Selenium WebDriver (v4.20.0)
* **Build & Dependency Tool:** Apache Maven
* **Synchronization Strategy:** Dynamic Explicit Waits (`WebDriverWait`) & `JavascriptExecutor` fallback
* **Browser:** Google Chrome (with custom `ChromeOptions`)
* **IDE & Platform:** Visual Studio Code on Windows 11

---

## 🧪 Automated Test Scenarios

1. **TC01 - Valid User Login:** Verifies authentication of `standard_user` and routing to `inventory.html`.
2. **TC02 - Locked-Out User:** Validates negative login flow and checks error banner display.
3. **TC03 - Add to Cart Flow:** Adds the Sauce Labs Backpack and verifies the cart badge counter updates to `1`.
4. **TC04 - Cart Item Verification:** Opens cart view and confirms added inventory matches item specifications.
5. **TC05 - End-to-End Checkout:** Completes the user detail form, advances through overview, confirms final order, and verifies the message `"Thank you for your order!"`.

---

## 🐛 Logged Defect Summary

* **BUG_01 (Low):** Locked-out error message layout misalignment on narrow mobile viewports.
* **BUG_02 (Medium):** Missing explicit visual validation highlight on empty Postal Code field during checkout.
* **BUG_03 (Medium):** Incorrect product thumbnail images displayed across catalog for `problem_user`.

---

## 🚀 How to Run the Automated Tests

### Prerequisites
* JDK 21 installed and configured in your `PATH`.
* Google Chrome installed.

### Steps
1. Clone the repository:
   ```bash
   git clone [https://github.com/priyankipanchal/09-Priyanki-SauceDemoTesting.git](https://github.com/priyankipanchal/09-Priyanki-SauceDemoTesting.git)
   cd 09-Priyanki-SauceDemoTesting
