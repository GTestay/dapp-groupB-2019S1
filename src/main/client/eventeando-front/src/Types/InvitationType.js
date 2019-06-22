import PropTypes from 'prop-types'
import UserType from './UserType'
import EventType from './EventType'

export default PropTypes.propTypes = {
  id: PropTypes.number.isRequired,
  guest: UserType,
  event: EventType
}
