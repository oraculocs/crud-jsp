








ALTER TABLE public.usuario ADD COLUMN cep character varying(200);
ALTER TABLE public.usuario ADD COLUMN rua character varying(200);
ALTER TABLE public.usuario ADD COLUMN bairro character varying(200);
ALTER TABLE public.usuario ADD COLUMN cidade character varying(200);
ALTER TABLE public.usuario ADD COLUMN estado character varying(200);
ALTER TABLE public.usuario ADD COLUMN ibge character varying(200);


#Criando sequencia para telefone, cria chaves primárias automaticamente
CREATE SEQUENCE fonesequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE fonesequence
  OWNER TO postgres;
  
#Colunas para Upload  
ALTER TABLE public.usuario ADD COLUMN fotobase64 text;
ALTER TABLE public.usuario ADD COLUMN contenttype text;

#Criando a sequence da Categoria
CREATE SEQUENCE categoriasequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1;
  
  
ALTER TABLE public.produto ADD COLUMN categoria_id bigint;  

ALTER TABLE public.produto ADD COLUMN categoria_id bigint;