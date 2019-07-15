import React, { Component } from 'react'
import { Button, Input } from 'semantic-ui-react'
import { FormattedMessage, injectIntl } from 'react-intl'

class Withdrawal extends Component {
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
          <Button className="primary button compact" fluid onClick={this.newWithdrawal} disabled={this.validAmount()} >
            <FormattedMessage id="userMenu.withdrawal" defaultMessage='Withdraw'/>
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

  newWithdrawal = () => {
    this.props.madeWithdrawalFor(this.state.user, this.state.amount)
    this.props.closeModal()
  };

  setAmount (amount) {
    this.setState({ amount: amount })
  }

  validAmount () {
    return this.state.amount <= 0
  }
}

Withdrawal = injectIntl(Withdrawal)

export default Withdrawal
