import React, {Component} from 'react'
import {ExpenseList} from "./ExpenseList";

class ViewEvent extends Component {
  constructor(props, context) {
    super(props, context);
    this.state = {
      event: null
    }
  }

  componentDidMount() {
    const {event, user} = this.props.location.state;
    this.setState({event, user})
  }

  render() {
    return this.state.event ? (
      <div>
        Un Evento
      </div>
    ) : <div/>
  }
}

export default ViewEvent;
