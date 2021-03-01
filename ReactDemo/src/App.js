import React, { useState } from 'react';

import { BrowserRouter, Link, Route, Switch, Redirect, NavLink } from 'react-router-dom'
import Index from './App/Index';
import Login from './App/Login';
import RouteTest from './App/RouteTest';

import moment from 'moment';
import 'moment/locale/zh-cn';
import 'antd/dist/antd.css';
moment.locale('zh-cn');

export default function App() {
  return (
    <div>
      <BrowserRouter>
        <Switch>
          <Route path='/index' component={Index} />
          <Route path='/login' component={Login} />
          {/* <Route path='/route/:userName/:role' component={RouteTest} /> */}
          <Route path='/route' component={RouteTest} />
          <Route path='/render' render={() => <a href="/index">主页</a>} />
          <Redirect to="/index" />
        </Switch>
      </BrowserRouter>
    </div>
  );
}
