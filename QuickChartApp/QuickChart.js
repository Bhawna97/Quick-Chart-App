function spinner() {
  document.getElementsByClassName('loader')[0].style.display = 'block';
}

function hideSpinner() {
  document.getElementsByClassName('loader')[0].style.display = 'none';
}

// Used this function in index html page for login button click
// eslint-disable-next-line no-unused-vars
function onClickLogin() {
  window.location.href = 'LoginPage.html';
}

// Used this function in index html for register button click
// eslint-disable-next-line no-unused-vars
function onClickRegister() {
  window.location.href = 'RegisterPage.html';
}

// Used this function in login and register page html for cancel button click
// eslint-disable-next-line no-unused-vars
function onClickCancel() {
  window.location.href = 'index.html';
}

// Using this function for fetching the current date and time
function getDateTime() {
  const options = {
    hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: 'true',
  };
  return new Date().toLocaleDateString(undefined, options);
}

// Used this function for onload Register page action in RegisterPage HTML file
// eslint-disable-next-line no-unused-vars
function onLoadRegisterPage() {
  document.getElementById('suggestContainer').style.visibility = 'hidden';
}

// Using this function in AddPatient html file to disable the future dates in DOB calender
// eslint-disable-next-line no-unused-vars
function loadCalendar() {
  let today = new Date();
  const dd = String(today.getDate()).padStart(2, '0');
  const mm = String(today.getMonth() + 1).padStart(2, '0');
  const yyyy = today.getFullYear();
  today = `${yyyy}-${mm}-${dd}`;
  document.getElementById('dob').max = today;
}

// Used this function for onload add patient page action in AddPatientPage HTML file
// eslint-disable-next-line no-unused-vars
function onLoadAddPntPage() {
  document.getElementById('gender').className = 'empty commonInput';
  document.getElementById('dateTime').innerHTML = getDateTime();
}

// Used this function for onchange gender field action in AddPatientPage HTML file
// eslint-disable-next-line no-unused-vars
function onchangeGender() {
  if (document.getElementById('gender').value === '') {
    document.getElementById('gender').className = 'empty commonInput';
  } else {
    document.getElementById('gender').className = 'commonInput';
  }
}

// Used this function for on blur first and last name text filed in registration page HTML file
// eslint-disable-next-line no-unused-vars
function onBlurNameField() {
  const firstname = document.getElementById('firstName').value;
  const lastname = document.getElementById('lastName').value;
  if (firstname.trim().length !== 0 && lastname.trim().length !== 0
      && document.getElementById('userName').value.trim().length !== 0
      && document.getElementById('password').value.trim().length !== 0) {
    document.getElementById('registerMessage').innerHTML = '';
  }
  if (firstname.trim().length !== 0) {
    document.getElementById('firstName').className = document.getElementById('firstName').className.replace('errorInput', 'commonInput');
  }
  if (lastname.trim().length !== 0) {
    document.getElementById('lastName').className = document.getElementById('lastName').className.replace('errorInput', 'commonInput');
  }
  if (firstname.trim().length !== 0 && lastname.trim().length !== 0) {
    document.getElementById('suggestContainer').style.visibility = 'visible';
    document.getElementById('suggestUsername').innerText = 'Select Username';
    const header = {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
    };
    // eslint-disable-next-line no-undef
    axios
      .post('http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/checkUsername', {
        firstname,
        lastname,
      }, {
        headers: header,
      })
      // eslint-disable-next-line no-loop-func
      .then((response) => {
        if (response.status === 200) {
          document.getElementById('username1').innerText = response.data[0].username1;
          document.getElementById('username2').innerText = response.data[0].username2;
          document.getElementById('username3').innerText = response.data[0].username3;
        }
      })
      // eslint-disable-next-line no-loop-func
      .catch(() => {
      });
  }
}

// Used this function in RegisterPage html file for ok button click in username suggestion box
// eslint-disable-next-line no-unused-vars
function onClickUsername(clickedId) {
  if (clickedId === 'username1') {
    document.getElementById('userName').value = document.getElementById('username1').innerText;
  } else if (clickedId === 'username2') {
    document.getElementById('userName').value = document.getElementById('username2').innerText;
  } else if (clickedId === 'username3') {
    document.getElementById('userName').value = document.getElementById('username3').innerText;
  }
  document.getElementById('suggestContainer').style.visibility = 'hidden';
  document.getElementById('userName').className = document.getElementById('userName').className.replace('errorInput', 'commonInput');
}

// Used this function in RegisterPage html file for cancel button click in username suggestion box
// eslint-disable-next-line no-unused-vars
function onClickSuggestCancel() {
  document.getElementById('suggestContainer').style.visibility = 'hidden';
}

