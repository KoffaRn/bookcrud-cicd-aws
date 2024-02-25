# Table of Contents
* [CI/CD](#cicd) 
* [OpenApi definition](#openapi-definition)
* [Smoke test script](#smoke-test-script)
## CI/CD 
This project uses GitHub actions for CI/CD. The workflow is defined in [`.github/workflows/cicd.yml`](.github/workflows/cicd.yml).

### Build
Builds the project using maven, skipping tests, puts the jar in a docker container and pushes it to dockerhub with test tag. Also puts the jar in an artifact and publishes it on GitHub actions.

### Test
Runs unit tests using maven. Downloads the staged jar. Then the dockerhub image is pulled to aws ec2 instance and is smoke tested (here is where you would do integration tests if that were applicable).
If the smoke test passes, the image is tagged as latest and pushed to dockerhub.

### Deploy
The old image is stopped and removed from the ec2 instance. The new image is pulled and started.

### Alternative approach
The CI/CD could also be run on AWS CodePipeline and deployed on for example BeanStalk. If this were a larger scaled production project that would probably be beneficial and require less work in terms of resource allocation, load balancing etc. But for the purposes of this project, a more hands-on approach with GitHub actions and a self-managed EC2 instance made more sense to me as was more cost-effective.
## OpenAPI definition

> Version v0.0.1

### Path Table

| Method | Path | Description                                     |
| --- | --- |-------------------------------------------------|
| GET | [/books](#getbooks) | Get all books in database                       |
| PUT | [/books](#putbooks) | Replace a book with new book                    |
| POST | [/books](#postbooks) | Add a new book                                  |
| DELETE | [/books](#deletebooks) | Remove a book                                   |
| GET | [/health](#gethealth) | Check health of API. Only replies "OK" in text. |
| GET | [/books/{id}](#getbooksid) | Get single book based on path variable ID.      |

### Reference Table

| Name | Path | Description                                                            |
| --- | --- |------------------------------------------------------------------------|
| Book | [#/components/schemas/Book](#componentsschemasbook) | Book object. Used both for requests and responses as application/json. |

### Path Details

***

#### [GET]/books

##### Responses

- 200 OK

`*/*`

```ts
{
  id?: string
  title?: string
  author?: string
}[]
```

***

#### [PUT]/books

##### RequestBody

- application/json

```ts
{
  id?: string
  title?: string
  author?: string
}
```

##### Responses

- 200 OK

`*/*`

```ts
{
  id?: string
  title?: string
  author?: string
}
```

***

#### [POST]/books

##### RequestBody

- application/json

```ts
{
  id?: string
  title?: string
  author?: string
}
```

##### Responses

- 200 OK

`*/*`

```ts
{
  id?: string
  title?: string
  author?: string
}
```

***

#### [DELETE]/books

##### RequestBody

- application/json

```ts
{
  id?: string
  title?: string
  author?: string
}
```

##### Responses

- 200 OK

`*/*`

```ts
{
  "type": "string"
}
```

***

#### [GET]/health

##### Responses

- 200 OK

`*/*`

```ts
{
  "type": "string"
}
```

***

#### [GET]/books/{id}

##### Responses

- 200 OK

`*/*`

```ts
{
    id?: string
    title?: string
    author?: string
}
```

### References

#### #/components/schemas/Book

```ts
{
    id?: string
    title?: string
    author?: string
}
```

## Smoke test script
This is the script that is run on the ec2 instance to smoke test the application. It waits for the application to start and then checks the health endpoint.

If it doesn't start within the timer it exits with an error code. 
```bash
#!/bin/sh
timeout=60
while ! curl -f http://localhost/health; do
  sleep 1
  timeout=$((timeout-1))
  if [ "$timeout" -le 0 ]; then
    echo "Spring Boot App did not start in time"
    exit 1
  fi
done
```