# SpringBoot Template Project

## Information

### Swagger url:

[]()


### Database Information:

```
spring:
  datasource:
    url: 
    username: 
    password: 
```

### Profile:

1. application: properties for all environment
2. application-dev: profile to build for dev environment
3. application-local: profile to use on local
4. application-prod: profile to build for production environment

### Development server information

```

```

### Docker logging command

```
Show log path: docker inspect --format='{{.LogPath}}' containername
Show live logs: tail -f `docker inspect --format='{{.LogPath}}' containername`
```

### Docker database backup

```
# Backup
docker exec CONTAINER /usr/bin/mysqldump -u root --password=root DATABASE > backup.sql

# Restore
docker exec -i <container_id> mysql -u <user> --password=<password> DATABASE < backup.sql
```