import React, { Component } from 'react'
import { withRouter } from 'react-router-dom'
import { Container, Pagination, Segment, Table } from 'semantic-ui-react'
import { FormattedDate, FormattedMessage, FormattedNumber } from 'react-intl'
import { obtainUserTransactions } from '../api/userApi'

class AccountStatusView extends Component {
  constructor (props, context) {
    super(props, context)
    this.state = {
      moneyTransactions: [],
      activePage: 1,
      totalPages: 1
    }
  }

  componentDidMount () {
    const { user } = this.props.location.state
    this.setState({ user }, () => this.obtainUserTransactions())
  }

  obtainUserTransactions () {
    this.fillPage(this.activePage)
  }

  render () {
    return (
      <div>
        <Container>
          <Segment>
            <Table celled striped columns={3}>
              {this.header()}
              {this.body()}
              {this.footer()}
            </Table>
          </Segment>
        </Container>
      </div>
    )
  }

  header () {
    let headerType = <FormattedMessage id="accountStatus.headerType" defaultMessage='Type'/>
    let headerAmount = <FormattedMessage id="accountStatus.headerAmount" defaultMessage='Amount'/>
    let headerDate = <FormattedMessage id="accountStatus.headerDate" defaultMessage='Date'/>

    return <Table.Header>
      <Table.Row>
        <Table.HeaderCell>{headerType}</Table.HeaderCell>
        <Table.HeaderCell>{headerAmount}</Table.HeaderCell>
        <Table.HeaderCell>{headerDate}</Table.HeaderCell>
      </Table.Row>
    </Table.Header>
  }

  body () {
    return <Table.Body>
      {this.showMoneyTransactions()}
    </Table.Body>
  }

  footer () {
    return <Table.Footer>
      <Table.Row>
        <Table.HeaderCell colSpan='3'>
          <Pagination
            activePage={this.state.activePage}
            onPageChange={this.changePage}
            totalPages={this.totalPages()}/>
        </Table.HeaderCell>
      </Table.Row>
    </Table.Footer>
  }

  totalPages () {
    return this.state.totalPages
  }

  changePage = (event, { activePage }) => {
    this.setState({
      activePage: activePage,
      page: this.fillPage(activePage)
    })
  };

  showMoneyTransaction (moneyTransaction) {
    const { id, type, amount, date } = moneyTransaction

    return <Table.Row key={id}>
      <Table.Cell><FormattedMessage id={type} defaultMessage={type} /></Table.Cell>
      <Table.Cell>$<FormattedNumber value={amount} /></Table.Cell>
      <Table.Cell>{<FormattedDate value={date}/>}</Table.Cell>
    </Table.Row>
  }

  showMoneyTransactions () {
    return this.state.moneyTransactions.map(moneyTransaction => this.showMoneyTransaction(moneyTransaction))
  }

  fillPage (activePage) {
    obtainUserTransactions(this.state.user, activePage, 5)
      .then(({ totalPages, moneyTransactions }) => {
        return this.setState({ totalPages, moneyTransactions })
      })
  }
}

export default withRouter(AccountStatusView)
