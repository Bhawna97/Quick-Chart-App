# Quick Chart

When a patient visits to the hospital, the first procedure he or she goes through is the basic body checkup which includes vital measurements like Blood Presure, Pulse Rate, Boddy Temperature etc. Therefore my application provides the plateform to keep the record of these vital measurements of each and every patient with date and time.

## Tech Stack

**Client:** HTML, CSS, Bootstrap, JavaScript, axios

**Server:** JAVA - RESTful Web Services
## Screenshots

[![Quick-Chart-Home-Page.jpg](https://i.postimg.cc/cJJGB2Nq/Quick-Chart-Home-Page.jpg)](https://postimg.cc/w1nr6WvF) [![Quick-Chart-Register-User.jpg](https://i.postimg.cc/HkdrdYbQ/Quick-Chart-Register-User.jpg)](https://postimg.cc/Xp1jg0HJ) [![Quick-Chart-Login-Page.jpg](https://i.postimg.cc/15ZGVZpz/Quick-Chart-Login-Page.jpg)](https://postimg.cc/nM0jNgS8) [![Quick-Chart-Patient-List-Page.jpg](https://i.postimg.cc/FHCGLBrn/Quick-Chart-Patient-List-Page.jpg)](https://postimg.cc/k28xrwtx) [![Quick-Chart-Add-Patient-Page.jpg](https://i.postimg.cc/fTQSLjxn/Quick-Chart-Add-Patient-Page.jpg)](https://postimg.cc/s1KgHW6w) [![Quick-Chart-Chart-List-Page.jpg](https://i.postimg.cc/FHxzWMdD/Quick-Chart-Chart-List-Page.jpg)](https://postimg.cc/MnvzXrYQ) [![Quick-Chart-Add-Chart-Page.jpg](https://i.postimg.cc/SQcjnhns/Quick-Chart-Add-Chart-Page.jpg)](https://postimg.cc/V5Lzh2BQ) [![Quick-Chart-View-Chart-Page.jpg](https://i.postimg.cc/Zn064T3b/Quick-Chart-View-Chart-Page.jpg)](https://postimg.cc/QKGFqZ0w) [![Quick-Chart-Edit-Chart-Page.jpg](https://i.postimg.cc/FzKwvJDY/Quick-Chart-Edit-Chart-Page.jpg)](https://postimg.cc/sQtT91Fz)
## API Reference

#### Add User Account

```http
  Post http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/addUser
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `firstname` `lastname` `username` `password` | `JSON` | **Required**. Data of the user to create user account|

#### Check Username

```http
  Post http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/checkUsername
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `firstname` `lastname` | `JSON` | **Required**. User data to create unique username|

Returns three unique usernames which are not taken.

#### Validate User Account

```http
  Post http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/validateUser
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` `password` | `JSON` | **Required**. User account data to validate the account|

#### Add Patient

```http
  Post http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/addPatient
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `firstname` `lastname` `dateOfBirth` `gender`| `JSON` | **Required**. Patient data to add patient in record|

#### Get Patient List

```http
  GET http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/PatientList
```
Returns list of all patients available in the recodrd.

#### Add Patient Chart

```http
  Post http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/addPatientChart
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `patientId` `dateTime` `chartName` `systolicBP` `diastolicBP` `pulse` `respiration` `temperature` | `JSON` | **Required**. Patient vital measurements to add vital chart in record|

#### Get Patient Chart List

```http
  GET http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/PatientChartList/${patientId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `patientId` | `integer` | **Required**. patientId of patient to fetch Charts List|

Returns the list of charts for a perticular patient.

#### Get Patient Chart

```http
  GET http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/PatientChart/${chartId}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `chartId` | `integer` | **Required**. chartId of chart to fetch Chart values|

Returns the values of the chart of a perticular patient.

#### Update Patient Chart

```http
  Post http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/updatePatientChart
```

| Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `patientId` `chartId` `dateTime` `chartName` `systolicBP` `diastolicBP` `pulse` `respiration` `temperature` | `JSON` | **Required**. Patient vital measurements to update vital chart|

#### Get Sorted Patient List

```http
  GET http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/SortedPatientList/columnName/${columnName}/sortOrder/${sortOrder}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `columnName` `sortOrder` | `String` | **Required**. column name and order for soring the list|

Returns the sorted list of patients. 

#### Get Sorted Chart List

```http
  GET http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/SortedChartList/${patientId}/columnName/${columnName}/sortOrder/${sortOrder}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `patientId` `columnName` `sortOrder` | `Integer` `String` | **Required**. patientId for fetching chart list and column name, order for soring the list|

Returns the sorted list of charts for a perticular patient.

## Installation

Install following applications to run the project locally

- Java SDK and set the JAVA_HOME environment variable to point to a valid Java SDK.
- Intellij IDEA - for taking the build of maven project
- Tomcat server - to deploy and run the project locally
    
## Run Locally

Clone the project

```bash
  git clone https://github.training.cerner.com/DevCenter/Quick-Chart-App.git
```

Go to the project directory

```bash
  cd Quick-Chart-App
```

Checkout into stable branch

```bash
  git Checkout stable
```

Pull project in local

```bash
  git Pull
```

Open project in intelliJ IDEA

Take the maven build from intelliJ - clean, compile, install

Start the Tomcat server in local

- Go in Tomcat bin folder 
- Type cmd in the address bar and hit enter
- Give command - catalina.bat start and hit enter
- hit localhost:8080 in any browser
- Tomcat server is started in local

Deploy and run the project on Tomcat server

- Upload the war file of the project generated from the build taken on intelliJ (war file generated in target folder)
- Upload the frontend code (the QuickChartApp folder) in tomcat server (paste the QuickChartApp folder in tomcat webapps folder)
- Restart the tomcat server
- Click on QuickChartApp on tomcat server on run the project. 


## Lessons Learned

I learned the following lessons while building this project

- Learned how to create RESTful web services (both get and post api) using java.
- Learned how to bind and implement the RESTful web services with Javascript using Axios.
- Learned how to use Bootstrap with HTML and CSS.
- Learned how to deploy and run the project (both client and server) Locally using tomcat server.
## Used By

This project can be used by following individuals:

- Doctors
- Nurses
- Hospital staff
