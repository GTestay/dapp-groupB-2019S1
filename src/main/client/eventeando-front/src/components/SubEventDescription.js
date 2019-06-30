import React, {Component} from "react";
import moment from "moment";
import {HighDivider} from "./utilComponents/HighDivider";
import {Icon} from "semantic-ui-react";
import {injectIntl} from "react-intl";

export class SubEventDescription extends Component {
  render() {
    return this.props.event.type === "Party" ? this.showLimitDate() : <React.Fragment/>;
  }

  showLimitDate() {
    const intl = this.props.intl
    const partyInvitationLimit = intl.formatMessage({
      id: 'partyDescription.invitationLimitDate'
    })
    moment().locale(intl.locale)
    return HighDivider(<Icon name="calendar check"/>, partyInvitationLimit,
      <label>{moment(this.props.event.invitationLimitDate).format("LLL")}</label>);
  }
}

SubEventDescription = injectIntl(SubEventDescription)
