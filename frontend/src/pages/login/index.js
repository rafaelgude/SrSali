import React, { Component } from "react";
import "tabler-react/dist/Tabler.css";
import {
  StandaloneFormPage,
  FormCard,
  FormTextInput,
  Alert
} from "tabler-react";
import api from "../../services/api";
import { login } from "../../services/auth";

export default class Login extends Component {
  constructor(props) {
    super(props);

    this.state = {
      email: "",
      senha: "",
      errors: {
        email: "",
        senha: "",
        others: ""
      }
    };
  }

  handleSubmit = async e => {
    e.preventDefault();
    const { errors, ...data } = this.state;

    errors.others = "";
    errors.email = data.email ? "" : "O e-mail é obrigatório.";
    errors.senha = data.senha ? "" : "A senha é obrigatória.";

    this.setState({ errors });

    if (!errors.email && !errors.senha) {
      const response = await api.post("/login", data).catch(error => {
        const {
          message = "Falha na autenticação."
        } = error.response.data.error;
        this.setState({ errors: { others: message } });
      });

      if (response) {
        login(response.headers.authorization);
        this.props.history.push("/app");
      }
    }
  };

  render() {
    const { errors } = this.state;

    return (
      <StandaloneFormPage imageURL="./demo/logo.svg">
        <FormCard
          title="Entre com sua conta"
          buttonText="Entrar"
          onSubmit={this.handleSubmit}
        >
          {errors.others && <Alert type="danger">{errors.others}</Alert>}
          <FormTextInput
            name="email"
            placeholder="E-mail"
            onChange={e => this.setState({ email: e.target.value })}
            error={errors && errors.email}
          />
          <FormTextInput
            name="senha"
            type="password"
            placeholder="Senha"
            onChange={e => this.setState({ senha: e.target.value })}
            error={errors && errors.senha}
          />
        </FormCard>
      </StandaloneFormPage>
    );
  }
}
