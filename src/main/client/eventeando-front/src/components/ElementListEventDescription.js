import React from 'react'
import EventType from '../Types/EventType'
import {ListItem} from "semantic-ui-react";
import PropTypes from "prop-types";

export function ElementListEventDescription (props) {
  function evenDescription () {
    return <p>{` ${props.event.organizer.lastname} ${props.event.type} ${props.event.description}`}</p>
  }

  return <ListItem onClick={() => props.onClick(props.event)} className="event" key={props.event.id}> {evenDescription()} </ListItem>
}

ElementListEventDescription.propTypes = {
  event: EventType.isRequired,
  onClick: PropTypes.func.isRequired
}
