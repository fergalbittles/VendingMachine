# VendingMachine

This Java application was built during my first year of university for the module **Object Oriented Programming**.

The system allows a user to interact with a console-based vending machine by inserting coins and purchasing items.

The user can also restock and add new items to the vending machine by entering `-1` on the main menu, followed by the password `snacks`, as shown below:

```
Vending Machine Menu
++++++++++++++++++++

1. List All Items
2. Insert Coins
3. Make Purchase
4. Quit

Enter option number: -1

Enter maintenance password: snacks
```

If the application is terminated from the main menu using the `Quit` option, the current state of the vending machine will be exported to a CSV file. Next time the application is run, this data will be loaded and the vending machine will be restored to its previous state.

Run `VendingApp.java` to get started.

# What I Learned

* OOP concepts (abstraction, encapsulation, polymorphism, inheritance).
* Exception handling & validating user input.
* Exporting to & importing from a CSV file.
