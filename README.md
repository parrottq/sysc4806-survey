# sysc4806-survey

SYSC 4806 Fall 2023 Team Project: Mini-SurveyMonkey web app

![Status Badge](https://github.com/parrottq/sysc4806-survey/actions/workflows/main_mini-survey.yml/badge.svg)

## Description:

The goal of this team project is to reproduce a simplified version of the popular survey creation tool, SurveyMonkey. 

This project will allow users to create polls, answer polls which have been created, and view the results of polls.

UML Class of Models:
![SYSC4806_SurveyChimp_UML](https://github.com/parrottq/sysc4806-survey/assets/89619482/33f6910e-3b96-495a-9022-3a7aab07c99d)


## Tools

This backend of this project is built using the java programming language and Springboot framework. The frontend is constructed using plain html, css, and javascript. The database supporting this project is an H2 database which uses SQL. 
Communications between the frontend and backend are currently performed using ajax, however this will be changed to include websockets in future iterations.


## Database

The database is tracked at jdbc:h2:mem:surveydb

The DB Schema can be viewed by going to localhost:8080/hs-console. The login information for the page this link brings you to is admin/admin (username/password)

Schema:
![SYSC4806_SurveyChimp_Schema](https://github.com/parrottq/sysc4806-survey/assets/89619482/cbb580da-0366-447e-9e8e-5d9371019f56)


## Usage:

The application allows surveyors to create a survey with a list of questions. These questions can be open-ended (text), or asking to choose among many options.

Users can then fill out the survey that is a form generated based on the type of questions in the survey. Once the surveyor closes the survey, a survey result is generated, compiling the answers: for open-ended questions, the answers are just listed as-is, for number questions a histogram of the answers is generated, for choice questions a pie chart is generated.

## UML Model Diagram

![SYSC4806_SurveyChimp_UML](https://github.com/parrottq/sysc4806-survey/assets/89619482/bff55e63-c0d5-4b88-954b-d64fa407d24b)


## Credits:

Copyright 2023 GROUP 15