// Used this function in RegisterPage html file for onblur all field
// eslint-disable-next-line no-unused-vars
function onBlurRegisterField() {
  const firstname = document.getElementById('firstName').value;
  const lastname = document.getElementById('lastName').value;
  const username = document.getElementById('userName').value;
  const password = document.getElementById('password').value;
  if (firstname.trim().length !== 0 && lastname.trim().length !== 0
      && username.trim().length !== 0 && password.trim().length !== 0) {
    document.getElementById('registerMessage').innerHTML = '';
  }
  if (username.trim().length !== 0) {
    document.getElementById('userName').className = document.getElementById('userName').className.replace('errorInput', 'commonInput');
  }
  if (password.trim().length !== 0) {
    document.getElementById('password').className = document.getElementById('password').className.replace('errorInput', 'commonInput');
    if (password.length < 6) {
      document.getElementById('passwordMessage').innerHTML = 'Password must have at least 6 characters';
    } else {
      document.getElementById('passwordMessage').innerHTML = '';
    }
  }
}

// Used this function in RegisterPage html file for Register button click
// eslint-disable-next-line no-unused-vars
function onClickRegisterButton() {
  const firstName = document.getElementById('firstName').value;
  const lastName = document.getElementById('lastName').value;
  const userName = document.getElementById('userName').value;
  const password = document.getElementById('password').value;

  if (firstName.trim().length === 0 && lastName.trim().length === 0
      && userName.trim().length === 0 && password.trim().length === 0) {
    document.getElementById('registerMessage').innerHTML = 'Please Enter All Details.';
    document.getElementById('firstName').className = document.getElementById('firstName').className.replace('commonInput', 'errorInput');
    document.getElementById('lastName').className = document.getElementById('lastName').className.replace('commonInput', 'errorInput');
    document.getElementById('userName').className = document.getElementById('userName').className.replace('commonInput', 'errorInput');
    document.getElementById('password').className = document.getElementById('password').className.replace('commonInput', 'errorInput');
  } else if (firstName.trim().length === 0 || lastName.trim().length === 0
      || userName.trim().length === 0 || password.trim().length === 0) {
    let RegisterMsg = 'Please Enter -';
    let count = 0;
    if (firstName.trim().length === 0) {
      document.getElementById('firstName').className = document.getElementById('firstName').className.replace('commonInput', 'errorInput');
      RegisterMsg = `${RegisterMsg} First Name`;
      count += 1;
    }
    if (lastName.trim().length === 0) {
      document.getElementById('lastName').className = document.getElementById('lastName').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        RegisterMsg = `${RegisterMsg},`;
      }
      count += 1;
      RegisterMsg = `${RegisterMsg} Last Name`;
    }
    if (userName.trim().length === 0) {
      document.getElementById('userName').className = document.getElementById('userName').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        RegisterMsg = `${RegisterMsg},`;
      }
      count += 1;
      RegisterMsg = `${RegisterMsg} Username`;
    }
    if (password.trim().length === 0) {
      document.getElementById('password').className = document.getElementById('password').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        RegisterMsg = `${RegisterMsg},`;
      }
      RegisterMsg = `${RegisterMsg} Password`;
    }
    document.getElementById('registerMessage').innerHTML = RegisterMsg;
  } else if (password.trim().length < 6) {
    document.getElementById('passwordMessage').innerHTML = 'Password must have at least 6 characters';
  } else {
    spinner();
    const header = {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
    };
    // eslint-disable-next-line no-undef
    axios
      .post('http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/addUser', {
        firstname: firstName,
        lastname: lastName,
        username: userName,
        password,
      }, {
        headers: header,
      })
      .then((response) => {
        hideSpinner();
        if (response.status === 200) {
          document.getElementById('firstName').value = '';
          document.getElementById('lastName').value = '';
          document.getElementById('userName').value = '';
          document.getElementById('password').value = '';
          document.getElementById('passwordMessage').innerHTML = '';
          document.getElementById('registerMessage').innerHTML = 'User Registered Successfully.';
        }
      })
      .catch((error) => {
        if (error.request.status === 406) {
          document.getElementById('registerMessage').innerHTML = 'Username is Taken, Please Enter Different Username.';
        } else {
          document.getElementById('registerMessage').innerHTML = 'Something Went Wrong, Please try again!';
        }
        hideSpinner();
      });
  }
}

// Used this function in LoginPage html file for onblur username field
// eslint-disable-next-line no-unused-vars
function onBlurLunField() {
  const username = document.getElementById('lUserName').value;
  const password = document.getElementById('lPassword').value;
  if (username.trim().length !== 0 && password.trim().length !== 0) {
    document.getElementById('loginMessage').innerHTML = '';
  }
  if (username.trim().length !== 0) {
    document.getElementById('lUserName').className = document.getElementById('lUserName').className.replace('errorInput', 'commonInput');
  }
}

