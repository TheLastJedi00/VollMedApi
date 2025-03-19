package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    //tratar erro 404 caso busca ID inexistente
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    //Tratar erro 400 caso exista falta de parametros obrigatorios na requisicao
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){
        //captura os parâmetros ausentes na requisição
        var erros = ex.getFieldErrors(); //"ERROS" no plural
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());
    }

    private record DadosErrosValidacao(String campos, String mensagem){
        private DadosErrosValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex){
        //devolve erro 400 com a mensagem de erro
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
