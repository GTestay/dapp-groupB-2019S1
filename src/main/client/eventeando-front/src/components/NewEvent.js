import React, { Component } from 'react'
import { allExpenses } from '../api/expensesApi'
import { ExpenseListForm } from './ExpenseListForm'
import { Container, Dropdown, Form, FormButton, FormInput, FormSelect } from 'semantic-ui-react'
import { createNewEvent } from '../api/eventApi'
import { searchEmails } from '../api/userApi'
import { eventTypes, optionOf } from '../utils/utils'
import moment from 'moment'
import { DateTimeInput } from 'semantic-ui-calendar-react'
import '../styles/Event.css'
import { RenderWhen } from './utilComponents/RenderWhen'
import { ErrorMessage } from './utilComponents/ErrorMessage'
import { FormattedMessage, injectIntl, intlShape } from 'react-intl'
import Loading from './utilComponents/Loading'

export default class NewEvent extends Component {
  constructor (props) {
    super(props)
    this.state = {
      loading: false,
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
    const intl = this.props.intl
    const creatingEvent = intl.formatMessage({ id: 'newEvent.creating', defaultMessage: 'Creating New Event!' })

    return (
      <Container>
        <h3>
          <FormattedMessage id="newEvent.header" defaultMessage='Creating Event'/>
        </h3>

        {this.state.loading ? <Loading message={creatingEvent}/> : this.renderEventForm(intl)}
        {this.renderError()}
      </Container>
    )
  }

  renderError () {
    return <ErrorMessage hidden={this.showError()} onDismiss={this.closeError} value={this.state.errorMessage}/>
  }

  showError () {
    return !this.state.error
  }

    handleChange = (event, { name, value }) => this.setState({ [name]: value });

    renderEventForm (intl) {
      const { description, selectedEventType, selectedGuestsEmails } = this.state
      const buttonContent = intl.formatMessage({
        id: 'newEvent.createEventButton',
        defaultMessage: 'Create Event!'
      })

      return <Form onSubmit={this.createEvent}>
        {this.addDescription(description, intl)}
        {this.selectTypeEvent(selectedEventType, intl)}
        {this.showDayPickerIfNeeded()}
        {this.selectGuestEmails(selectedGuestsEmails)}
        {this.getExpenseList()}

        <FormButton circular content={buttonContent}/>
      </Form>
    }

    addDescription (description, intl) {
      const label = intl.formatMessage({
        id: 'newEvent.description',
        defaultMessage: 'Add Description'
      })

      return (
        <FormInput required
          name='description'
          placeholder='Description'
          label={label}
          value={description}
          onChange={this.handleChange}
        />
      )
    }

    getExpenseList () {
      return <Form.Field>
        <label>
          <div className="total-cost">
            <text>
              <FormattedMessage id="newEvent.expenses" defaultMessage='Expenses:'/>
            </text>
            <text>{this.totalCost()}</text>
          </div>
        </label>
        <ExpenseListForm onItemClick={this.selectExpense} expenses={this.expenses()}/>
      </Form.Field>
    }

    selectGuestEmails (selectedGuestsEmails) {
      return <Form.Field>
        <label>
          <FormattedMessage id="newEvent.guestEmails" defaultMessage='Invite Friends!'/>
        </label>
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

    selectTypeEvent (selectedEventType, intl) {
      const label = intl.formatMessage({
        id: 'newEvent.chooseEventType',
        defaultMessage: 'Choose Event Type'
      })

      return (
        <FormSelect required
          label={label}
          placeholder='Choose'
          selection
          name='selectedEventType'
          value={selectedEventType}
          onChange={this.handleChange}
          options={eventTypes()}
        />
      )
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
      this.setState({ loading: true })

      createNewEvent(this.getJsonEvent())
        .then(event => this.props.history.push({
          pathname: '/home',
          state: { user: this.getUser() }
        }))
        .catch(error => this.setState({ error: true, errorMessage: error }))
        .then(() => this.setState({ loading: false }))
    };

    selectExpense = (anExpense) => {
      let expenseToReplace = this.expenses().find(expense => expense.id === anExpense.id)
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

NewEvent.propTypes = {
  intl: intlShape.isRequired
}

NewEvent = injectIntl(NewEvent)