// Used this function in LoginPage html file for onblur password field
// eslint-disable-next-line no-unused-vars
function onBlurLpField() {
  const username = document.getElementById('lUserName').value;
  const password = document.getElementById('lPassword').value;
  if (username.trim().length !== 0 && password.trim().length !== 0) {
    document.getElementById('loginMessage').innerHTML = '';
  }
  if (password.trim().length !== 0) {
    document.getElementById('lPassword').className = document.getElementById('lPassword').className.replace('errorInput', 'commonInput');
  }
}

// Used this function in LoginPage html file for Login button click
// eslint-disable-next-line no-unused-vars
function onClickLoginButton() {
  const lUserName = document.getElementById('lUserName').value;
  const lPassword = document.getElementById('lPassword').value;

  if (lUserName.trim().length === 0 && lPassword.trim().length === 0) {
    document.getElementById('loginMessage').innerHTML = 'Please Enter Username and Password.';
    document.getElementById('lPassword').className = document.getElementById('lPassword').className.replace('commonInput', 'errorInput');
    document.getElementById('lUserName').className = document.getElementById('lUserName').className.replace('commonInput', 'errorInput');
  } else if (lUserName.trim().length !== 0 && lPassword.trim().length === 0) {
    document.getElementById('loginMessage').innerHTML = 'Please Enter Password.';
    document.getElementById('lPassword').className = document.getElementById('lPassword').className.replace('commonInput', 'errorInput');
  } else if (lUserName.trim().length === 0 && lPassword.trim().length !== 0) {
    document.getElementById('loginMessage').innerHTML = 'Please Enter Username.';
    document.getElementById('lUserName').className = document.getElementById('lUserName').className.replace('commonInput', 'errorInput');
  } else {
    spinner();
    const header = {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
    };
      // eslint-disable-next-line no-undef
    axios
      .post('http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/validateUser', {
        username: lUserName,
        password: lPassword,
      }, {
        headers: header,
      })
      .then((response) => {
        if (response.status === 200) {
          document.getElementById('loginMessage').innerHTML = 'Login Successful';
          window.location.href = 'PatientListPage.html';
        }
      })
      .catch((error) => {
        if (error.request.status === 401) {
          document.getElementById('loginMessage').innerHTML = 'Invalid Username or Password';
        } else {
          document.getElementById('loginMessage').innerHTML = 'Something Went Wrong, Please try again!';
        }
        hideSpinner();
      });
  }
}

// Used this function in PatientListPage html file for loading the list of patients
// eslint-disable-next-line no-unused-vars
function onloadPatientListPage() {
  spinner();
  document.getElementById('dateTime').innerHTML = getDateTime();
  let patients = {};
  const header = {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  };
    // eslint-disable-next-line no-undef
  axios
    .get('http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/PatientList', {
      headers: header,
    })
    .then(async (response) => {
      patients = await response.data;
      // eslint-disable-next-line no-undef
      $(() => {
        // eslint-disable-next-line no-undef
        $('#patientListTable').bootstrapTable({
          showPaginationSwitch: true,
          pageList: [],
          paginationDetailHAlign: 'right',
          data: patients,
          onClickRow(row) {
            localStorage.setItem('pId', row.patientId);
            localStorage.setItem('pName', row.patientName);
            localStorage.setItem('pAge', row.age);
            localStorage.setItem('pGender', row.gender);
            window.location.href = 'PatientChartList.html';
          },
        });
        hideSpinner();
      });
    })
    .catch((err) => {
      patients = err;
       // eslint-disable-next-line no-undef
       $(() => {
         // eslint-disable-next-line no-undef
         $('#patientListTable').bootstrapTable({
           data: patients,
         });
         hideSpinner();
       });
    });
}

// Used this function in PatientListPage for filter table
// eslint-disable-next-line no-unused-vars
function filterTableFunction() {
  let input = ''; let filter = ''; let td = []; let tr = ''; let table = {}; let i; let txtValue;
  input = document.getElementById('searchInput').value;
  filter = input.toUpperCase();
  table = document.getElementById('patientListTable');
  tr = table.getElementsByTagName('tr');
  for (i = 0; i < tr.length; i += 1) {
    // eslint-disable-next-line prefer-destructuring
    td = tr[i].getElementsByTagName('td')[1];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = '';
      } else {
        tr[i].style.display = 'none';
      }
    }
  }
}

function pageHeader() {
  const pName = `Name: ${localStorage.getItem('pName')},`;
  const pAge = `Age: ${localStorage.getItem('pAge')},`;
  const pGender = `Gender: ${localStorage.getItem('pGender')}`;
  document.getElementById('pName').innerHTML = pName;
  document.getElementById('pAge').innerHTML = pAge;
  document.getElementById('pGender').innerHTML = pGender;
}

