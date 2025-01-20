# Monitoring UAH/USD Exchange Rate with Google Analytics

This project monitors the UAH/USD exchange rate using data from the National Bank of Ukraine (NBU) API and sends the information to Google Analytics 4 (GA4) as events. 

---

## Setup and Configuration

All sensitive information (e.g., API secrets) is stored in `application.properties` for better security.

1. **Clone the repository**
2. **Add configuration:**
      
    Add following content to the src/main/resources/application.properties file:
      
         google.analytics.measurement-id=YOUR_MEASUREMENT_ID
         google.analytics.api-secret=YOUR_API_SECRET
         google.analytics.client-id=YOUR_CLIENT_ID

3.	**Build the project:**

          ./mvnw clean install

4.	**Run the project:**

          ./mvnw mn:run