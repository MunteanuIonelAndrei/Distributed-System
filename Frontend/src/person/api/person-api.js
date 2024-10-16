import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";
//import { resetWarningCache } from 'prop-types/checkPropTypes';

const endpoint = {
    person: '/person',
    device: '/device'
};

// function getPersons(callback) {
//     let request = new Request(HOST.backend_api + "/api/v1/admin", {
//         method: 'GET',
//     });
//     console.log(request.url);
//     RestApiClient.performRequest(request, callback);
// }
function getPersons(callback) {
    // Retrieve the JWT token from local storage
    const token = sessionStorage.getItem('jwtToken');

    // Check if the token is available
    if (!token) {
        console.error("No token found in local storage.");
        return;
    }

    let headers = new Headers({
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    });

    let request = new Request(HOST.backend_api + "/api/v1/admin", {
        method: 'GET',
        headers: headers,
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function getAllDevices(callback) {
    // Retrieve the JWT token from local storage or your preferred method of storage
    const token = sessionStorage.getItem('jwtToken'); // Make sure 'jwtToken' is the correct key

    // Check if the token is available
    if (!token) {
        console.error("No token found in local storage.");
        return;
    }

    let headers = new Headers({
        'Authorization': `Bearer ${token}` // Include the token in the Authorization header
    });

    let request = new Request(HOST.backend_api + endpoint.device + "/admin/findAll", {
        method: 'GET',
        headers: headers // Add the headers to the request
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}


function getDevices(id, callback)
{
    const jwtToken = sessionStorage.getItem('jwtToken');

    // Check if the token is available
    if (!jwtToken) {
        console.error("No JWT token found in local storage.");
        return;
    }
      // Create headers with the token
      let headers = new Headers({
        'Authorization': `Bearer ${jwtToken}`
    });

    let request = new Request(HOST.backend_api + endpoint.device+"/user/findAll/"+id, {
        method: 'GET',
        headers: headers

    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);

}

function getPersonById(params, callback){
    let request = new Request(HOST.backend_api + "/api/v1/admin/" + params.id, {
       method: 'GET'
    });

    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function deleteDevice(id, callback) {
    // Retrieve the JWT token from local storage
    const jwtToken = sessionStorage.getItem('jwtToken');

    // Check if the token is available
    if (!jwtToken) {
        console.error("No JWT token found in local storage.");
        return;
    }

    // Create headers with the token
    let headers = new Headers({
        'Authorization': `Bearer ${jwtToken}`
    });

    let request = new Request(HOST.backend_api + endpoint.device +"/admin/"+id, {
        method: 'DELETE',
        headers: headers
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}


function postDevice(id, device, callback) {
    // Retrieve the JWT token from local storage
    const token = sessionStorage.getItem('jwtToken');

    // Check if the token is available
    if (!token) {
        console.error("No token found in local storage.");
        return;
    }

    // Create headers with the token
    let headers = new Headers({
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    });

    // Create the request with the headers that include the token
    let request = new Request(HOST.backend_api + endpoint.device + "/admin/" + id, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(device)
    });

    console.log("URL: " + request.url);

    // Perform the request with the RestApiClient
    RestApiClient.performRequest(request, callback);
}


function updateDevice(id, denumire, address, maxHours, callback) {
    // Retrieve the JWT token from local storage
    const jwtToken = sessionStorage.getItem('jwtToken');

    // Check if the token is available
    if (!jwtToken) {
        console.error("No JWT token found in local storage.");
        return;
    }

    // Create headers with the token
    let headers = new Headers({
        'Authorization': `Bearer ${jwtToken}`
    });

    let request = new Request(HOST.backend_api + endpoint.device +"/admin/"+id+"/"+denumire+"/"+address+"/"+maxHours, {
        method: 'POST',
        headers: headers
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);   
}



function getPersonsByParams(password, role, name, callback)
{
    let request = new Request(HOST.backend_api + endpoint.person + "/" + password +"/"+role+"/"+name, {
        method: 'GET'
    });
    RestApiClient.performRequest(request, callback);
}

// function postPerson(user, callback){
//     let request = new Request(HOST.backend_api + "/api/v1/auth/signup" , {
//         method: 'POST',
//         headers : {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(user)
//     });

//     console.log("URL: " + request.url);

//     RestApiClient.performRequest(request, callback);
// }
// function updatePerson(id, name, password, callback){
//     let request = new Request(HOST.backend_api + "/api/v1/admin" +"/"+id+"/"+name+"/"+password, {
//         method: 'POST'
//     });

//     console.log("URL: " + request.url);

//     RestApiClient.performRequest(request, callback);
// }

function postPerson(user, callback){
    // Retrieve the JWT token from local storage
    const token = sessionStorage.getItem('jwtToken');

    // Check if the token is available
    if (!token) {
        console.error("No token found in local storage.");
        return;
    }

    let headers = new Headers({
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    });

    let request = new Request(HOST.backend_api + "/api/v1/admin/signup", {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function updatePerson(id, name, password, callback){
    // Retrieve the JWT token from local storage
    const token = sessionStorage.getItem('jwtToken');

    // Check if the token is available
    if (!token) {
        console.error("No token found in local storage.");
        return;
    }

    let headers = new Headers({
        'Authorization': `Bearer ${token}`
    });

    let request = new Request(HOST.backend_api + "/api/v1/admin/" + id + "/" + name + "/" + password, {
        method: 'POST',
        headers: headers
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}


// function logIn(id, password, callback)
// {
//     let request = new Request(HOST.backend_api+endpoint.person+"/"+id+"/"+password, 
//     {
//         method: 'GET'
//     })
//     RestApiClient.performRequest(request,callback);
// }

// In your Api_User file

const logIn = (credentials, callback) => {
    fetch(HOST.backend_api + "/api/v1/auth/signin", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    })
    .then(response => response.json().then(data => ({ status: response.status, data })))
    .then(({ status, data }) => callback(data, status))
    .catch(error => callback(null, null, error));
  };
  
  
//   function logIn(user, callback){
//     let request = new Request(HOST.backend_api + "/api/v1/auth/signup" , {
//         method: 'POST',
//         headers : {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(user)
//     });

//     console.log("URL: " + request.url);

//     RestApiClient.performRequest(request, callback);
// }
  

// function deletePerson(id, callback)
// {
//     const headers = {'Authorization': 'Bearer '}
//     let request = new Request(HOST.backend_api+"/api/v1/admin/"+id, 
//     {
//         method: 'DELETE'
//     })
//     RestApiClient.performRequest(request,callback);
// }
function deletePerson(id, callback) {
    // Retrieve the JWT token from local storage
    const token = sessionStorage.getItem('jwtToken');

    // Check if the token is available
    if (!token) {
        console.error("No token found in local storage.");
        return;
    }
    console.log(token);
    console.log(id);

    let headers = new Headers({
        'Authorization': `Bearer ${token}`
    });

    let request = new Request(HOST.backend_api + "/api/v1/admin/" + id, {
        method: 'DELETE',
        headers: headers
        // credentials: 'include' // Uncomment this if needed for credentials
    });

    RestApiClient.performRequest(request, callback);
}


// function verifyPerson(id, callback)
// {
//     let request = new Request(HOST.backend_api+endpoint.person+"/role/"+id, 
//     {
//         method: 'GET'
//     })
//     RestApiClient.performRequest(request,callback);
// }
function verifyPerson(id, callback)
{
    let request = new Request(HOST.backend_api+"/api/v1/auth/role/"+id, 
    {
        method: 'GET'
    })
    RestApiClient.performRequest(request,callback);
}

export {
    getPersons,
    getPersonById,
    getPersonsByParams,
    postPerson,
    logIn,
    updatePerson,
    deletePerson,
    verifyPerson,
    getDevices,
    getAllDevices,
    updateDevice,
    postDevice,
    deleteDevice
};
