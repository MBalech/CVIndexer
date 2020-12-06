import './App.css';
import React from "react";
import {BrowserRouter as Router, Link, Route, Switch} from 'react-router-dom';
import Navbar from 'react-bootstrap/NavBar';
import Nav from 'react-bootstrap/Nav';
import GetAllCvs from "./Composant/GetAllCvs";
import Home from "./Composant/Home";
import InsertFile from "./Composant/InsertFile";
import Research from "./Composant/Research";

class App extends React.Component {
    render () {
        return (
            <Router>
                <Navbar bg="light" expand="lg">
                    <Navbar.Brand href="/">CVThèque</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="mr-auto">
                            <Nav.Link href="/">Home</Nav.Link>
                            <Nav.Link href="/insertCv">Ajouter un cv</Nav.Link>
                            <Nav.Link href="/research">Rechercher des cvs</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
                <Switch>
                    <Route exact path="/" component={GetAllCvs} />
                    <Route exact path="/insertCv" component={InsertFile} />
                    <Route exact path="/research" component={Research} />
                </Switch>
            </Router>
        )
    }
}

export default App;
