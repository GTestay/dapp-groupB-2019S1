import React, { Component } from 'react'
import { EventList } from './EventList'
import '../styles/Home.css'
import { obtainCurrentsEvent, obtainEventsMostPopular, obtainUserEvents } from '../api/eventApi'
import UserMenu from './UserMenu'
import { injectIntl, intlShape } from 'react-intl'
import { PendingInvitations } from './PendingInvitations'
import { Dropdown, Menu } from 'semantic-ui-react'
import { withRouter } from 'react-router-dom'

class Home extends Component {
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
      <React.Fragment>
        <Menu>
          <Dropdown scrolling item text={invitations}>
            <Dropdown.Menu>
              <PendingInvitations onClick={this.viewEvent} user={this.getUser()}/>
            </Dropdown.Menu>
          </Dropdown>
        </Menu>
        <div className="home">

          <EventList onClick={this.viewEvent} title={ongoingEventsTitle} events={this.getCurrentEvents()}/>
          <EventList onClick={this.viewEvent} title={myEventsTitle} events={this.getUserEvents()}/>
          <EventList onClick={this.viewEvent} title={popularEventsTitle} events={this.getPopularEvents()}/>
          <div className="user-menu">
            <UserMenu user={this.getUser()} intl={intl}/>
          </div>
        </div>
      </React.Fragment>
    )
  }

  getUser () { return this.state.user }

  getUserEvents () { return this.state.userEvents }

  getCurrentEvents () { return this.state.currentEvents }

  getPopularEvents () { return this.state.popularEvents }

  viewEvent = event => {
    this.props.history.push({
      pathname: '/view-event',
      state: { user: this.getUser(), event }
    })
  };
}

Home.propTypes = {
  intl: intlShape.isRequired
}

Home = injectIntl(Home)

export default withRouter(Home)
