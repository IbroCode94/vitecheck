CREATE SEQUENCE public.survey_id_seq
    INCREMENT 1
    START 2
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER TABLE survey
ALTER COLUMN id SET DEFAULT nextval('survey_id_seq'::regclass);

CREATE SEQUENCE public.survey_question_id_seq
    INCREMENT 1
    START 27
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER TABLE survey_question
ALTER COLUMN id SET DEFAULT nextval('survey_question_id_seq'::regclass);

CREATE SEQUENCE public.survey_answer_id_seq
    INCREMENT 1
    START 73
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

ALTER TABLE survey_answer
ALTER COLUMN id SET DEFAULT nextval('survey_answer_id_seq'::regclass);
