import React, {Component} from "react";

import '../styles/ListaDeEventos.css';

export class ListaDeEventos extends Component {
    render() {
        return (
            <div className="titulo-evento">
                <h3>{this.props.title}</h3>
                <ul className="evento-listado">
                    {this.listarEventos()}
                </ul>
            </div>
        )
    }

    listarEventos() {
        return (this.props.eventos.map(evento => <li className="evento"> {evento} </li>))
    }
}