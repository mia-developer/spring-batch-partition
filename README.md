# spring-batch-partition
Welcome to the Spring Batch project! This project demonstrates an efficient way to process data using Spring Batch Partitioner. For more detailed information about this GitHub repository, please refer to [my post](https://medium.com/@office.yeon/spring-batch-type-based-data-processing-with-partitioning-4f26c24a140c) 

<br>

## Technologies Used

- Java Development Kit (JDK) 17
- Gradle 7.6
- Spring Batch 3.0.1
- QueryDSL 5.0.0
- H2 2.1.214
  
<br>

## Workflow
![image](https://github.com/mia-developer/spring-batch-partition/assets/131224717/259fef05-6f4f-4f6f-b378-e4e50c9b515f)

We'll leverage Spring Batch partitioning to segment data based on its type. When Spring Batch partitioning operates with a single thread in a local environment, we can utilize it as if it were a 'for-each' loop. This approach allows the code to remain consistent while being tailored to handle different types.

<br>

## ERD
![image](https://github.com/mia-developer/spring-batch-partition/assets/131224717/4db0cf40-adb2-4d7b-b669-55a703be80ac)

The `payment_history` table served to manage shared fields across all payment gateways, abstracting common elements. Additionally, each payment gateway had its dedicated table to store specific fields.
The related table can be discerned by referencing the `payment_gateway_type` column.

<br>

## Entity

<img width="600" alt="entity" src="https://github.com/mia-developer/spring-batch-partition/assets/131224717/96b255b9-5c00-44a7-83c2-f3088a79db3b">

The `BasePaymentGatewayHistoryEntity` serves as the parent class for each payment gateway history entity, providing a set of common fields for its child entities. Each payment gateway history entity is designed to accommodate fields specific to individual payment gateways.

<br>

## Getting Started

1. Build the project using Gradle:

```bash
./gradlew build
```

2. Run the Spring Boot application:

```bash
./gradlew bootRun
```

<br>
