import React, { Component } from 'react'
import { Button, Input } from 'semantic-ui-react'
import { FormattedMessage, injectIntl } from 'react-intl'

class DepositByCash extends Component {
  constructor (props) {
    super(props)
    this.state = {
      user: props.user,
      amount: 0
    }
  }

  render () {
    const intl = this.props.intl
    const amount = intl.formatMessage({
      id: 'amount',
      defaultMessage: 'Amount..'
    })

    return (
      <div className="centrado-vertical transaccion">
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
          <Button className="primary button compact" fluid onClick={this.newDepositByCash} disabled={this.validAmount()} >
            <FormattedMessage id="userMenu.madeDepositButton" defaultMessage='Made Deposit'/>
          </Button>
          <Button className="button compact" fluid onClick={this.closeModal}>
            <FormattedMessage id="cancel" defaultMessage='Cancel'/>
          </Button>
        </div>
      </div>
    )
  }

  closeModal = () => {
    this.props.closeModal()
  };

  newDepositByCash = () => {
    this.props.madeDepositByCashFor(this.state.user, this.state.amount)
    this.props.closeModal()
  };

  setAmount (amount) {
    this.setState({ amount: amount })
  }

  validAmount () {
    return this.state.amount <= 0
  }
}

DepositByCash = injectIntl(DepositByCash)

export default DepositByCash
