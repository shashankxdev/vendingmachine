# Vending Machine - Shashank Shekhar

## Design

### default:

1. vending machine should have products initially
2. vending machine should have coin(s) initially

### supplier:

1. should be able to view product inventory
2. should be able to view coin inventory
3. should be able to add products to inventory
4. should be able to add coins to inventory

### for all users:

1. should display all products and their costs
2. should take input from user in the corresponding denominations present
3. should show total amount entered by user
4. should allow user to select products
5. should show user total cost of their selected product
6. should return the change amount to the user, if applicable

### exception cases:

1. should notify if selected product(s) is out of stock
2. should notify if amount entered is insufficient for the selected product(s)
3. should notify if machine doesn't have sufficient change

### for all the exception cases:

stop the process and refund the amount entered by the user

## Instructions on Running the Program

### if running using jar

use the following command in cli

```bash
java -cp vendingmachine.jar dev.shashank.VendingMachineApp
```

### if running using IDE

run the main function in

```
dev.shashank.VendingMachineApp.java
```

## Instructions for Program Input

### input - UserType

select either "User" or "Supplier", others invalid

### input - Coins

the coin's quantity should be entered in the form of an array in the following COMMA( , ) SEPARATED order: 1P,5P,10P,25P,50P,100P
<br>
for example, if a user wants to enter 5x 10P, 3x 50P and 7x 100P, then user should enter

```
0,0,5,0,3,7
```

### input - Products

the products should be entered along with their quantity in the following COMMA( , ) SEPARATED order: QUANTITYxPRODUCT,QUANTITYxPRODUCT
<br>
for example, if a user wants to purchase 7x COKE and 5x SPRITE, then user should enter

```
7xCOKE,5xSPRITE
```

## Result

rest of the program is self explanatory

### for user

change will be return on successful transaction of products

### for supplier

inventory will be updated for products and coins

## Tests

the tests are under

```
dev.shashank.test.VendingMachineTest
```

### testInsertCoins

insert coins and check if the total balance is reflected correctly

### testSelectProducts

select products and check if total amount of products is reflected correclty

### testOutOfStockException

to check if OutOfStockException is thrown

### testOutOfBalanceException

to check if OutOfBalanceException is thrown

### testOutOfChangeException

to check if OutOfChangeException is thrown

### testCalculateChange

to check if change is return correctly after purchasing product(s)

### testInventoryBalance

to check remaining inventory balance for coins and products after a user purchases few products

### testAddingProductsToInventory

by the Supplier - to add few products to inventory and check if its updated properly

### testAddingCoinsToInventory

by the Supplier - to add few coins of multiple denomination to inventory and check if its updated properly

### testProductWithExactPrice

purchase products with exact amount so that change to be returned is 0.

### end2endTest

end to end test - performing:

1. update of inventory - coins and products
2. adding coins
3. selecting products
4. purchasing products
5. calculate and verify change
6. verify product inventory
7. verify coin inventory
