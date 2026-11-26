Primero hacemos levantamos el docker con :

"docker compose up -d" => dentro de /Tema04/reserva 

<img width="966" height="95" alt="image" src="https://github.com/user-attachments/assets/7a0f5ccd-48eb-48c3-9382-a2dd160a06ed" />


Verifica que el contenedor se ha iniciado correctamente y está activo

"docker compose ps"

<img width="1460" height="125" alt="image" src="https://github.com/user-attachments/assets/0352928f-07bf-4c28-9df7-6e45d629b65b" />


Creamos la tabla en PostgreSQL

docker exec -it postgres_reservas psql -U alumno -d reservas -c "
CREATE TABLE IF NOT EXISTS reserva (
    id BIGSERIAL PRIMARY KEY,
    alumno VARCHAR(100) NOT NULL,
    aula VARCHAR(20) NOT NULL,
    fecha DATE NOT NULL
);"

Tenemos :
Puerto : 5442
Usuario : alumno
Contra : alumno123
Base de datos : Reserva

Funciones del menu : 

1. Realizar una reserva y almacenarla

   <img width="597" height="495" alt="image" src="https://github.com/user-attachments/assets/3d8e4653-8daa-451b-a871-77fa538351ca" />

2. Nos muestra todas las reservas hechas

   <img width="617" height="142" alt="image" src="https://github.com/user-attachments/assets/88df43b7-49ce-46f3-ad96-7ead2d54b743" />

3. Buscar reservas por aula:

   <img width="597" height="142" alt="image" src="https://github.com/user-attachments/assets/7604e422-ece1-442b-80f9-96dc3339a5c4" />

4. Eliminar una reserva:

   <img width="597" height="142" alt="image" src="https://github.com/user-attachments/assets/24bbd2f3-ff35-4aad-b41b-9f500c32225e" />

5. Modificar una reserva

   <img width="725" height="240" alt="image" src="https://github.com/user-attachments/assets/346deed2-9e30-4998-b7b6-e5ab25d65aae" />