// Used this function in PatientChartList html file for onload action
// eslint-disable-next-line no-unused-vars
function onloadChartListPage() {
  spinner();
  document.getElementById('dateTime').innerHTML = getDateTime();
  pageHeader();
  const pId = localStorage.getItem('pId');
  const viewChartButton = '<button class="viewChartButton" onclick="onClickViewChartButton()">View Chart</button>';
  let patientCharts = {};
  const header = {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  };
  // eslint-disable-next-line no-undef
  axios
    .get(`http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/PatientChartList/${pId}`, {
      headers: header,
    })
    .then(async (response) => {
      if (response.data !== null) {
        patientCharts = await response.data;
        for (let i = 0; i < patientCharts.length; i += 1) {
          patientCharts[i].Sno = i + 1;
          patientCharts[i].viewChart = viewChartButton;
        }
        // eslint-disable-next-line no-undef
        $(() => {
          // eslint-disable-next-line no-undef
          $('#patientChartList').bootstrapTable({
            showPaginationSwitch: true,
            pageSize: 5,
            pageList: [],
            paginationDetailHAlign: 'right',
            data: patientCharts,
          });
          hideSpinner();
        });
      }
    })
    .catch((err) => {
      patientCharts = err;
       // eslint-disable-next-line no-undef
       $(() => {
         // eslint-disable-next-line no-undef
         $('#patientChartList').bootstrapTable({
           data: patientCharts,
         });
         hideSpinner();
       });
    });
}

// Used this function in PatientChartListPage to filter Chart table
// eslint-disable-next-line no-unused-vars
function filterChartTableFunction() {
  let input = ''; let td = []; let tr = ''; let table = {}; let i; let txtValue;
  input = document.getElementById('searchInput').value;
  table = document.getElementById('patientChartList');
  tr = table.getElementsByTagName('tr');
  for (i = 0; i < tr.length; i += 1) {
    // eslint-disable-next-line prefer-destructuring
    td = tr[i].getElementsByTagName('td')[3];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.indexOf(input) > -1) {
        tr[i].style.display = '';
      } else {
        tr[i].style.display = 'none';
      }
    }
  }
}

// Used this function in PatientChartListPage for view Chart button click
// eslint-disable-next-line no-unused-vars
function onClickViewChartButton() {
  const table = document.getElementById('patientChartList');
  for (let i = 0; i < table.rows.length; i += 1) {
    // eslint-disable-next-line no-loop-func,func-names
    table.rows[i].onclick = function () {
      const chartId = this.cells[0].innerHTML;
      localStorage.setItem('chartId', chartId);
      window.location.href = 'ViewPatientChart.html';
    };
  }
}

// Used this function in PatientListPage html file for Add Patient button click
// eslint-disable-next-line no-unused-vars
function addPatientPageButton() {
  window.location.href = 'AddPatientPage.html';
}

// Used this function in PatientChartListPage html file for Patient List button click
// eslint-disable-next-line no-unused-vars
function onclickPatientListButton() {
  window.location.href = 'PatientListPage.html';
}

// Used this function in AddPatientPage html file for close button click
// eslint-disable-next-line no-unused-vars
function onClickCloseButton() {
  window.location.href = 'PatientListPage.html';
}

// Used this function in AddPatientPage html file for on blur of all fields
// eslint-disable-next-line no-unused-vars
function onBlurAddPatientField() {
  const firstname = document.getElementById('patientFirstname').value;
  const lastname = document.getElementById('patientLastname').value;
  const selectedGender = document.getElementById('gender').value;
  if (firstname.trim().length !== 0 && lastname.trim().length !== 0
      && document.getElementById('dob').value.length !== 0 && selectedGender.trim().length !== 0) {
    document.getElementById('addPatientMessage').innerHTML = '';
  }
  if (firstname.trim().length !== 0) {
    document.getElementById('patientFirstname').className = document.getElementById('patientFirstname').className.replace('errorInput', 'commonInput');
  }
  if (lastname.trim().length !== 0) {
    document.getElementById('patientLastname').className = document.getElementById('patientLastname').className.replace('errorInput', 'commonInput');
  }
  if (document.getElementById('dob').value.length !== 0) {
    document.getElementById('dob').className = document.getElementById('dob').className.replace('errorInput', 'commonInput');
  }
  if (selectedGender.trim().length !== 0) {
    document.getElementById('gender').className = document.getElementById('gender').className.replace('errorInput', 'commonInput');
  }
}

