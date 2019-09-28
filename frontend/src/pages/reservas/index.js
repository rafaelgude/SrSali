import React, { Component } from "react";
import moment from "moment";
import { Form, Grid } from "tabler-react";
import Select from "react-select";
import Calendar from "@toast-ui/react-calendar";
import api from "../../services/api";
import "tui-calendar/dist/tui-calendar.css";
import "tui-date-picker/dist/tui-date-picker.css";
import "tui-time-picker/dist/tui-time-picker.css";

const dateFormat = "DD/MM/YYYY HH:mm";

export default class Reservas extends Component {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  handleVisualizacaoChange = selectedOption => {
    const { calendarProps } = this.state;
    calendarProps.view = selectedOption.value;
    this.setState({ calendarProps });
  };

  render() {
    return (
      <>
        <Grid.Row>
          <Grid.Col>
            <Form.Group label="Visualização">
              <Select
                value={{ value: "month", label: "Mensal" }}
                options={[
                  { value: "month", label: "Mensal" },
                  { value: "week", label: "Semanal" },
                  { value: "day", label: "Diário" }
                ]}
                onChange={this.handleVisualizacaoChange}
              />
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Ambiente">
              <Form.Select></Form.Select>
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Turma">
              <Form.Select></Form.Select>
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Disciplina">
              <Form.Select></Form.Select>
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Professor">
              <Form.Select></Form.Select>
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Turno">
              <Form.Select></Form.Select>
            </Form.Group>
          </Grid.Col>

          <Grid.Col>
            <Form.Group label="Horário">
              <Form.Select></Form.Select>
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

              return `${moment(dateStart).format(dateFormat)} - ${moment(
                dateEnd
              ).format(endFormat)}`;
            }
          }}
        />
      </>
    );
  }
}
