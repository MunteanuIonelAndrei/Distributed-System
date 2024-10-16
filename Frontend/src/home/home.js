import React from 'react';

import BackgroundImg from '../commons/images/112.jpg';

import {Button, Container, Jumbotron} from 'reactstrap';

const backgroundStyle = {
    backgroundPosition: 'center',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    width: "100%",
    height: "100vh",
    backgroundImage: `url(${BackgroundImg})`
};
const textStyle = {color: 'white', };

class Home extends React.Component {


    render() {

        return (

            <div>
                <Jumbotron fluid style={backgroundStyle}>
                    <Container fluid>
                        <h1 className="display-3" style={textStyle}>Energy Management Device</h1>
                        <hr className="my-2"/>
                    </Container>
                </Jumbotron>

            </div>
        )
    };
}

export default Home
