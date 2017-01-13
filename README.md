CV-parser
How it works?
---------------------------
![alt tag](https://github.com/ATsyganok/cv-parser/blob/master/parser-cv_9-release_web/src/main/webapp/resources/pictures/parser%20flow.jpg)
----------------------------
Project Name: CV-parser

Application: Web-based application for automatic resume parser and Structure analysis for object recognition: Contact (name, phone), experience, skills, language skills, education, and others.

Input (restrictions apply):
- Either doc or pdf format;
- An amount from 1 to 100 per upload;
- English;
- A summary only for developers (loaded model for developers)
- 1-2 pages (40 paragraphs of text);
- Division into paragraphs with at least one interval;
- Vertical structure of a resume.
 
Features (main):
- Analysis of the structure of the resume to highlight logical blocks;
- Determine the type of a logical block on the basis of keywords (determining a priority probability) - Classification model for developer;
- Decision-making based on the probability indicators (Bayes' theorem and Total probability formula);
- Updated classification model based on the new data.
 
Features (optional):
- Drag & drop form to download CV (resume in the form of drag to upload);
- Parser resume and submitting it to web-form;
- Save the file on the server (the source) + parsed resume (structure);
- Registration of new users and link file to the account (Spring Security);
- Admin access to the division level of access to pages (log: admin pas: admin)
 
Technologies:
Spring MVC
JPA / Hibernate
Spring Security (Login & Registration)
Dropzone.js
Angular.js
Bootstrap
 
-----------------------------------------
Название проекта: CV-parser
 
Назначение: веб-приложение для автоматического парсера резюме и анализа его структуры для распознавания объектов: контакты (имя, телефон), опыта работы, навыки, знания языков, образования и др.
 
Входные данные (ограничения):
- формат doc или pdf;
- количестве от 1 до 100 за один upload;
- английський язык;
- резюме только разработчиков (загружена модель для разработчиков)
- 1-2 страницы(40 абзацов текста);
- разделенние на абзацы минимум с одним интервалом;
- вертикальная структура резюме.
 
Возможности (основные):
- анализ структуры резюме для выделения логических блоков;
- определения типа логического блока на основании ключевых слов (определения априорной вероятности) - составление классификационной модели;
- принятие решения на основании вероятностных показателей (Теорема Байеса + Формула полной вероятности);
- обновление классификационной модели на основании новых данных.
 
Возможности (дополнительные):
- drag&drop форма для загрузки резюме (перетащить резюме в форме для upload);
- персер резюме и представление его в стандатрной web-форме;
- сохранения файлов на сервере (исходники) + распарсенное резюме;
- регистрация новых пользователей и привязка их файлов к аккаунту (Spring Security);
- админский доступ с разделением уровня доступа к страницам(log:admin pas:admin)
 
Технологии:
Spring MVC
JPA/Hibernate
Spring Security (Login&Registration)
Dropzone.js
Angular.js
Bootstrap
 
-------------------------------------
