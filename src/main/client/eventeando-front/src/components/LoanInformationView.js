import React, { Component } from 'react'
import { withRouter } from 'react-router-dom'
import { Container, Pagination, Segment, Table, Header } from 'semantic-ui-react'
import { FormattedMessage, FormattedDate } from 'react-intl'
import { obtainAllLoans } from '../api/userApi'

class LoanInformationView extends Component {
  constructor (props, context) {
    super(props, context)
    this.state = {
      loanStatus: [],
      activePage: 1,
      totalPages: 1
    }
  }

  componentDidMount () {
    this.fillPage(this.activePage)
  }

  render () {
    let viewHeader = <FormattedMessage id="informationLoan.viewHeader" defaultMessage='Loans in progress'/>

    return (
      <div>
        <Container>
          <Header as='h2'>{viewHeader}</Header>
          <Segment>
            <Table celled striped columns={4}>
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
    let headerLoanDate = <FormattedMessage id="informationLoan.headerLoanDate" defaultMessage='Date'/>
    let headerLoanEmail = <FormattedMessage id="informationLoan.headerLoanEmail" defaultMessage='Email'/>
    let headerOwner = <FormattedMessage id="informationLoan.headerOwner" defaultMessage='Owner'/>
    let headerLoanStatus = <FormattedMessage id="informationLoan.headerLoanStatus" defaultMessage='Remaining Fees'/>
    let headerIndebt = <FormattedMessage id="informationLoan.headerIndebt" defaultMessage='Indebt'/>

    return <Table.Header>
      <Table.Row>
        <Table.HeaderCell>{headerLoanDate}</Table.HeaderCell>
        <Table.HeaderCell>{headerLoanEmail}</Table.HeaderCell>
        <Table.HeaderCell>{headerOwner}</Table.HeaderCell>
        <Table.HeaderCell>{headerLoanStatus}</Table.HeaderCell>
        <Table.HeaderCell>{headerIndebt}</Table.HeaderCell>
      </Table.Row>
    </Table.Header>
  }

  body () {
    return <Table.Body>
      {this.showAllLoanStatus()}
    </Table.Body>
  }

  footer () {
    return <Table.Footer>
      <Table.Row>
        <Table.HeaderCell colSpan='5'>
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

  showLoanStatus (loanStatus) {
    const { email, name, defaulter, remainingFees, date } = loanStatus

    const isDefaulter = defaulter ? 'Defaulter' : 'NotDefaulter'

    return <Table.Row negative={defaulter} key={email}>
      <Table.Cell><FormattedDate value={date}/></Table.Cell>
      <Table.Cell>{email}</Table.Cell>
      <Table.Cell>{name}</Table.Cell>
      <Table.Cell>{remainingFees}</Table.Cell>
      <Table.Cell>{<FormattedMessage id={isDefaulter} defaultMessage={isDefaulter}/>}</Table.Cell>
    </Table.Row>
  }

  showAllLoanStatus () {
    return this.state.loanStatus.map(loanStatus => this.showLoanStatus(loanStatus))
  }

  fillPage (activePage) {
    obtainAllLoans(activePage, 5)
      .then(({ totalPages, loanStatus }) => {
        return this.setState({ totalPages, loanStatus })
      })
  }
}

export default withRouter(LoanInformationView)
