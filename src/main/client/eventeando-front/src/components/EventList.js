import React, {Component} from "react";
import PropTypes from 'prop-types';

import '../styles/EventList.css';
import {ElementListEventDescription} from "./ElementListEventDescription";
import EventType from "../Types/EventType";

export class EventList extends Component {
    render() {
        return (
            <div className="titulo-evento">
                <h3>{this.props.title}</h3>
                {this.showEvents()}
            </div>
        )
    }

    showEvents() {

        if (this.events().length === 0) {
            return <h4>There are no events to show!</h4>
        }
        return <ul className="evento-listado">
            {this.listEvents()}
        </ul>;
    }

    listEvents() {
        return (this.events().map(event => <ElementListEventDescription event={event}/>))
    }

    events() {
        return this.props.events;
    }
}

EventList.propTypes = {
    events: PropTypes.arrayOf(EventType),
    title: PropTypes.string.isRequired
};