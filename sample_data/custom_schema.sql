DROP TABLE IF EXISTS public.saves;
CREATE TABLE public.saves (
   id serial NOT NULL PRIMARY KEY,
   current_map varchar(255) NOT NULL,
   saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
   name varchar(255) NOT NULL
);

DROP TABLE IF EXISTS public.player;
CREATE TABLE public.player (
   id serial NOT NULL PRIMARY KEY,
   save_id integer NOT NULL,
   hp integer NOT NULL,
   maximum_hp integer NOT NULL,
   armor integer NOT NULL,
   x integer NOT NULL,
   y integer NOT NULL,
   selected_item integer NOT NULL,
   has_red_key boolean NOT NULL,
   has_blue_key boolean NOT NULL
);

DROP TABLE IF EXISTS public.playerInventory;
CREATE TABLE public.playerInventory (
    id serial NOT NULL PRIMARY KEY,
    save_id integer NOT NULL,
    tile_name varchar(255) NOT NULL
);

DROP TABLE IF EXISTS public.enemies;
CREATE TABLE public.enemies (
    id serial NOT NULL PRIMARY KEY,
    save_id integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL,
    tile_name varchar(255) NOT NULL
);

DROP TABLE IF EXISTS public.pickedItems;
CREATE TABLE public.pickedItems (
    id serial NOT NULL PRIMARY KEY,
    save_id integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL
);

ALTER TABLE ONLY public.player
    ADD CONSTRAINT fk_save_id FOREIGN KEY (save_id) REFERENCES public.saves(id);

ALTER TABLE ONLY public.playerInventory
    ADD CONSTRAINT fk_save_id FOREIGN KEY (save_id) REFERENCES public.saves(id);

ALTER TABLE ONLY public.enemies
    ADD CONSTRAINT fk_save_id FOREIGN KEY (save_id) REFERENCES public.saves(id);

ALTER TABLE ONLY public.pickedItems
    ADD CONSTRAINT fk_save_id FOREIGN KEY (save_id) REFERENCES public.saves(id);