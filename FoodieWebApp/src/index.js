import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import {BrowserRouter as Router,
    Switch,
    Route} from "react-router-dom";
import Home from "./page/Home";
import Orders from "./page/Orders";
import Menu from "./page/Menu";
import Payment from "./page/Payment";
import Working from "./page/Working";
import Statistics from "./page/Statistics";

ReactDOM.render((
      <Router>
          <Switch>
          <Route path = "/" exact>
              <Home/>
          </Route>
          <Route path = "/Orders" exact>
              <Orders/>
          </Route>
          <Route path = "/Menu" exact>
              <Menu/>
          </Route>
          <Route path = "/Payment" exact>
              <Payment/>
          </Route>
          <Route path = "/Working" exact>
              <Working/>
          </Route>
          <Route path = "/Statistics" exact>
              <Statistics/>
          </Route>
          </Switch>
      </Router>
), document.getElementById('root'));
