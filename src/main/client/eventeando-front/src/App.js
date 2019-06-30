import React, {Component, Suspense} from 'react'
import { BrowserRouter, Route, Switch } from 'react-router-dom'
import 'semantic-ui-css/semantic.min.css'
import './styles/App.css'
import { UserLogin } from './components/UserLogin'
import Home from './components/Home'
import { Page404 } from './components/Page404'
import NewEvent from './components/NewEvent'
import ViewEvent from "./components/ViewEvent";

class App extends Component {
  render () {
    return (
      <BrowserRouter>
        <Suspense fallback={<p>Cargando</p>}>
          <Switch>
            <Route  exact path="/" component={UserLogin}/>
            <Route exact path="/home" component={Home}/>
            <Route exact path="/new-event" component={NewEvent}/>
            <Route exact path="/view-event" component={ViewEvent}/>
            <Route component={Page404}/>
          </Switch>
        </Suspense>
      </BrowserRouter>
    )
  }
}

export default App