// Used this function in AddPatientPage html file for Add Patient button click
// eslint-disable-next-line no-unused-vars
function onClickAddPatientButton() {
  const patientFirstname = document.getElementById('patientFirstname').value;
  const patientLastname = document.getElementById('patientLastname').value;
  const dob = document.getElementById('dob').value;
  const gender = document.getElementById('gender').value;
  let today = new Date();
  const dd = String(today.getDate()).padStart(2, '0');
  const mm = String(today.getMonth() + 1).padStart(2, '0');
  const yyyy = today.getFullYear();
  today = `${yyyy}-${mm}-${dd}`;

  if (patientFirstname.trim().length === 0 && patientLastname.trim().length === 0
      && dob.length === 0 && gender.trim().length === 0) {
    document.getElementById('addPatientMessage').innerHTML = 'Please Enter All Details';
    document.getElementById('patientFirstname').className = document.getElementById('patientFirstname').className.replace('commonInput', 'errorInput');
    document.getElementById('patientLastname').className = document.getElementById('patientLastname').className.replace('commonInput', 'errorInput');
    document.getElementById('dob').className = document.getElementById('dob').className.replace('commonInput', 'errorInput');
    document.getElementById('gender').className = document.getElementById('gender').className.replace('commonInput', 'errorInput');
  } else if (patientFirstname.trim().length === 0 || patientLastname.trim().length === 0
      || dob.length === 0 || gender.trim().length === 0) {
    let msg = 'Please Enter -';
    let count = 0;
    if (patientFirstname.trim().length === 0) {
      document.getElementById('patientFirstname').className = document.getElementById('patientFirstname').className.replace('commonInput', 'errorInput');
      msg = `${msg} First Name`;
      count += 1;
    }
    if (patientLastname.trim().length === 0) {
      document.getElementById('patientLastname').className = document.getElementById('patientLastname').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        msg = `${msg},`;
      }
      count += 1;
      msg = `${msg} Last Name`;
    }
    if (dob.length === 0) {
      document.getElementById('dob').className = document.getElementById('dob').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        msg = `${msg},`;
      }
      count += 1;
      msg = `${msg} Date of Birth`;
    }
    if (gender.trim().length === 0) {
      document.getElementById('gender').className = document.getElementById('gender').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        msg = `${msg},`;
      }
      msg = `${msg} Gender`;
    }
    document.getElementById('addPatientMessage').innerHTML = msg;
  } else if (dob > today) {
    document.getElementById('addPatientMessage').innerHTML = 'Invalid Date of Birth';
  } else {
    spinner();
    const header = {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
    };
    // eslint-disable-next-line no-undef
    axios
      .post('http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/addPatient', {
        firstname: patientFirstname,
        lastname: patientLastname,
        dateOfBirth: dob,
        gender,
      }, {
        headers: header,
      })
      .then((response) => {
        if (response.status === 200) {
          document.getElementById('patientFirstname').value = '';
          document.getElementById('patientLastname').value = '';
          document.getElementById('dob').value = '';
          document.getElementById('gender').value = '';
          hideSpinner();
          document.getElementById('addPatientMessage').innerHTML = 'Patient Added Successfully.';
        }
      })
      .catch(() => {
        hideSpinner();
        document.getElementById('addPatientMessage').innerHTML = 'Something Went Wrong, Please try again!';
      });
  }
}

// Used this function in PatientChartList html file for Add Chart button click
// eslint-disable-next-line no-unused-vars
function onclickAddChartButton() {
  window.location.href = 'AddPatientChart.html';
}

// Used this function in AddPatientChart html file for onload
// eslint-disable-next-line no-unused-vars
function onloadAddChart() {
  document.getElementById('dateTime').innerHTML = getDateTime();
  pageHeader();
}

// Used this function in AddPatientChart and ViewPatientChart html file for on blur of every field
// eslint-disable-next-line no-unused-vars
function onBlurChartField() {
  const systolicBP = document.getElementById('patientSystolicBP').value;
  const diastolicBP = document.getElementById('patientDiastolicBP').value;
  const patientRespiration = document.getElementById('patientRespiration').value;
  const patientPulse = document.getElementById('patientPulse').value;
  const patientTemp = document.getElementById('patientTemperature').value;

  if (systolicBP.trim().length !== 0 && diastolicBP.trim().length !== 0
      && patientRespiration.trim().length !== 0 && patientPulse.trim().length !== 0
      && patientTemp.trim().length !== 0) {
    document.getElementById('chartMessage').innerHTML = '';
  }
  if (systolicBP.trim().length !== 0) {
    document.getElementById('patientSystolicBP').className = document.getElementById('patientSystolicBP').className.replace('errorInput', 'commonInput');
  }
  if (diastolicBP.trim().length !== 0) {
    document.getElementById('patientDiastolicBP').className = document.getElementById('patientDiastolicBP').className.replace('errorInput', 'commonInput');
  }
  if (patientRespiration.trim().length !== 0) {
    document.getElementById('patientRespiration').className = document.getElementById('patientRespiration').className.replace('errorInput', 'commonInput');
  }
  if (patientPulse.trim().length !== 0) {
    document.getElementById('patientPulse').className = document.getElementById('patientPulse').className.replace('errorInput', 'commonInput');
  }
  if (patientTemp.trim().length !== 0) {
    document.getElementById('patientTemperature').className = document.getElementById('patientTemperature').className.replace('errorInput', 'commonInput');
  }
}

