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

export default class Cadastro extends Component {
  constructor(props) {
    super(props);

    this.state = {
      instituicao: {
        nome: ""
      },
      nome: "",
      email: "",
      senha: "",
      errors: {
        instituicao: "",
        nome: "",
        email: "",
        senha: ""
      }
    };
  }

  handleSubmit = async e => {
    e.preventDefault();
    const { errors, ...data } = this.state;

    errors.others = "";
    if (data.instituicao.nome) {
      errors.instituicao =
        data.instituicao.nome.length > 60
          ? "Máximo de 60 caracteres permitido."
          : "";
    } else {
      errors.instituicao = "O nome da Instituição é obrigatório.";
    }

    if (data.nome) {
      errors.nome =
        data.nome.length > 60 ? "Máximo de 60 caracteres permitido." : "";
    } else {
      errors.nome = "O nome da Usuário é obrigatório.";
    }

    if (data.email) {
      if (data.email.length > 60) {
        errors.email = "Máximo de 60 caracteres permitido.";
      } else {
        errors.email = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(data.email)
          ? ""
          : "O e-mail é inválido.";
      }
    } else {
      errors.email = "O e-mail é obrigatório.";
    }

    errors.senha =
      data.senha.length > 5
        ? ""
        : "A senha deve conter no mínimo 6 caracteres.";

    this.setState({ errors });

    if (!errors.instituicao && !errors.nome && !errors.email && !errors.senha) {
      try {
        const response = await api.post("/instituicoes", data).catch(error => {
          const { message = "Falha no cadastro." } =
            (error.response && error.response.data.error) || {};
          this.setState({ errors: { others: message } });
        });

        if (response) {
          const loginResponse = await api.post("/login", data);
          if (loginResponse) {
            login(loginResponse.headers.authorization.replace("Bearer ", ""));
            this.props.history.push("/app");
          }
        }
      } catch (err) {
        this.setState({ errors: { others: "Falha no cadastro." } });
      }
    }
  };

  render() {
    const { errors } = this.state;

    return (
      <StandaloneFormPage imageURL="./demo/logo.svg">
        <FormCard
          title="Cadastre sua Insituição de Ensino"
          buttonText="Cadastrar"
          onSubmit={this.handleSubmit}
        >
          {errors.others && <Alert type="danger">{errors.others}</Alert>}
          <FormTextInput
            name="instituicao"
            placeholder="Nome da Instituição de Ensino"
            onChange={e =>
              this.setState({ instituicao: { nome: e.target.value } })
            }
            error={errors && errors.instituicao}
          />
          <FormTextInput
            name="nome"
            placeholder="Nome do Usuário"
            onChange={e => this.setState({ nome: e.target.value })}
            error={errors && errors.nome}
          />
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
