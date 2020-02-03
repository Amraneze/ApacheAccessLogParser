import React from 'react';
import { Route, Switch } from 'react-router-dom';
import MainWrapper from '../MainWrapper';
import { NotFound } from '../../components/notfound/NotFound';
import Logs from '../../dashboard/logs/Logs';
import Layout from '../../components/layout/Layout';
import Socket from '../../components/Socket';
import { logsService } from "../../../services";

const logsPage = () => (
    <div>
        <Layout />
        <div className="container__wrap">
            <Socket endpoint={logsService.logsEndPoint} event={logsService.logsGraphEvent} >
                <Logs />
            </Socket>
        </div>
    </div>
);

const Router = () => (
  <MainWrapper>
    <main>
      <Switch>
        <Route exact path="/" component={logsPage} />
        <Route component={NotFound} />
      </Switch>
    </main>
  </MainWrapper>
);

export default Router;
