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
  return postUser(`/login`, jsonUser, googleUser)
}

export function getInvitationsOf (user) {
  return axios.get(`${url}/${user.id}/invitations`)
    .then((response) => response.data)
}

export function confirmAssistance (invitationId) {
  return axios.get('/invitations/' + invitationId + '/confirm')
}
