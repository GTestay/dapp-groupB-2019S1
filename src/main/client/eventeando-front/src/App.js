import React, {Component} from 'react';

import GoogleLogin from 'react-google-login';
import {BrowserRouter, Route} from 'react-router-dom'

import './App.css';

class UserLogin extends Component {
    render() {
        return <div className="App">
            <p>
                ¡Eventeando!
            </p>

            <GoogleLogin
                clientId="195708574425-b6o1u34k6e1cur7mnviq2dhr9fta7qs0.apps.googleusercontent.com"
                buttonText="Login"
                onSuccess={this.redirectToWithGoogleResponse}
                onFailure={this.invalidLoginWithGoogleResponse}
                cookiePolicy={"single_host_origin"}>
            </GoogleLogin>
        </div>;
    }

    redirectToWithGoogleResponse = (response) => {
        console.log(response);
        this.props.history.push("/home");
    };

    invalidLoginWithGoogleResponse = (response) => {
        console.log(response);
    };
}

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            eventosEnCurso: [],
            misEventos: [],
            eventosPopulares: []
        }
    }

    componentDidMount() {
        this.setState(
            {
                eventosEnCurso: ["Evento En Curso 1"],
                misEventos: ["Mi Evento 1", "Mi Evento 2"],
                eventosPopulares: ["Un Evento Popular 1", "Un Evento Popular 2", "Un Evento Popular 3"]
            }
        )
    }

    render() {
        return (
            <div>
                <ListaDeEventos title="Eventos En Curso" eventos={this.getEventosEnCurso()}/>
                <ListaDeEventos title="Mis Eventos" eventos={this.getMisEventos()}/>
                <ListaDeEventos title="Eventos Populares" eventos={this.getEventosPopulares()}/>
            </div>
        )
    }

    getMisEventos() {
        return this.state.misEventos;
    }

    getEventosEnCurso() {
        return this.state.eventosEnCurso;
    }

    getEventosPopulares() {
        return this.state.eventosPopulares;
    }
}

class ListaDeEventos extends Component {
    render() {
        return (
            <div>
                <h3>{this.props.title}</h3>
                <ul>
                    {this.listarEventos()}
                </ul>
            </div>
        )
    }

    listarEventos() {
        return (this.props.eventos.map(evento => <li> {evento} </li>))
    }
}

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
