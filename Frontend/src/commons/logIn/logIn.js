import React, { useState,useEffect } from "react";

import { Button, Container, Form } from "react-bootstrap";
import BackgroundImg from '../images/112.jpg';
import { Link } from "react-router-dom";
import * as Api_User from "../../person/api/person-api";
import { MyPopup } from "../popUp/popUp";
import { MyPopupFail } from "./logInFail";
import { withRouter } from "react-router-dom";
import { Redirect } from "react-router-dom";
import {jwtDecode} from 'jwt-decode';



const backgroundStyle = {
  backgroundPosition: 'center',
  backgroundSize: 'cover',
  backgroundRepeat: 'no-repeat',
  width: "100%",
  height: "100vh",
  backgroundImage: `url(${BackgroundImg})`
};

function Login(props) {
  const [formData, setFormData] = useState({ id: "", password: "" });
  const [showPopup, setShowPopup] = useState(false);
  const [showPopupFail, setShowPopupFail] = useState(false);
  const [redirectTo, setRedirectTo] = useState(null);

  useEffect(() => {
    if (redirectTo) {
      setShowPopup(true);
    }
  }, [redirectTo]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // const handleLogin = () => {
  //   const { id, password } = formData;
  //   Api_User.logIn(id, password, (response, status, error) => {
  //     if (status === 200) {
  //       Api_User.verifyPerson(id, (verifyResponse, verifyStatus, verifyError) => {
  //         if (verifyResponse === true) {
  //           setRedirectTo('/user');
  //         } else if (verifyResponse === false) {
  //           setRedirectTo('/admin');
  //         }
  //       });
  //     } else {
  //       setShowPopupFail(true);
  //     }
  //   });
  // };

  // if (redirectTo) {
  //   return <Redirect to={{ pathname: redirectTo, state: { id: formData.id } }} />;
  // }

  // const handleLogin = () => {
  //   const { id, password } = formData;
  //   Api_User.logIn({ id, password }, (response, status, error) => {
  //     if (status === 200 && response && response.token) {
  //       // Store the JWT token in the local storage
  //       sessionStorage.setItem('jwtToken', response.token);
  
  //       // Decode the JWT token to extract the user ID (sub field)
  //       try {
  //         const decodedToken = jwtDecode(response.token);
  //         const userId = decodedToken.sub; // 'sub' field usually contains the user ID
  //         console.log("User ID extracted from token: ", userId);
  //       } catch (e) {
  //         console.error("Failed to decode token: ", e);
  //       }
  
  //       // Rest of your login success logic...
  //     } else {
  //       // Handle login failure...
  //     }
  //   });
  // };
  
  // // Then, elsewhere in your component, you can use useEffect to react to changes in the state
  // useEffect(() => {
  //   // Check if the popup state is true and show it
  //   if (showPopup) {
  //     // Logic to show popup
  //   }
  // }, [showPopup]);
  
  const handleLogin = () => {
    const { id, password } = formData;
    Api_User.logIn({ id, password }, (response, status, error) => {
      if (status === 200 && response && response.token) {
        // Store the JWT token in the session storage
        sessionStorage.setItem('jwtToken', response.token);
  
        // Decode the JWT token to extract the user ID (sub field)
        let userId;
        try {
          const decodedToken = jwtDecode(response.token);
          userId = decodedToken.sub; // 'sub' field usually contains the user ID
          sessionStorage.setItem("LoginId", userId); // Store user ID in session storage
  
          console.log("User ID extracted from token: ", userId);
          console.log("Token: ", response.token);
        } catch (e) {
          console.error("Failed to decode token: ", e);
          return;
        }
  
        // Verify the person's role with the extracted user ID using Api_User.verifyPerson
        Api_User.verifyPerson(userId, (verifyResponse, verifyStatus, verifyError) => {
          if (verifyStatus === 200) {
            // Logic for successful verification
            console.log("User verified successfully: ", verifyResponse);
            if (verifyResponse === true) {
              sessionStorage.setItem("Role", "user"); // Store user ID in session storage
              console.log('I am a,',sessionStorage.getItem("Role"));
              setRedirectTo('/user');
            } else if (verifyResponse === false) {
              sessionStorage.setItem("Role", "admin"); // Store user ID in session storage
              console.log('I am an',sessionStorage.getItem("Role"));


              setRedirectTo('/admin');
            }
            // Rest of your login success logic...
          } else {
            // Logic for verification failure
            console.error("Failed to verify user: ", verifyError);
            // Handle login failure...
          }
        });
      } else {
        // Handle login failure...
      }
    });
  };
  
  if (redirectTo) {
    return <Redirect to={{ pathname: redirectTo, state: { id: formData.id } }} />;
  }
  
  // const handleLogin = () => {
  //   const { id, password } = formData;
  //   Api_User.logIn({ id, password }, (response, status, error) => {
  //     if (status === 200 && response && response.token) {
  //       // Store the JWT token in the local storage
  //       sessionStorage.setItem('jwtToken', response.token);
  
  //       // Decode the JWT token to extract the user ID (sub field)
  //       let userId;
  //       try {
  //         const decodedToken = jwtDecode(response.token);
  //         userId = decodedToken.sub; // 'sub' field usually contains the user ID
  //         sessionStorage.setItem("LoginId", userId);

  //         console.log("User ID extracted from token: ", userId);
  //         console.log("Token: ",response.token);
  //       } catch (e) {
  //         console.error("Failed to decode token: ", e);
  //         return;
  //       }
  
  //       // Verify the person's role with the extracted user ID using Api_User.verifyPerson
  //       Api_User.verifyPerson(userId, (verifyResponse, verifyStatus, verifyError) => {
  //         if (verifyStatus === 200) {
  //           // Logic for successful verification
  //           console.log("User verified successfully: ", verifyResponse);
  //           if (verifyResponse === true) {
  //                        setRedirectTo('/user');
  //                      } else if (verifyResponse === false) {
  //                       setRedirectTo('/admin');
  //                      }
  //           // Rest of your login success logic...
  //         } else {
  //           // Logic for verification failure
  //           console.error("Failed to verify user: ", verifyError);
  //           // Handle login failure...
  //         }
  //       });
  //     } else {
  //       // Handle login failure...
  //     }
  //   });
  // };

  //  if (redirectTo) {
  //    return <Redirect to={{ pathname: redirectTo, state: { id: formData.id } }} />;
  //  }
  
  // Then, elsewhere in your component, you can use useEffect to react to changes in the state
  // useEffect(() => {
  //   // Check if the popup state is true and show it
  //   if (showPopup) {
  //     // Logic to show popup
  //   }
  // }, [showPopup]);
  



  

  return (
    
    <Container fluid style={backgroundStyle}>
      <Form>
        <Form.Group>
          <Form.Control
            name="id"
            type="text"
            placeholder="ID"
            size="lg"
            value={formData.id}
            onChange={handleInputChange}
          />
        </Form.Group>
        <Form.Group>
          <Form.Control
            name="password"
            type="password"
            placeholder="Password"
            size="lg"
            value={formData.password}
            onChange={handleInputChange}
          />
        </Form.Group>
        <Button
          size="lg"
          variant="primary"
          onClick={handleLogin}
        >
          Login
        </Button>
        <Link to="/signup">
          <Button size="lg" variant="primary">
            SignUp
          </Button>
        </Link>
        {showPopup && <MyPopup />}
      {showPopupFail && <MyPopupFail />}
      </Form>
      {/* <div className="app">
          <ChatBox ></ChatBox>
        </div> */}
    </Container>
    
  );
}

export default withRouter(Login);
