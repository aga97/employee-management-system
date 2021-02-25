create table departments (
    dept_no char(4) not null,
    dept_name varchar(40) unique not null,
    primary key (dept_no)
);

create table employees (
    emp_no integer not null auto_increment,
    birth_date date not null,
    first_name varchar(14) not null,
    gender varchar(1) not null,
    hire_date date not null,
    last_name varchar(16) not null,
    primary key (emp_no)
);

create table dept_emp (
    dept_no varchar(255) not null,
    emp_no integer not null,
    from_date date not null,
    to_date date not null,
    foreign key (dept_no) references departments (dept_no),
    foreign key (emp_no) references employees (emp_no),
    primary key (dept_no, emp_no)
);

create table dept_manager (
    dept_no varchar(255) not null,
    emp_no integer not null,
    from_date date not null,
    to_date date not null,
    foreign key (dept_no) references departments (dept_no),
    foreign key (emp_no) references employees (emp_no),
    primary key (dept_no, emp_no)
);

create table salaries (
    emp_no integer not null,
    from_date date not null,
    salary integer not null,
    to_date date not null,
    foreign key (emp_no) references employees (emp_no),
    primary key (emp_no, from_date)
);

create table titles (
    emp_no integer not null,
    from_date date not null,
    title varchar(50) not null,
    to_date date not null,
    foreign key (emp_no) references employees (emp_no),
    primary key (emp_no, from_date, title)
);