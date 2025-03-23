package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @Mock
    private AgendaConsultas agendaConsultas;

    @Test
    @DisplayName("Deveria devolver ERRO 400 quando informações estão inválidas")
    @WithMockUser
    void cadastrar1() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();
        //erro 400
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

//    @Test
//    @DisplayName("Deveria devolver 200 OK")
//    @WithMockUser
//    void cadastrar2() throws Exception {
//        var data = LocalDateTime.now().plusDays(5);
//        var especialidade = Especialidade.DERMATOLOGIA;
//        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 21L, 33L, data);
//
//        when(agendaConsultas.agendar(any())).thenReturn(dadosDetalhamento);
//
//        var response = mvc.perform(post("/consultas").contentType(MediaType.APPLICATION_JSON)
//                        .content(dadosAgendamentoConsultaJson
//                                .write(new DadosAgendamentoConsulta((long) 21L, (Long) 33L, data, especialidade))
//                                .getJson()))
//                .andReturn().getResponse();
//
//        //erro 400
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//
//        var jsonEsperado = dadosDetalhamentoConsultaJson
//                .write(dadosDetalhamento).getJson();
//
//    }
}