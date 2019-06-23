import React, { Component } from 'react'
import GoogleLogin from 'react-google-login'

import '../styles/Login.css'
import { createUser, loginUser } from '../api/userApi'
import { ErrorMessage } from './utilComponents/ErrorMessage'

export class UserLogin extends Component {
  constructor (props) {
    super(props)
    this.state = {
      error: false
    }
  }

  render () {
    return <div className="App">
      <div>
        <p className={'title center'}>
                    ¡Eventeando!
        </p>
      </div>
      <div className={'login-options center'}>
        <GoogleLogin
          clientId="195708574425-b6o1u34k6e1cur7mnviq2dhr9fta7qs0.apps.googleusercontent.com"
          buttonText="Login"
          onSuccess={this.redirectWithGoogleResponse}
          onFailure={this.invalidLoginWithGoogleResponse}
          cookiePolicy={'single_host_origin'}>
        </GoogleLogin>
      </div>
      {this.renderError()}

    </div>
  }

    redirectWithGoogleResponse = (response) => {
      loginUser(response.profileObj)
        .catch(() => this.createUser(response.profileObj))
        .then((user) =>
          this.props.history.push({
            pathname: '/home',
            state: { user: user }
          }))
        .catch(error => this.setState({ error: true, errorMessage: error }))
    };

    createUser (googleUser) {
      return createUser(googleUser)
    }

    invalidLoginWithGoogleResponse = (response) => {
      this.setState({ error: true, errorMessage: response })
    };

    renderError () {
      return <ErrorMessage hidden={!this.showError()} onDismiss={this.closeError} value={this.state.errorMessage}/>
    }

    showError () {
      return this.state.error
    }

    closeError = (e, data) => {
      this.setState({ error: false, errorMessage: '' })
    };
}
