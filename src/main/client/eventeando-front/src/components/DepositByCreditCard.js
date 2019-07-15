import React, { Component } from 'react'
import { FormattedMessage, injectIntl } from 'react-intl'
import Cards from 'react-credit-cards'
import 'react-credit-cards/es/styles-compiled.css'
import { Button, Input } from 'semantic-ui-react'
import moment from "moment";

class DepositByCreditCard extends Component {
  constructor (props) {
    super(props)
    this.state = {
      user: props.user,
      amount: 0,
      name: '',
      number: '',
      expiry: '',
      cvc: '',
      focused: ''
    }
  }

  handleInputFocus = ({ target }) => {
      this.setState({ focused: target.name })
    };

  handleInputChange = ({ target }) => {
      this.setState({ [target.name]: target.value })
    };

  render () {
      const { name, number, expiry, cvc, focused } = this.state
      const intl = this.props.intl
      const amount = intl.formatMessage({
        id: 'amount',
        defaultMessage: 'Amount..'
      })
      const title = intl.formatMessage({
        id: 'userMenu.madeDepositByCreditCardTitle',
        defaultMessage: 'Enter Your Card Information'
      })
      const cardNumber = intl.formatMessage({
        id: 'cardNumber',
        defaultMessage: 'Card Number'
      })
      const cardName = intl.formatMessage({
        id: 'cardName',
        defaultMessage: 'Name'
      })
      const cardExpiry = intl.formatMessage({
        id: 'cardExpiry',
        defaultMessage: 'Valid Thru'
      })

      return (
        <div className="centrado-vertical deposito-con-tarjeta">
          <div className="formulario-informacion-tarjeta">
            <h4 className="titulo-deposito-tarjeta"> {title} </h4>

            <div className="formulario-deposito-tarjeta">
              <div className="informacion-deposito-tarjeta">
                <div className="centrado-vertical altura-total">
                  <Input
                    name="number"
                    focus
                    placeholder={cardNumber}
                    type='tel'
                    maxLength='16'
                    size='small'
                    onChange={this.handleInputChange}
                    onFocus={this.handleInputFocus}
                  />

                  <Input
                    name="name"
                    focus
                    placeholder={cardName}
                    type="text"
                    size='small'
                    onChange={this.handleInputChange}
                    onFocus={this.handleInputFocus}
                  />

                  <Input
                    name="expiry"
                    focus
                    placeholder={cardExpiry}
                    type="tel"
                    maxLength='4'
                    size='small'
                    onChange={this.handleInputChange}
                    onFocus={this.handleInputFocus}
                  />

                  <Input
                    name="cvc"
                    focus
                    placeholder='CVC'
                    type="text"
                    maxLength='4'
                    size='small'
                    onChange={this.handleInputChange}
                    onFocus={this.handleInputFocus}
                  />
                </div>
              </div>
              <div className="tarjeta-deposito-tarjeta">
                <Cards
                  number={number}
                  name={name}
                  expiry={expiry}
                  cvc={cvc}
                  focused={focused}
                />
              </div>
            </div>
          </div>

          <div className="formulario-monto-botones">
            <Input
              focus
              placeholder={amount}
              icon='dollar'
              type='number'
              min={0}
              size='small'
              onChange={input => this.setAmount(input.target.value)}
            />

            <div className="centrado-vertical botones-transacciones">
              <Button className="primary button compact" onClick={this.newDepositByCreditCard} disabled={this.invalidInformation()} >
                <FormattedMessage id="userMenu.madeDepositButton" defaultMessage='Made Deposit'/>
              </Button>
              <Button className="button compact" onClick={this.closeModal}>
                <FormattedMessage id="cancel" defaultMessage='Cancel'/>
              </Button>
            </div>
          </div>
        </div>
      )
}

  closeModal = () => {
      this.props.closeModal()
    };

  newDepositByCreditCard = () => {
      this.props.madeDepositByCreditCardFor(this.state.user, this.state.amount, this.state.expiry, this.state.number)
      this.props.closeModal()
    };

  invalidInformation () {
      return (this.state.amount <= 0) || this.invalidDueDate() || this.emptyFields()
  }

  invalidDueDate() {
    return moment().isAfter(moment(this.state.expiry, 'MMYY'));
  }

  setAmount (amount) {
      this.setState({ amount: amount })
  }

  emptyFields() {
    return (this.state.name === '') || (this.state.number === '') || (this.state.expiry === '') || (this.state.cvc === '')
  }
}

DepositByCreditCard = injectIntl(DepositByCreditCard)

export default DepositByCreditCard
