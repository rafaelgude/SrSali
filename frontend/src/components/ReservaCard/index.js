import React, { Component } from "react";
import { Grid, Card } from "tabler-react";
import "./styles.css";

export default class ReservaCard extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const { ambiente, disciplina, professor } = this.props;
    return (
      <>
        <Grid.Col md={2}>
          <Card title={ambiente} body={disciplina} footer={professor} />
        </Grid.Col>
      </>
    );
  }
}
