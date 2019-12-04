import React, { Component } from "react";
import MaterialTable from "material-table";
import Modal from "react-modal";
import moment from "moment";
import Select from "react-select";
import { Form, Grid } from "tabler-react";
import DatePicker from "react-datepicker";
import api from "../../services/api";
import "./icons.css";
import "./styles.css";
import "react-datepicker/dist/react-datepicker.css";

const dateFormat = "dd/MM/yyyy";
const dateTimeFormat = "DD/MM/YYYY HH:mm:ss";
const calendarFormat = "DD/MM/YYYY HH:mm";
const reservaFormat = "YYYY-MM-DD HH:mm:ss";

function formatUpperWord(text) {
  return text.charAt(0) + text.slice(1).toLowerCase();
}

const customStyles = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    width: "500px",
    height: "500px"
  }
};

export default class ReservasAdm extends Component {
  constructor(props) {
    super(props);
    this.turnoSelect = React.createRef();
    this.horarioSelect = React.createRef();
    this.disciplinaSelect = React.createRef();
    this.state = {
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
      modalIsOpen: false,
      data: new Date()
    };
  }

  componentDidMount() {
    this.loadSelects();
  }

  componentDidUpdate(prevProps) {
    if (this.props !== prevProps) this.loadSelects();
  }

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

    this.loadReservas();
  }

  async loadReservas() {
    const res = await api.get(`/reservas?linesPerPage=100`);
    if (res) {
      this.setState({
        reservas: res.data.content
      });
    }
  }

  cancelarReserva(id) {
    if (!window.confirm("Confirmar operação?")) return;
    api.delete(`/reservas/${id}`).then(response => this.loadReservas());
  }

  showModal() {
    this.setState({ modalIsOpen: true });
  }

  closeModal() {
    this.setState({ modalIsOpen: false });
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
      reservas,
      modalIsOpen,
      data
    } = this.state;

    const dados = reservas.map(x => {
      return {
        id: x.id,
        ambiente: x.ambiente.nome,
        professor: x.professor.nome,
        turmas: x.turmas.map(y => y.nome).join(", "),
        disciplina: x.disciplina.nome,
        data: moment(x.data).format(dateFormat),
        horario: x.horario.nome,
        turno: x.horario.turno
      };
    });

    return (
      <>
        <div style={{ maxWidth: "100%" }}>
          <MaterialTable
            columns={[
              { title: "Ambiente", field: "ambiente" },
              { title: "Professor", field: "professor" },
              { title: "Turmas", field: "turmas" },
              { title: "Disciplina", field: "disciplina" },
              { title: "Data", field: "data", type: "date" },
              { title: "Horário", field: "horario" },
              { title: "Turno", field: "turno" }
            ]}
            data={dados}
            title="Reservas"
            options={{
              actionsColumnIndex: 7
            }}
            actions={[
              {
                icon: "delete",
                tooltip: "Cancelar Reserva",
                onClick: (event, rowData) => this.cancelarReserva(rowData.id)
              },
              {
                icon: "add",
                tooltip: "Reservar Ambiente",
                isFreeAction: true,
                onClick: event => this.showModal()
              }
            ]}
          />
        </div>

        <Modal
          isOpen={modalIsOpen}
          onRequestClose={() => this.closeModal()}
          style={customStyles}
          aria={{
            labelledby: "heading",
            describedby: "full_description"
          }}
        >
          <Form.Group label="Turmas">
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

          <Form.Group label="Disciplina">
            <Select
              ref={this.disciplinaSelect}
              options={disciplinaOptions}
              theme={this.selectTheme}
              closeMenuOnSelect={false}
              onChange={event => this.setState({ currentDisciplinas: event })}
              value={currentDisciplinas}
            />
          </Form.Group>

          <Form.Group label="Professor">
            <Select
              options={professorOptions}
              theme={this.selectTheme}
              closeMenuOnSelect={false}
            />
          </Form.Group>

          <Form.Group label="Data">
            <DatePicker
              selected={data}
              onChange={date => this.setState({ data: date })}
              dateFormat={dateFormat}
            />
          </Form.Group>
        </Modal>
      </>
    );
  }
}
