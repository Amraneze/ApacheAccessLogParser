// Make sure react-hot-loader is required before react
import { hot, setConfig } from 'react-hot-loader';
import React, { Component } from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import Router from './router/Router';
import ScrollToTop from './ScrollToTop';
import store from './Store';
import GenericDialog from '../components/dialog/GenericDialog';
import 'bootstrap/dist/css/bootstrap.css';
import '../../scss/_app.scss';

class App extends Component {
	constructor(props) {
		super(props);
		this.state = {
			show: false,
			props: {},
		};
		store.subscribe(() => this.setState({
			show: store.getState().dialog.show,
			props: store.getState().dialog.props,
		}));
		// Disable react-🔥-dom patch is not detected
		// We can use two different webpack configuration
		// one with prod that will use react-dom because
		// it's the core of ReactJs, and one with dev conf
		// that will use @hot-loader/react-dom
		// Check this https://github.com/gaearon/react-hot-loader/issues/1227
		setConfig({
			showReactDomPatchNotification: false
		})
	}
	
  	render() {
		const { show, props } = this.state;
		return (
			<Provider store={store}>
					<BrowserRouter>
						<ScrollToTop>
							<div>
								<Router />
								{show ? <GenericDialog props={props} /> : ''}
							</div>
						</ScrollToTop>
					</BrowserRouter>
			</Provider>
		);
 	}
}

export default hot(module)(App);
