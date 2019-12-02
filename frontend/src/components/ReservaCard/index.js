import React, { Component } from "react";
import { Grid, Card } from "tabler-react";
import "./styles.css";

export default class ReservaCard extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const { ambiente, professor, turmas } = this.props;
    return (
      <>
        <Grid.Col className="reservaCard" md={2}>
          <Card title={ambiente} body={professor} footer={turmas} />
        </Grid.Col>
      </>
    );
  }
}
