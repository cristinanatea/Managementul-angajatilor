INSERT INTO contracts (contractId, contractName, dailyNorm, yearlyPaidDays, yearlySickDays) VALUES(1, "Standart", 8, 21,30);
INSERT INTO roles (roleId, roleName,  fullAdministration, manageHolidays, manageLeaveRequests, manageProject, mangeUsers, viewAllProjects) VALUES(1, "supervisor", true, true, true, true, true, true);
INSERT INTO users (userId, availablePaidDays, availableSickDays, contractId, email, firstName, isActive, lastName, password, roleId) VALUES (0,0,0,1,"admin@admin.com", "Admin", true, "Admin", "admin", 1);

