db.createUser(
    {
        user: "admin_nexos",
        pwd: "0000",
        roles: [
            {
                role: "readWrite",
                db: "db_nexos"
            }
        ]
    }
);