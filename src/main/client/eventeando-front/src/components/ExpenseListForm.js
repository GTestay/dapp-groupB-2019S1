import React, { Component } from 'react'
import '../styles/EventList.css'
import PropTypes from 'prop-types'
import ExpenseType from '../Types/ExpenseType'
import { ListItem, List, ListContent, ListIcon } from 'semantic-ui-react'
import { FormattedMessage } from 'react-intl'

export class ExpenseListForm extends Component {
  render () {
    return this.showExpenses()
  }

  showExpenses () {
    return (this.expenses().length === 0) ? this.renderNoExpenses() : this.renderExpenses()
  }

  renderNoExpenses () {
    return <h4><FormattedMessage id={'noExpenses'} defaultMessage="There are no expenses to show!"/></h4>
  }

  renderExpenses () {
    return <div className="border-list">
      <List divided selection>
        {this.listExpenses()}
      </List>
    </div>
  }

  listExpenses () {
    return (this.expenses().map(expense => this.describeExpense(expense)))
  }

  describeExpense (expense) {
    return <ListItem active={expense.selected}
      onClick={() => this.props.onItemClick(expense)}
      value={expense.id.toString()} key={expense.id}>
      <ListContent>
        {`${expense.name} $${expense.cost}`}
        {expense.selected ? <ListIcon color={'red'} name={'close'}/> : <ListIcon color={'green'} name={'plus circle'}/>}
      </ListContent>
    </ListItem>
  }

  expenses () {
    return this.props.expenses
  }
}

ExpenseListForm.defaultProps = {
  expenses: [],
  onItemClick: () => {}
}

ExpenseListForm.propTypes = {
  expenses: PropTypes.arrayOf(ExpenseType)
}
