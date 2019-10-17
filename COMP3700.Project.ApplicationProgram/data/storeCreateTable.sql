CREATE TABLE Customer (
	CustomerID INT NOT NULL UNIQUE PRIMARY KEY,
	Name TEXT,
	Phone INT,
	Address TEXT
);

CREATE TABLE Products (
	ProductID INT NOT NULL UNIQUE PRIMARY KEY,
	Name TEXT,
	Price NUMERIC,
	Quantity NUMERIC
);

CREATE TABLE Purchase (
	PurchaseID INT NOT NULL UNIQUE PRIMARY KEY,
	CustomerID INT,
	ProductID INT,
    Price NUMERIC,
	Quantity NUMERIC,
    Cost NUMERIC,
    Tax NUMERIC,
    Total NUMERIC,
    Date TEXT
);