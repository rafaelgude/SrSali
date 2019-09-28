import React, { Component } from "react";
import moment from "moment";
import { Form, Grid, Container } from "tabler-react";
import Select from "react-select";
import Calendar from "@toast-ui/react-calendar";
import api from "../../services/api";
import "tui-calendar/dist/tui-calendar.css";
import "tui-date-picker/dist/tui-date-picker.css";
import "tui-time-picker/dist/tui-time-picker.css";
import "./styles.css";

const dateFormat = "DD/MM/YYYY HH:mm";

export default class Reservas extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ambienteOptions: [],
      turmaOptions: [],
      disciplinaOptions: [],
      professorOptions: [],
      horarioOptions: [],
      calendarProps: {
        view: "month",
        schedules: [
          {
            title: "Teste 1",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 2",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 3",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 4",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 5",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 6",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 7",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 8",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 9",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 10",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 11",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 12",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 13",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 14",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 15",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 16",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 17",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 18",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 19",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 20",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 21",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 22",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 23",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 24",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          },
          {
            title: "Teste 25",
            category: "time",
            start: new Date(moment("27/09/2019 19:10:00", dateFormat)),
            end: new Date(moment("27/09/2019 20:50:00", dateFormat))
          }
        ]
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
            label: `${horario.nome} (${horario.turno.charAt(
              0
            )}${horario.turno.slice(1).toLowerCase()})`
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
          month={{
            startDayOfWeek: 0,
            narrowWeekend: true,
            daynames: [
              "Domingo",
              "Segunda",
              "Terça",
              "Quarta",
              "Quinta",
              "Sexta",
              "Sábado"
            ]
          }}
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
