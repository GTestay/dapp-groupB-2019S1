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

export function madeWithdrawalFor (user, amount) {
  return axios.post(`${url}/${user.id}/requireCredit`, { amount: amount }, { headers: headers }).then((response) => response.data)
}

export function madeDepositByCreditCardFor (user, amount, dueDate, cardNumber) {
  return axios.post(
    `${url}/${user.id}/madeDepositByCreditCard`,
    { amount: amount, dueDate: dueDate, cardNumber: cardNumber },
    { headers: headers }
  ).then((response) => response.data)
}

export function obtainUserTransactions (user, actualPage = 0, sizeOfPage = 5) {
  const depositCash = { type: 'DepositByCash', amount: 200, date: moment() }
  const depositCredit = { type: 'DepositByCreditCard', amount: 200, date: moment() }
  const loan = { type: 'Loan', amount: 500, date: moment(), ended: false }
  const loanPayment = { type: 'LoanPayment', amount: 500, date: moment()}
  const extraction = { type: 'Extraction', amount: 300, date: moment() }
  const transactions = (duplicateArr([depositCredit, loan, extraction,depositCash,loanPayment], 10));

  transactions.map((transaction,n) => {
    transaction['id'] = n;
    return transaction
  });

  const startPage = actualPage * sizeOfPage
  const endPage = startPage + sizeOfPage
  const pageOfTransactions = transactions.slice(startPage, endPage);
  const totalPages = Math.floor(transactions.length / sizeOfPage);

  const page = {
    actualPage,
    sizeOfPage,
    totalPages: totalPages,
    moneyTransactions: pageOfTransactions
  };
  console.log(page)
  return Promise.resolve(page)
}

const duplicateArr = (arr, times) =>
  Array(times)
    .fill([...arr])
    .reduce((a, b) => a.concat(b))
