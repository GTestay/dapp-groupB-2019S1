import React, {Component} from "react";
import {ListaDeEventos} from "./ListaDeEventos";
import {MenuUsuario} from "./MenuUsuario";

import '../styles/Home.css';

export class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            eventosEnCurso: [],
            misEventos: [],
            eventosPopulares: [],
            usuario: props.location.state.usuario
        };
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
            <div className="home">
                <ListaDeEventos title="Eventos En Curso" eventos={this.getEventosEnCurso()}/>
                <ListaDeEventos title="Mis Eventos" eventos={this.getMisEventos()}/>
                <ListaDeEventos title="Eventos Populares" eventos={this.getEventosPopulares()}/>
                <div className="menu-de-usuario">
                    <MenuUsuario usuario={this.state.usuario}/>
                </div>
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