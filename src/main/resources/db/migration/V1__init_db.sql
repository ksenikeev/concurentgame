
CREATE TABLE public.buyer (
    id bigint NOT NULL,
    createdate timestamp without time zone,
    groupname character varying(255),
    name character varying(255),
    question1 integer,
    question2 integer,
    question3 integer,
    question4 integer,
    question5 integer,
    question6 integer
);

CREATE TABLE public.game (
    id bigint NOT NULL,
    buyersbudget double precision,
    costprice double precision,
    enddate timestamp without time zone,
    startdate timestamp without time zone,
    startupcapital double precision
);

CREATE SEQUENCE public.game_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.gamestage (
    id bigint NOT NULL,
    enddate timestamp without time zone,
    startdate timestamp without time zone,
    game_id bigint
);

CREATE SEQUENCE public.gamestage_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.manufacturer (
    id bigint NOT NULL,
    name character varying(255),
    game_id bigint
);

CREATE SEQUENCE public.manufacturer_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.manufacturerstatus (
    id bigint NOT NULL,
    advertisement real,
    assortment integer,
    price real,
    productcount real,
    gamestage_id bigint,
    manufacturer_id bigint
);

CREATE SEQUENCE public.manufacturerstatus_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE public.users (
    id bigint NOT NULL,
    name character varying(255),
    password character varying(255),
    role character varying(255),
    username character varying(255)
);

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE ONLY public.buyer
    ADD CONSTRAINT buyer_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.gamestage
    ADD CONSTRAINT gamestage_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.manufacturer
    ADD CONSTRAINT manufacturer_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.manufacturerstatus
    ADD CONSTRAINT manufacturerstatus_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.manufacturer
    ADD CONSTRAINT fk6shfehb13c9ah90wn5mswt6hm FOREIGN KEY (game_id) REFERENCES public.game(id);

ALTER TABLE ONLY public.manufacturerstatus
    ADD CONSTRAINT fkde5ml24c417kxuohc4qvwav5p FOREIGN KEY (manufacturer_id) REFERENCES public.manufacturer(id);

ALTER TABLE ONLY public.gamestage
    ADD CONSTRAINT fkeiv6bue7nw9mxhqqrlxf1kqes FOREIGN KEY (game_id) REFERENCES public.game(id);

ALTER TABLE ONLY public.manufacturerstatus
    ADD CONSTRAINT fkphqocqw6j1s9bl5rbrsddi3bl FOREIGN KEY (gamestage_id) REFERENCES public.gamestage(id);

INSERT INTO public.users (id, name, password, role, username) VALUES (0, 'admin', '$2a$12$k5WCMuJUGZhehopPrdt1SO1fPtrlpPvlio5O.3M.trjf3nCpyLMVO', 'ADMINISTRATOR', 'admin');
