import React, { Component } from "react";
import moment from "moment";
import { Form, Grid } from "tabler-react";
import Select from "react-select";
import Calendar from "@toast-ui/react-calendar";
import api from "../../services/api";
import "tui-calendar/dist/tui-calendar.css";
import "tui-date-picker/dist/tui-date-picker.css";
import "tui-time-picker/dist/tui-time-picker.css";
import "./styles.css";
import ReservaCard from "../../components/ReservaCard";

const dateFormat = "DD/MM/YYYY";
const dateTimeFormat = "DD/MM/YYYY HH:mm:ss";
const calendarFormat = "DD/MM/YYYY HH:mm";
const reservaFormat = "YYYY-MM-DD HH:mm:ss";

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
  return moment();
}

export default class Reservas extends Component {
  constructor(props) {
    super(props);
    this.turnoSelect = React.createRef();
    this.horarioSelect = React.createRef();
    this.disciplinaSelect = React.createRef();
    this.state = {
      calendarView: "day",
      ambienteOptions: [],
      turmaOptions: [],
      disciplinaOptions: [],
      professorOptions: [],
      horarioOptions: [],
      horarios: [],
      currentHorarios: [],
      currentTurnos: [],
      currentDisciplinas: [],
      currentAmbientes: [],
      turnoOptions: [
        { value: 0, label: "Matutino" },
        { value: 1, label: "Vespertino" },
        { value: 2, label: "Noturno" }
      ],
      reservas: [],
      tipoAmbiente: 0
    };
  }

  componentDidMount() {
    this.loadSelects();
  }

  componentDidUpdate(prevProps) {
    if (this.props !== prevProps) this.loadSelects();
  }

  setInitialHorarioTurno() {
    const { horarios, horarioOptions, turnoOptions } = this.state;
    if (horarioOptions.length <= 0 || turnoOptions.length <= 0) return;

    let newHorario = horarios[0];

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

    if (filteredHorarios.length > 0) [newHorario] = filteredHorarios;

    const horarioSelect = this.horarioSelect.current;
    const turnoSelect = this.turnoSelect.current;

    horarioSelect.state.value = horarioOptions.find(
      x => x.value === newHorario.id
    );
    horarioSelect.focus();

    turnoSelect.state.value = turnoOptions.find(
      x => x.label === formatUpperWord(newHorario.turno)
    );
    turnoSelect.focus();
    turnoSelect.blur();

    this.setState({
      currentHorarios: [horarioSelect.state.value],
      currentTurnos: [turnoSelect.state.value]
    });
  }

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

  handleVisualizacaoChange = selectedOption => {
    this.setState({ calendarView: selectedOption.value }, () =>
      this.loadReservas()
    );
  };

  async loadDisciplinas(currentTurmas) {
    const turmasId = currentTurmas
      ? currentTurmas.map(event => event.value).join(",")
      : "";

    const resDisciplinas = await api.get(
      `/disciplinas?linesPerPage=100&turmasId=${turmasId}`
    );
    if (resDisciplinas) {
      this.setState({
        disciplinaOptions: resDisciplinas.data.content.map(disciplina => {
          return { value: disciplina.id, label: disciplina.nome };
        })
      });
    }
  }

  async loadSelects() {
    const tipoAmbiente = window.location.href.endsWith("/reservas/laboratorios")
      ? 1
      : 0;

    this.setState({
      currentAmbientes: [],
      tipoAmbiente
    });

    const resAmbientes = await api.get(
      `/ambientes?linesPerPage=100&tipoAmbiente=${tipoAmbiente}`
    );
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

    await this.loadDisciplinas();

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

    this.setInitialHorarioTurno();
    this.loadReservas();
  }

  async loadReservas() {
    const { tipoAmbiente, currentAmbientes, currentHorarios } = this.state;

    const paramAmbientes =
      currentAmbientes && currentAmbientes.length > 0
        ? `&ambientes=${currentAmbientes.map(x => x.value).join(",")}`
        : "";

    const paramHorarios =
      currentHorarios && currentHorarios.length > 0
        ? `&horarios=${currentHorarios.map(x => x.value).join(",")}`
        : "";

    const res = await api.get(
      `/reservas?linesPerPage=100&tipoAmbiente=${tipoAmbiente}${paramAmbientes}${paramHorarios}`
    );
    if (res) {
      this.setState({
        reservas: res.data.content
      });
    }
  }

  render() {
    const {
      ambienteOptions,
      turmaOptions,
      disciplinaOptions,
      professorOptions,
      horarioOptions,
      calendarView,
      turnoOptions,
      currentHorarios,
      currentTurnos,
      currentDisciplinas,
      currentAmbientes,
      reservas
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
                onChange={event => {
                  this.setState({ currentAmbientes: event }, () =>
                    this.loadReservas()
                  );
                }}
                value={currentAmbientes}
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
                onChange={event => {
                  this.loadDisciplinas(event);
                  this.setState({ currentDisciplinas: [] });
                }}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Disciplina">
              <Select
                ref={this.disciplinaSelect}
                options={disciplinaOptions}
                theme={this.selectTheme}
                isMulti
                closeMenuOnSelect={false}
                onChange={event => this.setState({ currentDisciplinas: event })}
                value={currentDisciplinas}
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
                ref={this.turnoSelect}
                isMulti
                options={turnoOptions}
                theme={this.selectTheme}
                closeMenuOnSelect={false}
                onChange={event => {
                  this.setState(
                    {
                      currentTurnos: event
                    },
                    () => this.loadDisciplinas()
                  );
                }}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Horário">
              <Select
                ref={this.horarioSelect}
                options={horarioOptions}
                theme={this.selectTheme}
                isMulti
                closeMenuOnSelect={false}
                onChange={event => {
                  this.setState(
                    {
                      currentHorarios: event
                    },
                    () => this.loadReservas()
                  );
                }}
              />
            </Form.Group>
          </Grid.Col>
        </Grid.Row>

        {calendarView === "day" &&
        currentTurnos &&
        currentHorarios &&
        currentTurnos.length === 1 &&
        currentHorarios.length === 1 ? (
          <Grid.Row cards>
            {reservas
              .filter(x => moment(x.data).isSame(moment().startOf("day")))
              .map(reserva => (
                <ReservaCard
                  key={reserva.id}
                  ambiente={reserva.ambiente.nome}
                  professor={reserva.professor.nome}
                  turmas={reserva.turmas.map(x => x.nome).join(", ")}
                />
              ))}
          </Grid.Row>
        ) : (
          <Calendar
            isReadOnly
            useDetailPopup
            view={calendarView}
            schedules={reservas.map(reserva => ({
              title: `${reserva.turmas.map(x => x.nome).join(", ")} - ${
                reserva.professor.nome
              } - ${reserva.ambiente.nome}`,
              category: "time",
              start: new Date(
                moment(
                  `${reserva.data} ${reserva.horario.horaInicio}`,
                  reservaFormat
                )
              ),
              end: new Date(
                moment(
                  `${reserva.data} ${reserva.horario.horaFim}`,
                  reservaFormat
                )
              )
            }))}
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
