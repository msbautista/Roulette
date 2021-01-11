# Prueba Clean Code - Juego de Ruleta

Esta aplicación es un API que representa una ruleta de apuestas.

### Crear una ruleta [GET] `/roulette/new`

Endpoint que crea una ruleta para empezar a jugar.

Resultado:
```json
{
    "status": "OK",
    "data": 9, 
    "error": null
}
```
- **status:** Resultado de la operación
- **data:** Id de la ruleta para posteriores peticiones.
- **error:** Mensaje de error si este ocurre

### Abrir una ruleta [GET] `/roulette/open/1` 
**Donde 1 es el id de la ruleta creada previamente**

Permite las posteriores peticiones de apuestas 

Resultado:
```json
{
    "status": "OK",
    "data": "successful operation",
    "error": null
}
```
- **status:** Resultado de la operación
- **data:**  Mensaje describiendo el resultado de la operación
- **error:** Mensaje de error si este ocurre

### Crear una apuesta a un NÚMERO [POST] `/bet/new/number` 
Endpoint de apuesta a un número (los números válidos para apostar son del 0 al 36)

**Body:**
```json
{
    "idRoulette": 1,
    "number": 7,
    "money": 10000
}
```
- **idRoulette:** Number | Id de la ruleta
- **number:** Number | Número al que se apostara
- **money:** Number | cantidad de dinero apostar | Minimo: 1 Máximo: 10000

**Resultado:**
```json
{
    "status": "OK",
    "data": 1,
    "error": null
}
```
- **status:** Resultado de la operación
- **data:** Id de la apuesta
- **error:** Mensaje de error si este ocurre

### Crear una apuesta a un COLOR [POST] `/bet/new/color` 
Endpoint de apuesta a un color (rojo o negro)
**Body:**
```json
{
    "idRoulette": 1,
    "color": "red",
    "money": 1
}
```
- **idRoulette:** Number | Id de la ruleta
- **number:** String | Color al que se apostara | valores disponibles: **BLACK** y **RED**
- **money:** Number | cantidad de dinero apostar | Minimo: 1 Máximo: 10000

**Resultado:**
```json
{
    "status": "OK",
    "data": 1,
    "error": null
}
```
- **status:** Resultado de la operación
- **data:** Id de la apuesta
- **error:** Mensaje de error si este ocurre

### Cerrar ruleta [GET] `/roulette/close/1`
**Donde 1 es el id de la ruleta a cerrar**

Este endpoint devuelve el resultado de las apuestas hechas desde su apertura hasta el cierre.
Resultado:
```json
{
    "status": "OK",
    "data": [
        {
            "idBet": 1,
            "money": 0,
            "result": "LOSER",
            "betType": "NUMBER"
        }
    ],
    "error": null
}
```
- **status:** Resultado de la operación
- **data:** Lista con el resultado de las apuestas
	-  **idBet**: Id de la apuesta
	-  **money:** Cantidad de dinero ganada
	-  **result:** WINNER si la apuesta fue ganadora y LOSER si la apuesta fue perdida
	-  **betType:** El tipo de apuesta que se realizó
- **error:** Mensaje de error si este ocurre

### Obtener todas las ruletas [GET] `/roulette/all`

Endpoint de listado de ruletas creadas con sus estados (abierta o cerrada)
Resultado:
```json
{
    "status": "OK",
    "data": [
        {
            "id": 1,
            "state": "CLOSED",
            "randomNumber": 1
        }
    ],
    "error": null
}
```
- **status:** Resultado de la operación
- **data:** Lista de todas las ruletas
	-  **id**: Id de la ruleta
	-  **state:** Estado de la ruleta | CREATED: la ruleta fue creada - OPEN: la ruleta está abierta - CLOSED: la ruleta esta cerrada 
	-  **randomNumber:** si la ruleta esta cerrada, este campo tiene el número aleatorio que lanzo la ruleta
- **error:** Mensaje de error si este ocurre
