import PropTypes from 'prop-types'
import UserType from './UserType'

export default PropTypes.propTypes = {
  id: PropTypes.number.isRequired,
  type: PropTypes.string.isRequired,
  organizer: UserType.isRequired,
  description: PropTypes.string.isRequired,
  guests: PropTypes.arrayOf(UserType),
  expenses: PropTypes.array.isRequired
}