// Used this function in AddPatientChart html file for submit button click
// eslint-disable-next-line no-unused-vars
function ChartSubmitButton(clickedId) {
  const pId = localStorage.getItem('pId');
  const cId = localStorage.getItem('chartId');
  const chartName = document.getElementById('chartName').innerHTML;
  const dateTime = document.getElementById('dateTime').innerHTML;
  const systolicBP = document.getElementById('patientSystolicBP').value;
  const diastolicBP = document.getElementById('patientDiastolicBP').value;
  const patientRespiration = document.getElementById('patientRespiration').value;
  const patientPulse = document.getElementById('patientPulse').value;
  const patientTemp = document.getElementById('patientTemperature').value;

  if (systolicBP.trim().length === 0 && diastolicBP.trim().length === 0
      && patientRespiration.trim().length === 0 && patientPulse.trim().length === 0
      && patientTemp.trim().length === 0) {
    document.getElementById('chartMessage').innerHTML = 'Please Enter All Details';
    document.getElementById('patientSystolicBP').className = document.getElementById('patientSystolicBP').className.replace('commonInput', 'errorInput');
    document.getElementById('patientDiastolicBP').className = document.getElementById('patientDiastolicBP').className.replace('commonInput', 'errorInput');
    document.getElementById('patientRespiration').className = document.getElementById('patientRespiration').className.replace('commonInput', 'errorInput');
    document.getElementById('patientPulse').className = document.getElementById('patientPulse').className.replace('commonInput', 'errorInput');
    document.getElementById('patientTemperature').className = document.getElementById('patientTemperature').className.replace('commonInput', 'errorInput');
  } else if (systolicBP.trim().length === 0 || diastolicBP.trim().length === 0
      || patientRespiration.trim().length === 0 || patientPulse.trim().length === 0
      || patientTemp.trim().length === 0) {
    let msg = 'Please Enter -';
    let count = 0;
    if (systolicBP.trim().length === 0) {
      document.getElementById('patientSystolicBP').className = document.getElementById('patientSystolicBP').className.replace('commonInput', 'errorInput');
      msg = `${msg} Systolic Pressure`;
      count += 1;
    }
    if (diastolicBP.trim().length === 0) {
      document.getElementById('patientDiastolicBP').className = document.getElementById('patientDiastolicBP').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        msg = `${msg},`;
      }
      count += 1;
      msg = `${msg} Diastolic Pressure`;
    }
    if (patientRespiration.trim().length === 0) {
      document.getElementById('patientRespiration').className = document.getElementById('patientRespiration').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        msg = `${msg},`;
      }
      count += 1;
      msg = `${msg} Respiration Rate`;
    }
    if (patientPulse.trim().length === 0) {
      document.getElementById('patientPulse').className = document.getElementById('patientPulse').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        msg = `${msg},`;
      }
      count += 1;
      msg = `${msg} Pulse Rate`;
    }
    if (patientTemp.trim().length === 0) {
      document.getElementById('patientTemperature').className = document.getElementById('patientTemperature').className.replace('commonInput', 'errorInput');
      if (count !== 0) {
        msg = `${msg},`;
      }
      msg = `${msg} Temperature`;
    }
    document.getElementById('chartMessage').innerHTML = msg;
  } else if (clickedId === 'submitChart') {
    spinner();
    const header = {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
    };
    // eslint-disable-next-line no-undef
    axios
      .post('http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/addPatientChart', {
        patientId: pId,
        chartName,
        dateTime,
        bloodPressureSP: systolicBP,
        bloodPressureDP: diastolicBP,
        respiration: patientRespiration,
        pulse: patientPulse,
        temperature: patientTemp,
      }, {
        headers: header,
      })
      .then((response) => {
        if (response.status === 200) {
          hideSpinner();
          document.getElementById('chartMessage').innerHTML = 'Chart Added Successfully.';
          document.getElementById('patientSystolicBP').value = '';
          document.getElementById('patientDiastolicBP').value = '';
          document.getElementById('patientRespiration').value = '';
          document.getElementById('patientPulse').value = '';
          document.getElementById('patientTemperature').value = '';
        }
      })
      .catch(() => {
        hideSpinner();
        document.getElementById('chartMessage').innerHTML = 'Something Went Wrong, Please try again!';
      });
  } else if (clickedId === 'updateChart') {
    spinner();
    const header = {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
    };
    // eslint-disable-next-line no-undef
    axios
      .post('http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/updatePatientChart', {
        patientId: pId,
        chartId: cId,
        dateTime,
        bloodPressureSP: systolicBP,
        bloodPressureDP: diastolicBP,
        respiration: patientRespiration,
        pulse: patientPulse,
        temperature: patientTemp,
      }, {
        headers: header,
      })
      .then((response) => {
        if (response.status === 200) {
          document.getElementById('chartMessage').innerHTML = 'Chart Updated Successfully.';
          window.location.href = 'ViewPatientChart.html';
        }
      })
      .catch(() => {
        hideSpinner();
        document.getElementById('chartMessage').innerHTML = 'Something Went Wrong, Please try again!';
      });
  }
}

