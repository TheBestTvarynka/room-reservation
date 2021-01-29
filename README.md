# Room reservation

## What it is?

Room reservation is a small web-app written in java (with servlets) that helps book rooms online.

It's not a real service. Never enter personal data!

## Motivation

I'm a Java developer. I was wanted experience with Java EE and then decide to write this web-app.

Maybe in future, I'll add new features to this project like advanced caching, security or more functionality for users.

## Technologies

### App

* Servlets
* Filters
* Listeners
* Sessions
* JSP/JSTL
* PostreSQL

### Other

* CSRF-attack protection

### Deploy

I've decided to deploy this app to [GCP Cloud Run](https://cloud.google.com/run). As a database, I use the [GCP Cloud SQL (PostgreSQL)](https://cloud.google.com/sql/docs/postgres/). Reasons why I've chosen GCP Cloud Run:

* Low in [cost](https://cloud.google.com/run/pricing)
* Easy to [deploy](https://cloud.google.com/run/docs/quickstarts/build-and-deploy#java)
* That's more than enough :wink:

