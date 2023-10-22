INSERT INTO payment_history(payment_gateway_type,number,amount,payment_date) VALUES('APPLE_PAY','001',1000,'2023-01-01');
INSERT INTO payment_history(payment_gateway_type,number,amount,payment_date) VALUES('GOOGLE_PAY','002',1000,'2023-01-01');

INSERT INTO apple_payment_history(number,promotion_code,shipping_type) VALUES('001','p001','delivery');
INSERT INTO google_payment_history(number,promotion_code) VALUES('002','p002');
