import {Component} from "react";
import React from "react";

export class MenuUsuario extends Component {
    render() {
        return (
            <h4>{this.props.nombreDeUsuario}</h4>
        )
    }
}