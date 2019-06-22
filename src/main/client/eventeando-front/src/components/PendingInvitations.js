import React, {Component} from 'react'
import UserType from '../Types/UserType'
import {confirmAssistance, getInvitationsOf} from '../api/userApi'
import {InvitationDescription} from "./InvitationDescription";

export class PendingInvitations extends Component {
  constructor (props) {
    super(props)
    this.state = {
      invitations: []
    }
  }

  componentDidMount () {
    getInvitationsOf(this.props.user)
      .then(invitations => this.setState({ invitations: invitations }))
  }

  render () {
    return <React.Fragment>
      {this.invitations().map(invitation => this.showInvitation(invitation))}
    </React.Fragment>
  }

  showInvitation (invitation) {
    return <InvitationDescription invitation={invitation}
      goToEvent={this.goToEvent}
      confirmAssistance={this.confirmAssistance}/>
  };

  invitations () {
    return this.state.invitations
  }

  goToEvent = () => {
    console.log('IR A EVENTO!')
  };

  confirmAssistance = (invitation) => {
    confirmAssistance(invitation.id)
      .then(() =>
        this.deleteInvitationConfirmed(invitation.id)
      )
  };

  deleteInvitationConfirmed (invitationId) {
    this.setState({
      invitations: this.invitations()
        .filter(invitation => invitation.id !== invitationId)
    })
  }
}

PendingInvitations.propTypes = { user: UserType.isRequired }
