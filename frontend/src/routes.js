import React from "react";
import { BrowserRouter, Route, Switch, Redirect } from "react-router-dom";
import { logout, isAuthenticated } from "./services/auth";
import Cadastro from "./pages/cadastro";
import Login from "./pages/login";
import Home from "./pages/home";
import Header from "./pages/header";
import Reservas from "./pages/reservas";
import Ferramentas from "./pages/ferramentas";
import ReservasAdm from "./pages/reservas-adm";

const PrivateRoute = ({ component: Component, ...rest }) => (
  <Route
    {...rest}
    render={props =>
      isAuthenticated() ? (
        <>
          <Header />
          <Component {...props} />
        </>
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
  <>
    <BrowserRouter>
      <Switch>
        <Route exact path="/" component={Home} />
        <NoAuthRoute path="/cadastro" component={Cadastro} />
        <NoAuthRoute path="/login" component={Login} />
        <PrivateRoute exact path="/app/reservas" component={Reservas} />
        <PrivateRoute exact path="/app/reservas/salas" component={Reservas} />
        <PrivateRoute
          exact
          path="/app/reservas/laboratorios"
          component={Reservas}
        />
        <PrivateRoute
          exact
          path="/app/reservas/gerenciamento"
          component={ReservasAdm}
        />
        <PrivateRoute exact path="/app/ferramentas" component={Ferramentas} />
        <PrivateRoute
          exact
          path="/app"
          component={() => {
            return <Redirect to={{ pathname: "/app/reservas/salas" }} />;
          }}
        />
        <Route
          path="/logout"
          component={() => {
            logout();
            return <Redirect to={{ pathname: "/" }} />;
          }}
        />
        <Route path="*" component={() => <h1>Página não encontrada</h1>} />
      </Switch>
    </BrowserRouter>
  </>
);

export default Routes;
