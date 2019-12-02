/* eslint-disable class-methods-use-this */
/* eslint-disable react/no-unused-state */
import React, { Component } from "react";
import { NavLink } from "react-router-dom";
import { Site } from "tabler-react";
import api from "../../services/api";
import "tabler-react/dist/Tabler.css";

const LinkComponent = NavLink;
const navBarItems = [
  {
    value: "Reservas",
    icon: "home",
    subItems: [
      {
        value: "Salas",
        to: "/app/reservas/salas",
        useExact: true,
        LinkComponent
      },
      {
        value: "Laboratórios",
        to: "/app/reservas/laboratorios",
        useExact: true,
        LinkComponent
      },
      {
        value: "Gerenciamento",
        to: "/app/reservas",
        useExact: true,
        LinkComponent
      }
    ]
  },
  {
    value: "Ambientes",
    to: "/app/ambientes",
    icon: "home",
    LinkComponent
  },
  {
    value: "Ferramentas",
    to: "/app/ferramentas",
    icon: "home",
    LinkComponent
  },
  {
    value: "Turmas",
    to: "/app/turmas",
    icon: "home",
    LinkComponent
  },
  {
    value: "Disciplinas",
    to: "/app/disciplinas",
    icon: "home",
    LinkComponent
  },
  {
    value: "Professores",
    to: "/app/professores",
    icon: "home",
    LinkComponent
  },
  {
    value: "Cursos",
    to: "/app/cursos",
    icon: "home",
    LinkComponent
  },
  {
    value: "Horários",
    to: "/app/horarios",
    icon: "home",
    LinkComponent
  }
];

export default class Header extends Component {
  constructor(props) {
    super(props);
    this.state = { usuario: {} };
  }

  async componentDidMount() {
    const response = await api.get("/usuarios/authenticated");
    if (response) {
      this.setState({
        usuario: {
          maxFuncao: this.getFuncao(response.data.funcoes.sort()[0]),
          ...response.data
        }
      });
    }
  }

  getFuncao(funcao) {
    switch (funcao) {
      case "ADMIN":
        return "Administrador";
      case "OPERADOR":
        return "Operador";
      case "USUARIO":
        return "Usuário";
      default:
        return "";
    }
  }

  render() {
    const { usuario } = this.state;
    return (
      <Site.Wrapper
        navProps={{ itemsObjects: navBarItems }}
        headerProps={{
          accountDropdown: {
            avatarURL: "a.png",
            name: usuario.nome,
            description: usuario.maxFuncao,
            options: [
              { icon: "user", value: "Perfil", to: "/app/perfil" },
              {
                icon: "settings",
                value: "Configurações",
                to: "/app/configuracoes"
              },
              { isDivider: true },
              { icon: "log-out", value: "Sair", to: "/logout" }
            ]
          }
        }}
      />
    );
  }
}
