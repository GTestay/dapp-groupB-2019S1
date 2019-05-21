import React, {Component} from "react";

export class ListaDeEventos extends Component {
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