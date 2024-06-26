﻿# sysc4806-survey

SYSC 4806 Fall 2023 Team Project: Mini-SurveyMonkey web app

![Status Badge](https://github.com/parrottq/sysc4806-survey/actions/workflows/main_mini-survey.yml/badge.svg)

## Description:

The goal of this team project is to reproduce a simplified version of the popular survey creation tool, SurveyMonkey. 

This project will allow users to create polls, answer polls which have been created, and view the results of polls.

The production version of this application can be found at: https://mini-survey.azurewebsites.net/

![SYSC4806_SurveyChimp_UML](https://github.com/parrottq/sysc4806-survey/assets/89619482/facd81bf-d073-47c7-8c35-e534d9fa8c69)

## Tools

This backend of this project is built using the java programming language and Springboot framework. The frontend is constructed using plain html, css, and javascript. The database supporting this project is an H2 database which uses SQL. 
Communications between the frontend and backend are currently performed using ajax, however this will be changed to include websockets in future iterations.


## Database

The database for this application consists of three main entities: Answer, Question, and Poll.
The ER diagram below provides a visual representation of the relationships between these entities.

Schema:
![image](https://github.com/parrottq/sysc4806-survey/assets/89619482/f5972704-894d-4643-a1b7-b6cd45a072db)


## Usage:

The application allows surveyors to create a survey with a list of questions. These questions can be open-ended (text), or asking to choose among many options.

Users can then fill out the survey that is a form generated based on the type of questions in the survey. Once the surveyor closes the survey, a survey result is generated, compiling the answers: for open-ended questions, the answers are just listed as-is, for choice questions a pie chart is generated.


## Credits:

Copyright 2023 GROUP 15

