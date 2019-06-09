import axios from 'axios';

const url = "/events";

function fetchEvents(url) {
    return axios
        .get(url)
        .then(value => value.data);
}

export function obtainCurrentsEvent() {
    return fetchEvents(url);
}

export function obtainEventsMostPopular() {
    return Promise.resolve([]);
}

export function obtainUserEvents(organizerId) {
    return fetchEvents(`${url}/${organizerId}`);
}