// Used this function in AddPatientChart html file for close button click
// eslint-disable-next-line no-unused-vars
function AddChartCloseButton() {
  window.location.href = 'PatientChartList.html';
}

// Used this function in ViewPatientChart html file for on onload
// eslint-disable-next-line no-unused-vars
function onloadViewChartPage() {
  spinner();
  document.getElementById('dateTime').innerHTML = getDateTime();
  pageHeader();

  const chartId = localStorage.getItem('chartId');
  const header = {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  };
  // eslint-disable-next-line no-undef
  axios
    .get(`http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/PatientChart/${chartId}`, {
      headers: header,
    })
    .then(async (response) => {
      if (response.status === 200) {
        localStorage.setItem('systolicBP', response.data[0].bloodPressureSP);
        localStorage.setItem('diastolicBP', response.data[0].bloodPressureDP);
        localStorage.setItem('respirationRate', response.data[0].respiration);
        localStorage.setItem('pulseRate', response.data[0].pulse);
        localStorage.setItem('temperature', response.data[0].temperature);
        hideSpinner();
        document.getElementById('chartName').innerHTML = response.data[0].chartName;
        document.getElementById('chartDateTime').innerHTML = response.data[0].dateTime;
        document.getElementById('systolicBP').innerHTML = `${response.data[0].bloodPressureSP} ${response.data[0].systolicPressureUnit}`;
        document.getElementById('diastolicBP').innerHTML = `${response.data[0].bloodPressureDP} ${response.data[0].diastolicPressureUnit}`;
        document.getElementById('respirationRate').innerHTML = `${response.data[0].respiration} ${response.data[0].respirationRateUnit}`;
        document.getElementById('pulseRate').innerHTML = `${response.data[0].pulse} ${response.data[0].pulseRateUnit}`;
        document.getElementById('temperature').innerHTML = `${response.data[0].temperature} ${response.data[0].temperatureUnit}`;
      }
    })
    .catch(() => {
      hideSpinner();
      document.getElementById('viewChartMessage').innerHTML = 'Something went wrong, Please try again!';
      document.getElementById('chartDateTime').innerHTML = '';
      document.getElementById('systolicBP').innerHTML = '';
      document.getElementById('diastolicBP').innerHTML = '';
      document.getElementById('respirationRate').innerHTML = '';
      document.getElementById('pulseRate').innerHTML = '';
      document.getElementById('temperature').innerHTML = '';
    });
}

// Used this function in ViewPatientChart html file for Edit Button Click
// eslint-disable-next-line no-unused-vars
function onclickEditButton() {
  window.location.href = 'EditPatientChart.html';
}

// Used this function in EditPatientChart html file for onload
// eslint-disable-next-line no-unused-vars
function onloadEditChart() {
  document.getElementById('dateTime').innerHTML = getDateTime();
  pageHeader();
  document.getElementById('chartName').innerHTML = localStorage.getItem('chartName');
  document.getElementById('patientSystolicBP').value = localStorage.getItem('systolicBP');
  document.getElementById('patientDiastolicBP').value = localStorage.getItem('diastolicBP');
  document.getElementById('patientRespiration').value = localStorage.getItem('respirationRate');
  document.getElementById('patientPulse').value = localStorage.getItem('pulseRate');
  document.getElementById('patientTemperature').value = localStorage.getItem('temperature');
}

// Used this function in EditPatientChart html file for close button click
// eslint-disable-next-line no-unused-vars
function EditChartCloseButton() {
  window.location.href = 'ViewPatientChart.html';
}

