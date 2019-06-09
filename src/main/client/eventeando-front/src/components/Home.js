import React, {Component} from "react";
import {ListaDeEventos} from "./ListaDeEventos";
import {UserMenu} from "./UserMenu";

import '../styles/Home.css';
import {obtainEventsMostPopular, obtainCurrentsEvent, obtainUserEvents} from "../api/eventApi";


export class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            currentEvents: [],
            userEvents: [],
            popularEvents: [],
            user: props.location.state.user
        };
    }

    componentDidMount() {
        this.currentEvents();
        this.userEvents();
        this.eventsMostPopular();
    }

    eventsMostPopular() {
        obtainEventsMostPopular().then((events) =>
            this.setState({popularEvents: events})
        );
    }

    userEvents() {
        obtainUserEvents().then((events) =>
            this.setState({userEvents: events})
        );
    }

    currentEvents() {
        obtainCurrentsEvent().then((events) =>
            this.setState({currentEvents: events})
        );
    }

    render() {
        return (
            <div className="home">
                <ListaDeEventos title="Eventos En Curso" eventos={this.getCurrentEvents()}/>
                <ListaDeEventos title="Mis Eventos" eventos={this.getUserEvents()}/>
                <ListaDeEventos title="Eventos Populares" eventos={this.getPopularEvents()}/>
                <div className="menu-de-user">
                    <UserMenu user={this.getUser()}/>
                </div>
            </div>
        )
    }

    getUser() { return this.state.user; }

    getUserEvents() { return this.state.userEvents; }

    getCurrentEvents() { return this.state.currentEvents; }

    getPopularEvents() { return this.state.popularEvents; }
}