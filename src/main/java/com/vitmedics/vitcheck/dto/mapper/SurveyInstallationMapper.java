package com.vitmedics.vitcheck.dto.mapper;

import com.vitmedics.vitcheck.dto.model.survey.*;
import com.vitmedics.vitcheck.model.entities.survey.SurveyInstallation;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SurveyInstallationMapper {

        public static SurveyInstallationDto toSurveyInstallationDto(SurveyInstallation installation) {
            return new SurveyInstallationDto()
                .setId(installation.getId())
                .setClientName(installation.getClient().getName())
                .setSurvey(
                    new SurveyDto()
                        .setId(installation.getSurvey().getId())
                        .setName(installation.getSurvey().getName())
                        .setIntroduction(installation.getSurvey().getIntroduction())
                        .setSections(
                            StreamSupport.stream(
                                    installation.getSurvey().getSections().spliterator(), false)
                                .map(
                                    section ->
                                        new SurveySectionDto()
                                            .setId(section.getId())
                                            .setLabel(section.getLabel())
                                            .setSortOrder(section.getSortOrder())
                                            .setIntroduction(section.getIntroduction())
                                            .setIcon(section.getIcon())
                                            .setQuestions(
                                                StreamSupport.stream(
                                                        section.getQuestions().spliterator(), false)
                                                    .map(
                                                        question ->
                                                            new SurveyQuestionDto()
                                                                .setId(question.getId())
                                                                .setLabel(question.getLabel())
                                                                .setSortOrder(question.getSortOrder())
                                                                .setQuestionType(question.getQuestionType())
                                                                .setRequired(question.isRequired())
                                                                .setTooltip(question.getTooltip())
                                                                .setHelpText(question.getHelpText())
                                                                .setParentAnswerId(question.getParentAnswer() != null ? question.getParentAnswer().getId() : null)
                                                                .setValidationRegex(question.getValidationRegex())
                                                                .setAnswers(
                                                                    StreamSupport.stream(
                                                                            question
                                                                                .getAnswers()
                                                                                .spliterator(),
                                                                            false)
                                                                        .map(
                                                                            answer ->
                                                                                new SurveyAnswerDto()
                                                                                    .setId(answer.getId())
                                                                                    .setLabel(
                                                                                        answer.getLabel())
                                                                                    .setSortOrder(
                                                                                        answer
                                                                                            .getSortOrder())
                                                                                    .setOther(
                                                                                        answer.isOther()))
                                                                        .collect(Collectors.toList())))
                                                    .collect(Collectors.toList())))
                                .collect(Collectors.toList())))
                .setEmailConfirmationEnabled(installation.isEmailConfirmationEnabled())
                .setSettings(installation.getSurveyInstallationSettingsAsTypedMap());
        }

}
