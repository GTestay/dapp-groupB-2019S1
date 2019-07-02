import React, { Component } from 'react'
import ImageRounded from 'react-rounded-image'
import '../styles/MenuUsuario.css'
import { withRouter } from 'react-router-dom'
import { Button } from 'semantic-ui-react'
import { FormattedMessage } from 'react-intl'
import {balance, newLoanFor} from "../api/userApi";

class UserMenu extends Component {
  constructor(props) {
    super(props);
    this.state ={
      balance :0
    }
  }
  componentDidMount() {
    this.userBalance()
  }

  render () {
    const intl = this.props.intl;
    const balance = intl.formatMessage({
      id: 'userMenu.balance',
      defaultMessage: 'Balance: $'
    });

    return (
      <div className="user-details">
        <div className="menu-vertical">
          <h4>{this.getFullName()}</h4>
        </div>
        <div className="user-picture menu-vertical">
          <ImageRounded
            image={this.getUser().imageUrl}
            roundedSize="0"
            imageWidth="75"
            imageHeight="75"
          />
        </div>
        <div className="menu-vertical">
          <h3>
            {balance} ${this.getUserBalance()}
          </h3>
        </div>
        <div className="menu-vertical">
          <Button.Group vertical size='medium' className="botones-de-accion menu-vertical">
            <Button onClick={this.newLoan} className="ui primary button compact">
              <FormattedMessage id="userMenu.newLoanButton" defaultMessage='Take Loan'/>
            </Button>
            <Button onClick={this.newEvent} className="ui primary button compact">
              <FormattedMessage id="userMenu.newEventButton" defaultMessage='Add Event'/>
            </Button>
          </Button.Group>
        </div>
      </div>
    )
  }

  userBalance () {
    balance(this.getUser()).then((balance) => this.setState({ balance }))
  }

  getFullName () {
    return this.getUser().name + ' ' + this.getUser().lastname
  }

    newEvent = () => {
      this.props.history.push({
        pathname: '/new-event',
        state: { user: this.getUser() }
      })
    };

    getUser () {
      return this.props.user
    }

    getUserBalance () {
      return this.state.balance;
    }

    newLoan = () => {
      newLoanFor(this.getUser()).then(() => this.userBalance())
    };
}

export default withRouter(UserMenu)
