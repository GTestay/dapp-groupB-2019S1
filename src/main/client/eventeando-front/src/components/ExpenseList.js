import React, {Component} from "react";
import '../styles/EventList.css';
import PropTypes from "prop-types";
import ExpenseType from "../Types/ExpenseType";

export class ExpenseList extends Component {
    render() {
        return (
            <div>
                {this.showExpenses()}
            </div>
        )
    }

    showExpenses() {
        if (this.expenses().length === 0) {
            return <h4>There are no expenses to show!</h4>
        }
        return <ul className="evento-listado">
            {this.listExpenses()}
        </ul>;
    }

    listExpenses() {
        return (this.expenses().map(expense => <p>{`${expense.name} $${expense.cost}`} </p>))
    }

    expenses() {
        return this.props.expenses;
    }
}

ExpenseList.propTypes = {
    expenses: PropTypes.arrayOf(PropTypes.oneOfType([ExpenseType])),
};