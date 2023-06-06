CREATE OR REPLACE FUNCTION random_group_name() RETURNS VARCHAR AS $$
DECLARE
    chars TEXT[] := ARRAY['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
    digits TEXT[] := ARRAY['0','1','2','3','4','5','6','7','8','9'];
    group_name VARCHAR := '';
BEGIN
    group_name := chars[FLOOR(RANDOM() * (array_upper(chars, 1) - 1) + 1)] || chars[FLOOR(RANDOM() * (array_upper(chars, 1) - 1) + 1)] || '-' || digits[FLOOR(RANDOM() * (array_upper(digits, 1) - 1) + 1)] || digits[FLOOR(RANDOM() * (array_upper(digits, 1) - 1) + 1)];
    RETURN group_name;
END;
$$ LANGUAGE plpgsql;

