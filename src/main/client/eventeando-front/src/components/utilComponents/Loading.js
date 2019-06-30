import React, { Component } from 'react'
import { Dimmer, Loader } from 'semantic-ui-react'
import PropTypes from 'prop-types'

class Loading extends Component {
  render () {
    return (
      <div>
        <Dimmer active>
          <Loader size='massive'> {this.props.message}</Loader>
        </Dimmer>
      </div>
    )
  }
}

Loading.propTypes = { message: PropTypes.string }

export default Loading
