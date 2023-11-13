# sysc4806-survey

SYSC 4806 Fall 2023 Team Project: Mini-SurveyMonkey web app

![Status Badge](https://github.com/parrottq/sysc4806-survey/actions/workflows/main_mini-survey.yml/badge.svg)

## Description:

The goal of this team project is to reproduce a simplified version of the popular survey creation tool, SurveyMonkey. 


## Database

The database is tracked at jdbc:h2:mem:surveydb

The DB Schema can be viewed by going to localhost:8080/hs-console. The login information for the page this link brings you to is admin/admin (username/password)

![SYSC4806_SurveyChimp_Schema](https://github.com/parrottq/sysc4806-survey/assets/89619482/cbb580da-0366-447e-9e8e-5d9371019f56)


## Usage:

The application allows surveyors to create a survey with a list of questions. These questions can be open-ended (text), or asking to choose among many options.

Users can then fill out the survey that is a form generated based on the type of questions in the survey. Once the surveyor closes the survey, a survey result is generated, compiling the answers: for open-ended questions, the answers are just listed as-is, for number questions a histogram of the answers is generated, for choice questions a pie chart is generated.


## Credits:

Copyright 2023 GROUP 15

