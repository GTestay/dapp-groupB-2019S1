import React, {Component} from "react";
import * as PropTypes from "prop-types";

export class RenderWhen extends Component {
    render() {
        const {condition, whenTrueRender, whenFalseRender} = this.props;
        return condition.apply() ? whenTrueRender() : whenFalseRender
    }
}

RenderWhen.propTypes = {
    condition: PropTypes.func.isRequired,
    whenTrueRender: PropTypes.any.isRequired,
    whenFalseRender: PropTypes.any
};

RenderWhen.defaultProps = {
    whenFalseRender: <React.Fragment/>
};