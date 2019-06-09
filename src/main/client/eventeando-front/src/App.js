import React, {Component} from 'react';
import {BrowserRouter, Route} from 'react-router-dom'

import './styles/App.css';
import {UserLogin} from "./components/UserLogin";
import {Home} from "./components/Home";

class App extends Component {
    render() {
        return (
            <BrowserRouter>
                <div>
                    <Route exact path="/" component={UserLogin}/>
                    <Route exact path="/home" component={Home}/>
                </div>
            </BrowserRouter>
        );
    }
}

export default App;
