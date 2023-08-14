-- New VitCheck Settings
INSERT INTO public.survey_installation_setting(
	setting, value, survey_installation_id, value_type)
	VALUES (0, 'TRAFFIC_LIGHT', 'efc078b2-5153-4b75-8890-49c93321f4f7', 'com.vitmedics.vitcheck.model.enumeration.resultsViewType.ResultsViewType');  -- Results View type

-- VIT D SURVEY

INSERT INTO public.survey(
	id, active, archived, created, description, introduction, name, version)
	VALUES (2, true, NULL, '2021-02-24 13:54:30.170161', 'VitDetector Survey', 'Intro', 'VitDetector', '1.0.0');
	
INSERT INTO public.survey_installation(
	id, description, email_confirmation_enabled, client_id, survey_id, installation_url, store_type, crm_type)
	VALUES ('afc078b2-5153-4b75-8890-49c93321f4f8', 'Vitmedics install of VitDetector', false, '74e322b0-13f3-47cb-851e-142c3ce9e652', 2, 'http://locahost:4000', 0, NULL);

INSERT INTO public.survey_installation_store_setting(
	id, store_setting, value, survey_installation_id)
	VALUES (2, 0, 'https://08ebd7a4a04c46d765b2ca95c58114af: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@vitmedics.myshopify.com/admin/api/2021-01/graphql.json', 'afc078b2-5153-4b75-8890-49c93321f4f8');
	
INSERT INTO public.survey_installation_setting(
	setting, value, survey_installation_id, value_type)
	VALUES (0, 'HEAT_SCALE', 'afc078b2-5153-4b75-8890-49c93321f4f8', 'com.vitmedics.vitcheck.model.enumeration.resultsViewType.ResultsViewType');  -- Results View type
	
INSERT INTO public.survey_installation_setting(
	setting, value, survey_installation_id, value_type)
	VALUES (1, '15', 'afc078b2-5153-4b75-8890-49c93321f4f8', 'java.lang.Integer');  -- HEAT SCALE CEILING
	
INSERT INTO public.survey_installation_setting(
	setting, value, survey_installation_id, value_type)
	VALUES (2, '7.5', 'afc078b2-5153-4b75-8890-49c93321f4f8', 'java.lang.Integer');  -- HEAT SCALE MID
	
INSERT INTO public.survey_installation_setting(
	setting, value, survey_installation_id, value_type)
	VALUES (3, '2', 'afc078b2-5153-4b75-8890-49c93321f4f8', 'java.lang.String');  -- Medication nutrient filter
	
INSERT INTO public.survey_installation_setting(
	setting, value, survey_installation_id, value_type)
	VALUES (4, 'true', 'afc078b2-5153-4b75-8890-49c93321f4f8', 'java.lang.Boolean');  -- Aggregate Nutrient Scores
	

-- SECTION
INSERT INTO public.survey_section(
	id,icon, introduction, label, name, sort_order, survey_id)
	VALUES (5, 'fa-user-md', '<p>In a perfect world, it might be possible to get all of the Vitamin D we need from the food we eat and a healthy amount of exposure to sunshine. But, in reality we&#39;re all individuals and differences in our genes, age, gender, skin type, lifestyle, and health status, mean we each need to personalize our vitamin D levels to make sure our body achieves its optimal nutritional status for good health.</p><p><strong>Here are some of the key issues about you that the team at VitaminDetector have identified that influence these needs.</strong></p>', 'About You', 'About You', 1, 2);

INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (27, 'Age', 'Age', 0, true, 1, NULL, NULL, NULL, 5);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (73, NULL, NULL, false, '18-24', '18-24', 1, 27);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (74, NULL, NULL, false, '25-44', '25-44', 2, 27);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (75, NULL, NULL, false, '45-64', '45-64', 3, 27);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (76, NULL, NULL, false, '65+', '65+', 4, 27);


INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (28, 'Gender', 'Gender', 0, true, 2, NULL, NULL, NULL, 5);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (77, NULL, NULL, false, 'Male', 'Male', 1, 28);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (78, NULL, NULL, false, 'Female', 'Female', 2, 28);
	
	
INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (29, 'Height', 'Height', 5, true, 3, NULL, NULL, NULL, 5);		
	

INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (30, 'Weight', 'Weight', 4, true, 4, NULL, NULL, NULL, 5);	
	
	
INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (31, 'Ethnic background', 'Ethnic Background', 0, true, 5, NULL, NULL, NULL, 5);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (79, NULL, NULL, false, 'Black', 'Black', 1, 31);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (80, NULL, NULL, false, 'Asian', 'Asian', 2, 31);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (81, NULL, NULL, false, 'White', 'White', 3, 31);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (82, NULL, NULL, false, 'Chinese', 'Chinese', 4, 31);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (83, NULL, NULL, false, 'Mixed', 'Mixed', 5, 31);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (84, NULL, NULL, true, 'Other', 'Other', 6, 31);


INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id, help_text)
	VALUES (32, 'Skin type', 'Skin type', 0, true, 6, NULL, NULL, NULL, 5, '<ul><li>Type I Skin - White, very fair, red or blonde hair, blue eyes freckles. Always burn, never tan</li><li>Type II - White, Fair, red or blonde hair, blue hazel or green eyes. Usually burn, tan with difficulty</li><li>Type III - Cream white, fair with any eye colour, sometimes mildly burns gradually tans</li><li>Type IV - Brown, typical Mediterranean Caucasian skin. Rarely burns, tan with ease</li><li>Type V - Dark drown, mid-eastern skin type, very rarely burns, tans very easily</li><li>Type VI - Black, never burns, tans very easily</li></ul>');
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (85, NULL, NULL, false, 'Type I', 'Type I', 1, 32);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (86, NULL, NULL, false, 'Type II', 'Type II', 2, 32);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (87, NULL, NULL, false, 'Type III', 'Type III', 3, 32);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (88, NULL, NULL, false, 'Type IV', 'Type IV', 4, 32);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (89, NULL, NULL, false, 'Type V', 'Type V', 5, 32);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (90, NULL, NULL, false, 'Type VI', 'Type VI', 6, 32);	
	

-- SECTION 2
	
INSERT INTO public.survey_section(
	id,icon, introduction, label, name, sort_order, survey_id)
	VALUES (6, 'fa-cloud-sun', '<p><strong>Thank you. Now some simple questions about your typical exposure sunshine, use of sun protection and tanning.</strong></p>', 'About You & Sunshine', 'About You & Sunshine', 2, 2);	


INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (33, 'Sun exposure in the last week', 'Last week sun exposure', 0, true, 1, NULL, NULL, NULL, 6);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (91, NULL, NULL, false, '<5 minutes/day', '<5 minutes/day', 1, 33);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (92, NULL, NULL, false, '5-15 minutes/day', '5-15 minutes/day', 2, 33);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (93, NULL, NULL, false, '15-30 minutes/day', '15-30 minutes/day', 3, 33);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (94, NULL, NULL, false, '>30 minutes/day', '>30 minutes/day', 4, 33);
	
	
INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (34, 'Sun exposure in the last 3 months', 'Last 3 month sun exposure', 0, true, 2, NULL, NULL, NULL, 6);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (95, NULL, NULL, false, 'Regular – an hour per day', 'Regular', 3, 34);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (96, NULL, NULL, false, 'Occasional - an hour per week', 'Occasional', 2, 34);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (97, NULL, NULL, false, 'Rarely – once a month', 'Rarely', 1, 34);
	
INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (35, 'Sun tan in the last 12 months', 'Sun tan', 0, true, 3, NULL, NULL, NULL, 6);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (98, NULL, NULL, false, 'No', 'No', 1, 35);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (99, NULL, NULL, false, 'Yes', 'Yes', 2, 35);

INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (36, 'Application of suncsreen', 'Sunscreen application', 0, true, 4, NULL, NULL, NULL, 6);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (100, NULL, NULL, false, 'Never', 'Never', 1, 36);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (101, NULL, NULL, false, 'Infrequently', 'Infrequently', 2, 36);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (102, NULL, NULL, false, 'Sometimes', 'Sometimes', 3, 36);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (103, NULL, NULL, false, 'More often than not', 'More often than not', 4, 36);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (104, NULL, NULL, false, 'Always', 'Always', 5, 36);
	
	
INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (37, 'Do you use a tanning booth?', 'Tanning booth usage', 0, true, 5, NULL, NULL, NULL, 6);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (105, NULL, NULL, false, 'No', 'No', 1, 37);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (106, NULL, NULL, false, 'Yes', 'Yes', 2, 37);
	
	
	
--  SECTION 3
INSERT INTO public.survey_section(
	id,icon, introduction, label, name, sort_order, survey_id)
	VALUES (7, 'fa-utensils', '<p><strong>Now some quick questions about your dietary intake of vitamin D sources and things that can affect its levels.</strong></p>', 'Vitamin D Consumption', 'Vitamin D Consumption', 3, 2);	

INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (38, 'Do you take a supplement containing Vitamin D?', 'Supplement', 0, true, 1, NULL, NULL, NULL, 7);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (119, NULL, NULL, false, 'No', 'No', 1, 38);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (107, NULL, NULL, false, '200iu', '200iu', 2, 38);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (108, NULL, NULL, false, '>200iu', '>200iu', 3, 38);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (109, NULL, NULL, false, 'Cod Liver Oil', 'Cod liver oil', 4, 38);
	
	
INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (39, 'How many portions of oily fish do you eat?', 'Oily Fish', 0, true, 2, NULL, NULL, NULL, 7);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (110, NULL, NULL, false, '0-1 per week', '0-1', 1, 39);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (111, NULL, NULL, false, '1-2 per week', '1-2', 2, 39);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (112, NULL, NULL, false, '>2 per week', '>2', 3, 39);
	
	
INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (40, 'How many portions of breakfast cereal fortified with Vitamin D do you eat?', 'Cereal', 0, true, 3, NULL, NULL, NULL, 7);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (113, NULL, NULL, false, 'None', 'None', 1, 40);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (114, NULL, NULL, false, '1-3 per week', '1-3 per week', 2, 40);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (115, NULL, NULL, false, '3-5 per week', '3-5 per week', 3, 40);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (116, NULL, NULL, false, '>5 per week', '>5 per week', 4, 40);


INSERT INTO public.survey_question(
	id, label, name, question_type, required, sort_order, tooltip, validation_regex, parent_answer_id, section_id)
	VALUES (41, 'Do you smoke?', 'Smoking', 0, true, 4, NULL, NULL, NULL, 7);	
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (117, NULL, NULL, false, 'No', 'No', 1, 41);
INSERT INTO public.survey_answer(
	id, additional_info, additional_info_evidence, is_other, label, name, sort_order, question_id)
	VALUES (118, NULL, NULL, false, 'Yes', 'Yes', 2, 41);
	
	

	
-- INTERACTIONS
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (73, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (74, 2, 0.5);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (75, 2, 0.75);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (76, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (77, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (78, 2, 0.5);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (79, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (80, 2, 0.5);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (81, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (82, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (83, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (84, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (85, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (86, 2, 0.75);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (87, 2, 0.5);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (88, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (89, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (90, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (91, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (92, 2, 0.5);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (93, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (94, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (95, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (96, 2, 0.5);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (97, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (98, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (99, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (100, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (101, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (102, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (103, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (104, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (105, 2, 0.5);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (106, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (107, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (108, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (109, 2, 0.1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (119, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (110, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (111, 2, 0.5);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (112, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (113, 2, 1);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (114, 2, 0.5);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (115, 2, 0.25);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (116, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (117, 2, 0);
INSERT INTO public.survey_answer_interaction(answer_id, nutrient_id, score)	VALUES (118, 2, 1);
	
	
	
	
	