import React, {Component} from "react";
import ExampleComponent from "react-rounded-image";
import '../styles/MenuUsuario.css'
import {withRouter} from "react-router-dom";

class UserMenu extends Component {
    render() {
        return (
            <div className="usuario-detalle">
                <div>
                    <h4>{this.getUser().name}</h4>
                </div>
                <div className="usuario-retrato">
                    <ExampleComponent
                        image={this.getUser().imageUrl}
                        roundedSize="0"
                        imageWidth="75"
                        imageHeight="75"
                    />
                </div>
                <button onClick={this.handleClick} type="button">{"Add new event!"}</button>

            </div>
        )
    }

    handleClick = () => {
        this.props.history.push({
            pathname: "/new-event",
            state: {user: this.getUser()}
        });
    };

    getUser() {
        return this.props.user;
    }
}

export default withRouter(UserMenu)