# VollMedApi
Implementação da funcionalidade de cancelamento de consultas.
Criada a classe DadosCancelamentoConsulta que vai gerar o id necessário para  fazer o delete da consulta agendada.
Agora Consultas recebem status AGENDADA ou CANCELADA criando uma consição que nos permite realizar exclusão lógica.
Na classe CancelaConsultas temos a lógica pra realizar a exclusão de consultas com algumas validações consicionais:
-Caso se tente excluir uma consulta que já estava excluída
-Caso o id da consulta não exista
No ConsultaController temos o método cancelar que faz o papel de DeleteMapping devolvendo NoContent ao concluír a operação.