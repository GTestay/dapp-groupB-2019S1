import React, { Component } from 'react'
import UserType from '../Types/UserType'
import { confirmAssistance, getInvitationsOf } from '../api/userApi'
import { InvitationDescription } from './InvitationDescription'
import { Grid } from 'semantic-ui-react'
import PropTypes from 'prop-types'

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
      <Grid columns={2} celled={'internally'}>
        {this.invitations().map(invitation => this.showInvitation(invitation))}
      </Grid>
    </React.Fragment>
  }

  showInvitation (invitation) {
    return <InvitationDescription
      invitation={invitation}
      goToEvent={this.goToEvent}
      confirmAssistance={this.confirmAssistance}/>
  };

  invitations () {
    return this.state.invitations
  }

  goToEvent = (event) => {
    this.props.onClick(event)
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

PendingInvitations.propTypes = { user: UserType.isRequired, onClick: PropTypes.func }
