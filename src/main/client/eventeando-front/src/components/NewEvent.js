import React, {Component} from "react";
import {allExpenses} from "../api/expensesApi";
import {ExpenseList} from "./ExpenseList";
import {Container, Dropdown, Form, FormButton, FormInput, FormSelect, Message, MessageHeader} from "semantic-ui-react";
import {createNewEvent} from '../api/eventApi'
import {searchEmails} from "../api/userApi";
import {eventTypes, optionOf} from "../utils/utils";
import moment from "moment";
import {DateTimeInput} from "semantic-ui-calendar-react";

import '../styles/Event.css';

export default class NewEvent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: props.location.state.user,
            submitted: false,
            expenses: [],
            searchEmail: "",
            guestsEmails: [],
            selectedEventType: null,
            description: '',
            selectedGuestsEmails: [],
            selectedInvitationLimitDate: moment()
        };
    }

    componentDidMount() {
        allExpenses()
            .then((expenses) => expenses.map(event => {
                event.selected = false;
                return event;
            }))
            .then((expenses) => this.setState({expenses: expenses}));

        searchEmails("")
            .then(emails => this.setState({guestsEmails: emails.map(optionOf)}))
    }

    render() {
        return <Container>
            <h3> Creating event!</h3>

            {this.renderEventForm()}
            {this.renderError()}
        </Container>
    }

    renderError() {
        return <Message error hidden={this.showError()} onDismiss={this.closeError}>
            <MessageHeader content={this.showError() || JSON.stringify(this.state.errorMessage)}/>
        </Message>;
    }

    showError() {
        return !this.state.error;
    }

    handleChange = (event, {name, value}) => this.setState({[name]: value});

    renderEventForm() {
        const {description, selectedEventType, searchEmail} = this.state;

        return <Form onSubmit={this.createEvent}>
            <FormInput required placeholder='Description'
                       label={"Add a description!"}
                       name='description'
                       value={description}
                       onChange={this.handleChange}
            />
            {this.selectTypeEvent(selectedEventType)}
            {this.showDayPickerIfNeeded()}
            {this.selectGuestEmails(searchEmail)}
            {this.getExpenseList()}
            <FormButton circular content='Create Event!'/>
        </Form>
    }

    getExpenseList() {
        return <Form.Field>
            <label>
                <div className="total-cost">
                    <text>Expenses:</text>
                    <text>{this.totalCost()}</text>
                </div>
            </label>
            <ExpenseList onItemClick={this.selectExpense} expenses={this.expenses()}/>
        </Form.Field>;
    }

    selectGuestEmails(searchEmail) {
        return <Form.Field>
            <label>Invite Friends!</label>
            <Dropdown
                fluid
                required
                multiple
                search
                selection
                name='searchEmail'
                value={searchEmail}
                onChange={this.addEmail}
                options={this.state.guestsEmails}
            />
        </Form.Field>;
    }

    selectTypeEvent(selectedEventType) {
        return <FormSelect required label="Choose the event type"
                           placeholder='Choose'
                           selection
                           name='selectedEventType'
                           value={selectedEventType}
                           onChange={this.handleChange}
                           options={eventTypes()}
        />;
    }

    expenses() {
        return this.state.expenses
    }

    getUser() {
        return this.state.user;
    }

    createEvent = () => {
        this.setState({submitted: true}, () => this.newEvent())
    };

    newEvent = () => {
        createNewEvent(this.getJsonEvent())
            .then(event => this.props.history.push({
                pathname: "/home",
                state: {user: this.getUser()}
                }))
            .catch(error => this.setState({error: true, errorMessage: error}))
    };

    selectExpense = (event, data) => {
        let expenseToReplace = this.expenses().find(expense => expense.id === data.value.id);
        expenseToReplace.selected = !expenseToReplace.selected;
        this.setState({
            expenses: unique([...this.expenses(), expenseToReplace])
        });
    };

    getJsonEvent() {
        const jsonEventBody = {
            type: this.state.selectedEventType,
            organizerEmail: this.getUser().email,
            description: this.state.description,
            guestsEmails: this.selectedEmails(),
            expensesIds: this.selectedExpenses().map(expense => expense.id),
        };
        this.addFieldsForParty(jsonEventBody);
        return jsonEventBody;
    }

    addFieldsForParty(jsonEventBody) {
        if (this.needsDayPicker()) {
            jsonEventBody["invitationLimitDate"] = this.state.selectedInvitationLimitDate;
        }
    }

    selectedExpenses() {
        return this.state.expenses.filter(expense => expense.selected);
    }

    selectedEmails() {
        return this.state.selectedGuestsEmails;
    }

    addEmail = (event, data) => {
        this.setState({
            [data.name]: data.value,
            selectedGuestsEmails: data.value
        });
    };

    closeError = (e, data) => {
        this.setState({error: false, errorMessage: null})
    };

    totalCost() {
        return <React.Fragment>
            ${this.selectedExpenses()
            .map(expense => expense.cost)
            .reduce(((previousValue, currentValue) => previousValue + currentValue), 0)}
        </React.Fragment>
    }

    showDayPickerIfNeeded() {
        return this.needsDayPicker() ?
            <Form.Field>
                <label>Invitation Limit Date</label>
                <DateTimeInput
                    name='selectedInvitationLimitDate'
                    value={this.state.selectedInvitationLimitDate}
                    onChange={this.handleChange}
                    minDate={moment()}
                    popupPosition={"center"}
                    closable
                />
            </Form.Field>
            :
            <React.Fragment/>;
    }

    needsDayPicker() {
        return this.state.selectedEventType === "Party";
    }
}

function unique(anArray) {
    return Array.from(new Set(anArray))
}

