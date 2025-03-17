package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.validacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

@Service
public class AgendaConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados) {
        // Lógica para agendar consulta
        if (!medicoRepository.existsById(dados.idMedico())||!pacienteRepository.existsById(dados.idPaciente())) {
            throw new validacaoException("ID não encontrado");
        }
        var medico = escolherMedico(dados);
                var paciente = pacienteRepository.findById(dados.idPaciente()).get();
                var consulta = new Consulta(null, medico, paciente, dados.data());
                consultaRepository.save(consulta);
            }
        
    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico()==null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new validacaoException("Especialidade não informada");
            
        }

        return medicoRepository.escolherMedicoDisponivel(dados.especialidade(), dados.data());
    }
}
