import React, {Component} from "react";
import GoogleLogin from "react-google-login";

import '../styles/Login.css';

export class UserLogin extends Component {
    render() {
        return <div className="App">
            <div>
                <p className={"title center"}>
                    ¡Eventeando!
                </p>
            </div>
            <div className={"login-options center"}>
                <GoogleLogin
                    clientId="195708574425-b6o1u34k6e1cur7mnviq2dhr9fta7qs0.apps.googleusercontent.com"
                    buttonText="Login"
                    onSuccess={this.redirectWithGoogleResponse}
                    onFailure={this.invalidLoginWithGoogleResponse}
                    cookiePolicy={"single_host_origin"}>
                </GoogleLogin>
            </div>
        </div>;
    }

    redirectWithGoogleResponse = (response) => {
        this.props.history.push( {
            pathname: "/home",
            state: { user: response.profileObj }
        });
    };

    invalidLoginWithGoogleResponse = (response) => {
        // Falta ver que hacer en este caso
    };
}