import React, {Component} from "react";
import '../styles/EventList.css';
import PropTypes from "prop-types";
import ExpenseType from "../Types/ExpenseType";
import {ListItem, List , ListContent, ListIcon} from 'semantic-ui-react'

export class ExpenseList extends Component {
    render() {
        return (
            <div>
                {this.showExpenses()}
            </div>
        )
    }

    showExpenses() {
        return (this.expenses().length === 0) ? this.renderNoExpenses() : this.renderExpenses();
    }

    renderNoExpenses() {
        return <h4>There are no expenses to show!</h4>;
    }

    renderExpenses() {
        return <div>
            <List divided selection className="evento-listado">
                {this.listExpenses()}
            </List>
        </div>;
    }

    listExpenses() {
        return (this.expenses().map(expense => this.describeExpense(expense)))
    }

    describeExpense(expense) {
        return <ListItem active={expense.selected}
                         onClick={(event, data) => this.props.onItemClick(event, data)}
                         value={expense} key={expense.id}>
            <ListContent>
                {`${expense.name} $${expense.cost}`}
                {expense.selected ? <ListIcon color={"red"}  name={"close circle"}/> : <ListIcon color={"green"} name={"plus circle"}/>}
            </ListContent>
        </ListItem>;
    }

    expenses() {
        return this.props.expenses;
    }
}

ExpenseList.propTypes = {
    expenses: PropTypes.arrayOf(PropTypes.oneOfType([ExpenseType])),
};