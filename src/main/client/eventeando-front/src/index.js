import React from 'react'
import ReactDOM from 'react-dom'
import './styles/index.css'
import App from './App'
import * as serviceWorker from './serviceWorker'
import { IntlProvider, addLocaleData } from 'react-intl'

import localeEn from 'react-intl/locale-data/en'
import localeEs from 'react-intl/locale-data/es'
import messagesEn from './translations/en.json'
import messagesEs from './translations/es.json'

addLocaleData([...localeEn, ...localeEs])

const messages = {
  'en': messagesEn,
  'es': messagesEs
}

const language = navigator.language.split(/[-_]/)[0] // language without region code

ReactDOM.render(
  <IntlProvider locale={language} messages={messages[language]}>
    <App/>
  </IntlProvider>
  , document.getElementById('root'))

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister()
