import axios from 'axios';

function fetchEvents() {
    return axios
        .get("/events")
        .then(value => value.data);
}

export function obtainCurrentsEvent() {
    return fetchEvents();
}

export function obtainEventsMostPopular() {
    return Promise.resolve([]);
}

export function obtainUserEvents() {
    return fetchEvents();
}