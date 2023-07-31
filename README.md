<h1>Проект по программированию на Java с использованием Scene Builder. Онлайн кинотеатр с интерфейсом для юзера и админа</h1>
<p><h2>Работа выполнена с помощью графического инструмента Java javaFX, с использованием SceneBuilder и базой данных PostgreSQL (Pg Admin)</h2></p>
<p><h3>Код для создания таблиц</h3></p>

<p><h3>-- Создание таблицы Movies</h3></p>
<p>CREATE TABLE Movies (</p>
<p>  ID SERIAL PRIMARY KEY,</p>
<p>  title VARCHAR(255),</p>
<p>  release_year INT,</p>
<p>  genre VARCHAR(255),</p>
<p>  rating INT</p>
<p>);</p>

<p><h3>-- Создание таблицы Users</h3></p>
<p>CREATE TABLE Users (</p>
<p>  ID SERIAL PRIMARY KEY,</p>
<p>  username VARCHAR(255),</p>
<p>  password VARCHAR(255)</p>
<p>);</p>
