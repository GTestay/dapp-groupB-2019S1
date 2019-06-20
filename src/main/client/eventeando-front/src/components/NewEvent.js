import React, { Component } from 'react'
import { allExpenses } from '../api/expensesApi'
import { ExpenseList } from './ExpenseList'
import { Container, Dropdown, Form, FormButton, FormInput, FormSelect } from 'semantic-ui-react'
import { createNewEvent } from '../api/eventApi'
import { searchEmails } from '../api/userApi'
import { eventTypes, optionOf } from '../utils/utils'
import moment from 'moment'
import { DateTimeInput } from 'semantic-ui-calendar-react'

import '../styles/Event.css'
import { RenderWhen } from './utilComponents/RenderWhen'
import { ErrorMessage } from './utilComponents/ErrorMessage'

export default class NewEvent extends Component {
  constructor (props) {
    super(props)
    this.state = {
      user: props.location.state.user,
      submitted: false,
      expenses: [],
      guestsEmails: [],
      selectedEventType: null,
      description: '',
      selectedGuestsEmails: [],
      selectedInvitationLimitDate: moment()
    }
  }

  componentDidMount () {
    allExpenses()
      .then((expenses) => expenses.map(event => {
        event.selected = false
        return event
      }))
      .then((expenses) => this.setState({ expenses: expenses }))

    searchEmails('')
      .then(emails => this.setState({ guestsEmails: emails.map(optionOf) }))
  }

  render () {
    return <Container>
      <h3> Creating event!</h3>

      {this.renderEventForm()}
      {this.renderError()}
    </Container>
  }

  renderError () {
    return <ErrorMessage hidden={this.showError()} onDismiss={this.closeError} value={this.state.errorMessage}/>
  }

  showError () {
    return !this.state.error
  }

    handleChange = (event, { name, value }) => this.setState({ [name]: value });

    renderEventForm () {
      const { description, selectedEventType, selectedGuestsEmails } = this.state

      return <Form onSubmit={this.createEvent}>
        {this.addDescription(description)}
        {this.selectTypeEvent(selectedEventType)}
        {this.showDayPickerIfNeeded()}
        {this.selectGuestEmails(selectedGuestsEmails)}
        {this.getExpenseList()}
        <FormButton circular content='Create Event!'/>
      </Form>
    }

    addDescription (description) {
      return <FormInput required placeholder='Description'
        label={'Add a description!'}
        name='description'
        value={description}
        onChange={this.handleChange}
      />
    }

    getExpenseList () {
      return <Form.Field>
        <label>
          <div className="total-cost">
            <text>Expenses:</text>
            <text>{this.totalCost()}</text>
          </div>
        </label>
        <ExpenseList onItemClick={this.selectExpense} expenses={this.expenses()}/>
      </Form.Field>
    }

    selectGuestEmails (selectedGuestsEmails) {
      return <Form.Field>
        <label>Invite Friends!</label>
        <Dropdown
          fluid
          required
          multiple
          search
          selection
          name='selectedGuestsEmails'
          value={selectedGuestsEmails}
          onChange={this.handleChange}
          options={this.state.guestsEmails}
        />
      </Form.Field>
    }

    selectTypeEvent (selectedEventType) {
      return <FormSelect required label="Choose the event type"
        placeholder='Choose'
        selection
        name='selectedEventType'
        value={selectedEventType}
        onChange={this.handleChange}
        options={eventTypes()}
      />
    }

    expenses () {
      return this.state.expenses
    }

    getUser () {
      return this.state.user
    }

    createEvent = () => {
      this.setState({ submitted: true }, () => this.newEvent())
    };

    newEvent = () => {
      createNewEvent(this.getJsonEvent())
        .then(event => this.props.history.push({
          pathname: '/home',
          state: { user: this.getUser() }
        }))
        .catch(error => this.setState({ error: true, errorMessage: error }))
    };

    selectExpense = (event, data) => {
      let expenseToReplace = this.expenses().find(expense => expense.id === data.value.id)
      expenseToReplace.selected = !expenseToReplace.selected
      this.setState({
        expenses: unique([...this.expenses(), expenseToReplace])
      })
    };

    getJsonEvent () {
      const jsonEventBody = {
        type: this.state.selectedEventType,
        organizerEmail: this.getUser().email,
        description: this.state.description,
        guestsEmails: this.selectedEmails(),
        expensesIds: this.selectedExpenses().map(expense => expense.id)
      }
      this.addFieldsForParty(jsonEventBody)
      return jsonEventBody
    }

    addFieldsForParty (jsonEventBody) {
      let dateToSend = moment(this.state.selectedInvitationLimitDate, 'DD-MM-YYYY HH:mm')
      if (this.needsDayPicker()) {
        jsonEventBody['invitationLimitDate'] = dateToSend
      }
    }

    selectedExpenses () {
      return this.state.expenses.filter(expense => expense.selected)
    }

    selectedEmails () {
      return this.state.selectedGuestsEmails
    }

    closeError = (e, data) => {
      this.setState({ error: false, errorMessage: '' })
    };

    totalCost () {
      return <React.Fragment>
            ${this.selectedExpenses()
          .map(expense => expense.cost)
          .reduce((previousValue, currentValue) => previousValue + currentValue, 0)}
      </React.Fragment>
    }

    showDayPickerIfNeeded () {
      return <RenderWhen condition={() => this.needsDayPicker()}
        whenTrueRender={() => this.dayPickerForInvitationLimitDate()}
      />
    }

    dayPickerForInvitationLimitDate () {
      let initialDate = moment()
      return <Form.Field>
        <label>Invitation Limit Date</label>
        <DateTimeInput
          name='selectedInvitationLimitDate'
          value={this.state.selectedInvitationLimitDate}
          initialDate={initialDate}
          onChange={this.handleChange}
          minDate={initialDate}
          popupPosition={'center'}
          localization={moment().locale()}
          closable
        />
      </Form.Field>
    }

    needsDayPicker () {
      return this.state.selectedEventType === 'Party'
    }
}

function unique (anArray) {
  return Array.from(new Set(anArray))
}
