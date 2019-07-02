import React, { Component } from 'react'
import { List, ListItem } from 'semantic-ui-react'

export class EventViewGuests extends Component {
  render () {
    return <List horizontal>
      {this.props.guests.map(guest => this.showGuest(guest))}
    </List>
  }

  showGuest (guest) {
    return <ListItem>{`${guest.name} ${guest.lastname}`}</ListItem>
  }
}
