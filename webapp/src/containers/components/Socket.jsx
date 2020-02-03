import React, {PureComponent} from 'react';
import PropTypes from "prop-types";
import { connect } from 'react-redux';
import { displayDialog } from '../../redux/actions/DialogActions';

class Socket extends PureComponent {
    constructor(props) {
        super(props);
        this.socket = null;
    }

    componentDidMount() {
        console.log(this.props.endpoint);
        this.socket = new WebSocket(this.props.endpoint);
        this.socket.onopen = () => {
            console.log('connected');
        };

        this.socket.onmessage = (event) => {
            const data = JSON.parse(event.data);
            console.log({event, data});
            this.onDataChanged(data);
        };

        this.socket.onclose = () => {
            console.log('disconnected');
        };

        this.socket.onerror = () => {
            this.disconnectSocket();
            displayDialog(this.props.dispatch);
        };
    }

    componentWillUnmount() {
        this.disconnectSocket();
    }

    disconnectSocket = () => {
        this.socket.close();
    };

    render() {
        const childrenDOMs = React.Children.map(this.props.children, (child) =>
            React.cloneElement(child, {
                ws: this.socket,
                setListener: fun => this.onDataChanged = fun,
            })
        );
        return <div>{childrenDOMs}</div>
    }
}

Socket.propTypes = {
    dispatch: PropTypes.func.isRequired,
};

export default connect()(Socket);