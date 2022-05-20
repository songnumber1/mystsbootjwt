DELETE FROM menucategory;
DELETE FROM menuonedept;
DELETE FROM menutwodept WHERE menuTwoDeptid = 32;



SELECT 'Xamarin', '',  1, '', 8, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid FROM menutwodept WHERE menuTwoDeptTitle = 'Software';

COMMIT;
SELECT * FROM menucategory;
SELECT * FROM menuonedept;
SELECT * FROM menutwodept;
SELECT * FROM menuthreedept;
SELECT * FROM user_role;
SELECT * FROM user;

DELETE FROM user WHERE username != 'admin';

UPDATE user SET roles = 'ROLE_ADMIN', active = TRUE WHERE username = 'song';

 DROP TABLE menucategory;
 DROP TABLE menuonedept;
 DROP TABLE menutwodept;
 DROP TABLE menuthreedept;

DELETE FROM menucategory WHERE categoryid = 4;


-- 사용자 권한 --
INSERT INTO user_role (role, securityrole, roleno) VALUES ('Admin', 'ROLE_ADMIN', 0);
INSERT INTO user_role (role, securityrole, roleno) VALUES ('Manager', 'ROLE_MANAGER', 1);
INSERT INTO user_role (role, securityrole, roleno) VALUES ('User', 'ROLE_USER', 2);
INSERT INTO user_role (role, securityrole, roleno) VALUES ('Guest', 'ROLE_GUEST', 3);

-- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- 카테고리 --
INSERT INTO menucategory (categorySort, categoryTitle, active, userRoleid) SELECT 0, 'PROFILE', 1, userroleid FROM user_role WHERE UPPER(role) = 'ADMIN';
INSERT INTO menucategory (categorySort, categoryTitle, active, userRoleid) SELECT 1, 'DATA', 1, userroleid FROM user_role WHERE UPPER(role) = 'ADMIN';
INSERT INTO menucategory (categorySort, categoryTitle, active, userRoleid) SELECT 2, 'SUMMARY', 1, userroleid FROM user_role WHERE UPPER(role) = 'ADMIN';

-- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- ONE DEPT (PROFILE)--
INSERT INTO menuonedept (menuOneDeptTitle, menuOneDeptUrl, active, menuOneDeptIcon, menuOneDeptSort, categoryId, userRoleid) SELECT 'Personal', '',  1, 'fas fa-user', 0, categoryId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menucategory WHERE categoryTitle = 'PROFILE';

-- ONE DEPT (DATA)--
INSERT INTO menuonedept (menuOneDeptTitle, menuOneDeptUrl, active, menuOneDeptIcon, menuOneDeptSort, categoryId, userRoleid) SELECT 'Program', '',  1, 'fas fa-th-list', 0, categoryId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menucategory WHERE categoryTitle = 'DATA';
INSERT INTO menuonedept (menuOneDeptTitle, menuOneDeptUrl, active, menuOneDeptIcon, menuOneDeptSort, categoryId, userRoleid) SELECT 'Storage', '/jwtauth/storage',  1, 'fas fa-database', 1, categoryId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menucategory WHERE categoryTitle = 'DATA';

-- ONE DEPT (SUMMARY)--
INSERT INTO menuonedept (menuOneDeptTitle, menuOneDeptUrl, active, menuOneDeptIcon, menuOneDeptSort, categoryId, userRoleid) SELECT 'Oddment', '/jwtauth/summary/oddment',  1, 'fa fa-bars', 0, categoryId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menucategory WHERE categoryTitle = 'SUMMARY';  
INSERT INTO menuonedept (menuOneDeptTitle, menuOneDeptUrl, active, menuOneDeptIcon, menuOneDeptSort, categoryId, userRoleid) SELECT 'Git', '/jwtauth/summary/git',  1, 'fa fa-code', 1, categoryId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menucategory WHERE categoryTitle = 'SUMMARY';  
INSERT INTO menuonedept (menuOneDeptTitle, menuOneDeptUrl, active, menuOneDeptIcon, menuOneDeptSort, categoryId, userRoleid) SELECT 'Windows', '/jwtauth/summary/windows',  1, 'fas fa-desktop', 2, categoryId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menucategory WHERE categoryTitle = 'SUMMARY';
INSERT INTO menuonedept (menuOneDeptTitle, menuOneDeptUrl, active, menuOneDeptIcon, menuOneDeptSort, categoryId, userRoleid) SELECT 'Movie', '/jwtauth/summary/movie',  1, 'fas fa-film', 3, categoryId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menucategory WHERE categoryTitle = 'SUMMARY';

-- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- TWO DEPT (PROFILE)--
INSERT INTO menutwodept (menuTwoDeptTitle, menuTwoDeptUrl, active, menuTwoDeptIcon, menuTwoDeptSort, menuonedeptId, userRoleid) SELECT 'Info', '/jwtauth/profile/',  1, '', 0, menuonedeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menuonedept WHERE menuOneDeptTitle = 'Personal';

-- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- TWO DEPT (DATA)--
INSERT INTO menutwodept (menuTwoDeptTitle, menuTwoDeptUrl, active, menuTwoDeptIcon, menuTwoDeptSort, menuonedeptId, userRoleid) SELECT 'Database', '',  1, '', 0, menuonedeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menuonedept WHERE menuOneDeptTitle = 'Program';
INSERT INTO menutwodept (menuTwoDeptTitle, menuTwoDeptUrl, active, menuTwoDeptIcon, menuTwoDeptSort, menuonedeptId, userRoleid) SELECT 'Software', '',  1, '', 1, menuonedeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menuonedept WHERE menuOneDeptTitle = 'Program';

-- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

-- THREE DEPT (DATA)--
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'MS-Sql', '/jwtauth/software/MS-Sql',  1, '', 0, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Database';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'Oracle', '/jwtauth/software/Oracle',  1, '', 1, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Database';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'Postgre', '/jwtauth/software/Postgre',  1, '', 2, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Database';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'Android', '/jwtauth/software/Android',  1, '', 0, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Software';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'CSharp', '/jwtauth/software/CSharp',  1, '', 1, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Software';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'Java', '/jwtauth/software/Java',  1, '', 2, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Software';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'JavaScript', '/jwtauth/software/JavaScript',  1, '', 3, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Software';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'MFC', '/jwtauth/software/MFC',  1, '', 4, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Software';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'React', '/jwtauth/software/React',  1, '', 5, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Software';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'Vue', '/jwtauth/software/Vue',  1, '', 6, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Software';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'WPF', '/jwtauth/software/WPF',  1, '', 7, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Software';
INSERT INTO menuthreedept (menuThreeDeptTitle, menuThreeDeptUrl, active, menuThreeDeptIcon, menuThreeDeptSort, menuTwodeptId, userRoleid) SELECT 'Xamarin', '/jwtauth/software/Xamarin',  1, '', 8, menuTwodeptId, (SELECT userroleid FROM user_role WHERE UPPER(role) = 'ADMIN') AS userroleid  FROM menutwodept WHERE menuTwoDeptTitle = 'Software';