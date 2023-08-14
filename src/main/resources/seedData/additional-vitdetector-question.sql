INSERT INTO survey_question(required,id,name,label,parent_answer_id,sort_order,tooltip,section_id,question_type)
VALUES (true, 42,'How would you rate your level of physical activity?','Physical Activity',NULL,6,NULL,6,0);

INSERT INTO survey_answer(id,question_id,name,label,additional_info,additional_info_evidence,sort_order,is_other)
VALUES (120,42,'Irregular','Irregular',NULL,NULL,1,False);
INSERT INTO survey_answer(id,question_id,name,label,additional_info,additional_info_evidence,sort_order,is_other)
VALUES (121,42,'Walking <1 Hour/Day','Walking <1 Hour/Day',NULL,NULL,1,False);
INSERT INTO survey_answer(id,question_id,name,label,additional_info,additional_info_evidence,sort_order,is_other)
VALUES (122,42,'Walking >=1 Hour/Day','Walking <1 Hour/Day',NULL,NULL,1,False);

INSERT INTO public.survey_answer_interaction (answer_id, nutrient_id, score) VALUES (120, 2, 1);
INSERT INTO public.survey_answer_interaction (answer_id, nutrient_id, score) VALUES (121, 2, 0.5);