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
import ReservaCard from "../../components/ReservaCard";

const dateFormat = "DD/MM/YYYY";
const dateTimeFormat = "DD/MM/YYYY HH:mm:ss";
const timeFormat = "HH:mm:ss";
const calendarFormat = "DD/MM/YYYY HH:mm";

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

function now(time) {
  if (time) return moment(moment().format(dateFormat) + time, dateTimeFormat);
  else return moment();
}

export default class Reservas extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ambienteOptions: [],
      turmaOptions: [],
      disciplinaOptions: [],
      professorOptions: [],
      horarioOptions: [],
      horarios: [],
      calendarProps: {
        view: "day",
        ...schedulesProp
      },
      currentHorarios: [],
      currentTurnos: [],
      turnoOptions: [
        { value: 0, label: "Matutino" },
        { value: 1, label: "Vespertino" },
        { value: 2, label: "Noturno" }
      ]
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
        horarios: resHorarios.data.content,
        horarioOptions: resHorarios.data.content.map(horario => {
          return {
            value: horario.id,
            label: `${horario.nome} (${formatUpperWord(horario.turno)})`
          };
        })
      });
    }

    const { horarios } = this.state;
    var newHorario = horarios[0];

    const filteredHorarios = horarios
      .filter(horario => {
        return (
          now(horario.horaInicio).isBetween(now(), now().add(15, "minutes")) ||
          now().isBetween(now(horario.horaInicio), now(horario.horaFim))
        );
      })
      .sort(
        (a, b) => now(b.horaInicio).valueOf() - now(a.horaInicio).valueOf()
      );

    if (filteredHorarios.length > 0) newHorario = filteredHorarios[0];

    var horarioSelect = this.refs.horarioSelect;
    horarioSelect.state.value = this.state.horarioOptions.find(
      x => x.value === newHorario.id
    );
    horarioSelect.focus();

    var turnoSelect = this.refs.turnoSelect;
    turnoSelect.state.value = this.state.turnoOptions.find(
      x => x.label === formatUpperWord(newHorario.turno)
    );
    turnoSelect.focus();
    turnoSelect.blur();

    this.setState({
      currentHorarios: [horarioSelect.state.value],
      currentTurnos: [turnoSelect.state.value]
    });
  }

  render() {
    const {
      ambienteOptions,
      turmaOptions,
      disciplinaOptions,
      professorOptions,
      horarioOptions,
      calendarProps,
      turnoOptions,
      currentHorarios,
      currentTurnos
    } = this.state;

    return (
      <>
        <Grid.Row className="grid-row-filters">
          <Grid.Col>
            <Form.Group label="Visualização">
              <Select
                defaultValue={{ value: "day", label: "Diário" }}
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
                ref="turnoSelect"
                isMulti
                options={turnoOptions}
                theme={this.selectTheme}
                closeMenuOnSelect={false}
                onChange={event => {
                  if (event)
                    this.setState({
                      currentTurnos: event
                    });
                }}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Horário">
              <Select
                ref="horarioSelect"
                options={horarioOptions}
                theme={this.selectTheme}
                isMulti
                closeMenuOnSelect={false}
                onChange={event => {
                  if (event)
                    this.setState({
                      currentHorarios: event
                    });
                }}
              />
            </Form.Group>
          </Grid.Col>
        </Grid.Row>

        {calendarProps.view === "day" &&
        currentTurnos.length > 0 &&
        currentHorarios.length > 0 ? (
          <ReservaCard />
        ) : (
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
                    moment(dateStart).format(calendarFormat) +
                    (isSameDate
                      ? ""
                      : ` - ${moment(dateEnd).format(calendarFormat)}`)
                  );
                }
                return `${moment(dateStart).format(calendarFormat)} - ${moment(
                  dateEnd
                ).format(endFormat)}`;
              }
            }}
          />
        )}
      </>
    );
  }
}
