import React, { Component } from 'react'
import { FormattedMessage } from 'react-intl'

export class Page404 extends Component {
  render () {
    return (
      <h1>
        <FormattedMessage id="page404.errorMessage" defaultMessage='Ooops... Page Not Found!'/>
      </h1>
    )
  }
}
