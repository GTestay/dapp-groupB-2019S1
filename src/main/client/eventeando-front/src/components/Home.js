import React, { Component } from 'react'
import { EventList } from './EventList'
import '../styles/Home.css'
import { obtainCurrentsEvent, obtainEventsMostPopular, obtainUserEvents } from '../api/eventApi'
import UserMenu from './UserMenu'
import { injectIntl, intlShape } from 'react-intl'
import { PendingInvitations } from './PendingInvitations'
import { Dropdown, DropdownItem, DropdownMenu, Icon, Menu, MenuItem } from 'semantic-ui-react'

export class Home extends Component {
  constructor (props) {
    super(props)
    this.state = {
      currentEvents: [],
      userEvents: [],
      popularEvents: [],
      user: props.location.state.user
    }
  }

  componentDidMount () {
    this.currentEvents()
    this.userEvents()
    this.eventsMostPopular()
  }

  eventsMostPopular () {
    obtainEventsMostPopular().then((events) =>
      this.setState({ popularEvents: events })
    )
  }

  userEvents () {
    obtainUserEvents(this.getUser().id).then((events) =>
      this.setState({ userEvents: events })
    )
  }

  currentEvents () {
    obtainCurrentsEvent().then((events) =>
      this.setState({ currentEvents: events })
    )
  }

  render () {
    const intl = this.props.intl
    const ongoingEventsTitle = intl.formatMessage({
      id: 'home.ongoingEvents',
      defaultMessage: 'Ongoing Events'
    })
    const myEventsTitle = intl.formatMessage({
      id: 'home.myEvents',
      defaultMessage: 'My Events'
    })
    const popularEventsTitle = intl.formatMessage({
      id: 'home.popularEvents',
      defaultMessage: 'Popular Events'
    })
    const invitations = intl.formatMessage({
      id: 'home.invitations',
      defaultMessage: 'Invitations!'
    })

    return (
      <div className="home">
        <Menu>
          <Dropdown item text={invitations}>
            <Dropdown.Menu>
              <PendingInvitations user={this.getUser()}/>
            </Dropdown.Menu>
          </Dropdown>
        </Menu>

        <EventList title={ongoingEventsTitle} events={this.getCurrentEvents()}/>
        <EventList title={myEventsTitle} events={this.getUserEvents()}/>
        <EventList title={popularEventsTitle} events={this.getPopularEvents()}/>
        <div className="user-menu">
          <UserMenu user={this.getUser()}/>
        </div>
      </div>
    )
  }

  getUser () { return this.state.user }

  getUserEvents () { return this.state.userEvents }

  getCurrentEvents () { return this.state.currentEvents }

  getPopularEvents () { return this.state.popularEvents }
}

Home.propTypes = {
  intl: intlShape.isRequired
}

Home = injectIntl(Home)
