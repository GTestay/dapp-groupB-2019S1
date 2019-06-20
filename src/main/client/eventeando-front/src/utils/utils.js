/**
 option es como un objeto que tiene clave, valor, y texto para JS...
 */
export function optionOf (aString) {
  return { key: aString, value: aString, text: aString }
}

export function eventTypes () {
  const baquitaCrowdfunding = 'BaquitaCrowdFundingEvent'
  const baquitaWithSharedExpenses = 'BaquitaSharedExpensesEvent'
  const potluckEvent = 'PotluckEvent'
  const party = 'Party'
  return [party, baquitaCrowdfunding, potluckEvent, baquitaWithSharedExpenses].map(optionOf)
}
