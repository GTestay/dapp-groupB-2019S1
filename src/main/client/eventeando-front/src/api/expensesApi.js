import axios from "axios";

const url = "/expenses";

function fetchExpenses(url) {
    return axios
        .get(url)
        .then(value => value.data);
}

export function allExpenses() {
    return fetchExpenses(url);
}
