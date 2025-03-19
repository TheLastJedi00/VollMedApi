package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.consulta.validacoes.StatusConsulta;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoConsultas;
import java.util.List;

@Service
public class AgendaConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        //Paciente existe?
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe!");
        }
        //Médico existe?
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do médico informado não existe!");
        }
        //Consulta Validadores
        validadores.forEach(v -> v.validar(dados));
        //Agendar
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        //Médico disponível?
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }
        //Escolhe Paciente, Médico, Data e define Status
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consulta.setStatus(StatusConsulta.AGENDADA);
        //Salva Consulta
        consultaRepository.save(consulta);
        //Retorna Dados Detalhamento Consulta
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade não informada");

        }

        return medicoRepository.escolherMedicoDisponivel(dados.especialidade(), dados.data());
    }

}
