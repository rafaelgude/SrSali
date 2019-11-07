import React from "react";
import "tabler-react/dist/Tabler.css";
import { Button } from "tabler-react";

const Home = props => (
  <Button.List>
    {/* <Button color="primary" onClick={() => props.history.push("/login")}> */}
    <Button color="primary" onClick={() => props.history.push("/app")}>
      Login
    </Button>
    <Button color="secondary" onClick={() => props.history.push("/cadastro")}>
      Cadastro
    </Button>
  </Button.List>
);

export default Home;
