package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.cancelamento.CancelaConsulta;
import med.voll.api.domain.consulta.cancelamento.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @Autowired
    private CancelaConsulta cancelaConsulta;

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelar(@PathVariable Long id, @RequestBody DadosCancelamentoConsulta dados){
        //Definir id da consulta
        dados = new DadosCancelamentoConsulta(id);
        //Chamar cancelamento
        cancelaConsulta.cancelar(dados);
        //NoContent 204
        return ResponseEntity.noContent().build();
    }
}
