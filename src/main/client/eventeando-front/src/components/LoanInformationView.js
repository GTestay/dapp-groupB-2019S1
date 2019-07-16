import React, { Component } from 'react'
import { withRouter } from 'react-router-dom'
import { Container, Pagination, Segment, Table, Header } from 'semantic-ui-react'
import { FormattedMessage, FormattedDate} from 'react-intl'
import { obtainAllLoans } from '../api/userApi'

class LoanInformationView extends Component {
  constructor (props, context) {
    super(props, context)
    this.state = {
      loans: [],
      activePage: 1,
      totalPages: 1
    }
  }

  componentDidMount () {
    this.obtainUserTransactions()
  }

  obtainUserTransactions () {
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
    let headerOwner  = <FormattedMessage id="informationLoan.headerOwner" defaultMessage='Owner'/>
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
      {this.showUsersStatusLoan()}
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

  showUsersLoanStatus (loan) {
    const {  email, name, defaulter , remainingFees, date } = loan

    const isDefaulter = defaulter ? "Defaulter" : "NotDefaulter";

    return <Table.Row negative={defaulter} key={email}>
      <Table.Cell><FormattedDate value={date}/></Table.Cell>
      <Table.Cell>{email}</Table.Cell>
      <Table.Cell>{name}</Table.Cell>
      <Table.Cell>{remainingFees}/6</Table.Cell>
      <Table.Cell>{<FormattedMessage id={isDefaulter} defaultMessage={isDefaulter}/>}</Table.Cell>
    </Table.Row>
  }

  showUsersStatusLoan () {
    return this.state.loans.map(userStatus => this.showUsersLoanStatus(userStatus))
  }

  fillPage (activePage) {
    obtainAllLoans(activePage, 5)
      .then(({ totalPages,  loansStatus }) => {
        return this.setState({ totalPages, loans: loansStatus })
      })
  }
}

export default withRouter(LoanInformationView)