import React, { Component } from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import 'semantic-ui-css/semantic.min.css'
import './styles/App.css'
import { UserLogin } from './components/UserLogin'
import { Home } from './components/Home'
import { Page404 } from './components/Page404'
import NewEvent from './components/NewEvent'

class App extends Component {
  render () {
    return (
      <BrowserRouter>
        <div>
          <Switch>
            <Route exact path="/" component={UserLogin}/>
            <Route exact path="/home" component={Home}/>
            <Route exact path="/new-event" component={NewEvent}/>
            <Route component={Page404}/>
          </Switch>
        </div>
      </BrowserRouter>
    )
  }
}

export default App
