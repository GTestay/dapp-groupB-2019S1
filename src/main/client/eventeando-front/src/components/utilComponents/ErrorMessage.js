import React, { Component } from 'react'
import { Message, MessageContent, MessageHeader } from 'semantic-ui-react'
import * as PropTypes from 'prop-types'

export class ErrorMessage extends Component {
  render () {
    return <Message error hidden={this.props.hidden} onDismiss={this.props.onDismiss}>
      <MessageHeader content={'Oops...'}/>
      <MessageContent content={this.props.hidden || JSON.stringify(this.props.value)}/>
    </Message>
  }
}

ErrorMessage.propTypes = {
  hidden: PropTypes.any,
  onDismiss: PropTypes.func,
  value: PropTypes.any
}
