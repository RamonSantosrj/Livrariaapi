create database livrariaapi;
use livrariaapi;
show tables;
select * from cliente;
select * from funcionario;
select * from livro;
select * from venda_livro;
select * from venda;
delete from livro;
drop database livrariaapi;

select distinct * from venda
 inner join funcionario on venda.id_funcionario = funcionario.id_funcionario 
 inner join venda_livro on venda.id_venda = venda_livro.id_venda  
 inner join livro on livro.id_livro = venda_livro.id_livro  
 inner join cliente on venda.id_cliente = cliente.id_cliente
 where venda.id_cliente = 1;
