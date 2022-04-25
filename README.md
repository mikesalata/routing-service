# Introduction
This is a simple routing service able to calculate routes
from country to another.

# Build and run process
build: mvn clean install,
run: mvn spring-boot:run

# Usage / Guide
Server starts on port 5000
The only available API operation (GET) is exposed on:/routing/{origin}/{destination}
with example usage /routing/CZE/ITA