// eslint-disable-next-line no-unused-vars
function sortPatientTable(id) {
  let columnName;
  let sortOrder;
  if (id === 'idUp' || id === 'idDown') {
    columnName = 'patientId';
    if (id === 'idUp') {
      sortOrder = 'ascending';
      document.getElementById('idUp').style.color = 'royalblue';
      document.getElementById('idDown').style.color = 'lightgrey';
      document.getElementById('nameUp').style.color = 'lightgrey';
      document.getElementById('nameDown').style.color = 'lightgrey';
      document.getElementById('ageUp').style.color = 'lightgrey';
      document.getElementById('ageDown').style.color = 'lightgrey';
    } else if (id === 'idDown') {
      sortOrder = 'descending';
      document.getElementById('idUp').style.color = 'lightgrey';
      document.getElementById('idDown').style.color = 'royalblue';
      document.getElementById('nameUp').style.color = 'lightgrey';
      document.getElementById('nameDown').style.color = 'lightgrey';
      document.getElementById('ageUp').style.color = 'lightgrey';
      document.getElementById('ageDown').style.color = 'lightgrey';
    }
  } else if (id === 'nameUp' || id === 'nameDown') {
    columnName = 'patientName';
    if (id === 'nameUp') {
      sortOrder = 'ascending';
      document.getElementById('nameUp').style.color = 'royalblue';
      document.getElementById('nameDown').style.color = 'lightgrey';
      document.getElementById('idUp').style.color = 'lightgrey';
      document.getElementById('idDown').style.color = 'lightgrey';
      document.getElementById('ageUp').style.color = 'lightgrey';
      document.getElementById('ageDown').style.color = 'lightgrey';
    } else if (id === 'nameDown') {
      sortOrder = 'descending';
      document.getElementById('nameUp').style.color = 'lightgrey';
      document.getElementById('nameDown').style.color = 'royalblue';
      document.getElementById('idUp').style.color = 'lightgrey';
      document.getElementById('idDown').style.color = 'lightgrey';
      document.getElementById('ageUp').style.color = 'lightgrey';
      document.getElementById('ageDown').style.color = 'lightgrey';
    }
  } else if (id === 'ageUp' || id === 'ageDown') {
    columnName = 'age';
    if (id === 'ageUp') {
      sortOrder = 'ascending';
      document.getElementById('ageUp').style.color = 'royalblue';
      document.getElementById('ageDown').style.color = 'lightgrey';
      document.getElementById('idUp').style.color = 'lightgrey';
      document.getElementById('idDown').style.color = 'lightgrey';
      document.getElementById('nameUp').style.color = 'lightgrey';
      document.getElementById('nameDown').style.color = 'lightgrey';
    } else if (id === 'ageDown') {
      sortOrder = 'descending';
      document.getElementById('ageUp').style.color = 'lightgrey';
      document.getElementById('ageDown').style.color = 'royalblue';
      document.getElementById('idUp').style.color = 'lightgrey';
      document.getElementById('idDown').style.color = 'lightgrey';
      document.getElementById('nameUp').style.color = 'lightgrey';
      document.getElementById('nameDown').style.color = 'lightgrey';
    }
  }
  let patients = {};
  const header = {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  };
  // eslint-disable-next-line no-undef
  axios
    .get(`http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/SortedPatientList/columnName/${columnName}/sortOrder/${sortOrder}`, {
      headers: header,
    })
    .then(async (response) => {
      patients = await response.data;
      // eslint-disable-next-line no-undef
      $(() => {
        // eslint-disable-next-line no-undef
        $('#patientListTable').bootstrapTable('load', patients);
      });
    })
    .catch((err) => {
      patients = err;
      // eslint-disable-next-line no-undef
      $(() => {
        // eslint-disable-next-line no-undef
        $('#patientListTable').bootstrapTable('load', patients);
      });
    });
}

// eslint-disable-next-line no-unused-vars
function sortChartTable(id) {
  const viewChartButton = '<button class="viewChartButton" onclick="onClickViewChartButton()">View Chart</button>';
  let columnName;
  let sortOrder;
  if (id === 'dateTimeUp' || id === 'dateTimeDown') {
    columnName = 'dateTime';
    if (id === 'dateTimeUp') {
      sortOrder = 'ascending';
      document.getElementById('dateTimeUp').style.color = 'royalblue';
      document.getElementById('dateTimeDown').style.color = 'lightgrey';
    } else if (id === 'dateTimeDown') {
      sortOrder = 'descending';
      document.getElementById('dateTimeUp').style.color = 'lightgrey';
      document.getElementById('dateTimeDown').style.color = 'royalblue';
    }
  }
  let patientCharts = {};
  const header = {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  };
  // eslint-disable-next-line no-undef
  axios
    .get(`http://localhost:8000/QuickChart-1.0.0-SNAPSHOT/service/QuickChart/SortedChartList/${localStorage.getItem('pId')}/columnName/${columnName}/sortOrder/${sortOrder}`, {
      headers: header,
    })
    .then(async (response) => {
      patientCharts = await response.data;
      for (let i = 0; i < patientCharts.length; i += 1) {
        patientCharts[i].Sno = i + 1;
        patientCharts[i].viewChart = viewChartButton;
      }
      // eslint-disable-next-line no-undef
      $(() => {
        // eslint-disable-next-line no-undef
        $('#patientChartList').bootstrapTable('load', patientCharts);
      });
    })
    .catch((err) => {
      patientCharts = err;
      // eslint-disable-next-line no-undef
      $(() => {
        // eslint-disable-next-line no-undef
        $('#patientChartList').bootstrapTable('load', patientCharts);
      });
    });
}
