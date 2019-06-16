import React, {Component} from "react";
import {allExpenses} from "../api/expensesApi";
import {ExpenseList} from "./ExpenseList";

export default class NewEvent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: props.location.state.user,
            expenses: []
        };
    }

    componentDidMount() {
        allExpenses().then((expenses) =>
            this.setState({expenses: expenses})
        );
    }

    render() {
        return <div>
            <div> Creating event! for: {this.getUser().name} </div>
            <ExpenseList expenses={this.expenses()}/>
        </div>
    }

    expenses() {
        return this.state.expenses
    }

    getUser() {
        return this.state.user;
    }
}