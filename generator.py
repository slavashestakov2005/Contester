import glob
import os
from shutil import copyfile


files = ['image.ico', 'style.css', 'script.js']
head1 = r'''<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="Windows-1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	try{
		Cookie name = new Cookie("name", request.getParameter("name").trim());
		Cookie surname = new Cookie("surname", request.getParameter("surname").trim());
		name.setMaxAge(60*60*10);
		surname.setMaxAge(60*60*10);
		// Add both the cookies in the response header.
		response.addCookie(name);
		response.addCookie(surname);
	}catch(Exception e){}
%>

'''
head2 = r'''<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="Windows-1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

'''
# root_directory = 		'...<имя папки>'
# contest_directory = 	'...<имя папки>/<имя контеста>'
# result_directory =	'...<имя папки>/<имя контеста>/<gen>'


def correct(string):
	return string.replace("+", "%2B")


def get_root_directory():
	with open('root.txt', 'r') as fd:	# in ANSII encode
		root_directory = fd.read()
	return root_directory


def copy_files(result_directory):
	'''for file in files:
		copyfile(file, result_directory + '\\' + file)'''


def get_tasks_list(contest_directory, result_directory):
	tasks_list = glob.glob(contest_directory + '\\*\\')
	result = []
	position = len(contest_directory) + 1
	for file in tasks_list:
		if file != result_directory + '\\':
			result.append(file[position:-1])
	return result


def read_file_correct(path, tab_count = 4):
	tabs = ''
	for _ in range(tab_count):
		tabs += '\t'
	result = ''
	with open(path, 'r', encoding='utf-8') as fd:
		for line in fd.readlines():
			result += tabs + '<p>' + line[:-1] + '</p>' + '\n'
	return result[:-1]


def get_contest_description(contest_directory):
	return read_file_correct(contest_directory + '\\about.txt')


def get_pages_header(contest_name, tasks_list, type = False):
	text = ""
	if type == True:
		text = head1
	else:
		text = head2
	text += r'''<html lang="ru">
	<head>
		<meta charset="Windows-1251">
		<link rel="shortcut icon" href="image.ico" type="image/x-icon">
		<link rel="stylesheet" href="style.css">
		<script src="script.js" type="text/javascript"> </script>
		<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
        <script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
		<title>''' + contest_name + r'''</title>
	</head>
	<body>
		<div id="header">
			<div id="nav1"><h1><a href="contest.jsp">''' + contest_name + '''</a></h1></div>
			<div id="nav2">
				<p align="right">
				<script> document.write(getCookie(document, "name")); </script>
				<br/>
				<script> document.write(getCookie(document, "surname")); </script>
				<br/>
				<a href="index.jsp">Выйти</a>
				</p>
			</div>
		</div>
		<div id="page">
			<div id="sidebar">'''
	for i in range(len(tasks_list)):
		text += r'''
				<h2><a href="task''' + str(i + 1) + r'''.jsp">''' + tasks_list[i] + '''</a></h2>'''
	text += r'''
				<div id="down"></div>
			</div>'''
	return text


def generate_main_file(contest_directory, contest_name, tasks_list, result_directory):
	text = get_pages_header(contest_name, tasks_list, True)
	text += r'''
			<div id="content">
				<center><h2>''' + contest_name + r'''</h2></center>
''' + get_contest_description(contest_directory) + r'''
				<div id="down2"></div>
			</div>
		</div>
	</body>
</html>'''
	with open(result_directory + '\\contest.jsp', 'w') as fd:
		fd.write(text)


def get_task_condition(contest_directory, task_name):
	return read_file_correct(contest_directory + '\\' + task_name + '\\condition.txt')


def get_task_input(contest_directory, task_name):
	return read_file_correct(contest_directory + '\\' + task_name + '\\input.txt')


def get_task_output(contest_directory, task_name):
	return read_file_correct(contest_directory + '\\' + task_name + '\\output.txt')


def get_tests_files(path):
	tests_files = glob.glob(path + '\\*.txt')
	return tests_files


def get_text_of_task(path):
	return read_file_correct(path, 7)


def get_task_tests(contest_directory, task_name):
	path = contest_directory + '\\' + task_name + '\\examples'
	tests_files = get_tests_files(path)
	text = ''
	for i in range(0, len(tests_files), 2):
		text += r'''
					<tr>
						<td>
''' + get_text_of_task(tests_files[i]) + r'''
						</td>
						<td>
''' + get_text_of_task(tests_files[i + 1]) + r'''
						</td>
					</tr>'''
	return text


def generate_task_file(contest_directory, contest_name, tasks_list, task_name, task_count, result_directory):
	text = get_pages_header(contest_name, tasks_list)
	text += r'''
			<div id="content">
				<center><h2>''' + task_name + r'''</h2></center>
				<h3>Условие:</h3>
''' + get_task_condition(contest_directory, task_name) + r'''
				<h3>Входные данные:</h3>
''' + get_task_input(contest_directory, task_name) + r'''
				<h3>Выходные данные:</h3>
''' + get_task_output(contest_directory, task_name) + r'''
				<table border="1" width="95%">
					<tr>
						<td width="50%"><center>Input</center></td>
						<td width="50%"><center>Output</center></td>
					</tr>''' + get_task_tests(contest_directory, task_name) + r'''
				</table>
				<div id="code">
					<p>Решение:</p>
					<% out.print("<form action=\"run?contest=''' + correct(contest_name) + r'''&task=''' + correct(task_name) + r'''&name="); %>${cookie['name'].getValue()}<% out.print("&surname="); %>${cookie['surname'].getValue()}<% out.print(" method=\"post\">"); %>
						<textarea id="code_text" name="code" placeholder="Введите код" oninput="textInput(document)"></textarea>
						<br/>
						<input id="code_file" type="file" oninput="codeInput(document, 'file');" onchange="readFile(document);"/>
						<select name="lang">
                            <option value="cpp">C++</option>
                            <option value="py">Python</option>
                        </select>
						<input type="submit" value="Отправить" onclick="return Start(this);" />
					</form>
				</div>
				<div id="down2"></div>
			</div>
		</div>
	</body>
</html>'''
	with open(result_directory + '\\task' + str(task_count) + '.jsp', 'w') as fd:
		fd.write(text)


def main():
	contest_name = input('Введите имя директории с контестом: ')
	contest_directory = get_root_directory() + '\\Contests\\' + contest_name
	result_directory = get_root_directory() + '\\apache-tomcat-9.0.27\\webapps\\Contester'
	copy_files(result_directory)
	tasks_list = get_tasks_list(contest_directory, result_directory)
	generate_main_file(contest_directory, contest_name, tasks_list, result_directory)
	i = 1
	for task_name in tasks_list:
		generate_task_file(contest_directory, contest_name, tasks_list, task_name, i, result_directory)
		i += 1



if __name__ == '__main__':
	try:
		main()
	except Exception as ex:
		print(ex)


input('Генерация завершена')
