import PropTypes from "prop-types";
import UserType from "./UserType"

export default PropTypes.propTypes = {
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    cost: PropTypes.number.isRequired
};