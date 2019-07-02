import React, { Component } from 'react'
import { Container, Icon, Segment } from 'semantic-ui-react'
import { injectIntl } from 'react-intl'
import { EventViewGuests } from './EventViewGuests'
import { EventViewExpenses } from './EventViewExpenses'
import { SubEventDescription } from './SubEventDescription'
import { HighDivider } from './utilComponents/HighDivider'

class EventView extends Component {
  constructor (props, context) {
    super(props, context)
    this.state = {
      event: null
    }
  }

  componentDidMount () {
    const { event, user } = this.props.location.state
    this.setState({ event, user })
  }

  render () {
    return this.state.event ? (
      <Container>
        <Segment>
          {this.showEventOrganizer()}
          {this.showEventDescription()}
          {this.showEventExtras()}
          {this.showEventGuests()}
          {this.showEventExpensesDetail()}
        </Segment>
      </Container>
    ) : <div/>
  }

  getEvent () {
    return this.state.event
  }

  showEventOrganizer () {
    const { lastname, name } = this.getEvent().organizer
    const intl = this.props.intl
    const eventOrganizerTitle = intl.formatMessage({
      id: 'viewEvent.eventOrganizer'
    })
    return HighDivider(<Icon name="user"/>, `${eventOrganizerTitle} ${name} ${lastname}`, React.Fragment)
  }

  showEventDescription () {
    return <p> {this.getEvent().description}</p>
  }

  showEventGuests () {
    const intl = this.props.intl
    const eventGuestTitle = intl.formatMessage({
      id: 'viewEvent.eventGuests'
    })
    return HighDivider(<Icon name="user"/>, `${eventGuestTitle}`, this.showGuests())
  }

  showEventExpensesDetail () {
    const intl = this.props.intl
    const eventGuestTitle = intl.formatMessage({
      id: 'viewEvent.eventExpenses'
    })
    return HighDivider(<Icon name="clipboard list"/>, `${eventGuestTitle}`, this.showExpenses())
  }

  showGuests () {
    const guests = this.getEvent().guests
    return <EventViewGuests guests={guests}/>
  }

  showExpenses () {
    return <EventViewExpenses expenses={this.getEvent().expenses}/>
  }

  showEventExtras () {
    return <SubEventDescription event={this.getEvent()}/>
  }
}

EventView = injectIntl(EventView)

export default EventView
