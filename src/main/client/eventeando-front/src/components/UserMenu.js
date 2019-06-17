import React, {Component} from "react";
import ImageRounded from "react-rounded-image";
import '../styles/MenuUsuario.css'
import {withRouter} from "react-router-dom";
import {Button} from "semantic-ui-react";

class UserMenu extends Component {
    render() {
        return (
            <div className="usuario-detalle">
                <div>
                    <h4>{this.getUser().name}</h4>
                </div>
                <div className="usuario-retrato">
                    <ImageRounded
                        image={this.getUser().imageUrl}
                        roundedSize="0"
                        imageWidth="75"
                        imageHeight="75"
                    />
                </div>
                <Button onClick={this.handleClick} type="button">{"Add new event!"}</Button>

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