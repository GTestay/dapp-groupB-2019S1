import React, {Component} from "react";
import {allExpenses} from "../api/expensesApi";
import {ExpenseList} from "./ExpenseList";
import {Dropdown, Form, FormButton, FormInput, FormSelect, Message, MessageHeader} from "semantic-ui-react";
import {createNewEvent} from '../api/eventApi'
import {searchEmails} from "../api/userApi";
import {eventTypes, optionOf} from "../utils/utils";
import moment from "moment";
import {DateInput} from "semantic-ui-calendar-react";


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
        return <div>
            <h3> Creating event!</h3>
            {this.renderEventForm()}
            {this.renderError()}
        </div>
    }

    renderError() {
        return <Message error hidden={this.showError()} onDismiss={this.closeError}>
            <MessageHeader content={this.showError() || this.state.errorMessage.name}/>
        </Message>;
    }

    showError() {
        return !this.state.error;
    }

    handleChange = (e, {name, value}) => this.setState({[name]: value});

    renderEventForm() {
        const {description, selectedEventType, searchEmail} = this.state;

        return <Form onSubmit={this.handleSubmit}>
            <FormInput required placeholder='Description' label={"Add a description!"} name='description'
                       value={description}
                       onChange={this.handleChange}
            />
            {this.selectTypeEvent(selectedEventType)}
            {this.selectPartyInvitation()}
            {this.selectGuestEmails(searchEmail)}
            <ExpenseList onItemClick={this.selectExpense} expenses={this.expenses()}/>
            {this.totalCost()}
            <FormButton circular content='Create Event!'/>
        </Form>
    }

    selectGuestEmails(searchEmail) {
        return <Dropdown fluid
                         required
                         multiple
                         search
                         selection
                         name='searchEmail'
                         value={searchEmail}
                         onChange={this.addEmail}
                         options={this.state.guestsEmails}
        />;
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

    handleSubmit = () => {
        this.setState({submitted: true}, () => this.createEvent())
    };

    createEvent = () => {
        createNewEvent(this.getJsonEvent())
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
        if (jsonEventBody.type === "Party") {
            jsonEventBody["invitationLimitDate"] = this.state.selectedInvitationLimitDate;
        }
        return jsonEventBody;
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
        return <div>
            ${this.selectedExpenses()
            .map(expense => expense.cost)
            .reduce(((previousValue, currentValue) => previousValue + currentValue), 0)}
        </div>
    }

    selectPartyInvitation() {
        return this.state.selectedEventType === "Party" ? <div>
            <DateInput
                name='selectedInvitationLimitDate'
                value={this.state.selectedInvitationLimitDate}
                onChange={this.handleChange}
            />
        </div> : <React.Fragment/>;
    }
}

function unique(anArray) {
    return Array.from(new Set(anArray))
}

