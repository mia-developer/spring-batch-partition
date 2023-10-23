# spring-batch-partition
Welcome to the Spring Batch project! This project demonstrates an efficient way to process data using Spring Batch Partitioner. 
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


<br>
