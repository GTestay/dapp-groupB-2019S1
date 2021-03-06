import axios from 'axios'

const url = '/events'
const headers = {
  'Content-Type': 'application/json'
}

function fetchEvents (url) {
  return axios
    .get(url)
    .then(value => value.data)
}

export function createNewEvent (anEvent) {
  return axios
    .post(url, JSON.stringify(anEvent), { headers: headers })
    .then(value => value.data)
}

export function obtainCurrentsEvent () {
  return fetchEvents(url)
}

export function obtainEventsMostPopular () {
  return fetchEvents('/popularEvents')
}

export function obtainUserEvents (organizerId) {
  return fetchEvents(`${url}/${organizerId}`)
}

export function obtainEventScore(eventId) {
  return fetchEvents(`${url}/${eventId}/score`)
}

