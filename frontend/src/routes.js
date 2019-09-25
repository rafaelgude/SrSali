import React from "react";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";
import Cadastro from "./pages/cadastro";
import Login from "./pages/login";

import { isAuthenticated } from "./services/auth";

const PrivateRoute = ({ component: Component, ...rest }) => (
  <Route
    {...rest}
    render={props =>
      isAuthenticated() ? (
        <Component {...props} />
      ) : (
        <Redirect to={{ pathname: "/", state: { from: props.location } }} />
      )
    }
  />
);

const NoAuthRoute = ({ component: Component, ...rest }) => (
  <Route
    {...rest}
    render={props =>
      isAuthenticated() ? (
        <Redirect to={{ pathname: "/app", state: { from: props.location } }} />
      ) : (
        <Component {...props} />
      )
    }
  />
);

const Routes = () => (
  <BrowserRouter>
    <Switch>
      <Route exact path="/" component={() => <h1>HomePage</h1>} />
      <NoAuthRoute path="/cadastro" component={Cadastro} />
      <NoAuthRoute path="/login" component={Login} />
      <PrivateRoute path="/app" component={() => <h1>App</h1>} />
      <Route path="*" component={() => <h1>Página não encontrada</h1>} />
    </Switch>
  </BrowserRouter>
);

export default Routes;
