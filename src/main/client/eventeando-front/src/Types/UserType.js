import PropTypes from 'prop-types'

export default PropTypes.propTypes = {
  id: PropTypes.number.isRequired,
  email: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  lastname: PropTypes.string.isRequired
}
