import React from 'react'
import EventType from '../Types/EventType'

export function ElementListEventDescription (props) {
  function evenDescription () {
    return <p>{` ${props.event.organizer.lastname} ${props.event.type} ${props.event.description}`}</p>
  }

  return <li className="event" key={props.event.id}> {evenDescription()} </li>
}

ElementListEventDescription.propTypes = {
  event: EventType.isRequired
}
