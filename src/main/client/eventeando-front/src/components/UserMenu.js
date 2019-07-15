import React, { Component } from 'react'
import ImageRounded from 'react-rounded-image'
import '../styles/MenuUsuario.css'
import { withRouter } from 'react-router-dom'
import { Button, Modal } from 'semantic-ui-react'
import { FormattedMessage } from 'react-intl'
import { balance, madeDepositByCashFor, madeDepositByCreditCardFor, madeWithdrawalFor, newLoanFor } from '../api/userApi'
import DepositByCash from './DepositByCash'
import DepositByCreditCard from './DepositByCreditCard'
import Withdrawal from './Withdrawal'

class UserMenu extends Component {
  constructor (props) {
    super(props)
    this.state = {
      balance: 0,
      showCashModal: false,
      showCreditCardModal: false,
      showWithdrawalModal: false
    }
  }

  componentDidMount () {
    this.userBalance()
  }

  render () {
    const intl = this.props.intl
    const balance = intl.formatMessage({
      id: 'userMenu.balance',
      defaultMessage: 'Balance: $'
    })
    const madeDepositByCash = intl.formatMessage({
      id: 'userMenu.madeDepositByCash',
      defaultMessage: 'Deposit By Cash'
    })
    const madeDepositByCreditCard = intl.formatMessage({
      id: 'userMenu.madeDepositByCreditCard',
      defaultMessage: 'Deposit By Credit Card'
    })
    const madeWithdrawal = intl.formatMessage({
      id: 'userMenu.creditWithdraw',
      defaultMessage: 'Credit Withdraw'
    })
    const { showCashModal, showCreditCardModal, showWithdrawalModal } = this.state

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
            <Button className="ui primary button compact" onClick={this.openCashModal}>
              {madeDepositByCash}
            </Button>
            <Modal
              header={madeDepositByCash}
              size='tiny'
              content={<DepositByCash user={this.getUser()} closeModal={this.closeCashModal} madeDepositByCashFor={this.madeDepositByCashFor}/>}
              closeIcon
              open={showCashModal}
              closeOnEscape
              closeOnDimmerClick
              onClose={this.closeCashModal}
            />
            <Button className="ui primary button compact" onClick={this.openCreditCardModal}>
              {madeDepositByCreditCard}
            </Button>
            <Modal
              header={madeDepositByCreditCard}
              size='small'
              content={<DepositByCreditCard user={this.getUser()} closeModal={this.closeCreditCardModal} madeDepositByCreditCardFor={this.madeDepositByCreditCardFor}/>}
              closeIcon
              open={showCreditCardModal}
              closeOnEscape
              closeOnDimmerClick
              onClose={this.closeCreditCardModal}
            />
            <Button className="ui primary button compact" onClick={this.openWithdrawalModal}>
              {madeWithdrawal}
            </Button>
            <Modal
              header={madeWithdrawal}
              size='tiny'
              content={<Withdrawal user={this.getUser()} closeModal={this.closeWithdrawalModal} madeWithdrawalFor={this.madeWithdrawalFor}/>}
              closeIcon
              open={showWithdrawalModal}
              closeOnEscape
              closeOnDimmerClick
              onClose={this.closeWithdrawalModal}
            />
            <Button onClick={this.newEvent} className="ui primary button compact">
              <FormattedMessage id="userMenu.newEventButton" defaultMessage='Add Event'/>
            </Button>
          </Button.Group>
        </div>
      </div>
    )
  }

  openCashModal = () => this.setState({ showCashModal: true })

  closeCashModal = () => this.setState({ showCashModal: false })

  openCreditCardModal = () => this.setState({ showCreditCardModal: true })

  closeCreditCardModal = () => this.setState({ showCreditCardModal: false })

  openWithdrawalModal = () => this.setState({ showWithdrawalModal: true })

  closeWithdrawalModal = () => this.setState({ showWithdrawalModal: false })

  madeDepositByCashFor = (user, amount) => {
    madeDepositByCashFor(user, amount).then(() => this.userBalance())
  }

  madeWithdrawalFor = (user, amount) => {
    madeWithdrawalFor(user, amount).then(() => this.userBalance())
  }

  madeDepositByCreditCardFor = (user, amount, dueDate, cardNumber) => {
    madeDepositByCreditCardFor(user, amount, dueDate, cardNumber).then(() => this.userBalance())
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
    return this.state.balance
  }

  newLoan = () => {
    newLoanFor(this.getUser()).then(() => this.userBalance())
  };
}

export default withRouter(UserMenu)
