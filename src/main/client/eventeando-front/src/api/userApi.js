import axios from 'axios'
import moment from 'moment'

const url = '/users'

const headers = { 'Content-Type': 'application/json' }

function postUser (url, json, googleUser) {
  return axios.post(url, json, { headers: headers })
    .then((response) => response.data)
    .then(user => {
      user['imageUrl'] = googleUser.imageUrl
      return user
    })
}

export function searchEmails (searchEmail) {
  return axios.get(`${url}/emails?email${searchEmail}`)
    .then((response) => response.data)
}

export function createUser (googleUser) {
  const jsonUser = {
    name: googleUser.givenName,
    lastname: googleUser.familyName,
    birthday: moment().format('YYYY-MM-DD'),
    password: 'P4SSW0RD',
    email: googleUser.email
  }
  return postUser(url, jsonUser, googleUser)
}

export function loginUser (googleUser) {
  const jsonUser = {
    email: googleUser.email
  }
  return postUser('/login', jsonUser, googleUser)
}

export function getInvitationsOf (user) {
  return axios.get(`${url}/${user.id}/invitations`)
    .then((response) => response.data)
}

export function confirmAssistance (invitationId) {
  return axios.get('/invitations/' + invitationId + '/confirm')
}

export function balance (user) {
  return axios.get(`${url}/${user.id}/balance`).then((response) => response.data)
}

export function newLoanFor (user) {
  return axios.post(`${url}/${user.id}/takeOutLoan`, { headers: headers }).then((response) => response.data)
}

export function madeDepositByCashFor (user, amount) {
  return axios.post(`${url}/${user.id}/madeDepositByCash`, { amount: amount }, { headers: headers }).then((response) => response.data)
}

export function madeDepositByCreditCardFor (user, amount, dueDate, cardNumber) {
  return axios.post(
    `${url}/${user.id}/madeDepositByCreditCard`,
    { amount: amount, dueDate: dueDate, cardNumber: cardNumber },
    { headers: headers }
  ).then((response) => response.data)
}
