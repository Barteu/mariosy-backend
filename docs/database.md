## Run database

Navigate to `dockerfiles/` and run `docker compose up`

## Insert test data

`psql -h localhost -d mariosy_keycloak -U postgres -f 01_insert_test_data.sql`

## Clear tables from data

`psql -h localhost -d mariosy_keycloak -U postgres -f clear_tables.sql`
