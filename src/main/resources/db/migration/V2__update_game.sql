ALTER TABLE IF EXISTS public.manufacturer DROP COLUMN IF EXISTS user_id;

ALTER TABLE IF EXISTS public.manufacturer ADD COLUMN user_id bigint;

ALTER TABLE IF EXISTS public.game DROP COLUMN IF EXISTS gamestatus;

ALTER TABLE IF EXISTS public.game ADD COLUMN gamestatus character varying(255) COLLATE pg_catalog."default";

ALTER TABLE IF EXISTS public.manufacturerstatus DROP COLUMN IF EXISTS income;

ALTER TABLE IF EXISTS public.manufacturerstatus ADD COLUMN income real;