import React from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import NavigationBar from './navigation-bar'
import Home from './home/home';
import PersonContainer from './person/person-container'

import ErrorPage from './commons/errorhandling/error-page';
import styles from './commons/styles/project-style.css';
import Login from './commons/logIn/logIn';
import { SignUp } from './commons/signUp/signup';
import { ManagePerson } from './commons/manageFile';
import UserPage from './commons/device/device';
import ChatPage from './chat/ChatPage';
import UserChatPage from './chat/UserChatPage';
class App extends React.Component {


    render() {

        return (
            <div className={styles.back}>
            <Router>
                <div>
                    <NavigationBar />
                    <Switch>

                        <Route
                            exact
                            path='/home'
                            render={() => <Home/>}
                        />
                        <Route
                            exact
                            path='/admin'
                            render={() => <ManagePerson/>}
                        />
                        <Route
                            exact
                            path='/user'
                            render={() => <UserPage/>}
                        />
                

                        <Route
                            exact
                            path='/person'
                            render={() => <PersonContainer/>}
                        />
                        <Route
                            exact
                            path='/signup'
                            render={() => <SignUp/>}
                        />
                        <Route
                            exact
                            path='/login'
                            render={()=><Login/>}

                            />
                        <Route exact 
                        path="/chat" 
                        render={() => <ChatPage/>}
                        />
                        <Route exact 
                        path="/user-chat" 
                        render={() => <UserChatPage/>}
                        />



                        {/*Error*/}
                        <Route
                            exact
                            path='/error'
                            render={() => <ErrorPage/>}
                        />

                        <Route render={() =><ErrorPage/>} />
                    </Switch>
                </div>
            </Router>
            </div>
        )
    };
}

export default App
