use jwtauth;

SELECT * FROM USER;

DELETE FROM user;


CREATE DATABASE mendixhospital;

use mendixhospital;

UPDATE user SET roles = 'ROLE_ADMIN', Active =TRUE WHERE username = 'song min woo';
UPDATE user SET roles = 'ROLE_USER', Active =true;

INSERT INTO course SELECT '1', 'title1', 'description1', 1, 1.0;

SELECT *
FROM user;



jwtauth
SELECT * FROM menucategory;
SELECT * FROM menuonedept;
SELECT * FROM menutwodept;

DELETE FROM menuonedept WHERE categoryid = 2;

INSERT INTO menutwodept (menuTwotitle, menuonedeptid, isActive) VALUES ('Sub-2', 3, 1);

COMMIT;