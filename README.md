# Breathe-Easy 🌬️

[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://codespaces.new/raza-rk1/Breathe-Easy)
Breathe-Easy is a full-stack, microservices-based application designed to monitor, analyze, and provide real-time updates on the Air Quality Index (AQI). The system leverages a scalable backend architecture alongside a responsive frontend to deliver accurate environmental insights.

## 🚀 Architecture & Tech Stack

### Backend (Microservices Architecture)
Built using **Java** and **Spring Boot**, the backend is split into specialized decoupled services:
* **API Gateway:** Acts as the single entry point, routing requests to appropriate services.
* **Discovery Service:** Handles service registration and discovery (Eureka).
* **Identity Service:** Manages user authentication and authorization.
* **Core Service:** Handles the main business logic and data processing.
* **Air Quality Index Service:** Fetches, processes, and manages AQI data.
* **Notification Service:** Dispatches alerts and updates to users.
* **Utility Service:** Contains shared configurations and helper components.

### Frontend
* **Breath-Easy-Frontend:** A responsive web interface built with JavaScript to visualize air quality metrics, interactive charts, and user dashboards.

---

## 🛠️ Getting Started

### Prerequisites
* Java 17 or higher
* Node.js & npm
* Maven (or Gradle, depending on your build tool)

### Local Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/raza-rk1/Breathe-Easy.git](https://github.com/raza-rk1/Breathe-Easy.git)
   cd Breathe-Easy
