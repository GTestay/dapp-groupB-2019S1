import React, { Component } from 'react'
import PropTypes from 'prop-types'
import '../styles/EventList.css'
import { ElementListEventDescription } from './ElementListEventDescription'
import EventType from '../Types/EventType'
import { FormattedMessage } from 'react-intl'

export class EventList extends Component {
  render () {
    return (
      <div className="event-title">
        <h3>{this.props.title}</h3>
        {this.showEvents()}
      </div>
    )
  }

  showEvents () {
    if (this.events().length === 0) {
      return (
        <h4>
          <FormattedMessage id="eventList.noEventsMessage" defaultMessage="There aren't Events to Show!"/>
        </h4>
      )
    }
    return <ul className="listed-event">
      {this.listEvents()}
    </ul>
  }

  listEvents () {
    return (this.events().map(event => <ElementListEventDescription event={event}/>))
  }

  events () {
    return this.props.events
  }
}

EventList.propTypes = {
  events: PropTypes.arrayOf(EventType),
  title: PropTypes.string.isRequired
}
