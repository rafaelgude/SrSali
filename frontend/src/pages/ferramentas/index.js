import React, { Component } from "react";
import { Table } from "tabler-react";
import api from "../../services/api";
import "tabler-react/dist/Tabler.css";

export default class Ferramentas extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: []
    };
  }

  async componentDidMount() {
    const response = await api.get("/ferramentas");
    if (response) this.setState({ data: response.data.content });
  }

  render() {
    return (
      <Table>
        <Table.Header>
          <Table.ColHeader>ID</Table.ColHeader>
          <Table.ColHeader>Nome</Table.ColHeader>
          <Table.ColHeader>Ativo</Table.ColHeader>
        </Table.Header>
        <Table.Body>
          {this.state.data.forEach(ferramenta => {
            return (
              <Table.Row>
                <Table.Col>{ferramenta.id}</Table.Col>
                <Table.Col>{ferramenta.nome}</Table.Col>
                <Table.Col>{ferramenta.ativo}</Table.Col>
              </Table.Row>
            );
          })}
        </Table.Body>
      </Table>
    );
  }
}
