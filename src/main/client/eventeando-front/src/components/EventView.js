import React, { Component } from 'react'
import { Container, Icon, Segment } from 'semantic-ui-react'
import { injectIntl } from 'react-intl'
import { EventViewGuests } from './EventViewGuests'
import { EventViewExpenses } from './EventViewExpenses'
import { SubEventDescription } from './SubEventDescription'
import { HighDivider } from './utilComponents/HighDivider'
import { obtainEventScore } from '../api/eventApi'

class EventView extends Component {
  constructor (props, context) {
    super(props, context)
    this.state = {
      event: null,
      eventScore: 0
    }
  }

  componentDidMount () {
    const { event, user } = this.props.location.state
    this.setState({ event, user },() => this.obtainScore(event))
  }

  render () {
    return this.state.event ? (
      <Container>
        <Segment>
          {this.showEventOrganizer()}
          {this.showEventScore()}
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

  showEventScore () {
    const intl = this.props.intl
    const eventOrganizerTitle = intl.formatMessage({
      id: 'viewEvent.score',
      defaultMessage: 'Score'
    })
    return HighDivider(<Icon name="star"/>, `${eventOrganizerTitle}: ${this.state.eventScore}`, React.Fragment)
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

  obtainScore = event => {
    obtainEventScore(event.id).then(eventScore =>
      this.setState({ eventScore })
    )
  };
}

EventView = injectIntl(EventView)

export default EventView
