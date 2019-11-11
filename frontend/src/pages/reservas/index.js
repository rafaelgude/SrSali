import React, { Component } from "react";
import moment from "moment";
import { Form, Grid } from "tabler-react";
import Select from "react-select";
import Calendar from "@toast-ui/react-calendar";
import api from "../../services/api";
import schedulesProp from "./schedules";
import "tui-calendar/dist/tui-calendar.css";
import "tui-date-picker/dist/tui-date-picker.css";
import "tui-time-picker/dist/tui-time-picker.css";
import "./styles.css";

const dateFormat = "DD/MM/YYYY HH:mm";

const dayNamesProp = {
  daynames: [
    "Domingo",
    "Segunda",
    "Terça",
    "Quarta",
    "Quinta",
    "Sexta",
    "Sábado"
  ]
};

function formatUpperWord(text) {
  return text.charAt(0) + text.slice(1).toLowerCase();
}

export default class Reservas extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ambienteOptions: [],
      turmaOptions: [],
      disciplinaOptions: [],
      professorOptions: [],
      // horarioOptions: [],
      horarioOptions: [
        { value: "Teste1", label: "Teste1" },
        { value: "Teste2", label: "Teste2" }
      ],
      calendarProps: {
        view: "month",
        ...schedulesProp
      }
    };

    this.loadSelects();
  }

  handleVisualizacaoChange = selectedOption => {
    const { calendarProps } = this.state;
    calendarProps.view = selectedOption.value;
    this.setState({ calendarProps });
  };

  selectTheme = theme => {
    return {
      ...theme,
      colors: {
        ...theme.colors,
        primary25: "#c7d8f0",
        primary: "#467fcf"
      }
    };
  };

  async loadSelects() {
    const resAmbientes = await api.get("/ambientes?linesPerPage=100");
    if (resAmbientes) {
      this.setState({
        ambienteOptions: resAmbientes.data.content.map(ambiente => {
          return { value: ambiente.id, label: ambiente.nome };
        })
      });
    }

    const resTurmas = await api.get("/turmas?linesPerPage=100");
    if (resTurmas) {
      this.setState({
        turmaOptions: resTurmas.data.content.map(turma => {
          return { value: turma.id, label: turma.nome };
        })
      });
    }

    const resDisciplinas = await api.get("/disciplinas?linesPerPage=100");
    if (resDisciplinas) {
      this.setState({
        disciplinaOptions: resDisciplinas.data.content.map(disciplina => {
          return { value: disciplina.id, label: disciplina.nome };
        })
      });
    }

    const resProfessores = await api.get("/professores?linesPerPage=100");
    if (resProfessores) {
      this.setState({
        professorOptions: resProfessores.data.content.map(professor => {
          return { value: professor.id, label: professor.nome };
        })
      });
    }

    const resHorarios = await api.get("/horarios?linesPerPage=100");
    if (resHorarios) {
      this.setState({
        horarioOptions: resHorarios.data.content.map(horario => {
          return {
            value: horario.id,
            label: `${horario.nome} (${formatUpperWord(horario.turno)})`
          };
        })
      });
    }
  }

  render() {
    const {
      ambienteOptions,
      turmaOptions,
      disciplinaOptions,
      professorOptions,
      horarioOptions
    } = this.state;

    return (
      <>
        <Grid.Row className="grid-row-filters">
          <Grid.Col>
            <Form.Group label="Visualização">
              <Select
                defaultValue={{ value: "month", label: "Mensal" }}
                options={[
                  { value: "month", label: "Mensal" },
                  { value: "week", label: "Semanal" },
                  { value: "day", label: "Diário" }
                ]}
                onChange={this.handleVisualizacaoChange}
                theme={this.selectTheme}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Ambiente">
              <Select
                options={ambienteOptions}
                theme={this.selectTheme}
                isMulti
                closeMenuOnSelect={false}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Turma">
              <Select
                options={turmaOptions}
                theme={this.selectTheme}
                isMulti
                closeMenuOnSelect={false}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Disciplina">
              <Select
                options={disciplinaOptions}
                theme={this.selectTheme}
                isMulti
                closeMenuOnSelect={false}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Professor">
              <Select
                options={professorOptions}
                theme={this.selectTheme}
                isMulti
                closeMenuOnSelect={false}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Turno">
              <Select
                options={[
                  { value: 0, label: "Matutino" },
                  { value: 1, label: "Vespertino" },
                  { value: 2, label: "Noturno" }
                ]}
                theme={this.selectTheme}
                isMulti
                closeMenuOnSelect={false}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Horário">
              <Select
                options={horarioOptions}
                theme={this.selectTheme}
                isMulti
                closeMenuOnSelect={false}
              />
            </Form.Group>
          </Grid.Col>
        </Grid.Row>

        <Calendar
          isReadOnly
          useDetailPopup
          {...this.state.calendarProps}
          taskView={false}
          setTheme={{ "week.timegridLeft.width": "500px" }}
          scheduleView={["time"]}
          month={{
            startDayOfWeek: 0,
            narrowWeekend: true,
            ...dayNamesProp
          }}
          week={{ narrowWeekend: true, ...dayNamesProp }}
          template={{
            popupDetailDate: (isAllDay, start, end) => {
              const isSameDate = moment(start).isSame(end);
              const endFormat = `${isSameDate ? "" : "DD/MM/YYYY"} HH:mm`;

              const dateStart = start.toDate();
              const dateEnd = end.toDate();

              if (isAllDay) {
                return (
                  moment(dateStart).format(dateFormat) +
                  (isSameDate ? "" : ` - ${moment(dateEnd).format(dateFormat)}`)
                );
              }

              return `${moment(dateStart).format(dateFormat)} - 
                      ${moment(dateEnd).format(endFormat)}`;
            }
          }}
        />
      </>
    );
  }
}
