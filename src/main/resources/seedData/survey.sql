INSERT INTO client(
    id, created, enabled, name)
    VALUES ('74e322b0-13f3-47cb-851e-142c3ce9e652', NOW(), true, 'VitMedics');

INSERT INTO public.survey(
    id, active, created, name, introduction, version)
VALUES (1, true, NOW(), 'Vitcheck Survey', '<p>Welcome to VitCheck - a simple way to assess the effects lifestyle, life stage and medications might have on your nutritional needs. Just click the button below to get started.</p>', '1.0.0');

INSERT INTO survey_installation(
    id, email_confirmation_enabled, results_view_type, client_id, survey_id, installation_url, store_type, crm_type)
    VALUES ('efc078b2-5153-4b75-8890-49c93321f4f7', true, 1, '74e322b0-13f3-47cb-851e-142c3ce9e652', 1, 'https://www.vitmedics.com/vitcheck', 0, 0);

-- Vitmedics Shopify settings
INSERT INTO public.survey_installation_store_setting(
	store_setting, value, survey_installation_id)
	VALUES (0, 'https://f523d533fc70b0ecdd74c7d0a649efce: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@jhtsandbox.myshopify.com/admin/api/2021-01/graphql.json', 'efc078b2-5153-4b75-8890-49c93321f4f7');

-- CRM Settings for Vitmedics
INSERT INTO survey_installation_crm_setting(
   crm_setting, value, survey_installation_id)
   VALUES(0, '98e2b537b8b92c30280f03be258a7034-us7', 'efc078b2-5153-4b75-8890-49c93321f4f7');

INSERT INTO survey_installation_crm_setting(
   crm_setting, value, survey_installation_id)
   VALUES(1, '26dabce88a', 'efc078b2-5153-4b75-8890-49c93321f4f7');

INSERT INTO survey_installation_crm_setting(
   crm_setting, value, survey_installation_id)
   VALUES(2, 'VC_MEDS', 'efc078b2-5153-4b75-8890-49c93321f4f7');

INSERT INTO survey_installation_crm_setting(
   crm_setting, value, survey_installation_id)
   VALUES(3, 'VC_Q', 'efc078b2-5153-4b75-8890-49c93321f4f7');

-- insert survey sections
INSERT INTO public.survey_section(
        id, survey_id, name, label, introduction, icon, sort_order)
    VALUES (1, 1, 'About You', 'About You', '<p>In a perfect world, it might be possible to get all of the nutrients we need from a balanced diet. But, in reality we''re all individuals and differences in our genes, age, gender, lifestyle, and health status, mean we each need to personalize our micronutrient intake to make sure our body achieves its optimal nutritional status for good health.</p><p><strong>Here are some of the key issues the team at VitMedics have identified that influence these needs.</strong></p>', 'fa-user-md', 1);

INSERT INTO public.survey_section(
            id, survey_id, name, label, introduction, icon, sort_order)
        VALUES (2, 1, 'Food Choices', 'Food Choices', '<p><strong>Thank you. Now some simple questions about your diet.</strong></p>', 'fa-apple-alt', 2);

INSERT INTO public.survey_section(
        id, survey_id, name, label, introduction, icon, sort_order)
    VALUES (3, 1, 'Lifestyle', 'Lifestyle', '<p>Thank you. Now some simple questions about your everyday life.</p>', 'fa-walking', 3);

INSERT INTO public.survey_section(
            id, survey_id, name, label, introduction, icon, sort_order)
        VALUES (4, 1, 'Special Nutritional Needs', 'Special Nutritional Needs', '<p><strong>Are you seeking nutritional support for any of the following:</strong></p>', 'fa-star', 4);

