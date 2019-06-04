import {Component} from "react";
import React from "react";
import ExampleComponent from "react-rounded-image";

import '../styles/MenuUsuario.css'

export class MenuUsuario extends Component {
    render() {
        return (
            <div className="usuario-detalle">
                <div>
                    <h4>{this.props.usuario.name}</h4>
                </div>
                <div className="usuario-retrato">
                    <ExampleComponent
                        image={this.props.usuario.imageUrl}
                        roundedSize="0"
                        imageWidth="75"
                        imageHeight="75"
                    />
                </div>
            </div>
        )
    }
}