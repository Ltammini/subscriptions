# subscriptions 
subscriptions is a java spring boot microservice, provides end points to create and manage Filmland subscription categories.

There are four end point:
 1. login: authenticate user and provide JWT token based on userId and password
 2. category: provides list of available categories and subscribed categories
 3. subscribe: where customer can subscribe a specific Filmland content ctogory from available categories
 4. share: to share a subscribe category to another existing customer 

# Sequesnce diagram:
![image](https://user-images.githubusercontent.com/54528349/120661902-f8945880-c488-11eb-8f87-2700610a4764.png)

# Data base desing
Used H2 in-memory data bese
![image](https://user-images.githubusercontent.com/54528349/120657694-0516b200-c485-11eb-88c6-4f8e6468ecb8.png)
