ALTER TABLE IF EXISTS public.manufacturerstatus DROP COLUMN IF EXISTS balance;
ALTER TABLE IF EXISTS public.manufacturerstatus RENAME balanse TO balance;
ALTER TABLE IF EXISTS public.manufacturerstatus ADD COLUMN IF NOT EXISTS balance double precision;