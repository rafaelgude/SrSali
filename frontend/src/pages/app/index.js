import React from "react";
import "tabler-react/dist/Tabler.css";
import { Button } from "tabler-react";
import { logout } from "../../services/auth";

const App = props => (
  <>
    <h1>App</h1>
    <Button
      onClick={() => {
        logout();
        props.history.push("/");
      }}
    >
      Sair
    </Button>
  </>
);

export default App;
