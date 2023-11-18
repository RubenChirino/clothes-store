# clothes-store
Clothes store E-commerce developed with Spring

## Dev setup Commands

```bash
# Install dependencies (only the first time)
mvn clean install

# Run the local server at localhost:8080
mvn spring-boot:run
```

### Home

<img width="1440" alt="Screenshot 2023-11-18 at 18 34 15" src="https://github.com/RubenChirino/clothes-store/assets/52714843/c2aa5dad-eb67-4562-b49a-a3a7615ca8f3">

## 👚 == Garments == 👕

### List 

<img width="1440" alt="Screenshot 2023-11-18 at 18 34 38" src="https://github.com/RubenChirino/clothes-store/assets/52714843/1e7fc928-71f3-4222-8992-4f702cd379f2">

### Create

<img width="1440" alt="Screenshot 2023-11-18 at 18 33 55" src="https://github.com/RubenChirino/clothes-store/assets/52714843/c2681a6b-172b-41af-b4df-2a62e92f33e3">

### Edit

<img width="1440" alt="Screenshot 2023-11-18 at 18 35 36" src="https://github.com/RubenChirino/clothes-store/assets/52714843/5bbe8daa-746f-4a28-a07c-dad3841b6606">

### Endpoints

| Method   | URL                                      | Description                              |
| -------- | ---------------------------------------- | ---------------------------------------- |
| `GET`    | `/api/garments/all`                      | Retrieves a list of all the garments     |
| `GET`    | `/api/garments`                          | Retrieves a paginated list of garments   |
| `GET`    | `/api/garments/:id`                      | Retrieves a garment by its ID            |
| `POST`   | `/api/garments`                          | Creates a new garment                    |
| `PUT`    | `/api/garments/:id`                      | Updates an existing garment              |
| `DELETE` | `/api/garments/:id`                      | Deletes a specific garment by its ID     |

### 👨‍👩‍👧‍👦 == Clients == 🙍🏻‍♀️👨🏻‍💼 

### List 

<img width="1440" alt="Screenshot 2023-11-18 at 18 51 23" src="https://github.com/RubenChirino/clothes-store/assets/52714843/09593f42-3051-4603-9c03-2d021267b227">

### Create

<img width="1440" alt="Screenshot 2023-11-18 at 18 51 36" src="https://github.com/RubenChirino/clothes-store/assets/52714843/4d3ac017-885c-4772-b660-d10ef6e0be0d">

### Edit

<img width="1440" alt="Screenshot 2023-11-18 at 18 51 53" src="https://github.com/RubenChirino/clothes-store/assets/52714843/2a95e8f2-62ba-4e72-aef3-5da0a5c2e29b">

### Endpoints

| Method   | URL                   | Description                                  |
| -------- | --------------------- | -------------------------------------------- |
| `GET`    | `/api/clients/all`    | Retrieves a list of all clients              |
| `GET`    | `/api/clients/:id`    | Retrieves a client by their ID               |
| `GET`    | `/api/clients`        | Retrieves a paginated list of clients        |
| `POST`   | `/api/clients`        | Creates a new client                         |
| `PUT`    | `/api/clients/:id`    | Updates an existing client                   |
| `DELETE` | `/api/clients/:id`    | Deletes a specific client by their ID        |

### 💶 == Sales == 🏷️

### List 

<img width="1440" alt="Screenshot 2023-11-18 at 18 56 20" src="https://github.com/RubenChirino/clothes-store/assets/52714843/abe3ea21-fefc-4851-b5af-d57dff5934dc">

### Create Cash Sale

<img width="1440" alt="Screenshot 2023-11-18 at 18 56 58" src="https://github.com/RubenChirino/clothes-store/assets/52714843/152a7b56-76f3-4025-8ac6-3ac84bc80180">

### Create Card Sale

<img width="1440" alt="Screenshot 2023-11-18 at 18 57 09" src="https://github.com/RubenChirino/clothes-store/assets/52714843/6cf94a13-bdd5-42f0-b9df-61b4029628a9">

### Show Sale

<img width="1440" alt="Screenshot 2023-11-18 at 18 57 30" src="https://github.com/RubenChirino/clothes-store/assets/52714843/59604f18-8c02-40c8-8579-6bbae54f00bb">

### Add Item to a Sale

<img width="1440" alt="Screenshot 2023-11-18 at 18 58 05" src="https://github.com/RubenChirino/clothes-store/assets/52714843/7abcc438-bdb7-4f83-942f-fc62ce078c6d">

### Endpoints

| Method   | URL                                      | Description                                      |
| -------- | ---------------------------------------- | ------------------------------------------------ |
| `GET`    | `/api/sales/all`                         | Retrieves a list of all sales                    |
| `GET`    | `/api/sales`                             | Retrieves a paginated list of sales              |
| `GET`    | `/api/sales/:id`                         | Retrieves details of a specific sale by its ID   |
| `POST`   | `/api/sales/cash`                        | Creates a new cash sale                           |
| `POST`   | `/api/sales/card`                        | Creates a new card sale                           |
| `POST`   | `/api/sales/:id/items`                   | Adds a new item to a specific sale               |
| `PUT`    | `/api/sales/:sale_id/items/:item_id`     | Modifies an existing item in a sale              |
| `DELETE` | `/api/sales/:sale_id/items/:item_id`     | Deletes a specific item from a sale              |
| `POST`   | `/api/sales/cash` (with business)        | Creates a new cash sale with business details    |
| `POST`   | `/api/sales/card` (with business)        | Creates a new card sale with business details    |
