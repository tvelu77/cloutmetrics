# CloutMetrics

A web application for computing git repositories metrics such as the number of commits or the language ratio.

## Features

- Light/dark mode toggle
- Add a git repository
- Manage a git repository (delete, rename)
- Metrics page (commits, tags)
- Sortable table

## Tech stack

**Client:** Angular, PrimeNG, PrimeFlex

**Server:** Java, Spring-Boot

## Installation

Clone the project

```bash
git clone git@github.com:tvelu77/cloutmetrics.git
```

Go to the project directory

```bash
cd cloutmetrics
```

For now, the application needs to have its backend and frontend launched at the same time.  
Let's launch the backend

```bash
mvn spring-boot:run # For Linux and UNIX
mvnw spring-boot:run # For Windows
```


And now, the frontend
> Be sure, you are in the project directory

```bash
cd frontend
ng serve
```

## Contributing

Contributions are always welcome!

Please fork this project and make a pull request for any bug fixes or features.

## Authors

- [@tvelu77](https://github.com/tvelu77)

