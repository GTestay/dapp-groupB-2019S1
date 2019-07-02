import React, { Component } from 'react'
import { List, ListContent, ListItem } from 'semantic-ui-react'
import EventType from '../Types/EventType'

export class EventViewExpenses extends Component {
  render () {
    return (this.props.expenses.length === 0) ? this.renderNoExpenses() : this.renderExpenses()
  }

  renderNoExpenses () {
    const intl = this.props.intl
    const noExpenses = intl.formatMessage({
      id: 'noExpenses'
    })
    return <h4>{noExpenses}</h4>
  }

  renderExpenses () {
    return <React.Fragment>
      <List divided horizontal>
        {this.listExpenses()}
      </List>
    </React.Fragment>
  }

  listExpenses () {
    return (this.props.expenses.map(expense => this.describeExpense(expense)))
  }

  describeExpense (expense) {
    return <ListItem key={expense.id}>
      <ListContent header={`${expense.name} $${expense.cost}`}>
      </ListContent>
    </ListItem>
  }
}

EventViewExpenses.propTypes = { event: EventType }
