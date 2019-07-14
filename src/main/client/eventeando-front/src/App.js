import React, { Component, Suspense } from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import 'semantic-ui-css/semantic.min.css'
import './styles/App.css'
import { UserLogin } from './components/UserLogin'
import Home from './components/Home'
import { Page404 } from './components/Page404'
import NewEvent from './components/NewEvent'
import EventView from './components/EventView'
import AccountStatusView from './components/AccountStatusView'
import 'moment/locale/es'
import Loading from './components/utilComponents/Loading'
import { injectIntl } from 'react-intl'

class App extends Component {
  render () {
    const intl = this.props.intl
    const message = intl.formatMessage({
      id: 'defaultMessageForLoading',
      defaultMessage: 'Loading!'
    })
    return (
      <BrowserRouter>
        <Suspense fallback={<Loading message={message}/>}>
          <Switch>
            <Route exact path="/" component={UserLogin}/>
            <Route exact path="/home" component={Home}/>
            <Route exact path="/new-event" component={NewEvent}/>
            <Route exact path="/view-event" component={EventView}/>
            <Route exact path="/account-status" component={AccountStatusView}/>
            <Route component={Page404}/>
          </Switch>
        </Suspense>
      </BrowserRouter>
    )
  }
}
App = injectIntl(App)

export default App
