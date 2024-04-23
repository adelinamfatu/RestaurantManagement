# Restaurant Order Management Application
The proposed application offers an innovative solution for managing orders in a restaurant, from the perspective of a waiter. It provides a well-designed set of functionalities aimed at simplifying the order-taking process, optimizing interaction with customers, and offering a clear overview of restaurant activities.

# Technologies Used:
- Java Programming Language: Provides portability, security, and scalability. Known for robust memory management and compatibility with libraries and frameworks.
- IntelliJ IDEA: Integrated development environment (IDE) offering intelligent code completion, debugging, and project management tools. Facilitates Java development and reduces errors.
- Swing GUI Framework: Used for creating the application's graphical user interface (UI). Offers a rich set of components for intuitive user interaction.
- SQLite Database: Lightweight and embeddable database for storing and managing restaurant order and product information. Efficient and portable, suitable for Java applications.

# Product Management
- Waiters can select available product categories from the menu.
- Upon selecting a category, the associated products are displayed, queried from the database.
- This functionality simplifies navigation and allows waiters to quickly find desired products to add to customers' orders.
- Simply tapping on a product from a category adds it to the current customer's order.
- This process is efficient and intuitive, allowing waiters to manage orders with ease.
![image](https://github.com/adelinamfatu/RestaurantManagement/assets/102324614/1c2929d4-7737-42ff-ab5c-624b7aad9b5f)

# Menu Management
- The application provides a dedicated page for adding new products to existing categories.
- This menu management tool allows the restaurant to update and expand its product offerings flexibly.
- Required data for a new product includes name, description, unit price, weight, and category.
- The system performs checks on each field and throws exceptions if the name contains digits, the price and weight are not numbers or not positive values, and the weight must fall within certain ranges.
![image](https://github.com/adelinamfatu/RestaurantManagement/assets/102324614/a014060a-b261-4f8f-83d3-d8fda183571e)
![image](https://github.com/adelinamfatu/RestaurantManagement/assets/102324614/faab698c-e3dc-4058-a090-0141034ef4cf)

# Table Management
- On the main page of the application, waiters can choose the table to which they are adding products to the order.
- Details about the table, such as the number of seats, availability, and occupancy status, are displayed.
- If the table is occupied, the added products will be included in that table's order, and the total order amount will be incremented.
- The waiter is asked if they want to finish the order for the respective table, to which they can refuse and add more products. If accepted, the table status changes.
![image](https://github.com/adelinamfatu/RestaurantManagement/assets/102324614/93e2efc1-1846-47fc-a616-dc41ea38de2a)

# Order List
- The right-side list displays newly added products and their quantities.
- Waiters can delete items from the list by tapping on the product and confirming the deletion.

# Statistics Generation
- The application offers a special section for generating relevant statistics.
- This includes information about the best-selling product, revenue, and orders for the current week.
- These data provide insight into the restaurant's performance and can help in making strategic decisions.
![image](https://github.com/adelinamfatu/RestaurantManagement/assets/102324614/17f28c32-e8bd-412e-b8d7-35458448f76b)
