import React, { Component } from "react";
import "tabler-react/dist/Tabler.css";
import {
  StandaloneFormPage,
  FormCard,
  FormTextInput,
  Alert
} from "tabler-react";
import api from "../../services/api";

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
    const { errors, ...dados } = this.state;

    if (dados.instituicao.nome) {
      errors.instituicao =
        dados.instituicao.nome.length > 60
          ? "Máximo de 60 caracteres permitido."
          : "";
    } else {
      errors.instituicao = "O nome da Instituição é obrigatório.";
    }

    if (dados.nome) {
      errors.nome =
        dados.nome.length > 60 ? "Máximo de 60 caracteres permitido." : "";
    } else {
      errors.nome = "O nome da Usuário é obrigatório.";
    }

    if (dados.email) {
      if (dados.email.length > 60) {
        errors.email = "Máximo de 60 caracteres permitido.";
      } else {
        errors.email = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(dados.email)
          ? ""
          : "O e-mail é inválido.";
      }
    } else {
      errors.email = "O e-mail é obrigatório.";
    }

    errors.senha = dados.senha ? "" : "A senha é obrigatória.";

    this.setState({ errors });

    if (!errors.instituicao && !errors.nome && !errors.email && !errors.senha) {
      try {
        await api.post("/instituicoes", dados);
        this.props.history.push("/");
      } catch (err) {
        this.setState({ errors: { others: "Falha no cadastro!" } });
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
