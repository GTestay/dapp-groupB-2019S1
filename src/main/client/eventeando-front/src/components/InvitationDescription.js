import React, { Component } from 'react'
import { Button, Container, DropdownItem, Grid, GridColumn, GridRow, HeaderContent, Icon, ItemDescription } from 'semantic-ui-react'
import * as PropTypes from 'prop-types'
import InvitationType from '../Types/InvitationType'

export class InvitationDescription extends Component {
  render () {
    return <DropdownItem>
      <Grid columns={2} celled>
        <GridRow>
          <GridColumn>
            <Container>
              <HeaderContent content={this.fullName(this.props.invitation.event.organizer)}/>
              <ItemDescription content={this.evenDescription()}/>
            </Container>
          </GridColumn>
          <GridColumn stretched>
            <Button.Group>
              <Button onClick={() => this.props.confirmAssistance(this.props.invitation)}>
                <Icon name={'telegram'}/>
              </Button>
              <Button onClick={this.props.goToEvent}>
                <Icon name={'eye'}/>
              </Button>
            </Button.Group>
          </GridColumn>
        </GridRow>
      </Grid>
    </DropdownItem>
  }

  evenDescription () {
    return `${this.props.invitation.event.type} ${this.props.invitation.event.description}`
  }

  fullName (organizer) {
    return `${organizer.name} ${organizer.lastname} `
  }
}

InvitationDescription.propTypes = {
  invitation: PropTypes.objectOf(InvitationType),
  confirmAssistance: PropTypes.func,
  goToEvent: PropTypes.func
}
