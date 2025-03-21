package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.validacoes.StatusConsulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;
    @Autowired
    private TestEntityManager entity;

    @Test
    @DisplayName("Return Null quando único médico cadastrado não está disponível")
    void escolherMedicoDisponivel() {
        //given arrange
        var proximaSegundaAs10 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).toLocalDate().atTime(10, 0);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.DERMATOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@voll.med", "12345678900");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);
        //when ou act
        var medicoDisponivel = repository.escolherMedicoDisponivel(Especialidade.DERMATOLOGIA, proximaSegundaAs10);
        //Them ou assert
        assertThat(medicoDisponivel).isNull();
    }
    @Test
    @DisplayName("Deve reornar médico se ele estiver diponível")
    void escolherMedicoDisponive2() {
        //given ou arrange
        var proximaSegundaAs10 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).toLocalDate().atTime(10, 0);
        cadastrarMedico("Medico", "medico2@voll.med", "123457", Especialidade.DERMATOLOGIA);
        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.DERMATOLOGIA);
        //when ou act
        var medicoDisponivel = repository.escolherMedicoDisponivel(Especialidade.DERMATOLOGIA, proximaSegundaAs10);
        //them ou assert
        assertThat(medicoDisponivel).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        entity.persist(new Consulta(medico, paciente, data, StatusConsulta.AGENDADA));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        entity.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        entity.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                dadosEndereco(),
                "11122233345",
                "1112224"
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}