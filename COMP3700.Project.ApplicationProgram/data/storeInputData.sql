INSERT INTO Customer(CustomerID, Name, Phone, Address)
VALUES(100000, 'James Ken', 3342345678, '1000 South Street, Auburn AL 36832'),
           (100001, 'Kelie B', 3342331108, '1020 North Street, Auburn AL 36832'),
           (100002, 'Ben K', 3343450987, '1220 North Street, Auburn AL 36832'),
           (100003, 'Alice Dali', 3569213456, '1210 College Street, Auburn AL 36832'),
           (100004, 'Kathy J', 4289827868, '320 West Street, Auburn AL 36832');
	  
INSERT INTO Product(ProductId, Name, Price, Quantity)
VALUES(100000000, 'organic apple', 3.89, 1),
           (100000001, 'platic bag', 3.02, 1),
           (100000002, 'brocolli', 1.98, 1),
           (100000003, '2% milk', 2.97, 1),
           (100000004, 'organic egg 12 count', 3.89, 1);
		   
INSERT INTO Purchase(PurchaseID, CustomerID, ProductID, Price, Quantity, Cost, Tax, Total, Date)
VALUES(1, 100000, 100000000, 3.89, 1, 3.89, 0.35, 4.24, "Mon Oct 14 18:22:20 CDT 2019"),
           (2, 100000, 100000001, 3.02, 1, 3.02, 0.27, 3.29, "Mon Oct 14 18:23:20 CDT 2019"),
		   (3, 100000, 100000002, 1.98, 1, 1.98, 0.18, 2.16, "Mon Oct 14 18:23:25 CDT 2019"),
		   (4, 100001, 100000000, 3.89, 1, 3.89, 0.35, 4.24, "Mon Oct 14 18:23:40 CDT 2019"),
		   (5, 100001, 100000002, 1.98, 1, 1.98, 0.18, 2.16, "Mon Oct 14 18:23:45 CDT 2019"),
		   (6, 100002, 100000003, 2.97, 1, 2.97, 0.27, 3.24, "Mon Oct 14 18:24:20 CDT 2019"),
		   (7, 100003, 100000001, 3.02, 1, 3.02, 0.27, 3.29, "Mon Oct 14 18:25:20 CDT 2019"),
		   (8, 100003, 100000002, 1.98, 1, 1.98, 0.18, 2.16, "Mon Oct 14 18:26:20 CDT 2019"),
		   (9, 100003, 100000003, 2.97, 1, 2.97, 0.27, 3.24, "Mon Oct 14 18:27:20 CDT 2019"),
		   (10, 100003, 100000004, 3.89, 1, 3.89, 0.35, 4.24, "Mon Oct 14 18:28:20 CDT 2019"),
		   (11, 100004, 100000001, 3.02, 1, 3.02, 0.27, 3.29, "Mon Oct 14 18:29:20 CDT 2019"),
		   (12, 100004, 100000002, 1.98, 1, 1.98, 0.18, 2.16, "Mon Oct 14 18:30:20 CDT 2019");
		   