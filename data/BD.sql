create schema bd_entregas;
use bd_entregas;

create table tab_cliente (
    id_cliente int auto_increment,
    nome varchar(64) not null,
    data_nascimento date,
    contato varchar(64),

    primary key (id_cliente)
);

create table tab_item (
    id_item int auto_increment,
    descricao varchar(128) not null,
    valor_und float not null,

    primary key (id_item)
);

create table tab_entregador (
    id_entregador int auto_increment,
    nome varchar(64) not null,
    documento varchar(32) not null unique,
    placa_veiculo char(7) not null unique,

    primary key (id_entregador)
);

create table tab_pedido (
    id_pedido int auto_increment,
    id_cliente int not null,
    id_entregador int,
    data_pedido date not null,
    data_finalizado date,
    endereco varchar(128) not null,

    primary key (id_pedido),

    foreign key (id_cliente)
        references tab_cliente(id_cliente),
    foreign key (id_entregador)
        references tab_entregador(id_entregador)
);

create table rel_item_pedido (
    id_pedido int not null,
    id_item int not null,

    foreign key (id_pedido)
        references tab_pedido(id_pedido),
    foreign key (id_item)
        references tab_item(id_item)
);